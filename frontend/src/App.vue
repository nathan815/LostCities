<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Navbar from './Navbar.vue';

@Component({
    components: {
        Navbar,
    },
})
export default class App extends Vue {}
</script>

<template>
    <div>
        <Navbar />
        <div class="container main-container">
            <router-view />
        </div>
    </div>
</template>

<style lang="scss" src="./assets/styles/app.scss" />
