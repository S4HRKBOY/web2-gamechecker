const path = "http://localhost:8080"

function getId() {
    const params = new URLSearchParams(window.location.search);
    return params.get('id');
}

async function getGameData() {
    const id = getId();

    if (!id) {
        return null;
    }

    const url = path + `/game/${id}`;
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }

        const json = await response.json();
        console.log(json);
        return json;
    } catch (error) {
        console.error(error.message);
        return null;
    }
}

let fetchDynamicData = async () => {
    let query = `{
        allAgeRatings
        allGenres
        allPlatforms
    }`;

    let response = await fetch(path + "/graphql", {
        method: "post",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: JSON.stringify({ query }),
    });
    let json = await response.json();
    return json.data;
}

async function populatePlatforms(platforms, gamePlatforms) {
    const dropdown = document.getElementById('platform-dropdown');
    dropdown.innerHTML = '';

    platforms.forEach(platform => {
        const label = document.createElement('label');
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = 'platform';
        checkbox.value = platform;

        if (gamePlatforms != null && gamePlatforms.includes(platform)) {
            checkbox.checked = true;
        }

        label.appendChild(checkbox);
        label.appendChild(document.createTextNode(platform));
        dropdown.appendChild(label);
    });
}

async function populateGenres(genres, gameGenres) {
    const dropdown = document.getElementById('genre-dropdown');
    dropdown.innerHTML = '';

    genres.forEach(genre => {
        const label = document.createElement('label');
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.name = 'genre';
        checkbox.value = genre;

        if (gameGenres != null && gameGenres.includes(genre)) {
            checkbox.checked = true;
        }

        label.appendChild(checkbox);
        label.appendChild(document.createTextNode(genre));
        dropdown.appendChild(label);
    });
}

async function populateAgeRatings(ageRatings, gameAgeRating) {
    const age = document.getElementById('age');
    age.innerHTML = '';

    ageRatings.forEach(ageRating => {
        const option = document.createElement('option');
        option.value = ageRating;
        if (gameAgeRating == ageRating) {
            option.selected = true;
        }
        option.textContent = ageRating;
        age.appendChild(option);
    });
}

async function populateImage(gameImage) {
    const imagearea = document.getElementById('imagearea');
    imagearea.innerHTML = '';
    const legend = document.createElement('legend');
    legend.textContent = 'Vorschaubild';
    imagearea.appendChild(legend);

    const img = document.createElement('img');
    img.id = "gameImage";
    if (gameImage) {
        img.src = 'data:image/jpg;base64,' + gameImage;
        img.alt = "Game Image";
    }
    imagearea.appendChild(img);
}

let fillData = async function () {
    const game = await getGameData();
    const data = await fetchDynamicData();
    const { allPlatforms, allGenres, allAgeRatings } = data;

    const headline = document.getElementById("headline");
    const formButtons = document.getElementById("gameForm");

    if (game) {
        populatePlatforms(allPlatforms, game.platforms);
        populateGenres(allGenres, game.genres);
        populateAgeRatings(allAgeRatings, game.ageRating);
        populateImage(game.gameImage);
        headline.textContent = "Spiel bearbeiten";
        const updateButton = document.createElement('input');
        const deleteButton = document.createElement('input');
        updateButton.id = "submit";
        updateButton.type = "submit";
        updateButton.value = "Änderungen speichern";
        deleteButton.id = "delete";
        deleteButton.value = "Spiel löschen";
        deleteButton.type = "button";
        formButtons.appendChild(updateButton);
        formButtons.appendChild(deleteButton);
        document.getElementById('title').value = game.title || '';
        document.getElementById('description').value = game.description || '';
        document.getElementById('publication').value = game.publicationDate || '';
        document.getElementById('developer').value = game.developer || '';
        document.getElementById('publisher').value = game.publisher || '';

        deleteButton.addEventListener('click', function (event) {
            event.preventDefault();
            deleteGameData();
        });

        updateButton.addEventListener('click', function (event) {
            event.preventDefault();
            submitGameData('PUT');
        });

    } else {
        populatePlatforms(allPlatforms, null);
        populateGenres(allGenres, null);
        populateAgeRatings(allAgeRatings, null);
        populateImage(null);
        headline.textContent = "Spiel anlegen";
        const createButton = document.createElement('input');
        createButton.id = "create";
        createButton.type = "submit";
        createButton.value = "Neues Spiel anlegen";
        formButtons.appendChild(createButton);

        createButton.addEventListener('click', function (event) {
            event.preventDefault();
            submitGameData('POST');
        });
    }
}


