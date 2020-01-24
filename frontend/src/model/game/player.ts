import { CardsInPlay } from '@/model/game/card';

export interface Player {
    id: number;
    name: string;
    readyToStart: boolean;
    inPlay: CardsInPlay;
}
