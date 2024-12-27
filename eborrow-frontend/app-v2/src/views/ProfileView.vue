<script setup>
import NavigationHeader from '../components/NavigationHeader.vue';
import ProfileInfo from '../components/Profile/ProfileInfo.vue';
import PinnedGames from '../components/Profile/PinnedGames.vue';
import * as useAccountApi from "@/composables/useAccountRestApi.js";
import PTH_DEFAULT_PROFILE_PIC from "@/assets/images/profile_pic_default.svg";
import router from "@/router";
import { onMounted, reactive, computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const account = reactive({});
const srcProfilePic = computed(() => account.profilePicture ? `data:image/jpeg;base64,${account.profilePicture}` : PTH_DEFAULT_PROFILE_PIC);

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
    <NavigationHeader />
    <main>
        <article id="content">
            <h1>Mein Profil</h1>
            <div v-if="account.publisher" class="publisher-account">(Redakteur-Account)</div>
            <article id="infos">
                <figure id="profile-pic">
                    <img :src="srcProfilePic" alt="Profilbild">
                </figure>
                <ProfileInfo />
                <form id="edit-profile-btn" @submit.prevent="router.push(`/account/edit/${account.id}`)">
                    <button type="submit">Profil bearbeiten</button>
                </form>
                <PinnedGames />
            </article>
        </article>
    </main>
</template>

<style scoped>
main {
    display: flex;
    flex: 1;
    justify-content: center;
    align-items: center;
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
}

#infos {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: "profile-pic profile-info"
        "profile-pic edit-profile-btn"
        "pinned-games pinned-games";
    gap: 0 5vw;
}

#edit-profile-btn {
    grid-area: edit-profile-btn;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}

#edit-profile-btn>button {
    width: 40%;
    height: 100%;
}

#profile-pic {
    grid-area: profile-pic;
    justify-self: center;
}

#profile-pic img {
    width: 288px;
    height: 288px;
    object-fit: cover;
    object-position: 50%;
    border-radius: 50%;
}
</style>