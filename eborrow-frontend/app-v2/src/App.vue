<script setup>
import { RouterView } from 'vue-router'
import NavigationHeader from './components/NavigationHeader.vue'
</script>

<template>
  <NavigationHeader /><!-- Zak: in die einzelnen Views einbauen, die einen NavigationHeader benoetigen -->
  <RouterView />
</template>