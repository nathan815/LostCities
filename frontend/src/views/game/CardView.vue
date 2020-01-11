<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Card, Color } from '@/store/modules/game';

@Component
export default class CardView extends Vue {
    @Prop()
    card!: Card;
}
</script>

<template>

</template>

<style lang="scss">
.card {
}
</style>
