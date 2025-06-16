<template>
    <img :src="media" :alt="props.alt">
</template>
<script setup>
    import { computed } from 'vue';
    import { FRONTEND_BASE } from '@/config';
    const props = defineProps({
        source: {
            type: String,
            required: true,
            default() {
                return '#';
            }
        },
        is64: {
            type: Boolean,
            required: false,
            default() {
                return false;
            }
        },
        alt: {
            type: String,
            required: false,
            default() {
                return "Foto";
            }
        }
    });
    const media = computed(() => {
        const prepend = 'data:image/png;base64,';
        if(props.source.length <= 20){
            //return FRONTEND_BASE + 'driver.svg';
            return '/driver.svg';
        }
        else {
            return prepend + props.source;
        }
    });
</script>