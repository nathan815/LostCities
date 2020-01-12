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
    numCards!: number;
}
</script>

<template>
    <div class="deck">
        <CardView class="cards" />
        <p>
            <em>{{ numCards }} left</em>
        </p>
    </div>
</template>

<style lang="scss">
.deck {
    width: 99%;
    height: 85%;
    p {
        text-align: center;
        font-size: 13px;
    }
}
</style>
