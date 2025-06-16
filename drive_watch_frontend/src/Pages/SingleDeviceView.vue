<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import * as CONSTS from '@/config.js';
import {Fetcher} from '@/api.js';
import Message from '@/Components/Message.vue';
import MediaComponent from '@/Components/MediaComponent.vue';

const route = useRoute();
const hasMsg = ref(false);
const msg = ref('');
const loading = ref(false);
const device_data = reactive({
    id_device: route.params.id,
    plate: '',
    version: '',
    company: '',
    phone: '',
    registers: []
});

const runEveryMount = async () => {
    const fetcher = new Fetcher();
    hasMsg.value = false;
    msg.value = '';
    loading.value = true;

    if(!device_data.id_device){
        msg.value = "Erro: ID invalido!";
        hasMsg.value = true;
    }
    else{
        try{
            // Fetches the device in question
            const device_response = await fetcher.useFetch(CONSTS.URL_DEVICE + `/${device_data.id_device}`);
            // Fetches the company by device
            const company_response = await fetcher.useFetch(CONSTS.URL_COMPANY + "/" + device_response.idCompany);
            // Fetches the phone by device. Not required.
            const phone_response = await fetcher.useFetchAllowEmpty(CONSTS.URL_PHONE_BY_DEVICE + device_data.id_device);
            // Debug
            //console.log(device_response, company_response, phone_response);
            // Se OK
            device_data.plate = device_response.plate;
            device_data.version = device_response.version;
            device_data.company = company_response.name;
            if(!phone_response) device_data.phone = "Não registrado!";
            else device_data.phone = `(${phone_response.areaCode}) ${phone_response.phoneNumber}`;
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

const getRegisters = async () => {
    const fetcher = new Fetcher();
    
    if(!device_data.id_device){
        msg.value = "Erro: ID invalido!";
        hasMsg.value = true;
    }else{
        try{
            const register_response =  await fetcher.useFetch(CONSTS.URL_REGISTER_BY_DEVICE + device_data.id_device);
            device_data.registers = register_response;
            //console.log(device_data.registers);
        }catch(error){
            console.error(error);
            msg.value = error.message;
            hasMsg.value = true;
        }
    }
};

function formatar_data(datestring){
    const newDate = new Date(datestring);
    const options = {
        weekday: 'short',
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric',
        timeZone: 'America/Sao_Paulo'
      };
      return newDate.toLocaleString('pt-BR', options);
}

function formatar_tipo(tipo){
    switch(tipo){
        case 'SLEEPING':
            return 'DORMINDO';
        case 'AWAKE':
            return 'ACORDADO';
        default:
            return tipo;
    }
}

// Resets Message
const close = () => {hasMsg.value = false; msg.value = '';}

</script>

<template>
<main class="container-fluid">
    <section class="row">
        <div class="container-fluid">
            <Message v-if="hasMsg"  @close="close">
                <p>{{ msg }}</p>
            </Message>
        </div>
        <p v-if="loading" class="m-4 text-center fs-2">Carregando...</p>
        <section class="col-md-3 col-12">
            <div v-if="device_data.company" class="mx-2 my-1 p-1 d-flex flex-column justify-content-start align-items-center">
                    <i class="bi bi-music-player-fill deviceIcon"></i>
                    <h2 class="text-center"><strong>Dispositivo:</strong> <span>{{ device_data.id_device }}</span></h2>
                    <p class="text-start"><strong>Empresa:</strong> <span>{{ device_data.company }}</span></p>
                    <p class="text-start"><strong>Placa:</strong> <span>{{ device_data.plate }}</span></p>
                    <p class="text-start"><strong>Telefone:</strong> <span>{{ device_data.phone }}</span></p>
                    <p class="text-end">Version: <span>{{ device_data.version }}</span></p>
                    <button @click="getRegisters" v-if="device_data.registers.length === 0" class="btn btn-primary">Registros</button>
            </div>
            <div v-else-if="!loading">
                <p class="mx-auto my-2 text-center p-1 fs-2">Houveram erros na obtenção de informações do dispositivo.<br> Tente novamente!</p>
            </div>
        </section>
        <section class="col-md-9 col-12">
            <div v-if="device_data.registers.length > 0" class="d-flex justify-content-center align-items-center gap-1 registercontainer">
                <div v-for="(item,index) of device_data.registers" :id="'dispositivo_'+index" class="mx-0 my-1 p-1 smallcard">
                    <div class="d-flex justify-content-around align-items-center">
                        <MediaComponent v-bind:source="item.image" :is64="true" alt="Captura Obtida do Dispositivo"></MediaComponent>
                    </div>
                    <p class="text-center">{{ formatar_tipo(item.type) }}</p>
                    <p class="text-center">{{ formatar_data(item.occurrenceDate) }}</p>
                </div>
            </div>
        </section>
    </section>
</main>
</template>

<style scoped>
.deviceIcon{
    font-size: 40pt;
    text-align: center;
    color: rgb(40,40,40);
    padding: 0 auto;
    margin: 0 auto;
}
.registercontainer{
    flex-wrap: wrap;
    font-size: 12pt;
}
.smallcard{
    font-size: 10pt;
    max-width: 100%;
    border: 1px solid gray;
    border-radius: 10px;
}
.smallcard img{
    max-width: 100%;
    width: 190px;
    height: 140px;
    border-radius: 6px;
}
</style>