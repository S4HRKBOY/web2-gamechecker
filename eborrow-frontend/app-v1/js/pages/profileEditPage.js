'use strict';

import { getAccountById } from "../accountController.js";
import { loadImage, removeCSSTags } from "../utils/utils.js";
import createHeader from "../views/partials/header.js";
import createProfileEditPage from "../views/profileEditPage.js";
import { srcDefaultProfilePic } from "../views/profileEditPage.js";

// #region global variables
let previewPicture = null;
// #endregion

// region functions
export function loadProfileEditPage(accountId) {
    getAccountById(accountId)
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
    head.insertAdjacentHTML("beforeend", `<link rel="stylesheet" href="../css/globals/main.css">`);
    head.insertAdjacentHTML("beforeend", `<link rel="stylesheet" href="../css/profile_edit_page.css">`);
}

function createContent(account) {
    const body = document.querySelector("body");
    const header = createHeader(account);
    const main = createProfileEditPage(account);
    [header, main].forEach((element) => body.insertAdjacentHTML("beforeend", element));
}

function assignEvents() {
    // TODO Bugfix: Wenn das Passwort-Feld geaendert wird, wird die Passwort-Wiederholung nicht zurueckgesetzt
    document.querySelector(".update-form").addEventListener("submit", (event) => {
        if (!validateInputs()) {
            event.preventDefault();
        }
    });

    assignValidationEvents();

    document.querySelector("#profile-pic-fileselect").addEventListener("change", (event) => {
        const fileInput = event.target;
        const file = fileInput.files[0]; // Get the selected file

        if (!file) {
            fileInput.value = ""; // Clear the invalid file input
            clearPreviewPicture();

            return;
        }

        if(!validateProfilePic(fileInput)) {
            return;
        }

        loadImage(event.target.files[0])
            .then((img) => previewPicture = img)
            .then(updatePreviewPicture)
            .catch((err) => console.error(err));
    });
}

function clearPreviewPicture() {
    previewPicture = null;
    updatePreviewPicture();
}

function updatePreviewPicture() {
    document.querySelector(".profile-pic>img").src = previewPicture ? previewPicture : srcDefaultProfilePic;
}

function assignValidationEvents() {
    const passwordInput = document.querySelector(".update-form #password");
    const passwordConfirmInput = document.querySelector(".update-form #password-confirm");
    const imageSelectInput = document.getElementById("profile-pic-fileselect");

    passwordInput.addEventListener("input", () => {
        passwordConfirmInput.setCustomValidity("");
    });

    passwordConfirmInput.addEventListener("input", () => {
        passwordConfirmInput.setCustomValidity("");
    });
}

function validateInputs() {
    const passwordInput = document.getElementById("password");
    const passwordConfirmInput = document.getElementById("password-confirm");
    const fileInput = document.getElementById("profile-pic-fileselect");

    if (!validatePasswords(passwordInput, passwordConfirmInput))
        return false;

    if (!validateProfilePic(fileInput))
        return false;

    // TODO Zak: Entweder hier ueber Backend pruefen, ob die Unique Constraints eingehalten werden
    // oder nicht hier weiter pruefen und ein update request versuchen, und wenn es fehlschlaegt, 
    // dann die Fehlermeldung auslesen und entsprechendes input Feld markieren

    return true; // Allow form submission
}

function validatePasswords(passwordInput, passwordConfirmInput) {
    if (passwordInput.value !== passwordConfirmInput.value) {
        passwordConfirmInput.setCustomValidity("Die eingegebenen Passwörter stimmen nicht überein.");
        passwordConfirmInput.reportValidity();

        return false;
    }

    return true;
}

function validateProfilePic(fileInput) {
    const file = fileInput.files[0]; // Get the selected file
    if(file && !file.type.startsWith("image/")) {
        alert("Bitte wählen Sie eine gültige Bilddatei aus.");
        fileInput.value = ""; // Clear the invalid file input
        clearPreviewPicture();

        return false;
    }

    return true;
}
// #endregion