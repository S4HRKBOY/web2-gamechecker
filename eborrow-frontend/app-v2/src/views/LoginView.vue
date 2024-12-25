<script></script>

<template>
    <main>
        <article id="content">
            <section class="login-section">
                <figure class="logo">
                    <img src="../assets/images/logo.svg" alt="Logo von Game-Tracker">
                </figure>
                <form class="login-form" action="./start_page.html" method="post">
                    <div class="form-input"><input type="text" name="username" placeholder="Benutzername"></div>
                    <div class="form-input"><input type="password" name="password" placeholder="Passwort"></div>
                    <div class="form-button"><button type="submit">Login</button></div>
                </form>
            </section>
            <RouterLink to="/register" class="register-link">Neuen Account registrieren</RouterLink>
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

.login-section {
    display: flex;
    justify-content: center;
}

.logo {
    flex: 2;
}

.logo>img {
    width: 100%;
}

.login-form {
    display: flex;
    flex: 3;
    flex-direction: column;
    gap: 10px;
}

.login-form>* {
    flex: 1;
}

.form-input>* {
    display: block;
    width: 100%;
    height: 100%;
}

.form-button {
    display: flex;
    justify-content: center;
    align-items: center;
}

.form-button>* {
    width: 62%;
    height: 80%;
}

.register-link {
    display: block;
    margin-top: 10px;
    font-size: smaller;
    text-align: center;
}
</style>