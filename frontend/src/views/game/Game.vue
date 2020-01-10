<script>
import Vue from 'vue';
import { Component } from 'vue-property-decorator';

@Component
export default class Game extends Vue {
    get id() {
        return this.$route.params.id;
    }
}
</script>

<template>
    <div>
        <h1>Game {{ id }}</h1>
    </div>
</template>
