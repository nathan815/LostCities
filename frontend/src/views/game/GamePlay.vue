<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { Subscription } from 'rxjs';
import { AxiosError } from 'axios';

import auth from '@/store/modules/auth';
import * as gameApi from '@/api/game';
import { GameState, GameStatus } from '@/model/game';
import { Player } from '@/model/game/player';
import { GamePreferences } from '@/model/game/preferences';
import { Move, MoveType, TurnStage } from '@/model/game/moves';

import BoardView from '@/views/game/Board.vue';
import CardsInPlayView from '@/views/game/CardsInPlayView.vue';
import Deck from '@/views/game/Deck.vue';
import Hand from '@/views/game/Hand.vue';
import GamePreStartBox from '@/views/game/GamePreStartBox.vue';
import GameStatusBox from '@/views/game/GameStatusBox.vue';
import { Card, Color } from '@/model/game/card';
import MoveLog from '@/views/game/MoveLog.vue';

@Component({
    components: { MoveLog, GameStatusBox, GamePreStartBox, Hand, CardsInPlayView, BoardView, Deck },
})
export default class GamePlay extends Vue {
    isLoading: boolean = true;
    isLoaded: boolean = false;
    isJoinInProgress: boolean = false;
    isStartInProgress: boolean = false;

    subscriptions: Subscription[] = [];

    game: GameState = new GameState();
    preferences: GamePreferences = {
        handFixedPosition: true,
    };

    get id(): number {
        return parseInt(this.$route.params.id);
    }

    mounted() {
        this.setupSubscriptions();
        gameApi.requestGameState(this.id);
    }

    beforeDestroy() {
        this.subscriptions.forEach(sub => sub.unsubscribe());
    }

    private setupSubscriptions() {
        const setGameState = (game: GameState) => {
            console.log('Received Game State: ', game);
            this.game = game;
            this.isLoading = false;
            this.isLoaded = true;
        };
        const handleError = error => {
            console.log(error);
            this.isLoading = false;
            this.showErrorToast(error.error);
        };
        this.subscriptions.push(
            gameApi.errorObservable().subscribe(handleError),
            gameApi.gameStateObservable(this.id).subscribe(setGameState),
            gameApi.userGameStateObservable(this.id).subscribe(setGameState)
        );
    }

    private showErrorToast(message, title = 'Error') {
        this.$root.$bvToast.toast(message, {
            title: title,
            autoHideDelay: 5000,
            variant: 'danger',
            toaster: 'b-toaster-top-center',
        });
    }

    async join() {
        if (this.status === GameStatus.New) {
            this.isJoinInProgress = true;
            await gameApi.join(this.id).catch((err: AxiosError) => {
                if (err.response && err.response.status === 403) {
                    this.showErrorToast('You must login to join a game');
                } else {
                    this.showErrorToast(err.message);
                }
            });
            this.isJoinInProgress = false;
        }
    }

    async start() {
        this.isStartInProgress = true;
        gameApi.makeMove(
            this.id,
            new Move((this.myPlayer && this.myPlayer.id) || 0, MoveType.ReadyToStart)
        );
    }

    playCard(card: Card) {
        this.makeMove(MoveType.PlayCard, card);
    }

    discardCard(card: Card) {
        this.makeMove(MoveType.DiscardCard, card);
    }

    drawFromDeck() {
        if (this.canDrawCard) {
            this.makeMove(MoveType.DrawDeck);
        }
    }

    drawFromDiscard(color: Color) {
        if (this.canDrawCard) {
            this.makeMove(MoveType.DrawDiscard, undefined, color);
        }
    }

    makeMove(type: MoveType, card?: Card, color?: Color) {
        if (this.isMyTurn) {
            gameApi.makeMove(
                this.id,
                new Move((this.myPlayer && this.myPlayer.id) || 0, type, card, color)
            );
        }
    }

    get canDrawCard() {
        return this.isMyTurn && this.game.turnStage == TurnStage.Draw;
    }

