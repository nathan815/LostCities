<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import auth from '@/store/modules/auth';

@Component
export default class Home extends Vue {
    randomNum() {
        return Math.round(Math.random() * 10); // fake stats for now
    }
    get isLoggedIn() {
        return auth.state.isLoggedIn;
    }
    get username() {
        return auth.currentUser && auth.currentUser.username;
    }
}
</script>

<template>
    <b-container class="home">
        <div class="jumbotron">
            <img src="../assets/pyramids.png" class="pyramids" />
            <div class="content">
                <h1 class="display-4">
                    {{ isLoggedIn ? `${username}, start an expedition...` : 'Your adventure awaits...' }}
                </h1>
                <b-button to="/games/new" variant="primary" size="lg">
                    <i class="fas fa-play" />
                    Play Now
                </b-button>
            </div>
        </div>
        <b-row class="stats">
            <b-col>
                <div class="stat">
                    <span class="stat-number">{{ randomNum() }}</span>
                    total games played
                </div>
            </b-col>
            <b-col>
                <div class="stat">
                    <span class="stat-number">{{ randomNum() }}</span>
                    players online
                </div>
            </b-col>
            <b-col>
                <div class="stat">
                    <span class="stat-number">{{ randomNum() }}</span>
                    moves played
                </div>
            </b-col>
        </b-row>
    </b-container>
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
        animation: jumbotron-animation 1s;
    }
    .pyramids {
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        z-index: 1;
    }
}
@keyframes jumbotron-animation {
    0% {
        transform: scale(0.7);
    }
    100% {
        transform: scale(1);
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

        background: #e6b41bdd url(../assets/sand.png) 40% 0%;

        font-size: 20px;
        text-align: center;
        color: black;

        animation: stats-animation 1s;
        opacity: 0.8;

        .stat-number {
            font-size: 35px;
        }

        &:hover {
            opacity: 1;
        }
    }
    .stat:nth-child(2) {
        background-position-x: 80%;
    }
    .stat:nth-child(3) {
        background-position-x: 75%;
    }
}
@keyframes stats-animation {
    0% {
        transform: translateY(-50px);
        opacity: 0;
    }
    100% {
        transform: translateY(0);
        opacity: 0.8;
    }
}
</style>
