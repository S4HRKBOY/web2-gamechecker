'use strict';

import {loadProfileEditPage} from "./profileEditPage.js";

const idAccountToFetch = 1;

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

export function removeCSSTags() {
    // remove all existing stylesheet imports from the head
    const links = document.querySelectorAll("link[rel=stylesheet]");
    links.forEach((link) => link.remove());
}

export function createHeader(account) {
    const newGameBtn = account.publisher ? `<li><a href="TODO">Neues Spiel anlegen</a></li>` : "";

    return `<header>
        <img class="logo" src="../resources/images/logo.svg" alt="logo">
        <nav class="navigation">
            <ul class="nav-links">
                <li><a href="//localhost:8080/thymeleaf/home">Home</a></li>
                ${newGameBtn}
                <li><a href="//localhost:8080/thymeleaf/account/${account.id}">Profil</a></li>
                <li><a href="TODO">Logout</a></li>
            </ul>
        </nav>
    </header>`;
}
