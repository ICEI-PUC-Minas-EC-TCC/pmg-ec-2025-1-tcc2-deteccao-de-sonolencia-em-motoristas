<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { URL_COMPANY, URL_COMPANY_GETALL } from '@/config.js';
import {Fetcher} from '@/api.js';
import SearchComponent from '@/Components/SearchComponent.vue';
import LoadingItem from '@/Components/LoadingItem.vue';
import Message from '@/Components/Message.vue';

const dados = ref([]);
const msg = ref("");
const mostrarErro = ref(false);
const loading = ref(false);
const router = useRouter();

onMounted(async () => {
    const fetcher = new Fetcher();
    loading.value = true;
    try{
        const response = await fetcher.useFetch(URL_COMPANY_GETALL);
        //console.log(response);
        dados.value = response;
    }catch(error){
        console.error('Error fetching data:', error);
        // Axios error has a message attribute
        msg.value = " " + error.message;
        mostrarErro.value = true;
    }finally{
        loading.value = false;
        // Apenas para debug:
        //dados.value = [{name: "SuperMart", email: "super"},{name: "Josjwcnwj", email: "sisxijwi"}];
    }
});

/**
 * Redireciona para uma route de uma empresa unica a partir do ID
 * @param {String} query 
 */
 function redirecionar(query){
  router.push({
    name: 'company_update',
    params: {id: query}
  });
}

function getSearch(data){
    // Obtem os dados do Emit do SearchComponent e joga no ref dados.
    dados.value = data;
}

function closeMsg(){
  mostrarErro.value = false;
}

</script>

<template>

<main>
    <div v-if="mostrarErro" class="warning">
      <Message titulo="Erro!" @close="closeMsg">
        Ocorreu um erro na requisição. <br>
        <span class="text-danger">{{msg}}</span>
      </Message>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-12 text-center m-4 p-2">
                <SearchComponent :endpoint="URL_COMPANY" @result="getSearch" />
            </div>
        </div>
        <div v-if="loading" class="text-center m-2 p-2"><LoadingItem></LoadingItem> Carregando...</div>
        <div v-if="dados.length === 0 && !loading" class="fs-4 text-center py-4">Nenhuma empresa foi encontrada</div>
        <div v-if="dados.length > 0" class="row">
            <div class="col mx-2 my-1 p-1 text-center" v-for="(item,index) of dados" :id="'empresa' + index"  >
                <i class="bi bi-building-fill company-icon"></i>
                <h4 @click="() => redirecionar(item.id)" style="cursor: pointer">{{ item.name }}</h4>
                <p class="text-center">{{ item.email }}</p>
            </div>
        </div>
    </div>
</main>
</template>

<style scoped>
.company-icon{
    display: inline-block;
    font-size: 18pt;
    text-align: center;
}
</style>