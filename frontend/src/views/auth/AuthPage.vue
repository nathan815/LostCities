<script>
    export default {
        name: "AuthPage",
        props: [ 'title' ],
    }
</script>

<template>

    <div class="col d-flex justify-content-center align-items-center">

        <div class="page-box">
            <h1 class="pb-2 mb-4 border-bottom">{{ title }}</h1>
            <slot></slot>

        </div>

    </div>

</template>