<script setup>
    import * as gamesRestApi from "@/composables/useGamesRestApi.js"
    import { onMounted } from "vue";

    const allGenresEndpoint = "http://localhost:8080/game/all-genres";
    const allPlatformsEndpoint = "http://localhost:8080/game/all-platforms";

    onMounted(async () => {
        const genres = await gamesRestApi.requestResource(allGenresEndpoint);
        const genreSelection = document.querySelector(".genre-filter");
        genreSelection.innerHTML = '';

        const allGenres = document.createElement("option");
        allGenres.setAttribute("value", "All");
        allGenres.appendChild(document.createTextNode("All"));
        genreSelection.appendChild(allGenres);

        for (var i = 0; i < genres.length; i++) {
            const option = document.createElement("option");
            option.setAttribute("value", genres[i]);
            const optionTextNode = document.createTextNode(genres[i]);
            option.appendChild(optionTextNode);
            genreSelection.appendChild(option);
        }

        const platforms = await gamesRestApi.requestResource(allPlatformsEndpoint);
        const platformsSelection = document.querySelector(".platform-filter");
        platformsSelection.innerHTML = '';

        const allPlatforms = document.createElement("option");
        allPlatforms.setAttribute("value", "All");
        allPlatforms.appendChild(document.createTextNode("All"));
        platformsSelection.appendChild(allPlatforms);

        for (var i = 0; i < platforms.length; i++) {
            const option = document.createElement("option");
            option.setAttribute("value", platforms[i]);
            const optionTextNode = document.createTextNode(platforms[i]);
            option.appendChild(optionTextNode);
            platformsSelection.appendChild(option);
        }

        const filterButton = document.querySelector(".apply-filter");
        filterButton.addEventListener("click", gamesRestApi.getGamesByFilter());
    });

    
</script>

<template>
    <div class="sort">
        <div class="filter-bar">
            <select name="genres" id="genres" class="genre-filter">
            </select>
            <select name="platform" id="platform" class="platform-filter">
            </select>
            <div class="dev-filter">
                <label for="dev">Developer: </label>
                <input type="text" id="dev" name="developer" class="dev-input">
            </div>
            <button type="submit" class="apply-filter">
                <i class="fa fa-filter"></i>
            </button>
        </div>
    </div>
</template>

<style>
    .sort{
        grid-area: sort;
        width: 66%;
    }

    .filter-bar{
        width: 100%;
        height: 100px;
        border: 3px solid #2b2929;
        border-radius: 5px;
        background-color: aliceblue;

        display: grid;
        grid-template-areas: "genre-filter platform-filter dev-filter apply-filter";
        grid-template-columns: 30% 30% 30% auto;
        align-items: center;
        justify-items: center;
                            
    }

    .genre-filter{
        grid-area: genre-filter; 
    }

    .date-filter{
        grid-area: date-filter;
    }

    .apply-filter{
        grid-area: apply-filter;
        width: 35%;
        height: 50%;
        border: 1px solid #2b2929;
        background: #2b2929;
        border-radius: 15px;
        cursor: pointer;
        color: #fff;
    }

    .apply-filter:hover{
        grid-area: apply-filter;
        width: 35%;
        height: 50%;
        border: 1px solid #2b2929;
        background: #fff;
        border-radius: 15px;
        cursor: pointer;
        color: #2b2929;
    }
</style>