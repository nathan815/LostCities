<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card, Color, Discard } from '@/store/modules/game/model';
import { getColorEnumValues } from '@/utils';
import CardView from '@/views/game/CardView.vue';

@Component({
    components: { CardView },
})
export default class Board extends Vue {
    @Prop({ required: true })
    discard!: Discard;

    colors = getColorEnumValues();

    lastThreeDiscardCards(color: Color): Card[] {
        return this.discard[color] && this.discard[color].slice(-3);
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
                        class="discard-card"
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
    margin: 0 auto;
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
    }
}
</style>
