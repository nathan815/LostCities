<script>
import { mapActions, mapState } from 'vuex';

export default {
    computed: mapState({
        isLoggedIn: state => state.auth.isLoggedIn,
        user: state => state.auth.user,
    }),
    methods: mapActions('auth', ['logout']),
};
</script>
<template>
    <b-navbar toggleable="md" type="dark" variant="primary">
        <div class="container">
            <b-navbar-brand to="/">Lost Cities</b-navbar-brand>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse is-nav id="nav-collapse">
                <b-navbar-nav>
                    <b-nav-item to="/games" exact
                        ><i class="fas fa-list-ul"></i> Games</b-nav-item
                    >
                    <b-nav-item to="/leaderboard"
                        ><i class="fas fa-trophy"></i> Leaderboard</b-nav-item
                    >
                </b-navbar-nav>

                <b-navbar-nav v-if="isLoggedIn" class="ml-auto">
                    <b-form>
                        <b-button
                            to="/games/new"
                            variant="light"
                            class="my- mr-2"
                        >
                            <i class="fas fa-plus-square"></i> Start a Game
                        </b-button>
                    </b-form>

                    <b-nav-item-dropdown :text="user.username" right>
                        <b-dropdown-item to="/settings"
                            >Settings</b-dropdown-item
                        >
                        <b-dropdown-item @click.prevent="logout"
                            >Logout</b-dropdown-item
                        >
                    </b-nav-item-dropdown>
                </b-navbar-nav>

                <b-navbar-nav v-else class="ml-auto">
                    <b-nav-item to="/login">Login</b-nav-item>
                    <b-nav-item to="/register">Register</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </div>
    </b-navbar>
</template>
