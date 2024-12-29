<script setup>
import { RouterView } from 'vue-router'
import NavigationHeader from './components/NavigationHeader.vue'
</script>

<template>
  <!-- <NavigationHeader /> --> <!-- manche views besitzen keinen header -> als component in einzelne Views einbauen -->
  <RouterView />
</template>
