<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card } from '@/store/modules/game/model';

@Component
export default class CardView extends Vue {
    @Prop()
    card?: Card;

    @Prop({ default: 'front' })
    showSide!: 'front' | 'back';

    get isBlank() {
        return this.card === undefined;
    }

    get showBack() {
        return this.showSide == 'back' || this.isBlank;
    }

    get showFront() {
        return this.showSide == 'front';
    }

    get classNames() {
        const classes: string[] = [];
        classes.push(this.showSide);
        if (this.card) {
            classes.push(`color-${this.card.color}`);
        }
        return classes;
    }
}
</script>

<template>
    <div class="game-card" :class="classNames">
        <div v-if="card && showFront" class="game-card-value">
            <template v-if="card.isWager">
                <span><i class="fas fa-handshake" /></span>
                <span><i class="fas fa-handshake" /></span>
            </template>
            <template v-else>
                <span>{{ card.value }}</span>
                <span>{{ card.value }}</span>
            </template>
        </div>
    </div>
</template>

<style lang="scss">
@function card-background($cardColors) {
    @return linear-gradient(to bottom, nth($cardColors, 1), nth($cardColors, 2));
}

// Card Background Colors (Gradients)
$cardColors: (
    'yellow': (
        #ddc248,
        #fae042,
    ),
    'blue': (
        #0f8ae7,
        #53c6ff,
    ),
    'white': (
        #ece9f5,
        #c3c3c3,
    ),
    'green': (
        #28ae3c,
        #41b53f,
    ),
    'red': (
        #d40505,
        #ef7872,
    ),
);
$cardBack: #6b574b, #83655c;

.game-card {
    border-radius: 5px;

    width: 105px;
    height: 145px;

    position: relative;
    user-select: none;
    box-sizing: border-box;

    border: 4px solid #272525;
    box-shadow: 0px -3px 5px -3px #000;

    .game-card-value {
        display: flex;
        justify-content: space-between;
        position: absolute;
        top: -20px;
        width: 100%;
        padding: 0 2px;
        font-size: 15px;
        font-weight: bold;
    }

    &.front {
        border-top-width: 18px;
    }
    &.back {
        background: card-background($cardBack);
    }
}
@each $color, $colorValues in $cardColors {
    .game-card.color-#{$color} {
        background: card-background($colorValues);
        color: nth($colorValues, 1);
    }
}
</style>