// platform dropdown 
document.getElementById('platform').addEventListener('click', (event) => {
    event.preventDefault();
    const dropdown = document.getElementById('platform-dropdown');
    dropdown.classList.toggle('open');
});

document.addEventListener('click', (event) => {
    const dropdown = document.getElementById('platform-dropdown');
    const button = document.getElementById('platform');
    if (!dropdown.contains(event.target) && !button.contains(event.target)) {
        dropdown.classList.remove('open');
    }
});

// genre Dropdown
document.getElementById('genre').addEventListener('click', (event) => {
    event.preventDefault();
    const dropdown = document.getElementById('genre-dropdown');
    dropdown.classList.toggle('open');
});

document.addEventListener('click', (event) => {
    const dropdown = document.getElementById('genre-dropdown');
    const button = document.getElementById('genre');
    if (!dropdown.contains(event.target) && !button.contains(event.target)) {
        dropdown.classList.remove('open');
    }
});

fillData();

function previewImage(event) {
    const fileInput = event.target;
    const preview = document.getElementById('gameImage');

    if (fileInput.files && fileInput.files[0]) {
        const reader = new FileReader();
        reader.onload = () => {
            preview.src = reader.result;
        };
        reader.readAsDataURL(fileInput.files[0]);
    }
}
document.getElementById('file').addEventListener('change', previewImage);

function convertToBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result.split(',')[1]);
        reader.onerror = (error) => reject(error);
    });
}

async function submitGameData(method) {
    const gameImage = document.getElementById('file');
    const id = getId();
    let base64Image = '';
    if (gameImage.files && gameImage.files[0]) {
        base64Image = await convertToBase64(gameImage.files[0]);
    }

    const gameData = {
        id: id,
        title: document.getElementById('title').value,
        description: document.getElementById('description').value,
        publicationDate: document.getElementById('publication').value,
        developer: document.getElementById('developer').value,
        publisher: document.getElementById('publisher').value,
        platforms: Array.from(document.querySelectorAll('input[name="platform"]:checked')).map(input => input.value),
        genres: Array.from(document.querySelectorAll('input[name="genre"]:checked')).map(input => input.value),
        ageRating: document.getElementById('age').value,
        gameImage: base64Image || document.getElementById('gameImage').src.split(',')[1],
    };
    console.log(gameData);

    let url = null;
    if (method == 'PUT') {
        url = path + `/game/update-game/${id}`;
    }
    else {
        url = path + `/game/create-game`;
    }

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(gameData),
        });

        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }
        const newViewResponse = await fetch("../html/start_page.html");
        if (!newViewResponse.ok) {
            throw new Error(`Failed to load new view: ${newViewResponse.status}`);
        }
        const newViewHtml = await newViewResponse.text();
        document.body.innerHTML = newViewHtml;
        const script = document.createElement('script');
        script.src = "../js/startpage.js";
        document.body.appendChild(script);

    } catch (error) {
        console.error('Error:', error.message);
    }

}

async function deleteGameData() {
    const id = getId();
    const url = path + `/game/delete-game/${id}`;
    try {
        const response = await fetch(url, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }
        const newViewResponse = await fetch("../html/start_page.html");
        if (!newViewResponse.ok) {
            throw new Error(`Failed to load new view: ${newViewResponse.status}`);
        }
        const newViewHtml = await newViewResponse.text();
        document.body.innerHTML = newViewHtml;
        const script = document.createElement('script');
        script.src = "../js/startpage.js";
        document.body.appendChild(script);

    } catch (error) {
        console.error('Error:', error.message);
    }
}
