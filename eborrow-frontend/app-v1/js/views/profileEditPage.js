export default (account) => {
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