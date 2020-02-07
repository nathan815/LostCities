<script lang="ts">
import Vue from 'vue';
import { Component, Prop, Watch } from 'vue-property-decorator';
import CardView from '@/views/game/CardView.vue';
import { GameState } from '@/model/game';

@Component({
    components: { CardView },
})
export default class Hand extends Vue {
    @Prop()
    game!: GameState;

    $refs!: {
        log: HTMLDivElement;
    };

    mounted() {
        this.scrollToBottom();
    }

    updated() {
        this.scrollToBottom();
    }

    scrollToBottom() {
        this.$refs.log.scrollTop = this.$refs.log.scrollHeight;
    }
}
</script>

<template>
    <b-card no-body header="Log" class="history">
        <b-card-body ref="log" class="log-card-body">
            <b-card-text>
                <em v-if="game.moves.length === 0">Nothing here yet</em>
                <div v-for="(move, index) in game.moves" :key="index" class="log-entry">
                    <small class="log-entry-number">{{ index + 1 }}</small>
                    <span class="log-entry-text">
                        <span class="player-name">{{ game.findPlayerById(move.playerId).name }}</span>
                        {{ move.description }}
                    </span>
                </div>
            </b-card-text>
        </b-card-body>
    </b-card>
</template>

<style lang="scss" scoped>
.log-card-body {
    max-height: 250px;
    overflow: scroll;
    padding: 1em;
}
.log-entry {
    display: flex;
    flex-direction: row;
    align-items: flex-start;
    line-height: 1;
    &:not(:last-child) {
        margin-bottom: 5px;
    }
}
.log-entry-number {
    padding-right: 5px;
    padding-top: 2px;
    color: #777;
}
.log-entry-text {
}
</style>
