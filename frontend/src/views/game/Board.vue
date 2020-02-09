<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card, Color, getColorEnumValues } from '@/model/game/card';
import CardView from '@/views/game/CardView.vue';
import { Board } from '@/model/game';

@Component({
    components: { CardView },
})
export default class BoardView extends Vue {
    @Prop({ required: true })
    board!: Board;

    @Prop({ required: true })
    canDraw!: boolean;

    colors = getColorEnumValues();

    get discard() {
        return this.board.discard;
    }

    lastThreeDiscardCards(color: Color): Card[] {
        return this.discard[color] ? this.discard[color]! : [];
    }

    // Returns some custom style for a card given its index in the pile
    discardCardStyle(index: number) {
        return {
            top: `${index}px`,
            left: `${index * 5}px`,
            zIndex: index,
            transform: `rotate(${index + 0.3}deg)`,
        };
    }

    discardCardClassNames(color: Color, index: number) {
        return {
            'discard-card': true,
            'can-draw': this.canDraw && index === this.lastThreeDiscardCards(color).length-1,
        };
    }
}
</script>

<template>
    <div class="board">
        <img src="../../assets/board.jpg" alt="Lost Cities Board" />
        <div class="discard-stacks-container">
            <div v-for="color in colors" :key="color" class="card-stack-container">
                <div v-if="discard[color]" class="card-stack">
                    <CardView
                        v-for="(card, index) of lastThreeDiscardCards(color)"
                        :key="index"
                        :card="card"
                        :style="discardCardStyle(index)"
                        :class="discardCardClassNames(color, index)"
                        @click="$emit('draw', color)"
                    />
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.board {
    position: relative;
    width: 100%;
    height: auto;
    margin: 5px auto;
    border-radius: 2px;
    overflow: hidden;
    box-shadow: 0 0 3px #555;
    img {
        width: 100%;
        z-index: 1;
        pointer-events: none;
    }
}
.discard-stacks-container {
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    z-index: 2;
    display: flex;
    align-items: center;
    .card-stack-container {
        width: 20%;
        height: 145px;
        padding-left: 14px;
    }
    .card-stack {
        position: relative;
        width: 100%;
        height: 100%;
    }
    .discard-card {
        position: absolute;
        &.can-draw {
            cursor: pointer;
            &:hover {
                filter: brightness(110%);
            }
        }
    }
}
</style>
