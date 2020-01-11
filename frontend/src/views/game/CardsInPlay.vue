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
            <div class="card-stack" :class="{ [color]: true, flipped: isOpponent }">
                <CardView v-for="card in cardsOfColor(color)" :key="card" :card="card" />
                <div v-if="cardsOfColor(color).length === 0" class="placeholder">
                    {{ color }}
                </div>
            </div>
        </b-col>
    </b-row>
</template>

<style lang="scss">
$cardGradientFadeTo: #f5f5f5;

.col.card-stack-col {
    margin: 0 12px;
    padding: 0;
    &.red {
    }
}
.card-stack {
    width: 100%;
    height: 150px;
    text-align: center;
    background: linear-gradient(#e5e5e5, $cardGradientFadeTo);
    &.flipped {
        transform: rotate(180deg);
    }
}
.row.card-stacks-container {
    margin: 0;
    padding: 0;
}
</style>
