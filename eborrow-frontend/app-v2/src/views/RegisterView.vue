<script setup>
import SurnameInput from '@/components/ProfileInputs/SurnameInput.vue';
import PrenameInput from '@/components/ProfileInputs/PrenameInput.vue';
import UsernameInput from '@/components/ProfileInputs/UsernameInput.vue';
import BirthdayInput from '@/components/ProfileInputs/BirthdayInput.vue';
import EmailInput from '@/components/ProfileInputs/EmailInput.vue';
import PasswordInputs from '@/components/ProfileInputs/PasswordInputs.vue';
import { provide, ref, reactive } from 'vue';
import router from "@/router";
import * as useAccountApi from "@/composables/useAccountRestApi.js";

const inputComps = {
    email: ref(null),
    username: ref(null),
    password: ref(null)
};

const inputVals = reactive({
    prename: "",
    surname: "",
    username: "",
    birthday: "",
    email: "",
    password: "",
    passwordConfirm: ""
});

provide('inputVals', inputVals);

async function createAccount() {
    let inputsValid;
    try {
        inputsValid = await validateInputs();
    } catch (error) {
        console.error(error);
        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
    }

    if (!inputsValid) {
        return;
    }

    const account = {
        prename: inputVals.prename,
        surname: inputVals.surname,
        username: inputVals.username,
        birthday: inputVals.birthday,
        email: inputVals.email,
        password: inputVals.password
    };

    useAccountApi.createAccount(account)
        .then(() => {
            router.push(`/login`);
        })
        .catch((err) => {
            console.error(err);
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
}

async function validateInputs() {
    let isValid = true;
    if (!validatePasswords()) {
        isValid = false;
    }

    if (!await validateUsername()) {
        isValid = false;
    }

    if (!await validateEmail()) {
        isValid = false;
    }

    return isValid;
}

function validatePasswords() {
    return inputComps.password.value.validatePasswords();
}

async function validateEmail() {
    return await inputComps.email.value.validateEmail();
}

async function validateUsername() {
    return await inputComps.username.value.validateUsername();
}
</script>

<template>
    <main>
        <article id="content">
            <section class="register-section">
                <figure class="logo">
                    <RouterLink to="/"><img src="../assets/images/logo.svg" alt="Logo von Game-Tracker"></RouterLink>
                </figure>
                <!-- TODO -->
                <form class="register-form" @submit.prevent="createAccount">
                    <fieldset>
                        <SurnameInput />
                        <PrenameInput />
                        <UsernameInput :ref="inputComps.username" />
                        <BirthdayInput />
                    </fieldset>
                    <fieldset>
                        <EmailInput :ref="inputComps.email" />
                        <PasswordInputs :ref="inputComps.password" />
                    </fieldset>
                    <div class="form-button"><button type="submit">KOMM IN
                            DIE GRUPPE!</button></div>
                </form>
            </section>
            <RouterLink to="/login" class="login-link">Ich habe bereits einen Account</RouterLink>
        </article>
    </main>
</template>

<style scoped>
* {
    box-sizing: border-box;
}

main {
    display: flex;
    flex-grow: 1;
    justify-content: center;
    align-items: center;
}

#content {
    border: 1px solid hsl(0, 0%, 75%);
    border-radius: 5px;
    padding: 20px;
    width: 50%;
}

.register-section {
    display: flex;
    align-items: center;
}

.logo {
    flex: 2;
}

.logo img {
    width: 100%;
}

.register-form {
    display: flex;
    flex: 3;
    flex-direction: column;
    gap: 10px;
}

.register-form>* {
    flex: 1;
}

.register-form fieldset {
    display: flex;
    flex-direction: column;
    gap: 2vh;
    border: 1px solid hsl(0, 0%, 75%);
    border-radius: 5px;
    padding: 15px;
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

.form-button {
    display: flex;
    flex-grow: 3;
    justify-content: center;
    align-items: center;
}

.form-button>* {
    width: 62%;
}

.login-link {
    display: block;
    margin-top: 10px;
    font-size: smaller;
    text-align: center;
}
</style>