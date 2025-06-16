<script setup>
import MainLayout from '@/Layouts/MainLayout.vue';
import { RouterView } from 'vue-router';

</script>

<template>
    <MainLayout>
        <RouterView>
        </RouterView>
    </MainLayout>

</template>

<style scoped>
.media-contain{
    display: block;
    max-width: 240px; /* Mudar isso depois para responsividade */
}
.media-content{
    width: 100%;
}
</style>