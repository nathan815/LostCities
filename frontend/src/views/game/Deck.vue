<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card } from '@/store/modules/game';
import CardView from '@/views/game/CardView.vue';
@Component({
    components: { CardView },
})
export default class Deck extends Vue {
    @Prop()
    topCard!: Card;
    @Prop()
    numCards!: number;
}
</script>

<template>
    <div class="deck">
        <CardView :card="topCard" />
        <b>{{ numCards }}</b>
        cards
    </div>
</template>

<style lang="scss">
.board {
    position: relative;
    width: 100%;
    height: auto;
    margin: 0 auto;
    border-radius: 1px;
    overflow: hidden;
    img {
        width: 100%;
        z-index: 1;
        pointer-events: none;
    }
}
</style>
