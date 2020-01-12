<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Board from '@/views/game/Board.vue';
import CardsInPlay from '@/views/game/CardsInPlay.vue';
import Deck from '@/views/game/Deck.vue';

@Component({
    components: { CardsInPlay, Board, Deck },
})
export default class GamePlay extends Vue {
    get id() {
        return this.$route.params.id;
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
                            <span class="text">Enemy</span>
                            <div class="line"></div>
                        </div>
                    </b-col>

                    <b-col cols="10">
                        <CardsInPlay :cards="[]" :is-opponent="true" class="cards-in-play" />
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
                        <div class="player-info bottom active">
                            <span class="text">You</span>
                            <div class="line"></div>
                        </div>
                    </b-col>
                    <b-col cols="10">
                        <CardsInPlay :cards="[]" class="cards-in-play" />
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
    </b-container>
</template>

<style lang="scss">
.player-info {
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    align-items: center;

    .line {
        border: 1px solid #ccc;
        border-right: none;
        width: 10px;
        height: 100%;
        margin-left: 5px;
        display: none;
    }
    &.top .line {
        border-top: none;
    }
    &.bottom .line {
        border-bottom: none;
    }

    .text {
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
