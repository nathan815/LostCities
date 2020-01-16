<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card } from '@/model/game/card';
import CardView from '@/views/game/CardView.vue';

@Component({
    components: { CardView },
})
export default class Hand extends Vue {
    @Prop()
    cards!: Card[];

    @Prop({ default: true })
    fixed!: boolean;
}
</script>

<template>
    <b-row>
        <b-container class="hand" :class="{ fixed: fixed }">
            <CardView v-for="card in cards" :key="card.toString()" :card="card" class="hand-card" />
        </b-container>
        <div v-if="fixed" class="spacer"></div>
    </b-row>
</template>

<style lang="scss" scoped>
.spacer {
    height: 185px;
}
.hand {
    box-shadow: 0 -5px 5px -5px #ccc;
    padding: 20px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    background: #f5f5f5; // TODO: add sass variable for body background
    z-index: 2;
    position: relative;
    &.fixed {
        position: fixed;
        bottom: 0;
    }
}
</style>
