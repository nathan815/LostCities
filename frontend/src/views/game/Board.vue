<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { Discard } from '@/store/modules/game';

@Component
export default class Board extends Vue {
    @Prop()
    discard!: Discard;
}
</script>

<template>
    <div class="board">
        <img src="../../assets/board.jpg" alt="Lost Cities Board" />
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
