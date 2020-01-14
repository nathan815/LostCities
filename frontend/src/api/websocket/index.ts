import stompClient, { defaultStompConfig } from '@/api/websocket/stompClient';
import { RxStompConfig } from '@stomp/rx-stomp';

export function connect(config?: RxStompConfig) {
    stompClient.configure({ ...defaultStompConfig, ...config });
    stompClient.activate();
}

export function disconnect() {
    stompClient.deactivate();
}
