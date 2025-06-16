<script setup>
import {RouterLink} from 'vue-router';
import HomeContent from '@/Layouts/HomeContent.vue';
</script>

<template>
    <main class="container-fluid">
        <div class="row justify-content-start">
            <h1 class="display">Meu Drivewatch</h1>
            <div class="col my-2 home-menu-item">
                <RouterLink
                    class="d-flex flex-column align-items-center d-flex flex-column justify-content-center text-decoration-none"
                    :to="{name: 'devices'}">
                    <i class="bi bi-webcam-fill fs-1"></i>
                    Dispositivos
                </RouterLink>
            </div>
            <div class="col my-2 home-menu-item">
                <RouterLink
                    class="d-flex flex-column align-items-center d-flex flex-column justify-content-center text-decoration-none"
                    :to="{name: 'events'}">
                    <i class="bi bi-calendar-event-fill fs-1"></i>
                    Eventos
                </RouterLink>
            </div>
            <div class="col my-2 home-menu-item">
                <RouterLink
                    class="d-flex flex-column align-items-center d-flex flex-column justify-content-center text-decoration-none"
                    :to="{name: 'devices'}">
                    <i class="bi bi-buildings-fill fs-1"></i>
                    Empresas
                </RouterLink>
            </div>
            <div class="col my-2 home-menu-item">
                <RouterLink
                    class="d-flex flex-column align-items-center d-flex flex-column justify-content-center text-decoration-none"
                    :to="{name: 'account'}">
                    <i class="bi bi-person-fill fs-1"></i>
                    Conta
                </RouterLink>
            </div>
        </div>
        <div class="row my-2">
            <h1 class="fs-4">Eventos Recentes</h1>
        </div>
        <HomeContent></HomeContent>
    </main>
</template>

<style>
a{
    color: var(--cor-universo-claro);
}
a:hover{
    color: var(--cor-dourado-claro);
}
.home-menu-item:hover{
    transform: scale(1.08);
    transition: all 0.5s;
}
@media (max-width: 768px) { 
    .home-menu-item:hover{
        transform: scale(1);
    }
}
</style>