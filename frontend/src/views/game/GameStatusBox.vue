<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { GameState } from '@/model/game';
import { GamePreferences } from '@/model/game/preferences';
import { TurnStage } from '@/model/game/moves';

@Component
export default class GameStatusBox extends Vue {
    @Prop({ required: true })
    private game!: GameState;

    @Prop({ required: true })
    private isMyGame!: boolean;

    @Prop({ required: true })
    private isMyTurn!: boolean;

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

    get headingText() {
        const playerNames = this.game.players.map(p => p.name);
        if (playerNames.length == 1) playerNames.push('_______');
        return playerNames.join(' vs. ');
    }

    toggleHandFixedPositionPreference() {
        this.$emit('preference-change');
        this.preferences.handFixedPosition = !this.preferences.handFixedPosition;
    }

    get nextMoveHint(): string {
        switch (this.game.turnStage) {
            case TurnStage.Draw:
                return 'Draw a Card';
            case TurnStage.PlayOrDiscard:
                return 'Play or Discard';
            default:
                return '';
        }
    }
}
</script>
<template>
    <b-card no-body class="status">
        <b-card-header>
            {{ headingText }}
        </b-card-header>
        <b-card-body>
            <b-card-text class="status-text">
                <template v-if="game.isStarted">
                    <div>
                        It is
                        <b>{{ currentTurnName }}</b>
                        turn
                    </div>
                    <div v-if="isMyTurn" class="next-move-hint">
                        {{ nextMoveHint }}
                    </div>
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
                        Always keep hand visible
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
.next-move-hint {
    font-style: italic;
    font-size: 90%;
}
</style>
