export enum Color {
    Yellow = 'yellow',
    Blue = 'blue',
    White = 'white',
    Green = 'green',
    Red = 'red',
}

export interface Card {
    value: number;
    color: Color;
    instance: number;
}

export type Discard = {
    Color: Card[];
};
