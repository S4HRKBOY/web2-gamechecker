export default (account) => `<header>
        <img class="logo" src="../resources/images/logo.svg" alt="logo">
        <nav class="navigation">
            <ul class="nav-links">
                <li><a href="//localhost:8080/thymeleaf/home">Home</a></li>
                ${account.publisher ? `<li><a href="TODO">Neues Spiel anlegen</a></li>` : ""}
                <li><a href="//localhost:8080/thymeleaf/account/${account.id}">Profil</a></li>
                <li><a href="TODO">Logout</a></li>
            </ul>
        </nav>
    </header>`;