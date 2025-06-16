<script setup>
import {ref} from 'vue';
import LoginComponent from '@/Components/LoginComponent.vue';
</script>

<template>
    <main class="container-fluid">
        <section id="landing-container" class="d-flex flex-column justify-content-center align-items-center">
            <img src="/drivewatch.png" alt="Drivewatch Icon" class="logo mx-auto my-0" />
            <p>Olá, bem-vindo(a) ao Drivewatch!</p>
            <nav class="d-flex justify-content-evenly align-items-center gap-2">
                <button class="btn btn-primary">Usuário</button>
                <button class="btn btn-info">Funcionário</button>
            </nav>
            <LoginComponent />
        </section>
    </main>
</template>

<style scoped>
main{
    background-color: var(--cor-universo);
    padding-bottom: 400px;
}
.logo{
    width: 200px;
}
#landing-container{
    max-width: 100%; /* isso previne que saia do container em mobiles */
    border: solid 1px rgb(255, 255, 255);
    border-radius: 20px;
    background-color: rgba(255, 255, 255, 0.6);
    color: #000000;
    margin: 0px 25% 8px 25%;
    padding: 10px 10px;
}
@media (max-width: 768px) { 
    .logo{
        width: 160px;
    }
}
</style>