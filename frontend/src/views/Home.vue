<script>
    import {mapState} from 'vuex';

    export default {
        computed: mapState({
            isLoggedIn: state => state.auth.isLoggedIn
        }),
    }
</script>

<template>
    <div class="home">
        <div class="jumbotron d-flex justify-content-around align-items-center">
            <div class="text-center">
                <h1 class="display-4">Lost Cities</h1>
                <p class="lead">Play <b>Lost Cities</b> against others!</p>
                <div v-if="isLoggedIn">
                    <b-button to="/games/new" variant="primary" size="lg">Start a Game</b-button>
                    <b-button to="/games" variant="secondary" size="lg">View Games</b-button>
                </div>
                <div v-else>
                    <b-button to="/register" variant="primary" size="lg">Create Account</b-button>
                    <b-button to="/login" variant="secondary" size="lg">Login</b-button>
                </div>
            </div>
            <div>
                <img src="../assets/game-setup.png" class="rounded" />
            </div>
        </div>
    </div>
</template>


<style lang="scss">
    .jumbotron .btn {
        margin-right: 10px;
    }
</style>