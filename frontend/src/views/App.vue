<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Header from './Header.vue';

@Component({
    components: {
        Header,
    },
})
export default class App extends Vue {}
</script>

<template>
    <div>
        <Header />
        <div class="container main-container">
            <router-view />
        </div>
    </div>
</template>

<style lang="scss" src="../assets/styles/app.scss" />
