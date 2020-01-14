import stompClient from '@/api/websocket/stompClient';
import { toJson } from '@/api/websocket/helpers';

export const gameTopicObservable = gameId =>
    stompClient.watch(`/topic/game/${gameId}`).pipe(toJson);

export const gameAppDestinationObservable = gameId =>
    stompClient.watch(`/app/game/${gameId}`).pipe(toJson);

export const testObservable = stompClient.watch('/topic/test').pipe(toJson);

export async function getInitialGameState(id) {
    return new Promise(resolve => {
        gameAppDestinationObservable(id).subscribe(data => resolve(data));
    });
}

export function sendMessage(msg: string) {
    stompClient.publish({ destination: '/app/upper', body: JSON.stringify({ msg }) });
}
