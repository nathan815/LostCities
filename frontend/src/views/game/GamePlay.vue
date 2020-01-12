<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Board from '@/views/game/Board.vue';
import CardsInPlayView from '@/views/game/CardsInPlayView.vue';
import Deck from '@/views/game/Deck.vue';
import Hand from '@/views/game/Hand.vue';
import { Card, Color } from '@/store/modules/game';

@Component({
    components: { Hand, CardsInPlayView, Board, Deck },
})
export default class GamePlay extends Vue {
    get id() {
        return this.$route.params.id;
    }
    get hand(): Card[] {
        return [
            new Card(0, Color.Blue),
            new Card(3, Color.Red),
            new Card(10, Color.White),
            new Card(5, Color.Yellow),
            new Card(4, Color.Green),
            new Card(7, Color.Blue),
            new Card(2, Color.White),
            new Card(4, Color.Red),
        ];
    }
}
</script>

<template>
    <b-container class="game-play-container">
        <b-row>
            <b-col sm="12" md="9" lg="9">
                <b-row>
                    <b-col cols="2" class="p-2">
                        <div class="player-info top">
                            <span class="description">Them</span>
                        </div>
                    </b-col>

                    <b-col cols="10">
                        <CardsInPlayView :cards="[]" :is-opponent="true" class="cards-in-play" />
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="2">
                        <div class="draw-pile">
                            <Deck :num-cards="10" />
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <Board />
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="2" class="p-2">
                        <div class="player-info bottom">
                            <span class="description">You</span>
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <CardsInPlayView :cards="[]" class="cards-in-play" />
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

        <Hand :cards="hand" />
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
