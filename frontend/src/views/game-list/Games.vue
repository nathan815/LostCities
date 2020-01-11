<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import GameList from '@/views/game-list/GameList.vue';

@Component({
    components: { GameList },
})
export default class Games extends Vue {
    get games() {
        return [];
    }
    get statusFilter() {
        return this.$route.query.status;
    }
}
</script>

<template>
    <b-container>
        <h1>Games {{ statusFilter }}</h1>
        <GameList :games="games" />
    </b-container>
</template>
