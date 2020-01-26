import { Card, Color } from '@/model/game/card';

export enum MoveType {
    ReadyToStart,
    PlayCard,
    DiscardCard,
    DrawDeck,
    DrawDiscard,
}

export class Move {
    playerId: number;
    type: MoveType;
    card?: Card;
    color?: Color;
    constructor(playerId: number, type: MoveType, card?: Card, color?: Color) {
        this.playerId = playerId;
        this.type = type;
        this.card = card;
        this.color = color;
    }
}
