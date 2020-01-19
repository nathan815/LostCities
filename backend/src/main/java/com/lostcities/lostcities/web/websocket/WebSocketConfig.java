package com.lostcities.lostcities.web.websocket;

import com.lostcities.lostcities.domain.user.User;
import com.lostcities.lostcities.web.security.JwtTokenHelper;
import com.lostcities.lostcities.web.security.SecurityConstants;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.lostcities.lostcities.web.security.AuthUtils.getPrincipalFromAuthentication;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String USER_HEADER = "user";
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.interceptors(new AuthenticationInterceptor());
    }

    private static class AuthenticationInterceptor implements ChannelInterceptor {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            if(accessor != null && accessor.getCommand() != null) {
                logger.debug("Auth Interceptor - STOMP Command: {}", accessor.getCommand());
                switch(accessor.getCommand()) {
                    case CONNECT:
                        onConnectSetupAuthentication(accessor);
                        break;
                    case SEND:
                        addUserHeaderToIncomingMessage(accessor);
                        break;
                }
            }
            return message;
        }

        private void onConnectSetupAuthentication(StompHeaderAccessor accessor) {
            // The 'Authorization' header is sent via STOMP headers when connection is initiated
            // Can't use normal auth flow here because browsers don't allow setting HTTP headers for websocket requests
            Object header = accessor.getFirstNativeHeader(SecurityConstants.AUTH_HEADER_NAME);
            if(header != null) {
                logger.info("Authorization header is present in STOMP CONNECT headers");
                String token = JwtTokenHelper.parseTokenFromHeader(header.toString());
                Authentication auth = JwtTokenHelper.parseAuthenticationToken(token);
                accessor.setUser(auth);
            } else {
                logger.info("No authorization header present in STOMP CONNECT headers");
            }
        }

        /**
         * Add user object to header to enable accessing it from websocket controllers via @Header param annotation
         */
        private void addUserHeaderToIncomingMessage(StompHeaderAccessor accessor) {
            Optional<User> optUser = Optional.empty();
            if(accessor.getUser() instanceof Authentication) {
                Authentication auth = (Authentication) accessor.getUser();
                optUser = getPrincipalFromAuthentication(auth);
            }
            logger.debug("Setting user header to: {}", optUser);
            accessor.setHeader(USER_HEADER, optUser);
        }
    }
}
