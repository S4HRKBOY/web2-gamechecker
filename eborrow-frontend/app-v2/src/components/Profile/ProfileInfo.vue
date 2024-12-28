<script setup>
import { inject } from "vue";

const account = inject('account');
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
                        <span>{{ new Date(account.birthday).toLocaleDateString('de-DE', { day: '2-digit', month: '2-digit', year: 'numeric' }) }}</span>
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

<style scoped>
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