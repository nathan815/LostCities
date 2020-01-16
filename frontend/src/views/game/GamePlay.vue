import { GameStatus } from '@/model/game';
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

@Component({
    components: { Hand, CardsInPlayView, BoardView, Deck },
})
export default class GamePlay extends Vue {
    isLoading: boolean = true;
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
        };
        const handleError = error => {
            console.log(error);
            this.error = error.error;
        };
        this.subscriptions.push(
            gameApi.errorObservable().subscribe(handleError),
            gameApi.gameStateObservable(this.id).subscribe(setGameState),
            gameApi.userGameStateObservable(this.id).subscribe(setGameState)
        );
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
        else return 'Player 2';
    }

    get bottomPlayer(): Player | undefined {
        return this.isMyGame ? this.player : this.gameState.players[1];
    }

    get bottomPlayerDesc() {
        if (this.isMyGame) return 'You';
        else if (this.bottomPlayer) return this.bottomPlayer.name;
        else return 'Player 1';
    }

    get statusText() {
        let text = 'Unknown Status';
        console.log(this.gameState.status, GameStatus.New);
        switch (this.gameState.status) {
            case GameStatus.New:
                text = 'Waiting for second player...';
                break;
        }
        return text;
    }
}
</script>

<template>
    <b-container class="game-play-container">
        <b-alert :show="error" variant="danger">
            <b>Error:</b>
            {{ error }}
        </b-alert>
        <template v-if="!isLoading">
            <b-row>
                <b-col sm="12" md="9" lg="9">
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
                                {{ statusText }}
                            </b-card-text>
                            <b-card-text>
                                <b-button variant="primary" size="sm">Nudge</b-button>
                                <b-dropdown
                                    id="dropdown-1"
                                    size="sm"
                                    class="m-md-2"
                                    variant="light"
                                >
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
        </template>

        <div v-if="isLoading" class="loading">
            <b-spinner variant="primary" />
            Loading game...
        </div>

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
</style>
