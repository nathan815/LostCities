<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card } from '@/store/modules/game';
import CardView from '@/views/game/CardView.vue';

@Component({
    components: { CardView },
})
export default class Hand extends Vue {
    @Prop()
    cards!: Card[];
}
</script>

<template>
    <div class="hand">
        <CardView v-for="card in cards" :key="card.toString()" :card="card" class="hand-card" />
    </div>
</template>

<style lang="scss">
.hand {
    box-shadow: 0 -5px 5px -5px #ccc;
    padding: 20px;
    margin: 20px auto;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
}
.hand-card {
    align-items: center;
    flex-shrink: 0;
}
</style>
