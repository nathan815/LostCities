import { AxiosResponse } from 'axios';
import { map } from 'rxjs/operators';
import apiClient from './client';
import stompClient from '@/api/websocket/stompClient';
import { parseJson } from '@/api/websocket/helpers';
import { Move } from '@/model/game/moves';
import { GameState } from '@/model/game';

export const errorObservable = () => stompClient.watch(`/user/queue/game/errors`).pipe(parseJson);

const toGame = map((state: any) => {
    return new GameState(state);
});

export const gameStateObservable = gameId =>
    stompClient.watch(`/topic/game/${gameId}`).pipe(parseJson, toGame);

export const userGameStateObservable = gameId =>
    stompClient.watch(`/user/topic/game/${gameId}`).pipe(parseJson, toGame);

const gamePath = (gameId, path) => `/app/game/${gameId}/${path}`;

export function requestGameState(gameId) {
    stompClient.publish({ destination: gamePath(gameId, 'requestState'), body: '' });
}

export function makeMove(gameId: number, move: Move) {
    stompClient.publish({
        destination: gamePath(gameId, 'move'),
        body: JSON.stringify(move.toDto()),
    });
}

export function join(gameId: number): Promise<AxiosResponse> {
    return apiClient.post(`/games/${gameId}/join`);
}
