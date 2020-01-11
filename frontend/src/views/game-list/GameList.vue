<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';

interface GameInfo {
    id: number;
    player1: string;
    player2: string;
}

@Component
export default class GameList extends Vue {
    @Prop()
    games!: GameInfo[];
}
</script>

<template>
    <div>
        <ul>
            <li v-for="game in games">
                {{ game }}
            </li>
        </ul>
    </div>
</template>
