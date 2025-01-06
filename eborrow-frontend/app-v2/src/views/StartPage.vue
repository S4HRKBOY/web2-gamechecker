<script setup>
    import { onMounted } from "vue";
    import { ref } from 'vue';
    import { useRoute } from "vue-router";
    
    import NavigationHeader from '../components/NavigationHeader.vue';
    import SearchBar from '../components/StartPage/SearchBar.vue';
    import SortPanel from '../components/StartPage/SortPanel.vue';
    import * as gamesRestApi from "../composables/useGamesRestApi.js"
    
    import PTH_DEFAULT_GAME_PIC from "@/assets/images/logo.svg";

    async function onSearchButtonPressed(query){
        games.value = await gamesRestApi.getGamesBySearchQuery(query);
    }

    async function onFilterButtonPressed(filterInfo){
        games.value = await gamesRestApi.getGamesByFilter(filterInfo);
    }

    function imgToSrc(img) {
        return img ? `data:image/jpeg;base64,${img}` : PTH_DEFAULT_GAME_PIC;
    }
    
    const games = ref([])
    const route = useRoute();

    onMounted(async () => {
        games.value = await gamesRestApi.getAllGamesGraphQL();
        console.log(games);
        console.log("Id in route is: " + route.params.id);
    });
</script>

<template>
    <NavigationHeader/>
    <div class="main-page-container">
        <SearchBar @apply-search="onSearchButtonPressed"/>
        <SortPanel @apply-filter="onFilterButtonPressed"/>
        <div class="overview">
            <div class="overview-table-container">
                <ul>
                    <li v-for="game in games" class="overview-entry">
                        <div class="overview-image">
                            <RouterLink :to="`/game/${game.id}`"> <!-- to="/game/${game.id}" -->
                                <img :src="imgToSrc(game.gameImage)" alt="Vorzeigebild des Spiels">
                            </RouterLink>
                        </div>
                        <div class="overview-info">
                            <div class="game-title">{{game.title}}</div>
                            <div class="game-developer">{{game.developer}}</div>
                            <div class="game-genres">
                                Genres: 
                                {{ game.genres.join(', ')}}
                            </div>
                            <div class="game-platforms">
                                Platforms:
                                {{ game.platforms.join(', ')}}
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>


<style scoped>
@import url(https://fonts.googleapis.com/css?family=Open+Sans);
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css');

.main-page-container {
    display: grid;
    font-family: 'Open Sans', sans-serif;
    grid-template-areas: "search"
                         "sort"
                         "overview";
    grid-template-columns: auto;
    grid-template-rows: 100px 100px auto;
    align-items: center;
    justify-items: center;
}

.overview{
    grid-area: overview;
    width: 66%;
    margin-top: 1%;
}

.overview-table-container{
    width: 100%;
    height: 100%;
}

.overview-entry{
    width:  100%;
    margin: 5%;
    display: grid;
    grid-template-areas: "overview-image overview-info";
    grid-template-columns: 50% 50%;
    grid-template-rows: auto;
    align-items: center;

    justify-items: start;
    justify-content: center;
    align-items: center;
}

.overview-image{
    grid-area: overview-image;
    max-height: 250px;
    display: block;
}

.overview-image img{
    max-width: 100%;
    max-height: 250px;
    display: block;
}

.overview-info{
    grid-area: overview-info;
    display: grid;
    grid-template-areas: "game-title game-title"
          "game-developer game-developer"
          "game-genres game-platforms"
    ;
    grid-template-columns: 50% 50%;
    grid-template-rows: 20% 10% auto;
    justify-content: stretch;
    justify-items: center;
    align-items: start;
    row-gap: 10%;

}

.game-title{
    grid-area: game-title;
    font-size: 150%;
    padding-bottom: 10%;
}

.game-developer{
    grid-area: game-developer;
}

.game-genres{
    grid-area: game-genres;
}

.game-platforms{
    grid-area: game-platforms
}

</style>