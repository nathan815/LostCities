<script>
    export default {
        methods: {
            logout() {
                alert('logout')
            }
        }
    }
</script>
<template>

    <b-navbar toggleable="md" type="dark" variant="primary">
        <div class="container">

            <b-navbar-brand to="/">Lost Cities</b-navbar-brand>
            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse is-nav id="nav-collapse">

                <b-navbar-nav>
                    <b-nav-item to="/dashboard" class="nav-item" exact>
                        Games
                    </b-nav-item>
                    <b-nav-item to="/games" class="nav-item">
                        Users
                    </b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">

                    <b-nav-item to="/login" class="nav-item">Login</b-nav-item>

                    <b-nav-item to="/register" class="nav-item">Register</b-nav-item>

                    <b-nav-item-dropdown text="My Account" right>
                        <b-dropdown-item to="/settings">Settings</b-dropdown-item>
                        <b-dropdown-item @click.prevent="logout">Logout</b-dropdown-item>
                    </b-nav-item-dropdown>

                </b-navbar-nav>
            </b-collapse>

        </div>
    </b-navbar>

</template>