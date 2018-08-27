<script>
    import {mapState} from 'vuex';

    export default {
        computed: mapState({
            show: state => !!state.alert.message,
            message: state => state.alert.message,
            variant: state => state.alert.variant,
        }),
        methods: {
            dismiss() {
                this.$store.dispatch('alert/dismiss');
            }
        }
    }
</script>

<template>
    <b-alert :show="show" :variant="variant" @dismissed="dismiss" dismissible>{{ message }}</b-alert>
</template>