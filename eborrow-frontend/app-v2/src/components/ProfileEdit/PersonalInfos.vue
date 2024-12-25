<script></script>

<template>
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
</template>

<style>
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
</style>