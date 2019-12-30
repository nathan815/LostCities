<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Navbar from './Navbar.vue';
import AlertMessage from './components/AlertMessage.vue';

@Component({
    components: {
        Navbar, AlertMessage
    },
})
export default class App extends Vue {}
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

<style lang="scss" src="./assets/styles/app.scss" />
