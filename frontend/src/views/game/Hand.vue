<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card } from '@/model/game/card';
import CardView from '@/views/game/CardView.vue';
import { GameState } from '@/model/game';
import { TurnStage } from '@/model/game/moves';

@Component({
    components: { CardView },
})
export default class Hand extends Vue {
    @Prop()
    game!: GameState;

    @Prop()
    isMyTurn!: boolean;

    @Prop({ default: true })
    fixed!: boolean;

    play(card: Card) {
        this.$emit('play', card);
    }

    discard(card: Card) {
        this.$emit('discard', card);
    }

    get showActions() {
        return this.isMyTurn && this.game.turnStage == TurnStage.PlayOrDiscard;
    }
}
</script>

<template>
    <b-row>
        <b-container class="hand" :class="{ fixed: fixed }">
            <div v-for="card in game.hand" :key="card.toString()" class="hand-card-container">
                <CardView :card="card" class="hand-card" />
                <div v-if="showActions" class="action-bar-container">
                    <div class="action-bar">
                        <b-button
                            variant="outline-light"
                            size="sm"
                            title="Play"
                            @click="play(card)"
                        >
                            P
                        </b-button>
                        <b-button
                            variant="outline-light"
                            size="sm"
                            title="Discard"
                            @click="discard(card)"
                        >
                            D
                        </b-button>
                    </div>
                </div>
            </div>
            <div v-if="game.hand.length === 0">
                <em>Your hand is empty</em>
            </div>
        </b-container>
        <div v-if="fixed" class="spacer"></div>
    </b-row>
</template>

<style lang="scss" scoped>
@import '../../styles/variables';

.spacer {
    height: 185px;
}
.hand {
    box-shadow: 0 -6px 6px -7px #444;
    padding: 20px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    background: $body-bg;
    z-index: 2;
    position: relative;
    &.fixed {
        position: fixed;
        bottom: 0;
    }
}
.hand-card-container {
    position: relative;
    .action-bar-container {
        width: 100%;
        position: absolute;
        bottom: 10px;
        left: 0;
        display: flex;
        justify-content: center;
        transition: opacity 0.2s;
        opacity: 0;
    }
    .action-bar {
        width: 80%;
        padding: 10px 0;
        border-radius: 4px;
        background: #00000055;

        text-align: center;

        .btn {
            margin: 0 5px;
            font-size: 75%;
        }
    }
    &:hover {
        .action-bar-container {
            opacity: 1;
        }
    }
}
</style>
