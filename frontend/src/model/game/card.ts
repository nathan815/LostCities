export enum Color {
    YELLOW = 'yellow',
    BLUE = 'blue',
    WHITE = 'white',
    GREEN = 'green',
    RED = 'red',
}

export function getColorEnumValues(): Color[] {
    return Object.keys(Color).map(key => Color[key]);
}

export class Card {
    value: number;
    color: Color;
    instance: number;

    constructor(color: Color, value: number, instance?: number) {
        this.value = value;
        this.color = color;
        this.instance = instance || 0;
    }

    get isWager() {
        return this.value === 0;
    }

    public toString() {
        return `${this.color}_${this.value}_${this.instance}`;
    }

    public static fromObject(data: { color: string; value: number; instance: number }) {
        return new Card(Color[data.color], data.value, data.instance);
    }

    public static fromString(str: string): Card {
        const parts = str.split('_');
        return new Card(Color[parts[0]], parseInt(parts[1]), parseInt(parts[2]));
    }

    /** Compares two cards based on color, then value */
    public static compare(a: Card, b: Card) {
        const stringCompare = a.color.toString().localeCompare(b.color.toString());
        const valueCompare = a.value - b.value;
        return stringCompare || valueCompare;
    }
}

export type Discard = {
    [key in Color]?: Card[];
};

export type CardsInPlay = {
    [key in Color]?: Card[];
};
