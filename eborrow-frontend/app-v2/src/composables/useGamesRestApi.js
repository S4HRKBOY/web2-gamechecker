'use strict'

const allGamesEndpoint = "http://localhost:8080/home";

const gamesBySearchQueryURL = "http://localhost:8080/game/games-search";
const gamesByFilterParamsURL = "http://localhost:8080/game/filtered-games";

const allGenresEndpoint = "http://localhost:8080/game/all-genres";
const allPlatformsEndpoint = "http://localhost:8080/game/all-platforms";

const graphQLURL = "http:///localhost:8080/graphql";

function handleGameFilling(games) {
    const table = document.querySelector(".overview-table-container");
    table.innerHTML = '';

    for (const game of games) {
        const entry = document.createElement("tr");
        entry.setAttribute("class", "overview-entry");

        table.appendChild(entry);

        const entryImage = document.createElement("td");
        const entryText = document.createElement("td");

        entryImage.appendChild(document.createElement("a"));
        const gameDescription = document.createTextNode(game["description"]);
        entryText.append(gameDescription);
        entry.appendChild(entryImage);
        entry.appendChild(entryText);
    }
}

async function requestResource(url) {
    let result;
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }
        result = await response.json();
        console.log(result);
    } catch (error) {
        console.error(error.message)
    }
    return result;
}

async function getGamesBySearchQuery(){
    const searchBox = document.querySelector(".searchBox");
    const searchQuery = searchBox.value;
    
    try{
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
    
        const response = await fetch(gamesBySearchQueryURL, {
            method: "POST",
            body: JSON.stringify({ query: searchQuery }),
            headers: myHeaders,
        });
        const gamesJson = await response.json();
        handleGameFilling(gamesJson);
    }catch (error) {
        console.error(error.message)
    }

    searchBox.value = "";
}

async function getGamesByFilter() {
    const generesSelect = document.querySelector(".genre-filter");
    const genre = generesSelect.options[generesSelect.selectedIndex].text;

    const platformSelect = document.querySelector(".platform-filter");
    const platform = platformSelect.options[platformSelect.selectedIndex].text;

    const developerInput = document.querySelector(".dev-input");
    let developer;

    developer = developer == null ? "" : developerInput.value;

    try{
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
    
        const response = await fetch(gamesByFilterParamsURL, {
            method: "POST",
            body: JSON.stringify({  genre: genre,
                                    developer: developer,
                                    platform: platform
                                }),
            headers: myHeaders,
        });
        const gamesJson = await response.json();
        handleGameFilling(gamesJson);
    }catch (error) {
        console.error(error.message)
    }

    developerInput.value = "";
}

async function getAllGamesGraphQL(){
    let result;
    
    const body = JSON.stringify({
        query: `{
          games {
                id
                description
                title
                gameImage
                developer
                genres
                platforms
            }
        }`,
    });
    
    try{
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
    
        const response = await fetch(graphQLURL, {
            method: "POST",
            body: body,
            headers: myHeaders,
        });
        result = await response.json();
        result = result["data"]["games"];
    }catch (error) {
        console.error(error.message)
    }
    return result;
}

async function getAvailableGenres(){
    const genres = await requestResource(allGenresEndpoint);
    return genres;
}

async function getAvailablePlatforms(){
    const platforms = await requestResource(allPlatformsEndpoint);
    return platforms;
}

/*async function initGamesOverview() {
    const games = await requestResource(allGamesEndpoint);
    handleGameFilling(games);
}*/

export {getGamesByFilter, getGamesBySearchQuery, requestResource, getAllGamesGraphQL, getAvailableGenres, getAvailablePlatforms}

/*
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
*/