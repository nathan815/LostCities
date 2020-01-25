<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { GameState } from '@/model/game';
import { Player } from '@/model/game/player';
import { GamePreferences } from '@/model/game/preferences';

@Component
export default class GameStatusCard extends Vue {
    @Prop({ required: true })
    private game!: GameState;

    @Prop({ required: true })
    private isMyGame!: boolean;

    @Prop({ required: true })
    private isMyTurn!: boolean;

    @Prop({ required: true })
    private topPlayer!: Player;

    @Prop({ required: true })
    private bottomPlayer!: Player;

    @Prop({ required: true })
    private preferences!: GamePreferences;

    get currentTurnName() {
        if (this.isMyTurn) {
            return 'your';
        } else if (this.game.currentTurnPlayer) {
            return `${this.game.currentTurnPlayer.name}'s`;
        }
        return null;
    }

    toggleHandFixedPositionPreference() {
        this.$emit('preference-change');
        this.preferences.handFixedPosition = !this.preferences.handFixedPosition;
    }
}
</script>
<template>
    <b-card no-body class="status">
        <b-card-header>
            {{ topPlayer.name || '______' }} vs. {{ bottomPlayer.name || '______' }}
        </b-card-header>
        <b-card-body>
            <b-card-text class="status-text">
                <template v-if="game.isStarted">
                    It is
                    <b>{{ currentTurnName }}</b>
                    turn
                </template>
                <template v-if="!game.isStarted">
                    <em>Game has not yet started</em>
                </template>
            </b-card-text>
            <b-card-text v-if="isMyGame">
                <b-button v-if="game.isStarted" variant="primary" size="sm" :disabled="isMyTurn">
                    Nudge
                </b-button>
                <b-dropdown size="sm" class="m-md-2" variant="light" right>
                    <template v-slot:button-content>
                        <i class="fas fa-cog" />
                    </template>
                    <b-dropdown-item @click="toggleHandFixedPositionPreference">
                        <i class="fas" :class="{ 'fa-check': preferences.handFixedPosition }" />
                        Keep hand fixed at bottom
                    </b-dropdown-item>
                </b-dropdown>
            </b-card-text>
        </b-card-body>
    </b-card>
</template>
<style scoped lang="scss">
.card-text {
    text-align: center;
}
.status-text {
    font-size: 105%;
}
</style>
