<script setup>
import { useRoute } from "vue-router";
import * as useAccountApi from "@/composables/useAccountRestApi.js";
import NavigationHeader from '../components/NavigationHeader.vue';
import PersonalInfos from '../components/ProfileEdit/PersonalInfos.vue';
import ProfilePic from '../components/ProfileEdit/ProfilePic.vue';
import router from "@/router";

const route = useRoute();
const accountId = route.params.id;

function deleteAccount() {
    if (!confirm("Möchten Sie Ihr Profil wirklich löschen?")) {
        return;
    }

    useAccountApi.deleteAccount(accountId)
        .then(() => {
            router.push("/login");
        })
        .catch((err) => {
            console.error(err)
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
}
</script>

<template>
    <NavigationHeader />
    <main>
        <article id="content">
            <section class="update-section">
                <form class="update-form">
                    <section class="form-content">
                        <PersonalInfos></PersonalInfos>
                        <ProfilePic></ProfilePic>
                    </section>
                    <section class="send-form-options">
                        <RouterLink :to="`/account/${accountId}`" class="cancel-link">Abbrechen</RouterLink>
                        <button type="submit">Änderungen speichern</button>
                    </section>
                </form>
                <form class="delete-profile" @submit.prevent="deleteAccount">
                    <button type="submit">Profil löschen</button>
                </form>
            </section>
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
    width: 50%;
}

.form-content {
    display: grid;
    grid-template-areas: "set-personal-infos set-profile-pic";
    grid-template-columns: 2fr 1fr;
    gap: 15px;
}

fieldset {
    border: 1px solid hsl(0, 0%, 75%);
    border-radius: 10px;
    padding: 10px;
}

.send-form-options {
    display: flex;
    justify-content: end;
    gap: 10px;
    margin-top: 10px;
}

.delete-profile {
    display: inline;
    position: relative;
    top: -21px;
}

.delete-profile>* {
    color: red;
}
</style>