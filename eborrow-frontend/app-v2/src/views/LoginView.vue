<script setup>
import { reactive } from "vue";
import { RouterLink } from "vue-router";
import router from "@/router";
import * as useAccountApi from "@/composables/useAccountGraphQLApi.js";

const form = reactive({
    username: "",
    password: ""
});

async function onSubmitLogin() {
    let accountId;
    try {
        accountId = await useAccountApi.fetchAccountId(form.username, form.password);
    } catch (error) {
        console.error(error);
        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");

        return;
    }

    if(accountId === null) {
        alert("Benutzername oder Passwort falsch.");
        return;
    }
    const publisher = await useAccountApi.getAccountById(accountId, ['publisher']);
    sessionStorage.setItem('accountId', JSON.stringify(accountId));
    sessionStorage.setItem('publisher', JSON.stringify(publisher.publisher));
    router.push({ name: 'home' });
}
</script>

<template>
    <main>
        <article id="content">
            <section class="login-section">
                <figure class="logo">
                    <img src="../assets/images/logo.svg" alt="Logo von Game-Tracker">
                </figure>
                <form @submit.prevent="onSubmitLogin">
                    <div class="form-input"><input type="text" v-model="form.username" placeholder="Benutzername" required></div>
                    <div class="form-input"><input type="password" v-model="form.password" placeholder="Passwort" required></div>
                    <div class="form-button"><button type="submit">Login</button></div>
                </form>
            </section>
            <RouterLink to="/register" class="register-link">Neuen Account registrieren</RouterLink>
        </article>
    </main>
</template>

<style scoped>
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

.login-section {
    display: flex;
}

.logo {
    flex: 2;
}

.logo>img {
    width: 100%;
}

form {
    flex: 3;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.form-input {
    flex: 4;
}

.form-input>* {
    width: 100%;
    height: 100%;
}

.form-button {
    flex: 5;
    display: flex;
    justify-content: center;
    align-items: center;
}

.form-button>* {
    width: 60%;
    height: 80%;
}

.register-link {
    display: block;
    margin-top: 10px;
    font-size: smaller;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;    text-align: center;
}
</style>
