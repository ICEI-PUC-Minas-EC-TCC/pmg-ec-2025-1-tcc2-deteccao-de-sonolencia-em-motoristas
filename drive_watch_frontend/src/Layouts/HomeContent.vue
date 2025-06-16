<script setup>
import {ref, onMounted} from 'vue';
import {fetchData} from '@/api.js';
import { URL_REGISTER } from '@/config.js';

import Message from '@/Components/Message.vue';
import LoadingItem from '@/Components/LoadingItem.vue';
import MediaComponent from '@/Components/MediaComponent.vue';

const dados = ref([]); //REATIVO
const resposta = ref("");
const mostrarErro = ref(false);
const loading = ref(false);


function sortLatest () {
  if(dados.value.length > 0){
    dados.value.sort((a,b) => {
      return (new Date(b.occurrenceDate)) - (new Date (a.occurrenceDate));
    });
  }
}

// Executa apos mounted
onMounted(async () => {
  loading.value = true;
  try {
    //console.log("Realizando fetch para " + URL_REGISTER);
    const response = await fetchData(URL_REGISTER);
    //console.log(response);
    dados.value = response;
    sortLatest();
  } catch (error) {
    console.error('Error fetching data:', error);
    resposta.value = "Erro: " + error.message;
    mostrarErro.value = true;
  }finally{
    loading.value = false;
  };
});

function closeMsg(){
  mostrarErro.value = false;
}

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

</script>
<template>
    <div v-if="mostrarErro" class="warning">
      <Message titulo="Erro!" @close="closeMsg">
        Ocorreu um erro na requisição. <br> 
        <span class="text-danger">{{resposta}}</span>
      </Message>
    </div>
    <div class="row justify-content-between" v-if="dados.length > 0">
            <div class="col-12 col-md-6 col-lg text-center my-1" v-for="(item,index) of (dados.slice(0,3))" :id="`register_${index}`">
                <div class="border rounded py-1">
                    <div class="icontainer" style="object-fit: contain;">
                      <MediaComponent :source="item.image" :is64="true" alt="Fotografia obtida do dispositivo" style="width: 400px;  max-width: 100%;">
                      </MediaComponent>
                    </div>
                    <p class="my-0 py-0" style="font-size: medium;"><span class="negrito">Classe: </span> {{ formatar_tipo(item.type) }}</p>
                    <p class="my-0 py-0" style="font-size: small;"><span class="negrito">Data:</span> {{ formatar_data(item.occurrenceDate) }}</p>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg text-center my-1 d-flex flex-column justify-content-center align-items-center">
                <RouterLink :to="{name: 'events'}" class="p-0 fs-4 text-decoration-none">
                  <i class="bi bi-three-dots fs-1"></i>
                </RouterLink>
            </div>
        </div>
        <div class="row" v-else>
            <div v-if="loading" class="text-center fs-5">
                <LoadingItem></LoadingItem>
                <p>Carregando...</p>
            </div>
            <p v-else class="text-center fs-5">Sem dados para exibir...</p>
        </div>
</template>