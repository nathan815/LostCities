<script>
    import Navbar from '@/Navbar';

    export default {
        components: { Navbar }
    }
</script>
<template>
    <div>

        <Navbar />

        <div class="container main-container">
            <router-view />
        </div>

    </div>
</template>

<style lang="scss" src="./assets/styles/app.scss"></style>