<script setup>
import { RouterView } from 'vue-router'
import CopyrightFooter from './components/CopyrightFooter.vue';
</script>

<template>
    <!-- <NavigationHeader /> --> <!-- manche views besitzen keinen header: als component in einzelne Views einbauen -->
    <RouterView />
    <CopyrightFooter />
</template>
