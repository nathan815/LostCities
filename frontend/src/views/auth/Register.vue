<script>
    import AuthPage from "./AuthPage";
    import {mapState} from 'vuex';

    export default {
        components: { AuthPage },
        beforeDestroy() {
            this.clearError();
        },
        data() {
            return {
                email: "",
                username: "",
                password: "",
                confirmPassword: "",
                loading: false,
            }
        },
        computed: mapState({
            error: state => state.account.error
        }),
        methods: {
            async register() {
                this.loading = true;
                await this.$store.dispatch('account/register', {
                    email: this.email,
                    username: this.username,
                    password: this.password,
                    confirmPassword: this.confirmPassword,
                });
                this.loading = false;

                if(!this.error) {
                    this.$store.dispatch('alert/show', {
                        message: 'Your account has been created. You may now login.',
                        variant: 'success'
                    });
                    this.$router.push('/login');
                }
            },
            clearError() {
                this.$store.dispatch('account/clearError');
            },
        }
    }
</script>

<template>
    <AuthPage title="Register">
        <b-alert variant="danger"
                 dismissible
                 :show="error != null"
                 @dismissed="clearError">
            {{ error }}
        </b-alert>

        <form method="post" @submit.prevent="register">

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" required
                       id="username" placeholder="Username" v-model="username">
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" required
                       id="email" placeholder="Email" v-model="email">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" required
                       id="password" placeholder="Password" v-model="password">
            </div>

            <div class="form-group">
                <label for="confirm-password">Confirm Password</label>
                <input type="password" class="form-control" required
                       id="confirm-password" placeholder="Confirm Password"
                       v-model="confirmPassword">
            </div>

            <button type="submit" class="btn btn-primary btn-block btn-lg" :disabled="loading">
                <i class="fas fa-check"></i>
                Create Account
            </button>

        </form>
    </AuthPage>
</template>