<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import AuthPage from './AuthPage.vue';
import auth from '@/store/modules/auth';

@Component({
    components: { AuthPage },
})
export default class Login extends Vue {
    private username: string = '';
    private password: string = '';

    get error() {
        return auth.state.error;
    }
    get loading() {
        return auth.state.isLoading;
    }
    get returnUrl() {
        return this.$route.query.to as string;
    }
    get registerUrl() {
        return `/register${this.returnUrl ? `?to=${this.returnUrl}` : ''}`;
    }
    async login() {
        await auth.login({
            username: this.username,
            password: this.password,
        });
        if (!this.error) {
            this.$router.push(this.returnUrl || '/');
        }
    }
    clearError() {
        auth.clearError();
    }
    beforeDestroy() {
        this.clearError();
    }
}
</script>

<template>
    <AuthPage title="Login">
        <b-alert variant="danger" dismissible :show="error != null" @dismissed="clearError">
            {{ error }}
        </b-alert>

        <form method="post" @submit.prevent="login">
            <div class="form-group">
                <label for="username">Username</label>
                <input
                    id="username"
                    v-model="username"
                    type="text"
                    class="form-control"
                    required
                    placeholder="Username"
                />
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input
                    id="password"
                    v-model="password"
                    type="password"
                    class="form-control"
                    required
                    placeholder="Password"
                />
            </div>

            <button type="submit" class="btn btn-primary btn-block btn-lg" :disabled="loading">
                Login
                <i class="fas fa-arrow-right" />
            </button>
        </form>

        <div class="alert alert-secondary text-center mt-4 mb-0">
            Don't have an account yet?
            <router-link tag="button" :to="registerUrl" class="btn btn-secondary btn-sm">
                Register
            </router-link>
        </div>
    </AuthPage>
</template>
