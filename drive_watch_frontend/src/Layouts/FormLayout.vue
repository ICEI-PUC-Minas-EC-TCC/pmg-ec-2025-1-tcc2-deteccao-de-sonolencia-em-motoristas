<script setup>
defineProps({
    data: {
        type: Object,
        required: true
    },
    onSubmit: {
        type: Function,
        required: true
    },
    action: {
        type: String,
        required: false,
        default(){
            return "#";
        }
    },
    method: {
        type: [String, Array],
        required: false,
        default(){
            return "POST";
        }
    },
    classes: {
        type: String,
        required: false,
        default(){
            return "d-flex flex-column justify-content-center align-items-center my-2 py-1 gap-1";
        }
    }
});
</script>

<template>
    <form 
    @submit.prevent="onSubmit" 
    :action="action" 
    :method="method" 
    v-bind:class="classes"
    >
        <slot />
    </form>
</template>

<style>
form{
    max-width: 100%;
}
</style>