<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import AuthPage from './AuthPage.vue';
import account, { RegisterData } from '@/store/modules/account';
import alert from '@/store/modules/alert';

@Component({
    components: { AuthPage },
})
export default class Register extends Vue {
    private registerData: RegisterData = {
        email: '',
        username: '',
        password: '',
        confirmPassword: '',
    };
    private loading = false;

    get error() {
        return account.state.registerError;
    }

    async register() {
        this.loading = true;
        await account.register(this.registerData);
        this.loading = false;

        if (!this.error) {
            alert.show({
                message: 'Your account has been created. You may now login.',
                variant: 'success',
            });
            this.$router.push('/login');
        }
    }
    clearError() {
        account.clearRegisterError();
    }
    beforeDestroy() {
        this.clearError();
    }
}
</script>

<template>
    <AuthPage title="Register">
        <b-alert variant="danger" dismissible :show="error != null" @dismissed="clearError">
            {{ error }}
        </b-alert>

        <form method="post" @submit.prevent="register">
            <div class="form-group">
                <label for="username">Username</label>
                <input
                    type="text"
                    class="form-control"
                    required
                    id="username"
                    placeholder="Username"
                    v-model="registerData.username"
                />
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input
                    type="email"
                    class="form-control"
                    required
                    id="email"
                    placeholder="Email"
                    v-model="registerData.email"
                />
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input
                    type="password"
                    class="form-control"
                    required
                    id="password"
                    placeholder="Password"
                    v-model="registerData.password"
                />
            </div>

            <div class="form-group">
                <label for="confirm-password">Confirm Password</label>
                <input
                    type="password"
                    class="form-control"
                    required
                    id="confirm-password"
                    placeholder="Confirm Password"
                    v-model="registerData.confirmPassword"
                />
            </div>

            <button type="submit" class="btn btn-primary btn-block btn-lg" :disabled="loading">
                <i class="fas fa-check"/>
                Create Account
            </button>
        </form>
    </AuthPage>
</template>
