<script setup>
import {RouterLink } from 'vue-router';
</script>

<template>
<div class="smallspace">

</div>
<footer class="footerbg text-light">
    <div class="d-none d-md-flex justify-content-between">
        <p class="m-0 p-0"><span class="negrito">Copyright Drivewatch</span> - Todos direitos reservados
            <br> Trabalho desenvolvido como parte da disciplina PISH - 2024-1
        </p>
    </div>
    <div class="d-flex d-md-none justify-content-center">
        <nav class="d-flex align-items-center gap-3 gap-sm-5">
            <RouterLink :to="{name: 'index'}" class="text-decoration-none text-light d-flex flex-column align-items-center">
                <i class="fs-2 bi bi-house-door-fill"></i>
                <span class="nav-item-text">Dashboard</span>
            </RouterLink>
            <RouterLink :to="{name: 'devices'}" class="text-decoration-none text-light d-flex flex-column align-items-center">
                <i class="fs-2 bi bi-file-person-fill"></i>
                <span class="nav-item-text">Dispositivos</span>
            </RouterLink>
            <RouterLink :to="{name: 'events'}" class="text-decoration-none text-light d-flex flex-column align-items-center">
                <i class="fs-2 bi bi-clock-history"></i>
                <span class="nav-item-text">Eventos</span>
            </RouterLink>
            <RouterLink :to="{name: 'account'}" class="text-decoration-none text-light d-flex flex-column align-items-center">
                <i class="fs-2 bi bi-gear-fill"></i>
                <span class="nav-item-text">Conta</span>
            </RouterLink>
        </nav>
    </div>
</footer>
</template>
<style scoped>
i:hover{
    color: var(--cor-dourado-escuro);
}
.nav-item-text{
    font-size: 1rem;
}
.footerbg{
    background-color: var(--cor-universo);
    color: var(--cor-branca);
    width: 100%;
    bottom: 0;
    padding-left: 40px;
    padding-right: 40px;
    padding-top: 20px;
    padding-bottom: 40px; /* to stop free-tinyhost thingy from hiding the footer */
}
.smallspace{
    display: none;
}
@media (max-width: 768px) { 
    footer{
        position: fixed;
        bottom: 0;
    }
    .smallspace{
        display: block;
        margin: 40px;
    }
    .nav-item-text{
    font-size: 0.7rem;
}
}
</style>