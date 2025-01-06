<script setup>
import { inject } from 'vue';

const inputVals = inject('inputVals');

function yesterday() {
    let yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    yesterday = yesterday.toISOString().split("T")[0];

    return yesterday;
}
</script>

<template>
    <div class="form-input">
        <label for="birthday">Geburtsdatum</label>
        <input 
            type="date"
            id="birthday" 
            name="birthday"
            :max="yesterday()"
            v-model="inputVals.birthday">
    </div>
</template>

<style scoped>
@import 'src/assets/css/inputs/inputs.css';
</style>