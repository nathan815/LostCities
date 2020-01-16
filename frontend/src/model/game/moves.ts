import { Card, Color } from '@/model/game/card';

enum MoveType {
    PlayCard,
    DiscardCard,
    DrawFromDiscard,
    DrawFromDeck,
}

export abstract class Move {
    type: MoveType;
    protected constructor(type: MoveType) {
        this.type = type;
    }
}

export class PlayCard extends Move {
    card: Card;
    constructor(card: Card) {
        super(MoveType.PlayCard);
        this.card = card;
    }
}
export class DiscardCard extends Move {
    card: Card;
    constructor(card: Card) {
        super(MoveType.DiscardCard);
        this.card = card;
    }
}
export class DrawFromDiscard extends Move {
    color: Color;
    constructor(color: Color) {
        super(MoveType.DrawFromDiscard);
        this.color = color;
    }
}
export class DrawFromDeck extends Move {
    constructor(color: Color) {
        super(MoveType.DrawFromDeck);
    }
}
