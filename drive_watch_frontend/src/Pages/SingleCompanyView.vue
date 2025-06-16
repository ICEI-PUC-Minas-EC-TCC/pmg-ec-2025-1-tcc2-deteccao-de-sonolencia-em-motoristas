<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import * as CONSTS from '@/config.js';
import {Fetcher} from '@/api.js';
import Message from '@/Components/Message.vue';
import FormLayout from '@/Layouts/FormLayout.vue';

const route = useRoute();
const hasMsg = ref(false);
const msgType = ref("warning");
const msg = ref('');
const loading = ref(false);
const isUpdate = ref(false);
const shouldCreateAddress = ref(false);
const data = reactive({
    id: route.params.id,
    name: '',
    email: '',
    contract: '',
    address: {}
});

const runEveryMount = async () => {
    const fetcher = new Fetcher();
    hasMsg.value = false;
    msg.value = '';
    loading.value = true;

    if(!data.id){
        msg.value = "Erro: ID invalido!";
        hasMsg.value = true;
    }
    else{
        try{
            // Fetches the device in question
            const company = await fetcher.useFetch(CONSTS.URL_COMPANY + `/${data.id}`);
            // Fetches the company by device
            const address_response = await fetcher.useFetchAllowEmpty(CONSTS.URL_ADDRESS_BY_COMPANY + data.id);
            
            // Debug
            //console.log(company, address_response);

            // Se OK
            data.name = company.name;
            data.email = company.email;
            data.contract = company.contract;
            data.address = address_response;

            // Se por acaso nao tiver um endereco, ele deve ser criado
            if (!address_response) {
                shouldCreateAddress.value = true;
                data.address = {idCompany: data.id, zipCode: '', street: '', number: '', neighborhood: '', city: '', state: '', country: ''};
            }
            

        }catch(error){
            console.error(error);
            msg.value = error.message;
            hasMsg.value = true;
        }finally{
            loading.value = false;
        }
    }
};

onMounted(async () => await runEveryMount());


const isValid = (data) => {
    let valid = true;
    
    if(!data.id || data.id < 0){
        valid = false;
        msg.value += "Um ID valido deve ser fornecido! ";
        hasMsg.value = true;
    }

    if(!data.name || !data.email){
        valid = false;
        msg.value += "Um Nome e E-mail devem ser fornecidos! ";
        hasMsg.value = true;
    }
    /*
    if(!(/^\d+$/.test(data.query))){
        valid = false;
        data.msg += "Um ID deve ser numérico! ";
        data.warning = data.msg;
        data.hasError = true;
    }*/

    return valid;
};

const sendUpdate = async () => {
    const fetcher = new Fetcher();
    msg.value = '';
    

    try{
        if(!isValid(data)){
            throw new Error("Não foi possível enviar a requisição!");
        }

        const newCompany = {name: data.name, email: data.email};
        const newAddress = {...data.address};
        delete newAddress.id;
        let response = await fetcher.usePutRequest(CONSTS.URL_COMPANY + `/${data.id}`, newCompany);

        // Checar se o Address deve ser criado pela flag
        if(shouldCreateAddress.value){
            response = await fetcher.usePostRequest(CONSTS.URL_ADDRESS, newAddress);
        }else{
            response = await fetcher.usePutRequest(CONSTS.URL_ADDRESS + `/${data.address.id}`, newAddress);
        }

        if(!response){
            throw new Error("Houve algum erro inesperado, tente novamente!");
        }else {
            msg.value = "Alterações realizadas com sucesso!";
            msgType.value = "success";
            hasMsg.value = true;
        }

    }catch(error){
        console.error(error);
        msg.value += error.message;
        hasMsg.value = true;
    }

};

// Resets Message if its not form warnings
const close = () => {hasMsg.value = false; msg.value = (isUpdate.value) ? msg.value : ''; };

// Shows Update Form
const showUpdateForm = () => {isUpdate.value = !isUpdate.value; };

</script>

