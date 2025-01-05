<script setup>
import { useRouter } from 'vue-router';

let id;
let publisher;
const router = useRouter();

try {
  if(sessionStorage.getItem('accountId') === null || sessionStorage.getItem('publisher') === null) {
    throw new Error('No account data found.');
  }

  id = JSON.parse(sessionStorage.getItem('accountId'));
  publisher = JSON.parse(sessionStorage.getItem('publisher'));
} catch (error) {
  console.error(error);
  alert('Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.');
  router.push('/login');
}

function logout() {
  sessionStorage.setItem('accountId', '');
  sessionStorage.setItem('publisher', '');
}
</script>

<template>
  <header>
    <nav class="navigation">
      <RouterLink to="/home" class="logo"><img class="logo" src="../assets/images/logo.svg" alt="logo">
      </RouterLink>
      <RouterLink class="home" to="/home">Home</RouterLink>
      <RouterLink v-if="publisher" class="create.game" to="/game/create-game">Neues Spiel anlegen</RouterLink>
      <div v-else class="create-game"></div>
      <RouterLink class="profile" :to="`/account/${id}`">Profil</RouterLink>
      <RouterLink class="logout" to="/login" @click=logout()>Logout</RouterLink>
    </nav>
  </header>
</template>

<style scoped>

header {
  position: sticky;
  top: 0;
  z-index: 99;
}

.navigation {
  display: grid;
  grid-template-columns: 45% 25% 10% 10% 10%;
  grid-template-areas:
    "logo home create-game profile logout";
  align-items: center;
  padding: 0.8em 0.8em;
  background-color: rgb(192, 192, 192);
  box-shadow: rgba(50, 50, 93, 0.25) 0px 13px 27px -5px, rgba(0, 0, 0, 0.3) 0px 8px 16px -8px;
  gap: 10px;
}

.logo {
  grid-area: logo;
  margin-left: 50px;
}

.navigation {
  list-style: none;
  padding-left: 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.24);
}

.navigation a:not(.logo) {
  display: block;
  text-decoration: none;
  color: black;
  width: fit-content;
  padding: 5px 10px 5px 10px;
  border-radius: 5px;
  border: 1px solid black;
  background: rgba(228, 236, 236, 0.3);
  box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
}

.navigation a:not(.logo):hover {
  text-decoration: underline;
}

.navigation a:not(.logo) {
    transform: scale(0.98);
    box-shadow: rgba(0, 0, 0, 0.16) 0px 2px 3px, rgba(0, 0, 0, 0.23) 0px 2px 3px;
}

.home {
  grid-area: home;
}

.create-game {
  grid-area: create-game;
}

.profile {
  grid-area: profile;
}

.logout {
  grid-area: logout;
}
</style>
