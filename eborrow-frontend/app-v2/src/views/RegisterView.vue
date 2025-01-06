<script setup></script>

<template>
    <main>
        <article id="content">
            <section class="register-section">
                <figure class="logo">
                    <RouterLink to="/"><img src="../assets/images/logo.svg" alt="Logo von Game-Tracker"></RouterLink>
                </figure>
                <!-- TODO -->
                <form class="register-form">
                    <fieldset>
                        <div class="form-input">
                            <label for="surname">Name</label>
                            <input type="text" id="surname" name="surname">
                        </div>
                        <div class="form-input">
                            <label for="prename">Vorname</label>
                            <input type="text" id="prename" name="prename">
                        </div>
                        <div class="form-input">
                            <label for="username">Benutzername</label>
                            <input type="text" id="username" name="username">
                        </div>
                        <div class="form-input">
                            <label for="birthday">Geburtsdatum</label>
                            <input type="date" id="birthday" name="birthday" value="2000-01-01">
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-input">
                            <label for="email">E-Mail Adresse</label>
                            <input type="email" id="email" name="email">
                        </div>
                        <div class="form-input">
                            <label for="password">Passwort</label>
                            <input type="password" id="password" name="password">
                        </div>
                        <div class="form-input">
                            <label for="password-confirm">Passwort wiederholen</label>
                            <input type="password" id="password-confirm">
                        </div>
                    </fieldset>
                    <div class="form-button"><button type="submit">KOMM IN
                            DIE GRUPPE!</button></div>
                </form>
            </section>
            <RouterLink to="/login" class="login-link">Ich habe bereits einen Account</RouterLink>
        </article>
    </main>
</template>

<style scoped>
* {
    box-sizing: border-box;
}

main {
    display: flex;
    flex-grow: 1;
    justify-content: center;
    align-items: center;
}

#content {
    border: 1px solid hsl(0, 0%, 75%);
    border-radius: 5px;
    padding: 20px;
    width: 50%;
}

.register-section {
    display: flex;
    align-items: center;
}

.logo {
    flex: 2;
}

.logo img {
    width: 100%;
}

.register-form {
    display: flex;
    flex: 3;
    flex-direction: column;
    gap: 10px;
}

.register-form>* {
    flex: 1;
}

.register-form fieldset {
    display: flex;
    flex-direction: column;
    gap: 2vh;
    border: 1px solid hsl(0, 0%, 75%);
    border-radius: 5px;
    padding: 15px;
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

.form-button {
    display: flex;
    flex-grow: 3;
    justify-content: center;
    align-items: center;
}

.form-button>* {
    width: 62%;
}

.login-link {
    display: block;
    margin-top: 10px;
    font-size: smaller;
    text-align: center;
}
</style>