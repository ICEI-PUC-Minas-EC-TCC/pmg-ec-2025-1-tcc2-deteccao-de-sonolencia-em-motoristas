<template>
    <div class="deviceCreateContainer">
        <h3 class="text-center">Registrar Novo Dispositivo Drivewatch</h3>
        <FormLayout v-if="state.loaded" :on-submit="handleSubmit" :data="state" method="POST">
            <label for="inputSelectCompany">Empresa Associada</label>
            <select name="inputSelectCompany" id="inputSelectCompany" v-model="state.idCompany">
                <option v-for="(item) of state.companies" :id="`company-${item.id}`" :value="item.id">{{ item.name }}</option>
            </select>
            <label for="inputPlate">Placa do Veículo Instalado</label>
            <input type="text" name="inputPlate" id="inputPlate" v-model="state.plate" />
            <label for="inputVersion">Versão Drivewatch</label>
            <input type="text" name="inputVersion" id="inputVersion" v-model="state.version" />
            <input type="submit" class="btn btn-info my-2" value="Registrar">
        </FormLayout>
        <div v-else class="mx-auto my-4 p-1 text-center fs-4">
            <p class="loadingTextStyle">Aguarde...</p>
        </div>
        <div class="mx-auto p-1 text-danger text-center fs-4">
            <p v-html="state.msg"></p>
        </div>
    </div>
</template>
<script setup>
import { reactive, onMounted } from 'vue';
import { Fetcher } from '@/api.js';
import * as CONSTANTS from '@/config.js';
import FormLayout from '@/Layouts/FormLayout.vue';

const fetcher = new Fetcher();
const state = reactive({
    idCompany: null,
    plate: '',
    version: null,
    msg: '',
    loaded: false,
    companies: []
});

const runEveryMount = async (fetcher) => {
    // Populate the companies array to use
    try{
        const companies = await fetcher.useFetch(CONSTANTS.URL_COMPANY_GETALL);
        if(companies){
            state.companies = [...companies];
            state.loaded = true; // Shows form
        }
    }catch(error){
        console.error(error);
        state.msg = error.message;
    }
};

onMounted(async () => await runEveryMount(fetcher));

const handleSubmit = async () => {
    try{
        if(!state.idCompany || !state.plate || !state.version){
            throw new Error("Erro: Dados insuficientes ou mal formatados!");
        }
        const newDevice = {idCompany: state.idCompany, plate: state.plate, version: state.version};
        const response = await fetcher.usePostRequest(CONSTANTS.URL_DEVICE, newDevice);
        state.msg = response ? "<span class='text-success'>Sucesso!</span>" : "Erro!";
    }catch(error){
        console.error(error);
        state.msg = error.message;
    }
};

</script>
<style scoped>
h3{
    color: var(--cor-branca);
}
.deviceCreateContainer{
    color: var(--cor-branca);
    background-color: none;
}
.loadingTextStyle{
    color: var(--cor-dourado-escuro);
}
</style>