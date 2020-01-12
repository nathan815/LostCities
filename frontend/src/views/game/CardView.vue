<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card, Color } from '@/store/modules/game';

@Component
export default class CardView extends Vue {
    @Prop()
    card?: Card;

    @Prop({ default: 'front' })
    showSide?: 'front' | 'back';

    get isBlank() {
        return this.card === undefined;
    }

    get showBack() {
        return this.showSide == 'back' || this.isBlank;
    }
}
</script>

<template>
    <div class="game-card" :class="{ back: showBack }">
        <div v-if="showBack" class="game-card-back">
        </div>
    </div>
</template>

<style lang="scss">
.game-card {
    background: #222;
    border-radius: 5px;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    .game-card-back {
        background: #6b544d;
        width: 91%;
        height: 93%;
    }
}
</style>
