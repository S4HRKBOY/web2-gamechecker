'use strict';

import { createHeader, removeCSSTags } from "./global.js";

// #region global variables
let previewPicture = null;
// #endregion


// region functions
export function loadProfileEditPage(accouhtId) {
    fetch(`//localhost:8080/account/${accouhtId}`)
        .then(response => response.json())
        .then(data => renderPage(data));
}

function renderPage(account) {
    createContent(account);
    assignEvents();
    setCSS();
}

function setCSS() {
    removeCSSTags();

    const head = document.querySelector("head");

    // add new stylesheet imports
    head.insertAdjacentHTML("beforeend", `<link rel="stylesheet" href="../css/global.css">`);
    head.insertAdjacentHTML("beforeend", `<link rel="stylesheet" href="../css/profile_edit_page.css">`);
}

function createContent(account) {
    const body = document.querySelector("body");
    const header = createHeader(account);
    const main = createMain(account);
    [header, main].forEach((element) => body.insertAdjacentHTML("beforeend", element));
}

function createMain(account) {
    // TODO: fix links and add logic to save changes/delete account
    let yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    yesterday = yesterday.toISOString().split("T")[0];

    return `<main>
        <article id="content">
            <section class="update-section">
                <form class="update-form" action="//localhost:8080/thymeleaf/account/${account.id}" method="get">
                    <section class="form-content">
                        <section class="set-personal-infos">
                            <fieldset>
                                <legend>Persönliche Daten</legend>
                                <div class="form-input">
                                    <label for="surname">Name</label>
                                    <input type="text" id="surname" name="surname" value="${account.surname}" required>
                                </div>
                                <div class="form-input">
                                    <label for="prename">Vorname</label>
                                    <input type="text" id="prename" name="prename" value="${account.prename}" required>
                                </div>
                                <div class="form-input">
                                    <label for="birthday">Geburtsdatum</label>
                                    <input type="date" id="birthday" name="birthday" max="${yesterday}" value="${account.birthday}">
                                </div>
                                <div class="form-input">
                                    <label for="email">E-Mail Adresse</label>
                                    <input type="email" id="email" name="email" value="${account.email}" required>
                                </div>
                            </fieldset>
                            <fieldset>
                                <legend>Anmeldeinformationen</legend>
                                <div class="form-input">
                                    <label for="username">Benutzername</label>
                                    <input type="text" id="username" name="username" value="${account.username}" required>
                                </div>
                                <div class="form-input">
                                    <label for="password">Passwort</label>
                                    <input type="password" id="password" name="password" required>
                                </div>
                                <div class="form-input">
                                    <label for="password-confirm">Passwort wiederholen</label>
                                    <input type="password" id="password-confirm" required>
                                </div>
                            </fieldset>
                        </section>
                        <section class="set-profile-pic">
                            <fieldset>
                                <figure class="profile-pic">
                                    <img src="../resources/images/profile_pic_default.svg" alt="Profilbild">
                                </figure>
                                <div class="form-input">
                                    <label for="profile-pic-fileselect">Bild ändern</label>
                                    <input type="file" id="profile-pic-fileselect" name="profile-pic-fpath">
                                </div>
                            </fieldset>
                        </section>
                    </section>
                    <section class="send-form-options">
                        <a class="cancel-link" href="//localhost:8080/thymeleaf/account/${account.id}">Abbrechen</a>
                        <button type="submit">Änderungen speichern</button>
                    </section>
                </form>
                <form class="delete-profile" action="//localhost:8080/thymeleaf/account/${account.id}" method="get">
                    <button type="submit">Profil löschen</button>
                </form>
            </section>
        </article>
    </main>`;
}


function assignEvents() {
    document.querySelector(".update-form").addEventListener("submit", (event) => {
        if (!validateInputs()) {
            event.preventDefault();
        }
    });

    document.querySelector("#profile-pic-fileselect").addEventListener("change", (event) => {
        loadImage(event)
            .then(updatePreviewPicture)
            .catch((err) => console.error(err));
    });
}

function loadImage(event) {
    const file = event.target.files[0]; // Get the selected file

    if (!file) {
        return Promise.reject("No file selected.");
    }

    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        // Set the image preview once the file is read
        reader.onload = (e) => {
            previewPicture = e.target.result;
            resolve(); // Resolve the promise when the file is read
        };

        reader.onerror = () => {
            reject("There was an error when reading the image file!");
        }

        reader.readAsDataURL(file); // Read the file as a data URL
    });
}

function updatePreviewPicture() {
    document.querySelector(".profile-pic>img").src = previewPicture;
}

function validateInputs() {
    const password = document.getElementById("password");
    const passwordConfirm = document.getElementById("password-confirm");

    if (password.value !== passwordConfirm.value) {
        passwordConfirm.setCustomValidity("Die eingegebenen Passwörter stimmen nicht überein.");
        passwordConfirm.reportValidity();

        return false;
    }

    // TODO Zak: Entweder hier ueber Backend pruefen, ob die Unique Constraints eingehalten werden
    // oder nicht hier weiter pruefen und ein update request versuchen, und wenn es fehlschlaegt, 
    // dann die Fehlermeldung auslesen und entsprechendes input Feld markieren

    return true; // Allow form submission
}
// #endregion