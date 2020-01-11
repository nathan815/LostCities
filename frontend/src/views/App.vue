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
        <div class="main-container">
            <router-view />
        </div>
    </div>
</template>
