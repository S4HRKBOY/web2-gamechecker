import * as accountController from "../../controller/accountGraphQLController.js";
import { ID_ACCOUNT_TO_FETCH } from "../../global.js";

accountController.getAccountById(ID_ACCOUNT_TO_FETCH, [
    "id",
    "publisher"
]).then(renderHeader);

export function renderHeader(account) {
    const header = document.querySelector("header");
    header.innerHTML = createHeader(account) + header.innerHTML;
};

function createHeader(account) {
    return `<img class="logo" src="../resources/images/logo.svg" alt="logo">
        <nav class="navigation">
            <ul class="nav-links">
                <li><a href="../html/start_page.html">Home</a></li>
                ${account.publisher ? `<li><a href="gameForm.html">Neues Spiel anlegen</a></li>` : ""}
                <li><a href="//localhost:8080/thymeleaf/account/${account.id}">Profil</a></li>
                <li><a href="#">Logout</a></li>
            </ul>
        </nav>`;
}

