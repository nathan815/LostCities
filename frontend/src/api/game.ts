import stompClient from '@/api/websocket/stompClient';
import { toJson } from '@/api/websocket/helpers';
import { Move } from '@/model/game/moves';

export const errorObservable = () => stompClient.watch(`/user/queue/game/errors`).pipe(toJson);

export const gameStateObservable = gameId =>
    stompClient.watch(`/topic/game/${gameId}`).pipe(toJson);

export const userGameStateObservable = gameId =>
    stompClient.watch(`/user/topic/game/${gameId}`).pipe(toJson);

const gamePath = (gameId, path) => `/app/game/${gameId}/${path}`;

export async function requestGameState(gameId) {
    stompClient.publish({ destination: gamePath(gameId, 'requestState'), body: '' });
}

export function makeMove(gameId: number, move: Move) {
    stompClient.publish({ destination: gamePath(gameId, 'move'), body: JSON.stringify(move) });
}

export function join(gameId: number) {
    stompClient.publish({ destination: gamePath(gameId, 'join'), body: '' });
}
