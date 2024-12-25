<script setup>
import NavigationHeader from '../components/NavigationHeader.vue'
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
onMounted(async () => console.log("Id in route is: " + route.params.id));
</script>

<template>
    <NavigationHeader />
    <main>
        <article id="content">
            <section class="update-section">
                <form class="update-form">
                    <section class="form-content">
                        <section class="set-personal-infos">
                            <fieldset>
                                <legend>Persönliche Daten</legend>
                                <div class="form-input">
                                    <label for="surname">Name</label>
                                    <input type="text" id="surname" name="surname" required>
                                </div>
                                <div class="form-input">
                                    <label for="prename">Vorname</label>
                                    <input type="text" id="prename" name="prename" required>
                                </div>
                                <div class="form-input">
                                    <label for="birthday">Geburtsdatum</label>
                                    <input type="date" id="birthday" name="birthday">
                                </div>
                                <div class="form-input">
                                    <label for="email">E-Mail Adresse</label>
                                    <input type="email" id="email" name="email" required>
                                </div>
                            </fieldset>
                            <fieldset>
                                <legend>Anmeldeinformationen</legend>
                                <div class="form-input">
                                    <label for="username">Benutzername</label>
                                    <input type="text" id="username" name="username" required>
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
                                    <img src="data:," alt="Profilbild">
                                </figure>
                                <div class="form-input">
                                    <label for="profile-pic-fileselect">Profilbild</label>
                                    <input type="file" accept="image/*" id="profile-pic-fileselect"
                                        name="profile-pic-fpath">
                                </div>
                            </fieldset>
                        </section>
                    </section>
                    <section class="send-form-options">
                        <RouterLink to="/account/42" class="cancel-link">Abbrechen</RouterLink>
                        <button type="submit">Änderungen speichern</button>
                    </section>
                </form>
                <form class="delete-profile">
                    <button type="submit">Profil löschen</button>
                </form>
            </section>
        </article>
    </main>
</template>

<style>
main {
    display: flex;
    flex-grow: 1;
    justify-content: center;
    align-items: center;
}

#content {
    width: 50%;
}

fieldset {
    border: 1px solid hsl(0, 0%, 75%);
    border-radius: 10px;
    padding: 10px;
}

/*input-styling*/
input:invalid,
select:invalid {
    border-color: red;
    outline: none;
}

.form-input {
    display: flex;
    column-gap: 10px;
}

.form-input>label {
    flex: 2;
}

.form-input>input {
    flex: 3;
}

.form-content {
    display: grid;
    grid-template-areas: "set-personal-infos set-profile-pic";
    grid-template-columns: 2fr 1fr;
    gap: 15px;
}

.set-personal-infos {
    display: flex;
    grid-area: set-personal-infos;
    flex-direction: column;
}

.set-personal-infos>fieldset {
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: space-evenly;
}

.set-profile-pic {
    grid-area: set-profile-pic;
}

.set-profile-pic {
    grid-area: set-profile-pic;
}

.set-profile-pic .form-input>label {
    text-align: end;
}

.profile-pic {
    justify-self: center;
}

.profile-pic img {
    width: 288px;
    height: 288px;
    object-fit: cover;
    object-position: 50%;
    border-radius: 50%;
}

.send-form-options {
    display: flex;
    justify-content: end;
    gap: 10px;
    margin-top: 10px;
}

.delete-profile {
    display: inline;
    position: relative;
    top: -21px;
}

.delete-profile>* {
    color: red;
}
</style>