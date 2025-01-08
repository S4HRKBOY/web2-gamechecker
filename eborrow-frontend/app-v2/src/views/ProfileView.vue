<script setup>
import { onMounted, reactive, computed, provide } from "vue";
import { useRoute } from "vue-router";
import router from "@/router";
import * as useAccountApi from "@/composables/useAccountRestApi.js";
import NavigationHeader from '../components/NavigationHeader.vue';
import ProfileInfo from '../components/Profile/ProfileInfo.vue';
import PinnedGames from '../components/Profile/PinnedGames.vue';
import PTH_DEFAULT_PROFILE_PIC from "@/assets/images/profile_pic_default.svg";

const route = useRoute();

const account = reactive({});
provide('account', account)

const srcProfilePic = computed(() => account.profilePicture ? `data:image/jpeg;base64,${account.profilePicture}` : PTH_DEFAULT_PROFILE_PIC);

onMounted(() => {
    useAccountApi.getAccountById(route.params.id, true)
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
        }).catch(err => {
            console.error(err);
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
});
</script>

<template>
    <NavigationHeader />
    <main>
        <article id="content">
            <h1>Mein Profil</h1>
            <div v-if="account.publisher" class="publisher-account">Redakteur-Account</div>
            <section class="account-data">
                <figure class="profile-pic">
                    <img :src="srcProfilePic" alt="Profilbild">
                </figure>
                <section class="info-section">
                    <ProfileInfo />
                    <form class="edit-profile-button" @submit.prevent="router.push(`/account/edit/${account.id}`)">
                        <button type="submit">Profil bearbeiten</button>
                    </form>
                </section>
            </section>
            <PinnedGames />
        </article>
    </main>
</template>

<style scoped>
main {
    display: flex;
    flex: 1;
    justify-content: center;
}

#content {
    width: 50%;
}

h1 {
    font-size: xx-large;
    text-align: center;
}

.publisher-account {
    color: red;
    text-align: center;
    font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
    margin-top: -2vh;
}

.account-data {
    display: flex;
    gap: 0 5vw;
}

.info-section {
    display: flex;
    flex-direction: column;
}

.edit-profile-button {
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
}

.edit-profile-button>button {
    width: 60%;
    height: 60%;
}

.profile-pic img {
    width: 16vw;
    height: 16vw;
    object-fit: cover;
    object-position: 50%;
    border-radius: 50%;
}
</style>