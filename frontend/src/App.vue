<script>
    import Navbar from '@/Navbar';
    import AlertMessage from "./components/AlertMessage";

    export default {
        components: { AlertMessage, Navbar }
    }
</script>
<template>
    <div>

        <Navbar />

        <div class="container main-container">
            <AlertMessage />
            <router-view />
        </div>

    </div>
</template>

<style lang="scss" src="./assets/styles/app.scss"></style>