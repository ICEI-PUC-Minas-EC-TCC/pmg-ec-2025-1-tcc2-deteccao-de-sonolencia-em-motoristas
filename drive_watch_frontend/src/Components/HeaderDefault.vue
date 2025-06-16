<script setup>
import {RouterLink} from 'vue-router';
import HeaderLogo from '@/Components/HeaderLogo.vue';
</script>

<template>
    <header>
        <div class="d-flex align-items-center justify-content-around justify-content-md-between headerbg">
            <div class="logo">
                <RouterLink :to="{name: 'index'}">
                    <HeaderLogo></HeaderLogo>
                </RouterLink>
            </div>
            <nav class="d-none d-md-flex gap-3 mx-2">
                <RouterLink class="headeritem negrito" :to="{name: 'index'}">Dashboard</RouterLink>
                <RouterLink class="headeritem negrito" :to="{name: 'devices'}">Dispositivos</RouterLink>
                <RouterLink class="headeritem negrito" :to="{name: 'events'}">Eventos</RouterLink>
                <RouterLink class="headeritem negrito" :to="{name: 'company'}">Empresas</RouterLink>
            </nav>
            <nav class="d-none d-md-flex gap-3 mx-2">
                <RouterLink class="headeritem negrito" :to="{name: 'account'}">Sua Conta</RouterLink>
                <RouterLink class="headeritem negrito" :to="{name: 'config'}">Config</RouterLink>
            </nav>
        </div>
    </header>
</template>

<style scoped>
.headerbg{
    background-color: var(--cor-universo);
}
.headerbg img{
    width: 110px;
}
.headeritem{
    color: var(--cor-branca);
    text-decoration: none;
    font-size: 18px;
}
.headeritem:hover{
    color: var(--cor-dourado-escuro);
    font-size: 20px;
}
@media (max-width: 768px) { 
    header{
        padding-bottom: 124px;
    }
    .headerbg{
        position: fixed;
        z-index: 20;
        width: 100%;
    }
    .headerbg img{
        width: 120px;
    }
}
</style>