<template>
<main class="container-fluid">
    <section class="row">
        <div class="container-fluid">
            <Message v-if="hasMsg" :tipo="msgType"  @close="close">
                <p>{{ msg }}</p>
            </Message>
        </div>
        <p v-if="loading" class="m-4 text-center fs-2 col-12">Carregando...</p>
        <section class="col">
            <div v-if="data.name || data.email || data.id" class="mx-2 my-1 p-1">
                <div v-if="!isUpdate" class="d-flex flex-column justify-content-start align-items-center">
                    <h2 class="text-center"><strong>Empresa:</strong> <span>{{ data.name }}</span></h2>
                    <p class="text-start"><strong>E-mail:</strong> <span>{{ data.email }}</span></p>
                    <h3 class="text-center">Endereço</h3>
                    <p class="text-start"> <span class="negrito">Estado:</span> <span>{{ data.address.state }}</span></p>
                    <p class="text-start"><span class="negrito">Cidade:</span><span>{{ data.address.city }}</span></p>
                    <p class="text-start"><span class="negrito">Rua</span> {{ data.address.street }}</p>
                    <p class="text-start"><span class="negrito">Bairro: </span>{{ data.address.neighborhood }}(num {{ data.address.number }})</p>
                    <p class="text-start"><strong>ZIP:</strong> <span>{{ data.address.zipCode }}</span></p>
                    <p class="text-start"><strong>País:</strong> <span>{{ data.address.country }}</span></p>
                    <p class="text-end escondido">ID: <span>{{ data.id }}</span></p>
                    <button @click="showUpdateForm" class="btn btn-warning">Editar</button>
                </div>
                <div v-else>
                    <h2 class="text-center">Editar Empresa:</h2>
                    <FormLayout v-bind:on-submit="sendUpdate" v-bind:data="data">
                        <label for="inputCompanyName">Empresa:</label>
                        <input type="text" name="inputCompanyName" id="inputCompanyName" v-model="data.name" required/>
                        <label for="inputCompanyEmail">E-mail:</label>
                        <input type="email" name="inputCompanyEmail" id="inputCompanyEmail" v-model="data.email" required/>
                        <label for="inputCompanyContract">Contrato:</label>
                        <input type="text" name="inputCompanyContract" id="inputCompanyContract" v-model="data.contract" />
                        <label for="inputAddressCity">Cidade:</label>
                        <input type="text" name="inputAddressCity" id="inputAddressCity" v-model="data.address.city" />
                        <label for="inputAddressNeighborhood">Bairro:</label>
                        <input type="text" name="inputAddressNeighborhood" id="inputAddressNeighborhood" v-model="data.address.neighborhood" />
                        <label for="inputAddressStreet">Rua:</label>
                        <input type="text" name="inputAddressStreet" id="inputAddressStreet" v-model="data.address.street" />
                        <label for="inputAddressNumber">Num:</label>
                        <input type="text" name="inputAddressNumber" id="inputAddressNumber" v-model="data.address.number" />
                        <label for="inputAddressState">Estado:</label>
                        <input type="text" name="inputAddressState" id="inputAddressState" v-model="data.address.state" />
                        <label for="inputAddressCountry">País:</label>
                        <input type="text" name="inputAddressCountry" id="inputAddressCountry" v-model="data.address.country" />
                        <label for="inputAddressZIP">ZIP:</label>
                        <input type="text" name="inputAddressZIP" id="inputAddressZIP" v-model="data.address.zipCode" />
                        <input type="submit" class="btn btn-success" value="Enviar">
                    </FormLayout>
                    <div v-if="msg.length > 0" class="text-danger p-2 rounded fs-4" id="formMessageBoxContainer">
                        {{ msg }}
                    </div>
                </div>
            </div>
            <div v-else-if="!loading">
                <p class="mx-auto my-2 text-center p-1 fs-3">Houveram erros na obtenção de informações da empresa.<br> Tente novamente!</p>
            </div>
        </section>
    </section>
</main>
</template>

<style scoped>
.escondido{
    display: none;
}
</style>