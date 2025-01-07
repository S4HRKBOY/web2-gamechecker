import * as accountController from "../../controller/accountGraphQLController.js";
import { getActiveAccountId } from "../../global.js";

accountController.getAccountById(getActiveAccountId(), [
    "id",
    "publisher"
]).then(renderHeader)
    .catch((err) => {
        console.error(err)
        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
    });

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
                <li><a href="profile_edit_page.html">Profil</a></li>
                <li><a href="#">Logout</a></li>
            </ul>
        </nav>`;
}