    get status() {
        return this.game.status;
    }

    get isMyTurn() {
        return this.game.currentTurnPlayer == this.myPlayer;
    }

    get isMyGame() {
        return !!this.myPlayer;
    }

    get myPlayer(): Player | undefined {
        return this.game.players.find(
            player => auth.currentUser && player.name === auth.currentUser.username
        );
    }

    get myOpponent(): Player | undefined {
        if (!auth.currentUser || !this.myPlayer) return undefined;
        return this.game.players.find(
            player => auth.currentUser && player.name !== auth.currentUser.username
        );
    }

    get topPlayer(): Player | undefined {
        return this.isMyGame ? this.myOpponent : this.game.players[0];
    }

    get topPlayerDesc() {
        if (this.isMyGame) return 'Them';
        else if (this.topPlayer) return this.topPlayer.name;
        else return 'Player 1';
    }

    get bottomPlayer(): Player | undefined {
        return this.isMyGame ? this.myPlayer : this.game.players[1];
    }

    get bottomPlayerDesc() {
        if (this.isMyGame) return 'You';
        else if (this.bottomPlayer) return this.bottomPlayer.name;
        else return 'Player 2';
    }
}
</script>

<template>
    <b-container class="game-play-container">
        <div v-if="isLoading" class="loading">
            <b-spinner variant="light" />
            Loading game...
        </div>

        <b-row v-if="isLoaded">
            <b-col sm="12" md="9" lg="9">
                <GamePreStartBox
                    :game="game"
                    :my-player="myPlayer"
                    :is-my-game="isMyGame"
                    :is-join-in-progress="isJoinInProgress"
                    :is-start-in-progress="isStartInProgress"
                    @join="join"
                    @start="start"
                />

                <b-row class="cards-in-play-top">
                    <b-col cols="2" class="p-2">
                        <div class="player-info top">
                            <span class="description">{{ topPlayerDesc }}</span>
                            <small v-if="topPlayer && isMyGame" class="description">
                                {{ topPlayer.name }}
                            </small>
                        </div>
                    </b-col>

                    <b-col cols="10">
                        <CardsInPlayView
                            :cards="topPlayer ? topPlayer.inPlay : {}"
                            :is-top="true"
                            class="cards-in-play"
                        />
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="2">
                        <div class="draw-pile">
                            <Deck
                                :size="game.deckSize"
                                :can-draw="canDrawCard"
                                @card-click="drawFromDeck"
                            />
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <BoardView :board="game.board" :can-draw="canDrawCard" @draw="drawFromDiscard" />
                    </b-col>
                </b-row>

                <b-row class="cards-in-play-bottom">
                    <b-col cols="2" class="p-2">
                        <div class="player-info bottom">
                            <span class="description">{{ bottomPlayerDesc }}</span>
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <CardsInPlayView
                            :cards="bottomPlayer ? bottomPlayer.inPlay : {}"
                            class="cards-in-play"
                        />
                    </b-col>
                </b-row>
            </b-col>

            <b-col sm="12" md="3" lg="3">
                <div class="sidebar">
                    <GameStatusBox
                        :game="game"
                        :is-my-game="isMyGame"
                        :is-my-turn="isMyTurn"
                        :preferences="preferences"
                    />
                    <MoveLog :game="game" />
                </div>
            </b-col>
        </b-row>
        <Hand
            v-if="isMyGame"
            :game="game"
            :is-my-turn="isMyTurn"
            :fixed="preferences.handFixedPosition"
            @play="playCard"
            @discard="discardCard"
        />
    </b-container>
</template>

<style scoped lang="scss">
.player-info {
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-end;

    .description {
        text-align: center;
        font-size: 13px;
    }
    small.description {
        font-size: 11px;
        color: #ccc;
    }
}
.cards-in-play-top {
    margin-top: -20px;
}
.draw-pile {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
.loading {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.sidebar {
    width: 100%;
    .card {
        margin-bottom: 15px;
        font-size: 95%;
    }
}
</style>
