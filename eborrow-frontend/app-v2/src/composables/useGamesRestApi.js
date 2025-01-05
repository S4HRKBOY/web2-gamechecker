'use strict'

const gamesBySearchQueryURL = "http://localhost:8080/game/games-search";
const gamesByFilterParamsURL = "http://localhost:8080/game/filtered-games";

const allGenresEndpoint = "http://localhost:8080/game/all-genres";
const allPlatformsEndpoint = "http://localhost:8080/game/all-platforms";

const graphQLURL = "http:///localhost:8080/graphql";

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
    
    let result;
    try{
        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
    
        const response = await fetch(gamesBySearchQueryURL, {
            method: "POST",
            body: JSON.stringify({ query: searchQuery }),
            headers: myHeaders,
        });
        result = await response.json();
    }catch (error) {
        console.error(error.message)
    }
    console.log(result);
    searchBox.value = "";
    return result;
}

async function getGamesByFilter() {
    const generesSelect = document.querySelector(".genre-filter");
    const genre = generesSelect.options[generesSelect.selectedIndex].text;

    const platformSelect = document.querySelector(".platform-filter");
    const platform = platformSelect.options[platformSelect.selectedIndex].text;

    const developerInput = document.querySelector(".dev-input");
    let developer;

    developer = developerInput.value;

    let result;
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
        result = await response.json();
    }catch (error) {
        console.error(error.message)
    }

    developerInput.value = "";
    return result;
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
    console.log(result);
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

export {getGamesByFilter, getGamesBySearchQuery, requestResource, getAllGamesGraphQL, getAvailableGenres, getAvailablePlatforms}