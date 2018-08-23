<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                username: "",
                password: ""
            }
        },
        methods: {
            async login() {
                const result = await axios.post('/api/rest-login', {
                    username: this.username,
                    password: this.password
                });
                console.log(result);
            }
        }
    }
</script>

<template>
    <div>
        <h1>Login</h1>
        <form action="/login" method="post" @submit.prevent="login">
            <div>
                <label> Email:
                    <input type="text" name="username"  v-model="username"/>
                </label>
            </div>
            <div>
                <label> Password:
                    <input type="password" name="password" v-model="password" />
                </label>
            </div>
            <div><input type="submit" value="signin"/></div>
        </form>
    </div>
</template>

<style scoped>

</style>