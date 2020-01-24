<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { GameState } from '@/model/game';
import { Player } from '@/model/game/player';

@Component
export default class GamePreStartBox extends Vue {
    @Prop({ required: true })
    private game!: GameState;

    @Prop({ required: true })
    private myPlayer!: Player;

    @Prop({ required: true })
    private isMyGame!: boolean;

    @Prop({ required: true })
    private isJoinInProgress!: boolean;

    @Prop({ required: true })
    private isStartInProgress!: boolean;

    private join() {
        this.$emit('join');
    }

    private start() {
        this.$emit('start');
    }
}
</script>
<template>
    <transition name="fade" mode="out-in">
        <b-card v-if="game.isNew" key="1" class="pre-start-card">
            <span>
                <i class="fas fa-clock" />
                Waiting for second player to join
            </span>
            <b-button
                v-if="!isMyGame"
                :disabled="isJoinInProgress"
                variant="success"
                size="sm"
                @click="join"
            >
                <i class="fas fa-plus-circle" />
                {{ isJoinInProgress ? 'Joining...' : 'Join' }}
            </b-button>
        </b-card>

        <b-card v-if="game.isReadyToStart" key="2" class="pre-start-card">
            <div class="waiting-players-start">
                <p>Waiting for players to start</p>
                <small
                    v-for="player in game.players"
                    :key="player.id"
                    :class="{ 'text-success': player.readyToStart }"
                >
                    <i :class="`fa ${player.readyToStart ? 'fa-check-circle' : 'fa-ellipsis-h'}`" />
                    {{ player.name }}
                </small>
            </div>
            <b-button
                v-if="isMyGame"
                :disabled="isStartInProgress || currentPlayer.readyToStart"
                variant="success"
                @click="start"
            >
                <i :class="`fas ${currentPlayer.readyToStart ? 'fa-check-circle' : 'fa-play-circle'}`" />
                Start
            </b-button>
        </b-card>
    </transition>
</template>
<style scoped lang="scss">
.pre-start-card {
    margin-bottom: 15px;

    .card-body {
        padding: 15px 20px;
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
    }
}

.waiting-players-start {
    p {
        margin: 0;
    }

    small {
        font-size: 85%;
        padding-right: 15px;
        color: #777;
    }
}
</style>
