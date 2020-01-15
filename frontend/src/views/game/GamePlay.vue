<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Board from '@/views/game/Board.vue';
import CardsInPlayView from '@/views/game/CardsInPlayView.vue';
import Deck from '@/views/game/Deck.vue';
import Hand from '@/views/game/Hand.vue';
import { Card, CardsInPlay, Color, Discard, GameState } from '@/store/modules/game';
import game from '@/store/modules/game';

@Component({
    components: { Hand, CardsInPlayView, Board, Deck },
})
export default class GamePlay extends Vue {
    deckNumCards: number = 10;
    alwaysShowHand: boolean = true;

    get id(): number {
        return parseInt(this.$route.params.id);
    }

    get gameData(): GameState {
        return game.state;
    }

    mounted() {
        game.loadInitialGameData({ id: this.id });
    }

    get discard(): Discard {
        return {
            [Color.Red]: [
                new Card(2, Color.Red),
                new Card(3, Color.Red),
                new Card(7, Color.Red),
                new Card(5, Color.Red),
                new Card(7, Color.Red),
            ],
            [Color.Yellow]: [new Card(2, Color.Yellow)],
            [Color.Blue]: [new Card(3, Color.Blue), new Card(10, Color.Blue)],
            [Color.White]: [new Card(6, Color.White)],
            [Color.Green]: [new Card(7, Color.Green, 0), new Card(2, Color.Green)],
        };
    }

    get hand(): Card[] {
        return [
            new Card(0, Color.Blue, 0),
            new Card(3, Color.Red),
            new Card(10, Color.White),
            new Card(5, Color.Yellow),
            new Card(4, Color.Green),
            new Card(5, Color.Blue),
            new Card(2, Color.White),
            new Card(4, Color.Red),
        ];
    }

    get bottomInPlay(): CardsInPlay {
        return {
            [Color.Blue]: [
                new Card(0, Color.Blue, 0),
                new Card(0, Color.Blue, 1),
                new Card(2, Color.Blue),
                new Card(4, Color.Blue),
                new Card(7, Color.Blue),
                new Card(8, Color.Blue),
                new Card(9, Color.Blue),
            ],
            [Color.Yellow]: [new Card(0, Color.Yellow, 0), new Card(3, Color.Yellow)],
            [Color.White]: [new Card(0, Color.White)],
        };
    }

    get topInPlay(): CardsInPlay {
        return {
            [Color.Green]: [
                new Card(0, Color.Green, 0),
                new Card(3, Color.Green),
                new Card(5, Color.Green),
            ],
            [Color.White]: [
                new Card(2, Color.White),
                new Card(4, Color.White),
                new Card(7, Color.White),
                new Card(10, Color.White),
            ],
        };
    }
}
</script>

<template>
    <b-container class="game-play-container">
        <b-row>
            <b-col sm="12" md="9" lg="9">
                <b-row class="cards-in-play-top">
                    <b-col cols="2" class="p-2">
                        <div class="player-info top">
                            <span class="description">Them</span>
                        </div>
                    </b-col>

                    <b-col cols="10">
                        <CardsInPlayView :cards="topInPlay" :is-top="true" class="cards-in-play" />
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="2">
                        <div class="draw-pile">
                            <Deck :num-cards="deckNumCards" />
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <Board :discard="discard" />
                    </b-col>
                </b-row>

                <b-row class="cards-in-play-bottom">
                    <b-col cols="2" class="p-2">
                        <div class="player-info bottom">
                            <span class="description">You</span>
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <CardsInPlayView :cards="bottomInPlay" class="cards-in-play" />
                    </b-col>
                </b-row>
            </b-col>

            <b-col sm="12" md="3" lg="3">
                <div class="sidebar">
                    <b-card class="status">
                        <b-card-text class="text-italic">
                            <b>Player 2's</b>
                            turn
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

        <Hand :cards="hand" :fixed="alwaysShowHand" />
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
.sidebar {
    width: 100%;
    .card {
        margin-bottom: 15px;
        font-size: 95%;
    }
}
</style>
