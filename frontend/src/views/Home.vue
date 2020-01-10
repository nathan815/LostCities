<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import auth from '@/store/modules/auth';

@Component
export default class Home extends Vue {
    get isLoggedIn() {
        return auth.state.isLoggedIn;
    }
}
</script>

<template>
    <div class="home">
        <div class="jumbotron">
            <img src="../assets/pyramids.png" class="pyramids" />
            <div class="content">
                <h1 class="display-4">Your adventure awaits...</h1>
                <b-button to="/games/new" variant="primary" size="lg">
                    <i class="fas fa-play" />
                    Play Now
                </b-button>
            </div>
        </div>
        <b-row class="stats">
            <b-col>
                <div class="stat">
                    <span class="stat-number">0</span>
                    total games played
                </div>
            </b-col>
            <b-col>
                <div class="stat">
                    <span class="stat-number">0</span>
                    players online
                </div>
            </b-col>
            <b-col>
                <div class="stat">
                    <span class="stat-number">0</span>
                    moves played
                </div>
            </b-col>
        </b-row>
    </div>
</template>

<style lang="scss">
@import '../styles/main.scss';
@import '~bootstrap/scss/functions';
.jumbotron {
    position: relative;
    height: 400px;
    overflow: hidden;
    justify-content: flex-start;
    background: transparent !important;
    padding-top: 0 !important;
    margin-top: 50px;
    .content {
        z-index: 2;
        position: relative;
        color: theme-color('primary');
        h1 {
            font-family: Georgia, serif;
        }
    }
    .pyramids {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        z-index: 1;
    }
}
.stats {
    .stat {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        border-radius: 5px;
        padding: 15px;

        background: #e4af0e;

        font-size: 20px;
        text-align: center;
        .stat-number {
            font-size: 35px;
        }
    }
}
</style>
