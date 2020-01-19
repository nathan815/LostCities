<script lang="ts">
import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';

@Component
export default class GamePreStartBox extends Vue {
    @Prop({ required: true })
    private isWaitingForPlayer!: boolean;

    @Prop({ required: true })
    private isMyGame!: boolean;

    @Prop({ required: true })
    private isJoinInProgress!: boolean;

    @Prop({ required: true })
    private isReadyToStart!: boolean;

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
        <b-alert
            v-if="isWaitingForPlayer"
            key="1"
            show="true"
            variant="dark"
            class="pre-start-alert"
        >
            <span>
                <i class="fas fa-clock" />
                Waiting for second player to join
            </span>
            <b-button
                v-if="!isMyGame"
                :disabled="isJoinInProgress"
                variant="success"
                size="md"
                @click="join"
            >
                <i class="fas fa-plus-circle" />
                {{ isJoinInProgress ? 'Joining...' : 'Join' }}
            </b-button>
        </b-alert>
        <b-alert v-if="isReadyToStart" key="2" show="true" variant="dark" class="pre-start-alert">
            Ready to start!
            <b-button v-if="isMyGame" variant="primary" @click="start">Start</b-button>
        </b-alert>
    </transition>
</template>
<style scoped>
.pre-start-alert {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
}
</style>
