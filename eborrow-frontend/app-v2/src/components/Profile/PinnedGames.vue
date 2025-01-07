<script setup>
import { inject, computed } from "vue";
import * as useAccountApi from "@/composables/useAccountRestApi.js";
import PTH_DEFAULT_GAME_PIC from "@/assets/images/logo.svg";

const account = inject('account');
const gamesSorted = computed(() => {
    return account.taggedGames?.toSorted((a, b) => a.title.localeCompare(b.title));
})

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
        <table v-else>
            <tbody>
                <tr v-for="game in gamesSorted" :key="game.id" class="overview-entry">
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
    </section>
</template>

<style scoped>

#pinned-games table {
    border-collapse: collapse;
    width: 100%;
}

#pinned-games th,
#pinned-games tr:not(:last-child) {
    border-bottom: 1px solid black;
}

.overview-image img {
    width: 16vw;
    height: 16vw;
    object-fit: contain;
    object-position: 50%;
}

.overview-description {
    text-align: center;
    width: 100%;
    font-size: 120%;
    font-family: 'Open Sans', sans-serif;
}

.unlist-button {
    align-self: center;
}
</style>
