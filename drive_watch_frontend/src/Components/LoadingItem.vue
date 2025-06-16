<template>
    <img class="rodar" :src="source" width="100" alt="Carregando... aguarde!" />
</template>

<script setup>
import { FRONTEND_BASE } from '@/config.js';
//const source = FRONTEND_BASE + '/drivewatch.png';
const source = '/drivewatch.png';
</script>

<style scoped>
.rodar{
    width: 100px;
    height: 100px;
    user-select: none;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;

    animation: roda 2s infinite linear;
    
}

@keyframes roda{
    0% {
        transform: rotate(0);
    }
    100% {
        transform: rotate(360deg);
    }
}
</style>