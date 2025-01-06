<script setup>
import { inject, provide, reactive, ref } from "vue";
import EmailInput from "../ProfileInputs/EmailInput.vue";
import UsernameInput from "../ProfileInputs/UsernameInput.vue";

// Injected properties
const account = inject('account');

// Refs for child components
const emailInputComp = ref(null);
const usernameInputComp = ref(null);

const inputVals = reactive({
    prename: "",
    surname: "",
    birthday: "",
    email: "",
    username: "",
    password: "",
    passwordConfirm: ""
});

const inputRefs = {
    password: ref(null),
    passwordConfirm: ref(null)
};

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
    const passwordInput = inputRefs.password.value;
    const passwordConfirmInput = inputRefs.passwordConfirm.value;

    if (passwordInput.value !== passwordConfirmInput.value) {
        passwordConfirmInput.setCustomValidity("Die eingegebenen Passwörter stimmen nicht überein.");
        passwordConfirmInput.reportValidity();

        return false;
    }

    return true;
}

async function validateEmail(accountId) {
    return emailInputComp.value.validateEmail(accountId);
}

async function validateUsername(accountId) {
    return usernameInputComp.value.validateUsername(accountId);
}

function yesterday() {
    let yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    yesterday = yesterday.toISOString().split("T")[0];

    return yesterday;
}
</script>

<template>
    <section class="set-personal-infos">
        <fieldset>
            <legend>Persönliche Daten</legend>
            <div class="form-input">
                <label for="surname">Name</label>
                <input type="text" v-model="inputVals.surname" id="surname" name="surname" required>
            </div>
            <div class="form-input">
                <label for="prename">Vorname</label>
                <input type="text" v-model="inputVals.prename" id="prename" name="prename" required>
            </div>
            <div class="form-input">
                <label for="birthday">Geburtsdatum</label>
                <input 
                    type="date"
                    v-model="inputVals.birthday"
                    id="birthday" 
                    name="birthday"
                    :max="yesterday()">
            </div>
            <EmailInput ref="emailInputComp" />
            <!-- <div class="form-input">
                <label for="email">E-Mail Adresse</label>
                <input 
                type="email" 
                v-model="inputVals.email"
                :ref="inputRefs.email"
                @input="inputRefs.email.value.setCustomValidity('')"
                id="email" 
                name="email" 
                required>
            </div> -->
        </fieldset>
        <fieldset>
            <legend>Anmeldeinformationen</legend>
            <UsernameInput ref="usernameInputComp" />
            <!-- <div class="form-input">
                <label for="username">Benutzername</label>
                <input 
                    type="text" 
                    v-model="inputVals.username" 
                    :ref="inputRefs.username"
                    @input="inputRefs.username.value.setCustomValidity('')"
                    id="username" 
                    name="username" 
                    required>
            </div> -->
            <div class="form-input">
                <label for="password">Passwort</label>
                <input
                    type="password"
                    v-model="inputVals.password"
                    :ref="inputRefs.password"
                    @input="inputRefs.password.value.setCustomValidity('')"
                    id="password" 
                    name="password" 
                    required>
            </div>
            <div class="form-input">
                <label for="password-confirm">Passwort wiederholen</label>
                <input 
                    type="password" 
                    v-model="inputVals.passwordConfirm"
                    :ref="inputRefs.passwordConfirm"
                    @input="inputRefs.passwordConfirm.value.setCustomValidity('')" 
                    id="password-confirm" 
                    required>
            </div>
        </fieldset>
    </section>
</template>

<style scoped>
input:invalid,
select:invalid {
    border-color: red;
    outline: none;
}

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