<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import CardView from '@/views/game/CardView.vue';

@Component({
    components: { CardView },
})
export default class Deck extends Vue {
    @Prop()
    numCards!: number;
}
</script>

<template>
    <div class="deck">
        <CardView class="cards" show-side="back" />
        <p>
            <em>{{ numCards }} left</em>
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
