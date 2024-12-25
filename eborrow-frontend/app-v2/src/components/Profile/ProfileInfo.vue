<script setup></script>

<template>
    <section id="profile-info">
        <section id="personal-data">
            <h2>Persönliche Daten</h2>
            <table>
                <tbody>
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
                </tbody>
            </table>
        </section>
    </section>
</template>

<style>
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
</style>