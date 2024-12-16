'use strict';

import {loadProfileEditPage} from "./profileEditPage.js";
import header from "./views/partials/header.js";

// The logged in account, currently hardcoded, will change in app-v2
const idAccountToFetch = 1;

// The starting page (homePage in app-v1, should be the login page in app-v2)
profileEditPage();

function homePage() {
    // ...
}

function profileEditPage() {
    loadProfileEditPage(idAccountToFetch);
}

function gameForm() {
    // ...
}