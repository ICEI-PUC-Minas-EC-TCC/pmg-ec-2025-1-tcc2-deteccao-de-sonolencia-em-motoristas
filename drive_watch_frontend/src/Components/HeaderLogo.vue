<template>
    <img :src="source" alt="Drivewatch Logotipo" />
</template>
<script setup>
import { FRONTEND_BASE } from '@/config.js';
//const source = FRONTEND_BASE + '/drivewatch.png';
const source = '/drivewatch.png';
</script>