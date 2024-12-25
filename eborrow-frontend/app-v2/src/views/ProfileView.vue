<script setup>
import NavigationHeader from '../components/NavigationHeader.vue';
import ProfileInfo from '../components/Profile/ProfileInfo.vue';
import PinnedGames from '../components/Profile/PinnedGames.vue';
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
onMounted(async () => console.log("Id in route is: " + route.params.id));
</script>

<template>
    <NavigationHeader />
    <main>
        <article id="content">
            <h1>Mein Profil</h1>
            <div class="publisher-account">(Redakteur-Account)</div> <!-- only if account.isPublisher()" -->
            <article id="infos">
                <figure id="profile-pic">
                    <img src="data:," alt="Profilbild">
                </figure>
                <ProfileInfo />
                <!-- <form id="edit-profile-btn">
                    <button type="submit">Profil bearbeiten</button>
                </form> -->
                <RouterLink to="/account/edit/42" id="edit-profile-btn" v-slot="navigate">
                    <button type="submit" @click="href">Profil bearbeiten</button>
                </RouterLink>
                <PinnedGames />
            </article>
        </article>
    </main>
</template>

<style>
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