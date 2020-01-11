<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import Board from '@/views/game/Board.vue';
import CardsInPlay from '@/views/game/CardsInPlay.vue';

@Component({
    components: {CardsInPlay, Board },
})
export default class GamePlay extends Vue {
    get id() {
        return this.$route.params.id;
    }
}
</script>

<template>
    <div>
        <b-row>
            <b-col sm="12" md="9" lg="9" xl="9">
                <div class="content">
                    <CardsInPlay :cards="[]" :is-opponent="true" />
                    <Board />
                    <CardsInPlay :cards="[]" />
                </div>
            </b-col>
            <b-col sm="12" md="3" lg="3">
                <div class="sidebar">
                    <b-card header="History" class="history">
                        <b-card-text>
                            <em>No moves have been made yet</em>
                        </b-card-text>
                    </b-card>
                    <b-card header="Actions" no-body class="settings">
                        <b-card-body>
                            <b-button variant="dark" size="sm">Give Up</b-button>
                        </b-card-body>
                    </b-card>
                </div>
            </b-col>
        </b-row>
    </div>
</template>

<style lang="scss">
.sidebar {
    width: 100%;
    .card {
        margin-bottom: 15px;
        font-size: 95%;
    }
}
.opponent-in-play {

}
</style>
