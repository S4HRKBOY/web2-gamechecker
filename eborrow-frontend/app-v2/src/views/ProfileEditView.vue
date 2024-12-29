<script setup>
import { useRoute } from "vue-router";
import * as useAccountApi from "@/composables/useAccountRestApi.js";
import NavigationHeader from '../components/NavigationHeader.vue';
import PersonalInfosEdit from '../components/ProfileEdit/PersonalInfosEdit.vue';
import ProfilePicEdit from '../components/ProfileEdit/ProfilePicEdit.vue';
import router from "@/router";
import { ref, reactive, provide, onMounted } from "vue";

const route = useRoute();

// Refs for child components
const personalInfosEdit = ref(null);
const profilePicEdit = ref(null);

const account = reactive({});
provide('account', account);

onMounted(() => {
    useAccountApi.getAccountById(route.params.id)
        .then(acc => Object.assign(account, acc))
        .catch(err => {
            console.error(err);
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
});

async function deleteAccount() {
    if (!confirm("Möchten Sie Ihr Profil wirklich löschen?")) {
        return;
    }

    useAccountApi.deleteAccount(account.id)
        .then(() => {
            router.push("/login");
        })
        .catch((err) => {
            console.error(err)
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
}

async function updateAccount() {
    const inputsValid = await validateInputs();
    if (!inputsValid) {
        return;
    }

    const inputsPersonalInfos = personalInfosEdit.value?.inputVals;
    if (!inputsPersonalInfos) {
        console.error("SOME ERROR MESSAGE");
        return;
    }

    const loadedImage = profilePicEdit.value?.loadedImage;
    
    if (!loadedImage) {
        console.error("SOME ERROR MESSAGE");
        return;
    }

    const accountUpdates = {
        id: account.id,
        prename: inputsPersonalInfos.prename,
        surname: inputsPersonalInfos.surname,
        username: inputsPersonalInfos.username,
        birthday: inputsPersonalInfos.birthday,
        email: inputsPersonalInfos.email,
        password: inputsPersonalInfos.password,
        profilePicture: loadedImage
    };

    useAccountApi.updateAccount(accountUpdates)
        .then(() => {
            router.push(`/account/${account.id}`);
        })
        .catch((err) => {
            console.error(err)
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
}

async function validateInputs() {
    let isValid = true;

    if (!profilePicEdit.value?.validateInputs()) {
        isValid = false;
    }

    if (!await personalInfosEdit.value?.validateInputs()) {
        isValid = false;
    }

    return isValid;
}
</script>

<template>
    <NavigationHeader />
    <main>
        <article id="content">
            <section class="update-section">
                <form class="update-form" @submit.prevent="updateAccount">
                    <section class="form-content">
                        <PersonalInfosEdit ref="personalInfosEdit"></PersonalInfosEdit>
                        <ProfilePicEdit ref="profilePicEdit"></ProfilePicEdit>
                    </section>
                    <section class="send-form-options">
                        <RouterLink :to="`/account/${account.id}`" class="cancel-link">Abbrechen</RouterLink>
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