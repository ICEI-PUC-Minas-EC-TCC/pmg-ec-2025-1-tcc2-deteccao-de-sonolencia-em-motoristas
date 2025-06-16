<script setup>
import { computed } from 'vue';

const props = defineProps({
    tipo: {
        type: String,
        default(){
            return 'warning';
        }
    },
    titulo: {
        type: String,
        default(){
            return "Aviso";
        }
    },
    styling: {
        type: String,
        default(){
            return "";
        }
    }
});

const emit = defineEmits(['close']);

const messageType = computed(() => {
    const base = "msgicon bi ";
    switch(props.tipo){
        case "error":
            return base + "bi-exclamation-triangle-fill use-error";
        case "warning":
            return base + "bi-exclamation-triangle-fill use-warning";
        case "info":
            return base + "bi-info-circle-fill use-info";
        case "success":
            return base + "bi-check-circle-fill use-success";
        default:
            return base + "bi-exclamation-triangle-fill use-warning";
    }
});


function close(){
    emit('close',true);
}

</script>

<template>
    <div class="container-mensagem">
        <div class="content-mensagem d-flex flex-column justify-content-center align-items-center">
            <i :class="messageType"></i>
            <h2>{{ titulo }}</h2>
            <p>
                <slot></slot>
            </p>
            <button class="btn btn-danger" @click="close">Fechar</button>
        </div>
    </div>
</template>

<style scoped>
.container-mensagem{
    position: absolute;
    z-index: 99;
    left: 25%;
    top: 25%;
    background-color: var(--cor-universo-escuro);
    border: solid 1px rgba(20,20,20,0.8);
    border-radius: 20px;
}
.content-mensagem{
    color: var(--cor-branca);
    font-size: 20px;
    padding: 10px;
}
.msgicon{
    font-size: 60px;
}
.use-warning{ color: yellow; }
.use-info{ color: white; }
.use-error{ color: red; }
.use-success{ color: green; }
</style>