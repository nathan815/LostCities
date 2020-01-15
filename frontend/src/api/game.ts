import stompClient from '@/api/websocket/stompClient';
import { toJson } from '@/api/websocket/helpers';
import { Observable } from 'rxjs';
import { IMessage } from '@stomp/stompjs';
import { GameData } from '@/store/modules/game';

export const gameStateObservable = gameId =>
    stompClient.watch(`/topic/game/${gameId}`).pipe(toJson);

export const testObservable: Observable<IMessage> = stompClient.watch('/topic/test').pipe(toJson);

export async function requestInitialGameData(id): Promise<GameData> {
    return new Promise(resolve => {
        const subscription = stompClient
            .watch(`/app/game/${id}`)
            .pipe(toJson)
            .subscribe(data => {
                resolve(data);
                subscription.unsubscribe(); // Only need this subscription to initially request game state
            });
    });
}

export function sendMessage(msg: string) {
    stompClient.publish({ destination: '/app/upper', body: JSON.stringify({ msg }) });
}
