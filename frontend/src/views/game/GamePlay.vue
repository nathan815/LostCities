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

@Component({
    components: { Hand, CardsInPlayView, BoardView, Deck },
})
export default class GamePlay extends Vue {
    isLoading: boolean = true;
    isLoaded: boolean = false;
    isJoinInProgress: boolean = false;
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

    get status() {
        return this.gameState.status;
    }

    get isReadyToStart() {
        return this.status === GameStatus.ReadyToStart;
    }

    get isWaitingForPlayer() {
        return this.status === GameStatus.New;
    }

    get isMyGame() {
        return !!this.player;
    }

    get player(): Player | undefined {
        return this.gameState.players.find(
            player => auth.currentUser && player.name === auth.currentUser.username
        );
    }

    get opponent(): Player | undefined {
        if (!auth.currentUser || !this.player) return undefined;
        return this.gameState.players.find(
            player => auth.currentUser && player.name !== auth.currentUser.username
        );
    }

    get topPlayer(): Player | undefined {
        return this.isMyGame ? this.opponent : this.gameState.players[0];
    }

    get topPlayerDesc() {
        if (this.isMyGame) return 'Them';
        else if (this.topPlayer) return this.topPlayer.name;
        else return 'Player 1';
    }

    get bottomPlayer(): Player | undefined {
        return this.isMyGame ? this.player : this.gameState.players[1];
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
        <b-alert :show="error" variant="warning">
            <b>Error:</b>
            {{ error }}
        </b-alert>

        <div v-if="isLoading" class="loading">
            <b-spinner variant="primary" />
            Fetching game...
        </div>

        <b-row v-if="isLoaded">
            <b-col sm="12" md="9" lg="9">
                <b-alert :show="isWaitingForPlayer" variant="dark" class="pre-start-status-box">
                    <span>
                        <i class="fas fa-clock" />
                        Waiting for second player to join
                    </span>
                    <b-button
                        v-if="!isMyGame"
                        :disabled="isJoinInProgress"
                        variant="success"
                        size="md"
                        @click="join"
                    >
                        <i class="fas fa-plus-circle" />
                        {{ isJoinInProgress ? 'Joining...' : 'Join' }}
                    </b-button>
                </b-alert>
                <b-alert :show="isReadyToStart" variant="dark" class="pre-start-status-box">
                    Ready to start?
                    <b-button v-if="isMyGame" variant="primary">Start</b-button>
                </b-alert>
                <b-row class="cards-in-play-top">
                    <b-col cols="2" class="p-2">
                        <div class="player-info top">
                            <span class="description">{{ topPlayerDesc }}</span>
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
                    <b-card class="status">
                        <b-card-text class="text-italic">
                            Player 2's turn
                        </b-card-text>
                        <b-card-text>
                            <b-button variant="primary" size="sm">Nudge</b-button>
                            <b-dropdown id="dropdown-1" size="sm" class="m-md-2" variant="light">
                                <template v-slot:button-content>
                                    <i class="fas fa-cog" />
                                </template>
                                <b-dropdown-item @click="alwaysShowHand = !alwaysShowHand">
                                    <i class="fas" :class="{ 'fa-check': alwaysShowHand }" />
                                    Keep hand visible when scrolling
                                </b-dropdown-item>
                            </b-dropdown>
                        </b-card-text>
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

<style lang="scss">
.player-info {
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    align-items: center;

    .description {
        text-align: center;
        font-size: 13px;
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
.pre-start-status-box {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
}
</style>
