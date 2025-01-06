<script setup>
import { inject, provide, reactive, ref } from "vue";
import EmailInput from "../ProfileInputs/EmailInput.vue";
import UsernameInput from "../ProfileInputs/UsernameInput.vue";
import BirthdayInput from "../ProfileInputs/BirthdayInput.vue";
import PrenameInput from "../ProfileInputs/PrenameInput.vue";
import PasswordInputs from "../ProfileInputs/PasswordInputs.vue";
import SurnameInput from "../ProfileInputs/SurnameInput.vue";

// Injected properties
const account = inject('account');

// Refs to child components
const inputComps = {
    email: ref(null),
    username: ref(null),
    password: ref(null),
};

const inputVals = reactive({
    prename: "",
    surname: "",
    birthday: "",
    email: "",
    username: "",
    password: "",
    passwordConfirm: ""
});

// Provide properties for child components and expose functions to parent component
provide('inputVals', inputVals);
defineExpose({
    inputVals,
    populateInputs,
    validateInputs
});

function populateInputs(account) {
    inputVals.prename = account.prename;
    inputVals.surname = account.surname;
    inputVals.birthday = account.birthday;
    inputVals.email = account.email;
    inputVals.username = account.username;
}

async function validateInputs() {
    let isValid = true;
    if (!validatePasswords()) {
        isValid = false;
    }

    if (!await validateUsername(account.id)) {
        isValid = false;
    }

    if (!await validateEmail(account.id)) {
        isValid = false;
    }

    return isValid;
}

function validatePasswords() {
    return inputComps.password.value.validatePasswords();
}

async function validateEmail(accountId) {
    return await inputComps.email.value.validateEmail(accountId);
}

async function validateUsername(accountId) {
    return await inputComps.username.value.validateUsername(accountId);
}
</script>

<template>
    <section class="set-personal-infos">
        <fieldset>
            <legend>Persönliche Daten</legend>
            <SurnameInput />
            <PrenameInput />
            <BirthdayInput />
            <EmailInput :ref="inputComps.email" />
        </fieldset>
        <fieldset>
            <legend>Anmeldeinformationen</legend>
            <UsernameInput :ref="inputComps.username" />
            <PasswordInputs :ref="inputComps.password" />
        </fieldset>
    </section>
</template>

<style scoped>
.set-personal-infos {
    display: flex;
    grid-area: set-personal-infos;
    flex-direction: column;
}

.set-personal-infos>fieldset {
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: space-evenly;
}
</style>