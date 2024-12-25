<script>
import NavigationHeader from '../components/NavigationHeader.vue'

export default {
    components: {
        NavigationHeader
    }
}
</script>

<template>
    <NavigationHeader />
    <main>
        <article id="content">
            <h1>Mein Profil</h1>
            <div class="publisher-account">(Redakteur-Account)</div> <!-- only if account.isPublisher()" -->
            <article id="infos">
                <figure id="profile-pic">
                    <img src="data:," alt="Profilbild">
                </figure>
                <section id="profile-info">
                    <section id="personal-data">
                        <h2>Persönliche Daten</h2>
                        <table>
                            <tr>
                                <td>Benutzername</td>
                                <td>
                                    <span>Kein Benutzername verfügbar</span>
                                </td>
                            </tr>
                            <tr>
                                <td>Vorname</td>
                                <td>
                                    <span>Kein Vorname verfügbar</span>
                                </td>
                            </tr>
                            <tr>
                                <td>Nachname</td>
                                <td>
                                    <span>Kein Nachname verfügbar</span>
                                </td>
                            </tr>
                            <tr>
                                <td>Geburtsdatum</td>
                                <td>
                                    <span>
                                        Kein Geburtsdatum verfügbar</span>
                                </td>
                            </tr>
                            <tr>
                                <td>E-Mail</td>
                                <td>
                                    <span>Keine E-Mail verfügbar</span>
                                </td>
                            </tr>
                        </table>
                    </section>
                </section>
                <!-- <form id="edit-profile-btn">
                    <button type="submit">Profil bearbeiten</button>
                </form> -->
                <RouterLink to="/account/edit" id="edit-profile-btn" v-slot="navigate">
                    <button type="submit" @click="href">Profil bearbeiten</button>
                </RouterLink>
                <section id="pinned-games">
                    <h2>Angepinnte Spiele</h2>
                    <span>Keine Spiele angepinnt</span> <!-- only if account.taggedGames.size() == 0" -->
                    <div class="overview">
                        <table class="overview-table-container">
                            <tr class="overview-entry"> <!-- for each game : ${account.taggedGames} -->
                                <td class="overview-image">
                                    <RouterLink to="/game"> <!-- to="/game/${game.id}" -->
                                        <img src="data:," alt="Vorzeigebild des Spiels">
                                    </RouterLink>
                                </td>
                                <td class="overview-description">
                                    <span>Kein Titel verfügbar</span>
                                </td>
                                <td class="unlist-button">
                                    <form>
                                        <button type="submit">entfernen</button>
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </div>
                </section>
            </article>
        </article>
    </main>
</template>

<style>
main {
    display: flex;
    flex: 1;
    justify-content: center;
    align-items: center;
}

#content {
    width: 50%;
}

h1 {
    font-size: xx-large;
    text-align: center;
}

.publisher-account {
    color: red;
    text-align: center;
}

#infos {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-areas: "profile-pic profile-info"
        "profile-pic edit-profile-btn"
        "pinned-games pinned-games";
    gap: 0 5vw;
}

#profile-pic {
    grid-area: profile-pic;
    justify-self: center;
}

#profile-pic img {
    width: 288px;
    height: 288px;
    object-fit: cover;
    object-position: 50%;
    border-radius: 50%;
}

#profile-info {
    grid-area: profile-info;
    place-self: center;
}

#profile-info #personal-data {
    margin: 1em;
    border: 1px solid black;
    border-radius: 10px;
    padding: 10px;
}


#profile-info #personal-data h2 {
    padding-left: 10px;
    padding-right: 10px;
}

#profile-info h2 {
    margin: 0.2em 0;
}

#profile-info td {
    vertical-align: top;
    padding: 5px 10px;
}

#profile-info ul {
    margin: 0;
    padding: 0;
}

#profile-info li {
    list-style: none;
}

#edit-profile-btn {
    grid-area: edit-profile-btn;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
}

#edit-profile-btn>button {
    width: 40%;
    height: 100%;
}

#pinned-games {
    grid-area: pinned-games;
}

#pinned-games table {
    border-collapse: collapse;
    width: 100%;
}

#pinned-games th,
#pinned-games td {
    padding: 10px;
    text-align: center;
}

#pinned-games th,
#pinned-games tr:not(:last-child) {
    border-bottom: 1px solid black;
}

.overview {
    margin-top: 1%;
}

.overview-table-container {
    width: 100%;
    height: 100%;
}

.overview-entry {
    width: 100%;
    height: 10%;
    padding: 2.5%;
    display: grid;
    grid-template-areas: "overview-image overview-description unlist-button";
    grid-template-columns: 2fr 4fr 1fr;
}

.overview-image {
    grid-area: overview-image;
    max-height: 250px;
    display: block;
}

.overview-image img {
    max-width: 100%;
    max-height: 250px;
    display: block;
}

.overview-description {
    grid-area: overview-description;
    place-self: center;
    display: block;
}

.unlist-button {
    grid-area: unlist-button;
    place-self: center;
}
</style>