import { RxStomp, RxStompConfig } from '@stomp/rx-stomp';

const WS_URL = 'ws://localhost:8089/ws';

export const baseStompConfig: RxStompConfig = {
    connectHeaders: {},
    brokerURL: WS_URL,
    debug: function(str) {
        console.log('STOMP: ' + str);
    },
    reconnectDelay: 1000,
};

export function createStompClient(config = baseStompConfig) {
    const stompClient = new RxStomp();
    stompClient.configure(config);
    return stompClient;
}

export default createStompClient();
