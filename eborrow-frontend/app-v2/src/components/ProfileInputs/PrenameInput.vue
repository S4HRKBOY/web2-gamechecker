<script setup>
import { inject } from 'vue';

const inputVals = inject('inputVals');
</script>

<template>
    <div class="form-input">
        <label for="prename">Vorname</label>
        <input 
            type="text" 
            id="prename" 
            name="prename" 
            minlength="2"
            maxlength="50"
            required
            v-model="inputVals.prename">
    </div>
</template>

<style scoped>
@import '../../assets/css/inputs/inputs.css';
</style>