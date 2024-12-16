'use strict';

import { getAccountById, isEmailTaken, isUsernameTaken } from "../accountController.js";
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
    document.querySelector(".update-form").addEventListener("submit", async (event) => {
        {
            event.preventDefault(); // needs to be done, else the form will be submitted before the fetch is done
            const inputsValid = await validateInputs();
            if (!inputsValid) {
                event.preventDefault();
            }
            else {
                event.target.submit();  // manual submission
            }
            // wenn stattdessen clientseitig gerendert werden soll
            // else {
            //     postprocessInputs();
            //     createAccount();
            //     renderProfilePage();
            // }
        }
    });

    assignResetValidityEvents();

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

function assignResetValidityEvents() {
    const passwordInput = document.querySelector(".update-form #password");
    const passwordConfirmInput = document.querySelector(".update-form #password-confirm");
    const emailInput = document.querySelector(".update-form #email");
    const usernameInput = document.querySelector(".update-form #username");

    passwordInput.addEventListener("input", () => {
        passwordConfirmInput.setCustomValidity("");
    });

    passwordConfirmInput.addEventListener("input", () => {
        passwordConfirmInput.setCustomValidity("");
    });

    emailInput.addEventListener("input", () => {
        emailInput.setCustomValidity("");
    });

    usernameInput.addEventListener("input", () => {
        usernameInput.setCustomValidity("");
    });
}

async function validateInputs() {
    const passwordInput = document.querySelector(".update-form #password");
    const passwordConfirmInput = document.querySelector(".update-form #password-confirm");
    const fileInput = document.querySelector(".update-form #profile-pic-fileselect");
    const emailInput = document.querySelector(".update-form #email");
    const usernameInput = document.querySelector(".update-form #username");

    if (!validatePasswords(passwordInput, passwordConfirmInput))
        return false;

    if (!validateProfilePic(fileInput))
        return false;

    if (!await validateEmail(emailInput))
        return false;

    if (!await validateUsername(usernameInput))
        return false;

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

async function validateEmail(emailInput) {
    if(emailInput.value === "") {
        emailInput.setCustomValidity("Bitte geben Sie eine E-Mail Adresse ein.");
        emailInput.reportValidity();

        return false;
    }

    // Zak: Theoretisch muesste man noch schauen, ob es sich um dieselbe Mail handelt, die bereits im Account hinterlegt war
    let emailTaken;
    try {
        emailTaken = await isEmailTaken(emailInput.value);

        if (emailTaken !== false && emailTaken !== true) {
            throw new Error("Unexpected response from server.");
        }
    } catch (err) {
        console.error(err);
        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");

        return false;
    }

    if (emailTaken === true) {
        emailInput.setCustomValidity("Diese E-Mail Adresse wird bereits verwendet.");
        emailInput.reportValidity();

        return false;
    }

    return true;
}

async function validateUsername(usernameInput) {
    if(usernameInput.value === "") {
        usernameInput.setCustomValidity("Bitte geben Sie einen Benutzernamen ein.");
        usernameInput.reportValidity();

        return false;
    }

    // Zak: Theoretisch muesste man noch schauen, ob es sich um denselben username handelt, der bereits im Account hinterlegt war
    let usernameTaken;
    try {
        usernameTaken = await isUsernameTaken(usernameInput.value);

        if (usernameTaken !== false && usernameTaken !== true) {
            throw new Error("Unexpected response from server.");
        }
    } catch (err) {
        console.error(err);
        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");

        return false;
    }

    if (usernameTaken === true) {
        usernameInput.setCustomValidity("Dieser Benutzername ist bereits vergeben.");
        usernameInput.reportValidity();

        return false;
    }

    return true;
}
// #endregion