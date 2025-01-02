<script setup>
    import NavigationHeader from '../components/NavigationHeader.vue';
    import SearchBar from '../components/StartPage/SearchBar.vue';
    import SortPanel from '../components/StartPage/SortPanel.vue';
    import * as gamesRestApi from "../composables/useGamesRestApi.js"
    import { onMounted } from "vue";
    import { useRoute } from "vue-router";
    import PTH_DEFAULT_GAME_PIC from "@/assets/images/logo.svg";
    
    function imgToSrc(img) {
        return img ? `data:image/jpeg;base64,${img}` : PTH_DEFAULT_GAME_PIC;
    }
    
    const route = useRoute();

    onMounted(async () => {
        const games = await gamesRestApi.getAllGamesGraphQL();
        console.log(games);
        console.log("Id in route is: " + route.params.id);
    });
</script>

<template>
    <NavigationHeader/>
    <div class="main-page-container">
        <SearchBar />
        <SortPanel />
        <div class="overview">
            <table class="overview-table-container">
                <tbody>
                    <tr v-for="game in games" class="overview-entry">
                        <td class="overview-image">
                            <RouterLink :to="`/game/${game.id}`"> <!-- to="/game/${game.id}" -->
                                <img :src="imgToSrc(game.gameImage)" alt="Vorzeigebild des Spiels">
                            </RouterLink>
                        </td>
                        <td class="overview-info">
                            <div class="game-title">{{game.title}}</div>
                            <div class="game-developer">{{game.developer}}</div>
                            <div class="game-genres">
                                Genres: 
                                <ul v-for="genre in game.genres">
                                    <li>{{genre}}</li>
                                </ul>
                            </div>
                            <div class="game-platforms">
                                Platforms:
                                <ul v-for="platform in game.platforms">
                                    <li>{{platform}}</li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>


<style scoped>
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
    height: 10%;
    margin: 5%;
    display: grid;
    grid-template-areas: "overview-image overview-description";
    grid-template-columns: 50% 50%;
    grid-template-rows: auto;
    align-items: center;

    justify-items: normal;
    justify-content: space-between;
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
    align-items: center;
    row-gap: 10%;

}

.game-title{
    grid-area: game-title;
    font-size: 150%;
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