<script>
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import alert from '@/store/modules/alert';

@Component
export default class AlertMessage extends Vue {
    get show() {
        return !!this.message;
    }
    get message() {
        return alert.state.message;
    }
    get variant() {
        return alert.state.variant;
    }
    dismiss() {
        alert.dismiss();
    }
}
</script>

<template>
    <b-alert :show="show" :variant="variant" @dismissed="dismiss" dismissible>
        {{ message }}
    </b-alert>
</template>
