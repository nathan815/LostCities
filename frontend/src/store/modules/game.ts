export enum Color {
    Yellow = 'yellow',
    Blue = 'blue',
    White = 'white',
    Green = 'green',
    Red = 'red',
}

export class Card {
    value: number = 0;
    color: Color = Color.White;
    instance?: number;

    constructor(value: number, color: Color, instance?: number) {
        this.value = value;
        this.color = color;
        this.instance = instance;
    }

    get isWager() {
        return this.value === 0;
    }

    public toString() {
        return this.color + ' ' + this.value;
    }
}

export type Discard = {
    Color: Card;
};

export type CardsInPlay = {
    Color: Card[];
};
