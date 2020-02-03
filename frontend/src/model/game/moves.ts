import { Card, Color } from '@/model/game/card';

export enum MoveType {
    ReadyToStart = 'ReadyToStart',
    PlayCard = 'PlayCard',
    DiscardCard = 'DiscardCard',
    DrawDeck = 'DrawDeck',
    DrawDiscard = 'DrawDiscard',
}

export interface MoveDto {
    playerId: number;
    type: string;
    card?: string;
    color?: string;
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

    public toDto(): MoveDto {
        return {
            playerId: this.playerId,
            type: this.type,
            card: this.card ? this.card.toString() : undefined,
            color: this.color ? this.color.toString() : undefined,
        };
    }

    public toString() {
        return `${this.playerId} ${this.type} ${this.card} ${this.color}`;
    }

    public static fromDto(obj: MoveDto): Move {
        return new Move(
            obj.playerId,
            MoveType[obj.type],
            obj.card ? Card.fromString(obj.card) : undefined,
            obj.color ? Color[obj.color] : undefined
        );
    }
}
