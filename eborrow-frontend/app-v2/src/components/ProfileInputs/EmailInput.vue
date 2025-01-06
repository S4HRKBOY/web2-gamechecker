<script setup>
import { ref } from 'vue';
import * as useAccountGraphQLApi from "@/composables/useAccountGraphQLApi.js";
import { inject } from 'vue';

const inputVals = inject('inputVals');

const inputRef = ref(null);

function populateInput(mail) {
    inputVals.email = mail;
}

async function validateEmail(accountId) {
    const emailInput = inputRef.value;
    const emailLowercase = inputVals.email.toLowerCase();
    if (!emailLowercase) {
        emailInput.setCustomValidity("Bitte geben Sie eine E-Mail Adresse ein.");
        emailInput.reportValidity();

        return false;
    }

    let emailTaken;
    try {
        emailTaken = await useAccountGraphQLApi.isEmailUsedByOtherAccount(accountId, emailLowercase);

        if (emailTaken !== false && emailTaken !== true) {
            throw new Error("Unexpected response from server.");
        }
    } catch (err) {
        console.error(err);

        return false;
    }

    if (emailTaken === true) {
        emailInput.setCustomValidity("Diese E-Mail Adresse wird bereits verwendet.");
        emailInput.reportValidity();

        return false;
    }

    return true;
}

function resetValidity() {   
    inputRef.value.setCustomValidity('')
}

defineExpose({
    populateInput,
    validateEmail
});
</script>

<template>
    <div class="form-input">
        <label for="email">E-Mail Adresse</label>
        <input
            type="email"
            id="email"
            name="email"
            required
            v-model="inputVals.email"
            ref="inputRef"
            @input="resetValidity">
    </div>
</template>

<style>
.form-input {
    display: flex;
    column-gap: 10px;
}

.form-input>label {
    flex: 2;
}

.form-input>input {
    flex: 3;
}
</style>