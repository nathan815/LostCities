import { Discard } from './card';

export class GameState {
    id: number | null = null;
    discard: Discard | null = null;
    player1: any = null;
    player2: any = null;
}
