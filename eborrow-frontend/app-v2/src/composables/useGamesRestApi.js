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
        entryImage.setAttribute("class", "overview-image")
        entryImage.appendChild(document.createElement("a"));

        const imageSource = `data:image/jpeg;base64,` + game["gameImage"];
        const gameImage = document.createElement("img");
        gameImage.setAttribute("src", imageSource);
        entryImage.appendChild(gameImage);

        const entryInfo = document.createElement("td");
        entryInfo.setAttribute("class", "overview-info")

        const gameTitle = document.createElement("div");
        gameTitle.setAttribute("class", "game-title");
        const gameTitleText = document.createTextNode(game["title"]);
        gameTitle.appendChild(gameTitleText);

        const gameDeveloper = document.createElement("div");
        gameDeveloper.setAttribute("class", "game-developer");
        const gameDeveloperText = document.createTextNode(game["developer"]);
        gameDeveloper.appendChild(gameDeveloperText);

        const gameGenres = document.createElement("div");
        gameGenres.setAttribute("class", "game-genres");
        const genresText = document.createTextNode("Genres: ");
        gameGenres.appendChild(genresText);
        const genreList = document.createElement("ul");

        for(const genre of game["genres"]){
            const genreListElement = document.createElement("li");
            genreListElement.appendChild(document.createTextNode(genre));
            genreList.appendChild(genreListElement);
        }
        gameGenres.appendChild(genreList);

        const gamePlatforms = document.createElement("div");
        gamePlatforms.setAttribute("class", "game-platforms");
        const platformsText = document.createTextNode("Platforms: ");
        gamePlatforms.appendChild(platformsText);
        const platformsList = document.createElement("ul");

        for(const platform of game["platforms"]){
            const platformListElement = document.createElement("li");
            platformListElement.appendChild(document.createTextNode(platform));
            platformsList.appendChild(platformListElement);
        }
        gamePlatforms.appendChild(platformsList);
        
        entryInfo.append(gameTitle);
        entryInfo.append(gameDeveloper);
        entryInfo.append(gameGenres);
        entryInfo.append(gamePlatforms);

        entry.appendChild(entryImage);
        entry.appendChild(entryInfo);
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

    developer = developer == null ? "" : developerInput.value;

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