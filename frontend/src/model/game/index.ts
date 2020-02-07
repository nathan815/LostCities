import { Card, Discard, instantiateColorCardListObjects } from '@/model/game/card';
import { Player } from '@/model/game/player';
import { Move, TurnStage } from '@/model/game/moves';

export class Board {
    discard: Discard = {};
    constructor(discard: Discard) {
        this.discard = discard;
    }
    public static fromDto(data: { discard: Discard }): Board {
        return new Board(instantiateColorCardListObjects(data.discard));
    }
}

export enum GameStatus {
    Unknown = 'Unknown',
    New = 'New',
    ReadyToStart = 'ReadyToStart',
    Started = 'Started',
    Ended = 'Ended',
}

export class GameState {
    public id: number = 0;
    public status: GameStatus = GameStatus.Unknown;
    public deckSize: number = 0;
    public turnStage: TurnStage = TurnStage.PreStart;
    private currentTurnPlayerId: number = 0;

    public board: Board = {
        discard: {},
    };
    public players: Player[] = [];
    public moves: Move[] = [];

    public hand: Card[] = [];

    constructor(data?: any) {
        if (data) {
            this.id = data.id;
            this.status = data.status;
            this.deckSize = data.deckSize;
            this.turnStage = data.turnStage as TurnStage;
            this.currentTurnPlayerId = data.currentTurnPlayerId;

            this.board = Board.fromDto(data.board);
            this.players = data.players.map(player => Player.fromDto(player));
            this.moves = data.moves.map(move => Move.fromDto(move));
            this.hand = data.hand.map(Card.fromDto).sort(Card.compare);
        }
    }

    get isNew() {
        return this.status === GameStatus.New;
    }

    get isReadyToStart() {
        return this.status === GameStatus.ReadyToStart;
    }

    get isStarted() {
        return this.status === GameStatus.Started;
    }

    get currentTurnPlayer(): Player | undefined {
        return this.findPlayerById(this.currentTurnPlayerId);
    }

    findPlayerById(id: number): Player | undefined {
        return this.players.find(player => player.id == id);
    }
}
