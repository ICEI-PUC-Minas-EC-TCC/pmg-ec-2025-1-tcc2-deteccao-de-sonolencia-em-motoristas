<script setup>
import HeaderDefault from '@/Components/HeaderDefault.vue';
import FooterDefault from '@/Components/FooterDefault.vue';

</script>
<template>
    <HeaderDefault />
    <div id="content">
        <span id="goto"></span>
        <slot />
    </div>
    <FooterDefault />
</template>

<style>
#content{
    min-height: 100vh;
}
</style>