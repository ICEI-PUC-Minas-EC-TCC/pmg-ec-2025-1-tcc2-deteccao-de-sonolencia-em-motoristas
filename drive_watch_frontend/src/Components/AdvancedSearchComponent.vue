<script setup>
import FormLayout from '@/Layouts/FormLayout.vue';
import Message from '@/Components/Message.vue';
import { reactive } from 'vue';


// O componente precisa do array de dados a ser efetuada a busca
const props = defineProps({
    data: {
        type: [Array, Object],
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


const formData = reactive({
    queryId: 0,
    queryClass: 'all',
    queryDate: null,
    queryDateOp: 'any',
    dados: props.data,
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
    if(!data.queryId){
        data.queryId = null;
    } else if (!(/^\d+$/.test(data.queryId))) {
        valid = false;
        data.msg += "Um ID deve ser numérico! ";
        data.warning = data.msg;
        data.hasError = true;
    }

    if(!data.queryClass || !data.queryDateOp){
        valid = false;
        data.msg += "Erro inesperado: alguns dados estão ausentes!";
        data.warning = data.msg;
        data.hasError = true;
    }

    if(data.queryClass !== 'sleeping' && data.queryClass !== 'awake' && data.queryClass !== 'all'){
        valid = false;
        data.msg += "Um tipo válido de classe deve ser fornecido!";
        data.warning = data.msg;
        data.hasError = true;
    }

    if(data.queryDateOp !== 'any' && data.queryDateOp !== 'asc' && data.queryDateOp !== 'dsc' && data.queryDateOp !== 'exact'){
        valid = false;
        data.msg += "Um tipo válido de ordenamento por data deve ser fornecido! Encontrado: " + data.queryDateOp;
        data.warning = data.msg;
        data.hasError = true;
    }

    return valid;
};

const handleSearch = () => {
    // Reseta mensagens de erro:
    resetMessages();
    formData.warning = '';
    formData.isLoading = true;
    // Mantem a data requerida no formato correto.
    const tgDate = new Date(formData.queryDate);
    try{
        // Testa a validacao
        if(!isValid(formData)) throw new Error("Formulario Inválido!");
        // Filtra os dados com base nas condicoes estabelecidas
        formData.dados = formData.dados.filter((el) => {
            let returnIt = true;
            const elDate = new Date(el.occurrenceDate);
            if(formData.queryId !== null && Number(el.idDevice) !== Number(formData.queryId)) returnIt = false;
            if(formData.queryClass !== "all" && el.type !== formData.queryClass.toUpperCase()) returnIt = false;
            if(formData.queryDate){
                switch (formData.queryDateOp) {
                    case 'exact':
                        if(elDate.getDate() !== tgDate.getUTCDate()) returnIt = false;
                        break;
                    case 'asc':
                        if(elDate.getDate() < tgDate.getUTCDate()) returnIt = false;
                        break;
                    case 'dsc':
                        if(elDate.getDate() > tgDate.getUTCDate()) returnIt = false;
                        break;
                    default:
                        //nothing
                        break;
                }
            }
            return returnIt;
        });
        // Assumindo que deu certo
        sendEmit(formData.dados);
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
    <FormLayout method="POST" v-bind:on-submit="handleSearch" v-bind:data="formData" classes="d-flex flex-column justify-content-center align-items-center my-2 py-1 gap-1">
        <h3 class="text-center fs-2 text-dark mx-auto p-1">Busca Avançada</h3>
        <div>
            <label for="searchId">ID do device:</label>
            <input class="mx-1" type="number" name="searchId" id="searchId" v-model="formData.queryId" placeholder="ID do Device"/>
        </div>
        <div>
            <label for="searchClass">Tipo</label>
            <select class="mx-1" name="searchClass" id="searchClass" v-model="formData.queryClass">
                <option value="all" selected>Todos</option>
                <option value="awake">Acordado</option>
                <option value="sleeping">Dormindo</option>
            </select>
        </div>
        <div class="d-flex flex-column gap-1 justify-content-center align-items-center">
            <label for="searchDate">Data</label>
            <div>
                <select name="searchDateOption" id="searchDateOption" v-model="formData.queryDateOp" class="mx-1">
                    <option value="asc">Maior que</option>
                    <option value="dsc">Menor que</option>
                    <option value="exact">Igual a</option>
                    <option value="any" selected>Qualquer</option>
                </select>
                <input type="date" name="searchDate" id="searchDate" v-model="formData.queryDate"/>
            </div>
            <label for="searchDate" class="reducefont">(Deixe sem valor para desconsiderar)</label>
        </div>
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
.reducefont{
    font-size: smaller;
}
</style>