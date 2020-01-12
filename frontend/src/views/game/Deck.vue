<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import CardView from '@/views/game/CardView.vue';

const WARN_CARDS_LEFT = 3;

@Component({
    components: { CardView },
})
export default class Deck extends Vue {
    @Prop()
    numCards!: number;

    get numCardsBadgeVariant() {
        return this.numCards <= WARN_CARDS_LEFT ? 'danger' : 'light';
    }
}
</script>

<template>
    <div class="deck">
        <CardView class="cards" show-side="back" />
        <p>
            <b-badge :variant="numCardsBadgeVariant">{{ numCards }} left</b-badge>
        </p>
    </div>
</template>

<style lang="scss">
.deck {
    margin-top: 15px;

    p {
        text-align: center;
        font-size: 13px;
        margin: 0;
    }
}
</style>
