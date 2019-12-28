<script>
import AlertMessage from '@/components/AlertMessage';

export default {
    name: 'AuthPage',
    components: { AlertMessage },
    props: ['title'],
};
</script>

<template>
    <div class="col d-flex justify-content-center align-items-center">
        <div class="page-box">
            <AlertMessage />
            <h1 class="pb-2 mb-4 border-bottom">{{ title }}</h1>
            <slot></slot>
        </div>
    </div>
</template>
