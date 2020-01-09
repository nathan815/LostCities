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
        <div class="banner"></div>
        <div class="jumbotron d-flex justify-content-around align-items-center">
            <div class="text-center">
                <h1 class="display-4">Lost Cities</h1>
                <p class="lead">
                    Play
                    <b>Lost Cities</b>
                    against others
                </p>
                <b-button to="/games/new" variant="primary" size="lg">Play Now!</b-button>
            </div>
            <div>
                <img src="../assets/game-setup.png" class="rounded" />
            </div>
        </div>
    </div>
</template>

<style lang="scss">
.banner {
    background: url('../assets/lostcitiesbanner.jpg');
}
.jumbotron .btn {
    margin-right: 10px;
}
</style>
