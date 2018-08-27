<script>
    import axios from 'axios';
    import AuthPage from "./AuthPage";

    export default {
        components: { AuthPage },
        data() {
            return {
                username: '',
                password: '',
                error: null,
                loading: false,
            }
        },
        methods: {
            async login() {
                this.loading = true;
                this.error = null;
                try {
                    const result = await axios.post('/api/authentication', {
                        username: this.username,
                        password: this.password
                    });

                } catch (err) {
                    if (err.response.status === 401) {
                        this.error = "Incorrect username or password.";
                    }
                }
                this.loading = false;
            }
        }
    }
</script>

<template>
    <AuthPage title="Login">

        <b-alert variant="danger"
                 dismissible
                 :show="error != null"
                 @dismissed="error = null">
            {{ error }}
        </b-alert>

        <form method="post" @submit.prevent="login">

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" required
                       id="username" placeholder="Username" v-model="username">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" required
                       id="password" placeholder="Password" v-model="password">
            </div>

            <button type="submit" class="btn btn-primary btn-block btn-lg" :disabled="loading">
                Login
                <i class="fas fa-arrow-right"></i>
            </button>

        </form>

        <div class="alert alert-secondary text-center mt-4 mb-0">
            Don't have an account yet?
            <router-link tag="button" to="/register" class="btn btn-secondary btn-sm">
                Register
            </router-link>
        </div>

    </AuthPage>

</template>