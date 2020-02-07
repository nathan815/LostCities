import { CardsInPlay, instantiateColorCardListObjects } from '@/model/game/card';

export class Player {
    id: number;
    name: string;
    readyToStart: boolean;
    inPlay: CardsInPlay;

    constructor(id, name, readyToStart, inPlay) {
        this.id = id;
        this.name = name;
        this.readyToStart = readyToStart;
        this.inPlay = inPlay;
    }

    public static fromDto(data: any): Player {
        return new Player(
            data.id,
            data.name,
            data.readyToStart,
            instantiateColorCardListObjects(data.inPlay)
        );
    }
}
