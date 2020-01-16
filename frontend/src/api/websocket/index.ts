import stompClient, { baseStompConfig } from '@/api/websocket/stompClient';
import { RxStompConfig } from '@stomp/rx-stomp';
import { AuthInfo } from '@/store/modules/auth';

export function connect(authInfo?: AuthInfo | null) {
    const connectHeaders = {};
    if (authInfo && authInfo.token) {
        connectHeaders['Authorization'] = `Bearer ${authInfo.token}`;
    }
    const config: RxStompConfig = { connectHeaders };
    stompClient.configure({ ...baseStompConfig, ...config });
    console.log('Websocket: Connecting to STOMP');
    stompClient.activate();
}

export function disconnect() {
    if (stompClient.connected()) {
        console.log('Websocket: Disconnecting from STOMP');
        stompClient.deactivate();
    }
}
