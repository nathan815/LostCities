<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { Subscription } from 'rxjs';

import BoardView from '@/views/game/Board.vue';
import CardsInPlayView from '@/views/game/CardsInPlayView.vue';
import Deck from '@/views/game/Deck.vue';
import Hand from '@/views/game/Hand.vue';
import { GameState, GameStatus } from '@/model/game';
import { Player } from '@/model/game/player';

import * as gameApi from '@/api/game';
import auth from '@/store/modules/auth';
import { AxiosError } from 'axios';
import GamePreStartBox from '@/views/game/GamePreStartBox.vue';
import { ReadyToStart } from '@/model/game/moves';

@Component({
    components: { GamePreStartBox, Hand, CardsInPlayView, BoardView, Deck },
})
export default class GamePlay extends Vue {
    isLoading: boolean = true;
    isLoaded: boolean = false;
    isJoinInProgress: boolean = false;
    isStartInProgress: boolean = false;
    alwaysShowHand: boolean = true;
    gameState: GameState = new GameState();
    error: string | null = null;
    subscriptions: Subscription[] = [];

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
        const setGameState = (gameState: GameState) => {
            console.log('Received Game State: ', gameState);
            this.gameState = gameState;
            this.isLoading = false;
            this.isLoaded = true;
        };
        const handleError = error => {
            console.log(error);
            this.error = error.error;
            this.isLoading = false;
        };
        this.subscriptions.push(
            gameApi.errorObservable().subscribe(handleError),
            gameApi.gameStateObservable(this.id).subscribe(setGameState),
            gameApi.userGameStateObservable(this.id).subscribe(setGameState)
        );
    }

    async join() {
        if (this.status === GameStatus.New) {
            this.error = null;
            this.isJoinInProgress = true;
            await gameApi.join(this.id).catch((err: AxiosError) => {
                if (err.response && err.response.status === 403) {
                    this.error = 'You must login to join a game';
                } else {
                    this.error = err.message;
                }
            });
            this.isJoinInProgress = false;
        }
    }

    async start() {
        this.error = null;
        this.isStartInProgress = true;
        gameApi.makeMove(this.id, new ReadyToStart());
    }

    get status() {
        return this.gameState.status;
    }

    get isMyTurn() {
        return this.gameState.currentTurnPlayer == this.myPlayer;
    }

    get isMyGame() {
        return !!this.myPlayer;
    }

    get myPlayer(): Player | undefined {
        return this.gameState.players.find(
            player => auth.currentUser && player.name === auth.currentUser.username
        );
    }

    get myOpponent(): Player | undefined {
        if (!auth.currentUser || !this.myPlayer) return undefined;
        return this.gameState.players.find(
            player => auth.currentUser && player.name !== auth.currentUser.username
        );
    }

    get topPlayer(): Player | undefined {
        return this.isMyGame ? this.myOpponent : this.gameState.players[0];
    }

    get topPlayerDesc() {
        if (this.isMyGame) return 'Them';
        else if (this.topPlayer) return this.topPlayer.name;
        else return 'Player 1';
    }

    get bottomPlayer(): Player | undefined {
        return this.isMyGame ? this.myPlayer : this.gameState.players[1];
    }

    get bottomPlayerDesc() {
        if (this.isMyGame) return 'You';
        else if (this.bottomPlayer) return this.bottomPlayer.name;
        else return 'Player 2';
    }

    get currentTurnName() {
        if (this.isMyTurn) {
            return 'your';
        } else if (this.gameState.currentTurnPlayer) {
            return `${this.gameState.currentTurnPlayer.name}'s`;
        }
        return null;
    }
}
</script>

<template>
    <b-container class="game-play-container">
        <b-alert :show="error" variant="warning">
            <b>Error:</b>
            {{ error }}
        </b-alert>

        <div v-if="isLoading" class="loading">
            <b-spinner variant="primary" />
            Loading game...
        </div>

        <b-row v-if="isLoaded">
            <b-col sm="12" md="9" lg="9">
                <GamePreStartBox
                    :game="gameState"
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
                            <Deck :size="gameState.deckSize" />
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <BoardView :board="gameState.board" />
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
                    <b-card no-body class="status">
                        <b-card-header>
                            {{ topPlayer.name || '______' }} vs. {{ bottomPlayer.name || '______' }}
                        </b-card-header>
                        <b-card-body>
                            <b-card-text class="status-text">
                                <template v-if="gameState.isStarted">
                                    It is
                                    <b>{{ currentTurnName }}</b>
                                    turn
                                </template>
                                <template v-if="!gameState.isStarted">
                                    <em>Game has not yet started</em>
                                </template>
                            </b-card-text>
                            <b-card-text v-if="isMyGame">
                                <b-button
                                    v-if="gameState.isStarted"
                                    variant="primary"
                                    size="sm"
                                    :disabled="isMyTurn"
                                >
                                    Nudge
                                </b-button>
                                <b-dropdown size="sm" class="m-md-2" variant="light" right>
                                    <template v-slot:button-content>
                                        <i class="fas fa-cog" />
                                    </template>
                                    <b-dropdown-item @click="alwaysShowHand = !alwaysShowHand">
                                        <i class="fas" :class="{ 'fa-check': alwaysShowHand }" />
                                        Keep hand fixed at bottom
                                    </b-dropdown-item>
                                </b-dropdown>
                            </b-card-text>
                        </b-card-body>
                    </b-card>
                    <b-card header="Moves" class="history">
                        <b-card-text>
                            <em>No moves have been made yet</em>
                        </b-card-text>
                    </b-card>
                </div>
            </b-col>
        </b-row>
        <Hand v-if="isMyGame" :cards="gameState.hand" :fixed="alwaysShowHand" />
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
        color: #555;
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
    .card-text {
        text-align: center;
    }
    .status-text {
        font-size: 105%;
    }
}
</style>
