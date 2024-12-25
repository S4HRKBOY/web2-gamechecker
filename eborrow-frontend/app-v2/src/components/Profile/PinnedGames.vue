<script></script>

<template>
    <section id="pinned-games">
        <h2>Angepinnte Spiele</h2>
        <span>Keine Spiele angepinnt</span> <!-- only if account.taggedGames.size() == 0" -->
        <div class="overview">
            <table class="overview-table-container">
                <tbody>
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
                </tbody>
            </table>
        </div>
    </section>
</template>

<style>
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