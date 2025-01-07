<script setup>
import { ref } from 'vue';
import * as useAccountGraphQLApi from "@/composables/useAccountGraphQLApi.js";
import { inject } from 'vue';

const inputVals = inject('inputVals');

const inputRef = ref(null);

defineExpose({validateUsername});

async function validateUsername(accountId = null) {
    const usernameInput = inputRef.value;

    if (!usernameInput.value) {
        usernameInput.setCustomValidity("Bitte geben Sie einen Benutzernamen ein.");
        usernameInput.reportValidity();

        return false;
    }

    let usernameTaken;
    try {
        if(accountId !== null 
            && accountId !== '' 
            && Number.isInteger(Number(accountId))) {
            usernameTaken = await useAccountGraphQLApi.isUsernameUsedByOtherAccount(accountId, usernameInput.value);
        } else {
            usernameTaken = await useAccountGraphQLApi.isUsernameTaken(usernameInput.value);
        }

        if (usernameTaken !== false && usernameTaken !== true) {
            throw new Error("Unexpected response from server.");
        }
    } catch (err) {
        console.error(err);

        return false;
    }

    if (usernameTaken === true) {
        usernameInput.setCustomValidity("Dieser Benutzername ist bereits vergeben.");
        usernameInput.reportValidity();

        return false;
    }

    return true;
}

function resetValidity() {   
    inputRef.value.setCustomValidity('')
}
</script>

<template>
    <div class="form-input">
        <label for="username">Benutzername</label>
        <input 
            type="text" 
            id="username" 
            name="username" 
            required
            v-model="inputVals.username"
            ref="inputRef"
            @input="resetValidity"
            >
    </div>
</template>

<style scoped>
@import '../../assets/css/inputs/inputs.css';
</style>