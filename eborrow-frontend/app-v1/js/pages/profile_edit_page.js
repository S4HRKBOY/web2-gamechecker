'use strict';

import * as utils from "../utils/utils.js";
import * as accountRestController from "../controller/accountRestController.js";
import * as accountGraphQLController from "../controller/accountGraphQLController.js";
import Account from "../entities/Account.js";
import { PATH_DEFAULT_PROFILE_PIC, ID_ACCOUNT_TO_FETCH } from "../global.js";

// #region global variables
let previewPicture = null;
// #endregion

accountGraphQLController.getAccountById(ID_ACCOUNT_TO_FETCH, 
    ["id", "prename", "surname", "username", "birthday", "email"]
).then(renderPage);

// region functions
function renderPage(account) {
    setFormActions(account.id);
    setFormValidationConstraints();
    prefillFormInputs(account);
    assignEvents();
}

function setFormActions(accountId) {
    const updateform = document.querySelector(".update-form");
    updateform.action = `//localhost:8080/thymeleaf/account/${accountId}`;
    updateform.method = "get";

    const cancelLink = document.querySelector(".update-form .send-form-options .cancel-link");
    cancelLink.href = `//localhost:8080/thymeleaf/account/${accountId}`;

    const deleteForm = document.querySelector(".delete-profile");
    deleteForm.action = `//localhost:8080/thymeleaf/account/${accountId}`;
    updateform.method = "get";
}

function setFormValidationConstraints() {
    let yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    yesterday = yesterday.toISOString().split("T")[0];

    const birthdayInput = document.querySelector(".update-form #birthday");
    birthdayInput.max = yesterday;
}

function prefillFormInputs(account) {
    const updateform = document.querySelector(".update-form");
    updateform.querySelector("#surname").value = account.surname;
    updateform.querySelector("#prename").value = account.prename;
    updateform.querySelector("#birthday").value = account.birthday;
    updateform.querySelector("#email").value = account.email;
    updateform.querySelector("#username").value = account.username;
    updateform.querySelector(".profile-pic img").src = PATH_DEFAULT_PROFILE_PIC;
}

function assignEvents() {
    assignResetValidityEvents();
    assignProfilePicSelectionEvent();
    assignSubmitEvent();
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

function assignProfilePicSelectionEvent() {
    document.querySelector("#profile-pic-fileselect").addEventListener("change", (event) => {
        const fileInput = event.target;
        const file = fileInput.files[0]; // Get the selected file

        if (!file) {
            fileInput.value = ""; // Clear the invalid file input
            clearPreviewPicture();

            return;
        }

        if (!validateProfilePic(fileInput)) {
            return;
        }

        utils.loadImage(event.target.files[0])
            .then((img) => previewPicture = img)
            .then(updatePreviewPicture)
            .catch((err) => console.error(err));
    });
}

function assignSubmitEvent() {
    document.querySelector(".update-form").addEventListener("submit", async (event) => {
        {
            event.preventDefault(); // needs to be done, else the form will be submitted before the fetch is done
            const inputsValid = await validateInputs(ID_ACCOUNT_TO_FETCH);
            if (inputsValid) {
                const form = event.target;

                const accountUpdates = new Account({
                    id: ID_ACCOUNT_TO_FETCH,
                    prename: form.prename.value,
                    surname: form.surname.value,
                    username: form.username.value,
                    birthday: form.birthday.value,
                    email: form.email.value,
                    password: form.password.value,
                    profilePicture: previewPicture
                });

                accountRestController.updateAccount(accountUpdates)
                    .then(() => {
                        window.location.href = form.action;
                    })
                    .catch((err) => {
                        console.error(err)
                        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
                    }
                );
            }
        }
    });
}

function clearPreviewPicture() {
    previewPicture = null;
    updatePreviewPicture();
}

function updatePreviewPicture() {
    document.querySelector(".profile-pic>img").src = previewPicture ? `data:image/jpeg;base64,${previewPicture}` : PATH_DEFAULT_PROFILE_PIC;
}


async function validateInputs(accountId) {
    const passwordInput = document.querySelector(".update-form #password");
    const passwordConfirmInput = document.querySelector(".update-form #password-confirm");
    const fileInput = document.querySelector(".update-form #profile-pic-fileselect");
    const emailInput = document.querySelector(".update-form #email");
    const usernameInput = document.querySelector(".update-form #username");

    let isValid = true;
    if (!validatePasswords(passwordInput, passwordConfirmInput)) {
        isValid = false;
    }

    if (!validateProfilePic(fileInput)) {
        isValid = false;
    }

    // async validation (requires server calls)
    if (!await validateUsername(accountId, usernameInput)) {
        isValid = false;
    }

    if (!await validateEmail(accountId, emailInput)) {
        isValid = false;
    }

    return isValid; // Allow form submission
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
    if (file && !file.type.startsWith("image/")) {
        alert("Bitte wählen Sie eine gültige Bilddatei aus.");
        fileInput.value = ""; // Clear the invalid file input
        clearPreviewPicture();

        return false;
    }

    return true;
}

async function validateEmail(accountId, emailInput) {
    const email = emailInput.value.toLowerCase();
    if (email === "") {
        emailInput.setCustomValidity("Bitte geben Sie eine E-Mail Adresse ein.");
        emailInput.reportValidity();

        return false;
    }

    let emailTaken;
    try {
        emailTaken = await accountRestController.isEmailUsedByOtherAccount(accountId, email);

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

async function validateUsername(accountId, usernameInput) {
    if (usernameInput.value === "") {
        usernameInput.setCustomValidity("Bitte geben Sie einen Benutzernamen ein.");
        usernameInput.reportValidity();

        return false;
    }

    let usernameTaken;
    try {
        usernameTaken = await accountRestController.isUsernameUsedByOtherAccount(accountId, usernameInput.value);

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