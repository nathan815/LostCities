<script>
    export default {
        data() {
            return {
                navIsActive: false,
            };
        },
        methods: {
            toggleMenu: () => this.navIsActive = !this.navIsActive
        },
    }
</script>
<template>
    <nav class="navbar navbar-expand-lg navbar-dark bg-tan">
        <div class="container">
            <router-link class="navbar-brand" to="/">Lost Cities</router-link>

            <div class="collapse navbar-collapse">

                <ul class="navbar-nav mr-auto">
                    <router-link tag="li" to="/dashboard" class="nav-item" exact exact-active-class="active">
                        <a class="nav-link">Games</a>
                    </router-link>
                    <router-link tag="li" to="/games" class="nav-item" active-class="active">
                        <a class="nav-link">Users</a>
                    </router-link>
                </ul>

                <ul class="navbar-nav">

                    <router-link tag="li" to="/login" class="nav-item" exact exact-active-class="active">
                        <a class="nav-link">Login</a>
                    </router-link>

                    <b-nav-item-dropdown text="My Account" right>
                        <b-dropdown-item href="#">EN</b-dropdown-item>
                        <b-dropdown-item href="#">ES</b-dropdown-item>
                        <b-dropdown-item href="#">RU</b-dropdown-item>
                        <b-dropdown-item href="#">FA</b-dropdown-item>
                    </b-nav-item-dropdown>

                </ul>
            </div>
        </div>
    </nav>

</template>