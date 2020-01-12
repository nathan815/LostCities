<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card, Color } from '@/store/modules/game';
import CardView from '@/views/game/CardView.vue';
@Component({
    components: { CardView },
})
export default class CardsInPlay extends Vue {
    @Prop({ required: true })
    cards!: { Color: Card[] };

    @Prop({ default: false })
    isOpponent!: Boolean;

    get colors(): Color[] {
        return Object.keys(Color).map(key => Color[key]);
    }

    cardsOfColor(color: Color): Card[] {
        return [];
    }
}
</script>

<template>
    <b-row class="card-stacks-container">
        <b-col v-for="color in colors" :key="color" class="card-stack-col" :class="color">
            <div class="card-stack" :class="{ flipped: isOpponent }">
                <CardView v-for="card in cardsOfColor(color)" :key="card" :card="card" />
                <div v-if="cardsOfColor(color).length === 0" class="placeholder"></div>
            </div>
        </b-col>
    </b-row>
</template>

<style lang="scss">
.col.card-stack-col {
    margin: 0 12px;
    padding: 0;
}
.card-stack {
    width: 100%;
    height: 170px;
    text-align: center;
    display: flex;

    &.flipped {
        transform: rotate(180deg);
    }
    .placeholder {
        background: #efefef;
        border: 1px dashed #dcdcdc;
        border-radius: 5px;
        width: 85%;
        height: 70%;
        margin: auto;
        align-self: center;
    }
}
.row.card-stacks-container {
    margin: 0;
    padding: 0;
}
</style>
