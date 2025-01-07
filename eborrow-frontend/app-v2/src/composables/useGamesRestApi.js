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
    } catch (error) {
        console.error(error.message)
    }
    return result;
}

async function getGamesBySearchQuery(searchQuery){    
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
    return result;
}

async function getGamesByFilter(filterInfo) {    
    const genre = filterInfo.genre;
    const platform = filterInfo.platform;
    const developer = filterInfo.developer;

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