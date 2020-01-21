import { Card, Color } from '@/model/game/card';

enum MoveType {
    ReadyToStart,
    PlayCard,
    DiscardCard,
    DrawDeck,
    DrawDiscard,
}

export abstract class Move {
    type: MoveType;
    protected constructor(type: MoveType) {
        this.type = type;
    }
}

export class ReadyToStart extends Move {
    constructor() {
        super(MoveType.ReadyToStart);
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
export class DrawDiscard extends Move {
    color: Color;
    constructor(color: Color) {
        super(MoveType.DrawDiscard);
        this.color = color;
    }
}
export class DrawDeck extends Move {
    constructor() {
        super(MoveType.DrawDeck);
    }
}
