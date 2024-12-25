<script></script>

<template>
    <header>
        <RouterLink to="/" class="logo-container"><img class="logo" src="../assets/images/logo.svg" alt="logo"></RouterLink>
        <nav class="navigation">
            <ul class="nav-links">
                <li><RouterLink to="/home">Home</RouterLink></li>
                <li><RouterLink to="/gameForm">Neues Spiel anlegen</RouterLink></li>
                <li><RouterLink to="/account">Profil</RouterLink></li>
                <li><RouterLink to="/login">Logout</RouterLink></li>
            </ul>
        </nav>
    </header>
</template>

<style>
header{
    display: grid;
    grid-template-areas: "logo links";
    grid-template-columns: 30% auto;
    grid-template-rows: auto;
    align-items: center;
    position: sticky;
    top: 0;
    background-color: rgb(192, 192, 192);
}

header>.logo-container{
    grid-area: logo;
    place-self: center;
}

.navigation{
    grid-area: links;
}

.nav-links{
    list-style: none;
    display: flex;
    align-items: center;
    justify-items: center;
    justify-content: space-evenly;
}

.nav-links li {
    display: inline-block;
    border: 1px solid #2b2929;
    border-radius: 5px;
    padding: 5px; 
    background-color: #2b2929;
}

.nav-links li:hover {
    display: inline-block;
    border: 1px solid #2b2929;
    border-radius: 5px;
    padding: 5px; 
    background-color: rgb(192, 192, 192);
}

.nav-links li a{
    transition: all 0.3s ease;
    color: white;
    font-style: italic;
    text-decoration: none;
}

.nav-links li:hover a{
    font-style: italic;
    color: black;
    text-decoration: none;
}
</style>