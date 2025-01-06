<script setup>
import { inject } from "vue";
import * as useAccountApi from "@/composables/useAccountRestApi.js";
import PTH_DEFAULT_GAME_PIC from "@/assets/images/logo.svg";

const account = inject('account');

function imgToSrc(img) {
    return img ? `data:image/jpeg;base64,${img}` : PTH_DEFAULT_GAME_PIC;
}

function unlistGameFromAccount(gameId) {
    useAccountApi.unlistGameFromAccount(account.id, gameId)
        .then(() => {
            // either remove the game from taggedGames or reload the account entirely
            account.taggedGames = account.taggedGames.filter(game => game.id !== gameId);
        })
        .catch(err => {
            console.error(err);
            alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        });
}
</script>

<template>
    <section id="pinned-games">
        <h2>Angepinnte Spiele</h2>
        <span v-if="account.taggedGames && account.taggedGames.length === 0">Keine Spiele angepinnt</span>
        <div v-else class="overview">
            <table class="overview-table-container">
                <tbody>
                    <tr v-for="game in account.taggedGames" :key="game.id" class="overview-entry">
                        <td class="overview-image">
                            <RouterLink :to="`/game/${game.id}`">
                                <img :src="imgToSrc(game.gameImage)" alt="Vorzeigebild des Spiels">
                            </RouterLink>
                        </td>
                        <td class="overview-description">
                            <span>{{ game.title }}</span>
                        </td>
                        <td class="unlist-button">
                            <form @submit.prevent="unlistGameFromAccount(game.id)">
                                <button type="submit">entfernen</button>
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </section>
</template>

<style scoped>
#pinned-games {
    grid-area: pinned-games;
}

#pinned-games table {
    border-collapse: collapse;
    width: 100%;
}

#pinned-games th,
#pinned-games td {
    padding: 10px;
    text-align: center;
}

#pinned-games th,
#pinned-games tr:not(:last-child) {
    border-bottom: 1px solid black;
}

.overview {
    margin-top: 1%;
}

.overview-table-container {
    width: 100%;
    height: 100%;
}

.overview-entry {
    width: 100%;
    height: 10%;
    padding: 2.5%;
    display: grid;
    grid-template-areas: "overview-image overview-description unlist-button";
    grid-template-columns: 2fr 4fr 1fr;
}

.overview-image {
    grid-area: overview-image;
    max-height: 250px;
    display: block;
}

.overview-image img {
    max-width: 100%;
    max-height: 250px;
    display: block;
}

.overview-description {
    grid-area: overview-description;
    place-self: center;
    display: block;
}

.unlist-button {
    grid-area: unlist-button;
    place-self: center;
}
</style>
