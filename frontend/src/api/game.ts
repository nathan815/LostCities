import stompClient from '@/api/websocket/stompClient';
import { toJson } from '@/api/websocket/helpers';
import { Move } from '@/store/modules/game/moves';

export const errorObservable = () => stompClient.watch(`/user/queue/game/errors`).pipe(toJson);

export const gameDataObservable = gameId => stompClient.watch(`/topic/game/${gameId}`).pipe(toJson);

export const userGameDataObservable = gameId =>
    stompClient.watch(`/user/topic/game/${gameId}`).pipe(toJson);

export async function requestGameState(gameId) {
    stompClient.publish({ destination: `/app/game/${gameId}/requestState`, body: '' });
}

export function makeMove(gameId: number, move: Move) {
    stompClient.publish({ destination: `/app/game/${gameId}/move`, body: JSON.stringify(move) });
}
