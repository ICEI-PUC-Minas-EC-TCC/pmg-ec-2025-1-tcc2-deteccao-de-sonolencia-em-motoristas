<script setup>
import FormLayout from '@/Layouts/FormLayout.vue';
import Message from '@/Components/Message.vue';
import {Fetcher} from '@/api.js';
import { reactive } from 'vue';


// O componente precisa do endpoint a ser enviada a busca
const props = defineProps({
    endpoint: {
        type: String,
        required: true
    }
});

// Double-way Binding pra adicionar o resultado da pesquisa
const emits = defineEmits({
    'result': (data) => {
        if(Array.isArray(data)){
            return true;
        }
        else {
            console.warn("Emit em formato invalido: Not Array");
            return false;
        }
    }
});
const sendEmit = (value) => emits('result', value);

// Classe que padroniza os fetches
// Construtor padrao ja cria com o endpoint base
const fetcher = new Fetcher();

const formData = reactive({
    query: 0,
    dados: [],
    msg: '',
    hasError: false,
    warning: '',
    isLoading: false
});

const resetMessages = () => {
    formData.msg = '';
    formData.hasError = false;
};

const isValid = (data) => {
    let valid = true;
    
    if(!data.query && data.query !== 0){
        valid = false;
        data.msg += "Um ID deve ser fornecido! ";
        data.warning = data.msg;
        data.hasError = true;
    }

    if(!(/^\d+$/.test(data.query))){
        valid = false;
        data.msg += "Um ID deve ser numérico! ";
        data.warning = data.msg;
        data.hasError = true;
    }

    if(data.query < 0){
        valid = false;
        data.msg += "Um ID válido deve ser fornecido! ";
        data.warning = data.msg;
        data.hasError = true;
    }

    return valid;
};

const onSubmit = async () => {
    // Se for outra tentativa, limpe o controle de error
    resetMessages();
    formData.warning = '';
    formData.isLoading = true;
    formData.dados = []; // Limpa lixo de pesquisas anteriores

    // Bloco try-catch da validacao e requisicao
    try{
        if(isValid(formData)){
            console.log("DEBUG: Realizando fetch para o endpoint: ", props.endpoint + `/${formData.query}`);
            const response = await fetcher.useFetch(props.endpoint + `/${formData.query}`);
            // emitir novos:
            formData.dados.push(response);
            sendEmit(formData.dados);
        }else{
            throw new Error("Erro Local: Nao foi possivel enviar a requisicao! ");
        }
    }catch(error){
        console.error(error);
        formData.msg += error.message;
        formData.hasError = true;
    }finally{
        formData.isLoading = false;
    }
};

// Resetaa as mensagens, fechando o componente Message devido ao v-if.
const closeMsg = () => resetMessages();

</script>

<template>
    <Message v-if="formData.hasError" @close="closeMsg">
        <p>{{ formData.msg }}</p>
    </Message>
    <FormLayout method="POST" v-bind:on-submit="onSubmit" v-bind:data="formData" >
        <label for="searchInput">Busca por ID:</label>
        <input type="number" name="query" id="searchInput" v-model="formData.query"  required/>
        <input type="submit" class="btn btn-primary" value="Go" />
    </FormLayout>
    <span v-if="formData.isLoading" class="bg-dark text-warning p-2">Aguarde, Carregando...</span>
    <section v-if="formData.warning.length > 0" class="formWarnings d-flex justify-content-center align-items-start text-danger">
        {{ formData.warning }}
    </section>
</template>

<style>
input{
    border-radius: 10px;
    border: 1px solid var(--cor-universo);
    padding: 5px;
    max-width: 100%;
}
</style>