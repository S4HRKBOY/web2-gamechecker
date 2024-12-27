<script setup>
import * as useAccountApi from "@/composables/useAccountRestApi.js";
import { onMounted } from "vue";
import { useRoute } from "vue-router";
import { reactive } from 'vue';

const route = useRoute();
// TODO: Reuse account from parent component
const account = reactive({});

onMounted(() => {
    useAccountApi.getAccountById(route.params.id, true)
        .then(acc => Object.assign(account, acc))
        .catch(err => {
            console.error(err);
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        })
});
</script>

<template>
    <section id="profile-info">
        <h2>Persönliche Daten</h2>
        <table>
            <tbody>
                <tr v-if="account.username">
                    <td>Benutzername</td>
                    <td>
                        <span>{{account.username}}</span>
                    </td>
                </tr>
                <tr v-if="account.prename">
                    <td>Vorname</td>
                    <td>
                        <span>{{account.prename}}</span>
                    </td>
                </tr>
                <tr v-if="account.surname">
                    <td>Nachname</td>
                    <td>
                        <span>{{account.surname}}</span>
                    </td>
                </tr>
                <tr v-if="account.birthday">
                    <td>Geburtsdatum</td>
                    <td>
                        <span>{{account.birthday}}</span>
                    </td>
                </tr>
                <tr v-if="account.email">
                    <td>E-Mail</td>
                    <td>
                        <span>{{account.email}}</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </section>
</template>

<style>
#profile-info {
    grid-area: profile-info;
    place-self: center;
}

#profile-info {
    margin: 1em;
    border: 1px solid black;
    border-radius: 10px;
    padding: 10px;
}

#profile-info h2 {
    padding-left: 10px;
    padding-right: 10px;
}

#profile-info h2 {
    margin: 0.2em 0;
}

#profile-info td {
    vertical-align: top;
    padding: 5px 10px;
}

#profile-info ul {
    margin: 0;
    padding: 0;
}

#profile-info li {
    list-style: none;
}
</style>