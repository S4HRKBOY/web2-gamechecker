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
const inputRefs = {
    personalInfosEdit: ref(null),
    profilePicEdit: ref(null)
};

const account = reactive({});
provide('account', account);

onMounted(() => {
    useAccountApi.getAccountById(route.params.id)
        .then(acc => {
            account.id = acc.id;
            account.prename = acc.prename;
            account.surname = acc.surname;
            account.birthday = acc.birthday;
            account.username = acc.username;
            account.email = acc.email;
            account.password = acc.password;
            account.profilePicture = acc.profilePicture;
            account.taggedGames = acc.taggedGames;
            inputRefs.personalInfosEdit.value.populateInputs(account);
            inputRefs.profilePicEdit.value.populateInputs(account);
        })
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
            sessionStorage.setItem('accountId', '');
            sessionStorage.setItem('publisher', '');
            router.push("/login");
        })
        .catch((err) => {
            console.error(err)
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
}

async function updateAccount() {
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

    const inputsPersonalInfos = inputRefs.personalInfosEdit.value?.inputVals;
    if (!inputsPersonalInfos) {
        console.error("inputs personal infos subcomponent cannot be read");
        return;
    }

    const loadedImage = inputRefs.profilePicEdit.value?.loadedImage;
    if (!inputRefs.profilePicEdit.value) {
        console.error("loaded image subcomponent cannot be read");
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

    if (!inputRefs.profilePicEdit.value?.validateInputs()) {
        isValid = false;
    }

    if (!await inputRefs.personalInfosEdit.value?.validateInputs()) {
        isValid = false;
    }

    return isValid;
}
</script>

<template>
    <NavigationHeader />
    <main>
        <article id="content">
            <form @submit.prevent="updateAccount">
                <section class="form-content">
                    <PersonalInfosEdit :ref="inputRefs.personalInfosEdit"></PersonalInfosEdit>
                    <ProfilePicEdit :ref="inputRefs.profilePicEdit"></ProfilePicEdit>
                </section>
                <section class="send-form-options">
                    <RouterLink :to="`/account/${account.id}`" class="cancel-link">Abbrechen</RouterLink>
                    <button type="submit">Änderungen speichern</button>
                </section>
            </form>
            <form class="delete-profile" @submit.prevent="deleteAccount">
                <button type="submit">Profil löschen</button>
            </form>
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
    display: flex;
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

.delete-profile>button {
    color: red;
}

.cancel-link {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    text-decoration: none;
}
</style>
