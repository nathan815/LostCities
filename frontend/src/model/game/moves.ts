import { Card, Color } from '@/model/game/card';

export enum MoveType {
    ReadyToStart = 'ReadyToStart',
    PlayCard = 'PlayCard',
    DiscardCard = 'DiscardCard',
    DrawDeck = 'DrawDeck',
    DrawDiscard = 'DrawDiscard',
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

    get description(): string {
        switch (this.type) {
            case MoveType.ReadyToStart:
                return 'is ready to start';
            case MoveType.DrawDeck:
                return `drew ${this.card} from the deck`;
            case MoveType.PlayCard:
                return `played ${this.card}`;
            case MoveType.DiscardCard:
                return `discarded ${this.card}`;
            case MoveType.DrawDiscard:
                return `drew ${this.card} from discard`;
            default:
                return '';
        }
    }

    public static fromObject(obj: any): Move {
        return new Move(obj.playerId, obj.type, obj.card && Card.fromObject(obj.card), obj.color);
    }

    public toString() {
        return `${this.playerId} ${this.type} ${this.card} ${this.color}`;
    }
}
