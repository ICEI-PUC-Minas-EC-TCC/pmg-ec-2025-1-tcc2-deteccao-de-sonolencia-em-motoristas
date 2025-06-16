<script setup>
import { URL_COMPANY } from '../config';
import { Fetcher } from '@/api.js';
import { reactive } from 'vue';

const mock = reactive({
        name: '',
        email: '',
        contract: ''
});

const mockDevice = reactive({
    idCompany: null,
    plate: '',
    version: null
});

const mockupCompany = async (instance, url, params = '') => {
    let example = {name: mock.name, email: mock.email, contract: mock.contract};
    try {
        const response = await instance.usePostRequest(url, example);
        console.log(response.data); // Retorno do metodo
    }catch(error){
        console.error('Error fetching data:', error);
        throw error;
    }
};

const mockit = () => {
        const fetcher = new Fetcher();
        mockupCompany(fetcher, URL_COMPANY);
};
</script>

<template>
<section class="container-fluid configurationSection">
        <h1 class="display text-center">Configurações</h1>
        <section class="row">
                <article  class="col-12 border-1 rounded m-1 p-1 flex justify-evenly items-center">
                        Projeto desenvolvido como parte da disciplina PISH em 2024.<br>
                        Grupo:<br> Cristian<br>
                        Dayane<br>
                        Gabriel B<br>
                        Guilherme<br>
                </article>
        </section>
        <article class="row">
                <h2>Inserir nova empresa: </h2>
                <form @submit.prevent="mockit" action="#" method="POST" class="d-flex flex-column justify-content-center align-items-center gap-1">
                        <label for="inputCompanyName">Nome da Empresa: </label>
                        <input type="text" name="inputCompanyName" id="inputCompanyName" v-model="mock.name"/>
                        <label for="inputCompanyEmail">E-mail: </label>
                        <input type="email" name="inputCompanyEmail" id="inputCompanyEmail" v-model="mock.email"/>
                        <label for="inputCompanyContract">Contrato: </label>
                        <input type="text" name="inputCompanyContract" id="inputCompanyContract" v-model="mock.contract" />
                        <input type="submit" value="Registrar" />
                </form>
        </article>
</section>
</template>

<style scoped>
.configurationSection{
       overflow-x: hidden;
}
</style>