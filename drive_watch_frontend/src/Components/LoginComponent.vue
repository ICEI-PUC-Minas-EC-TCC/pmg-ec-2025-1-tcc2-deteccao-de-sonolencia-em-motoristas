<script setup>
import FormLayout from '@/Layouts/FormLayout.vue';
import Message from '@/Components/Message.vue';
import {Fetcher} from '@/api.js';
import {URL_ACESSO} from '@/config.js';
import { reactive } from 'vue';

// Classe que padroniza os fetches
// Construtor padrao ja cria com o endpoint base
const fetcher = new Fetcher();

const formData = reactive({
    username: '',
    password: '',
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
    
    if(!data.username || !data.password){
        valid = false;
        data.msg += "Um nome de usuário e senha devem ser fornecidos! ";
        data.warning = data.msg;
        data.hasError = true;
    }

    if(data.password.length < 8 || data.password.length > 20){
        valid = false;
        data.msg += "O tamanho da senha deve estar entre 8 e 20 caracteres ";
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

    // Bloco try-catch da validacao e requisicao
    try{
        if(isValid(formData)){
            
            const response = await fetcher.useFetch(URL_ACESSO);
        }else{
            throw new Error("Erro Local: Nao foi possivel enviar a requisicao! ");
        }
    }catch(error){
        console.error(error);
        formData.msg += error;
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
    <FormLayout v-bind:on-submit="onSubmit" v-bind:data="formData" >
        <label for="usernameInput">Username:</label>
        <input type="text" name="username" id="usernameInput" v-model="formData.username" />
        <label for="passwordInput">Senha:</label>
        <input type="password" name="password" id="passwordInput" v-model="formData.password" />
        <input type="submit" class="btn btn-primary" value="Login" />
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