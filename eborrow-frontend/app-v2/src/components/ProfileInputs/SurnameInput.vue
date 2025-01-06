<script setup>
import { inject } from 'vue';

const inputVals = inject('inputVals');
</script>

<template>
    <div class="form-input">
        <label for="surname">Name</label>
        <input 
            type="text"
            id="surname"
            name="surname"
            minlength="2"
            maxlength="50"
            required
            v-model="inputVals.surname">
    </div>
</template>

<style scoped>
@import '../../assets/css/inputs/inputs.css';
</style>