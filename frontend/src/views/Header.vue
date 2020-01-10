<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import auth from '@/store/modules/auth';

@Component
export default class Navbar extends Vue {
    get isLoggedIn() {
        return auth.state.isLoggedIn;
    }
    get user() {
        return auth.currentUser;
    }
    get showPlayButton() {
        return this.$route.fullPath !== '/';
    }
    logout() {
        auth.logout();
    }
}
</script>
<template>
    <b-navbar toggleable="md" type="dark" variant="primary" class="navbar">
        <div class="container">
            <b-navbar-brand to="/" class="site-name">Lost Cities</b-navbar-brand>
            <b-navbar-toggle target="nav-collapse" />

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item to="/games" exact>
                        <i class="fas fa-list-ul" />
                        Games
                    </b-nav-item>
                    <b-nav-item to="/leaderboard">
                        <i class="fas fa-trophy" />
                        Leaderboard
                    </b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">
                    <b-form>
                        <b-button v-if="showPlayButton" to="/games/new" variant="light" class="my- mr-2">
                            <i class="fas fa-play" />
                            Play Now
                        </b-button>
                    </b-form>

                    <b-nav-item-dropdown v-if="isLoggedIn" :text="user.username" right>
                        <b-dropdown-item to="/settings">Settings</b-dropdown-item>
                        <b-dropdown-item @click.prevent="logout">Logout</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>

                <b-navbar-nav v-if="!isLoggedIn">
                    <b-nav-item to="/login">Login</b-nav-item>
                    <b-nav-item to="/register">Register</b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </div>
    </b-navbar>
</template>
<style lang="scss">
.navbar {
    .site-name {
        font-size: 30px;
        line-height: 1;
        font-family: Bangers, Arial, sans-serif;
    }
}
</style>
