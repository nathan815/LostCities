<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { CardsInPlay, Color, getColorEnumValues } from '@/model/game/card';
import CardView from '@/views/game/CardView.vue';

@Component({
    components: { CardView },
})
export default class CardsInPlayView extends Vue {
    @Prop({ required: true })
    cards!: CardsInPlay;

    @Prop({ default: false })
    isTop!: boolean;

    colors = getColorEnumValues();

    get cardStackStyle() {
        const additionalHeight = this.isTop ? 0 : this.maxNumCards * 15;
        return {
            height: 170 + additionalHeight + 'px',
        };
    }

    get maxNumCards() {
        return Object.values(this.cards)
            .map(cards => (cards ? cards.length : 0))
            .reduce((max, cur) => Math.max(max, cur), 0);
    }

    hasCards(color: Color): boolean {
        return !!this.cards[color] && this.cards[color]!.length > 0;
    }
}
</script>

<template>
    <b-row class="card-stacks-container">
        <b-col v-for="color in colors" :key="color" class="card-stack-col" :class="color">
            <div class="card-stack" :class="{ flipped: isTop }" :style="cardStackStyle">
                <CardView
                    v-for="(card, index) in cards[color]"
                    :key="index"
                    :card="card"
                    :style="{ top: `${5 + index * 18}px` }"
                    class="in-play-card"
                />
                <div v-if="!hasCards(color)" class="placeholder"></div>
            </div>
        </b-col>
    </b-row>
</template>

<style lang="scss" scoped>
.col.card-stack-col {
    margin: 0 12px;
    padding: 0;
}
.card-stack {
    width: 100%;
    height: 170px;
    text-align: center;
    display: flex;
    justify-content: center;
    position: relative;
    z-index: 1;

    &.flipped {
        transform: rotate(180deg);
    }
    .placeholder {
        background: #52525233;
        border: 1px dashed #424242;
        border-radius: 5px;
        width: 85%;
        height: 70%;
        max-height: 119px;
        margin-top: 20px;
    }
}
.row.card-stacks-container {
    margin: 0;
    padding: 0;
}
.in-play-card {
    position: absolute;
}
</style>
