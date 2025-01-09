<script setup>
import { onMounted } from 'vue'
import { ref } from 'vue'

import NavigationHeader from '../components/NavigationHeader.vue'
import SearchBar from '../components/StartPage/SearchBar.vue'
import SortPanel from '../components/StartPage/SortPanel.vue'
import * as gamesRestApi from '../composables/useGamesRestApi.js'

import PTH_DEFAULT_GAME_PIC from '@/assets/images/logo.svg'

async function onSearchButtonPressed(query) {
  games.value = await gamesRestApi.getGamesBySearchQuery(query)
}

async function onFilterButtonPressed(filterInfo) {
  games.value = await gamesRestApi.getGamesByFilter(filterInfo)
}

function imgToSrc(img) {
  return img ? `data:image/jpeg;base64,${img}` : PTH_DEFAULT_GAME_PIC
}

const games = ref([])

onMounted(async () => {
  games.value = await gamesRestApi.getAllGamesGraphQL()
})
</script>

<template>
  <NavigationHeader />
  <div class="main-page-container">
    <SearchBar @apply-search="onSearchButtonPressed" />
    <SortPanel @apply-filter="onFilterButtonPressed" />
    <div class="overview">
      <ul class="overview-table-container">
        <li v-for="game in games" :key="game.id" class="overview-entry">
          <RouterLink class="overview-image" :to="`/game/${game.id}`">
            <img :src="imgToSrc(game.gameImage)" alt="Vorzeigebild des Spiels" />
          </RouterLink>
          <div class="overview-info">
            <div class="game-title">{{ game.title }}</div>
            <div class="game-genres-label">Genres</div>
            <div class="game-genres">{{ game.genres.join(', ') }}</div>
            <div class="game-platforms-label">Plattformen</div>
            <div class="game-platforms">{{ game.platforms.join(', ') }}</div>
            <div class="game-developer-label">Entwickler</div>
            <div class="game-developer">{{ game.developer }}</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Open+Sans);
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');

.main-page-container {
  display: grid;
  grid-template-areas:
    'search'
    'sort'
    'overview';
  grid-template-columns: auto;
  grid-template-rows: 100px 100px auto;
  align-items: center;
  justify-items: center;
}

.overview {
  grid-area: overview;
  width: 60%;
  margin-top: 1%;
}

.overview-entry {
  padding: 1%;
  gap: 20px;
  display: grid;
  grid-template-areas: 'overview-image overview-info';
  grid-template-columns: 30% auto;
  align-items: center;
}

.overview-info {
  display: grid;
  grid-area: overview-info;
  grid-template-areas:
    'game-title game-title game-title'
    'game-genres-label game-platforms-label game-developer-label'
    'game-genres game-platforms game-developer';
  grid-template-columns: 33% 33% auto;
  justify-items: start;
  gap: 25px;
}

.overview-image {
  grid-area: overview-image;
  max-width: 100%;
  max-height: 100%;
}

.overview-image img {
  width: 16vw;
  height: 16vw;
  object-fit: contain;
  object-position: 50%;
}

.overview-table-container li:not(:last-child) {
  border-bottom: 1px solid black;
}

.game-title {
  grid-area: game-title;
  font-size: 200%;
  font-weight: bold;
}

.game-developer {
  grid-area: game-developer;
}

.game-genres {
  grid-area: game-genres;
}

.game-platforms {
  grid-area: game-platforms;
}

.game-genres-label {
  grid-area: game-genres-label;
  font-style: italic;
  font-weight: bold;
}

.game-platforms-label {
  grid-area: game-platforms-label;
  font-style: italic;
  font-weight: bold;
}

.game-developer-label {
  grid-area: game-developer-label;
  font-style: italic;
  font-weight: bold;
}

ul {
  padding: 0;
}
</style>
