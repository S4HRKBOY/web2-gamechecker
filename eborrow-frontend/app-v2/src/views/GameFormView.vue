<script setup>
import { onMounted} from "vue";
import { useRoute, useRouter } from 'vue-router'

//const router = useRouter()
const route = useRoute()
import NavigationHeader from '../components/NavigationHeader.vue';
import useGameApi from "@/composables/useGameApi";
import CustomDropdown from "../components/GameEdit/CustomDropdown.vue";

const { platforms, genres, ageRatings, getAllPlatforms, getAllGenres, getAllAgeRatings} = useGameApi();
onMounted(async () => {
  await getAllPlatforms();
  await getAllGenres();
  await getAllAgeRatings();
});

const gameId = route.params.id;
</script>

<template>

  <body>
    <NavigationHeader />

    <main>
      <h1 v-if="gameId === undefined" id="headline">Spiel anlegen</h1>
      <h1 v-else id="headline">Spiel bearbeiten</h1>
      <form id="gameForm" action="./detail_page.html">
        <label for="title">Spieletitel</label>
        <input id="title" type="text" name="title" maxlength="255" required>
        <label for="platform">Plattform</label>
        <CustomDropdown buttonText="Verfuegbare Plattformen auswaehlen" :items="platforms" name="platform">
        </CustomDropdown>
        <label for="description">Beschreibung</label>
        <textarea id="description" name="description" required></textarea>
        <label for="genre">Genre</label>
        <CustomDropdown buttonText="Genre auswählen" :items="genres" name="genre">
        </CustomDropdown>
        <label for="publication">Veröffentlichung</label>
        <input id="publication" type="date" name="publication" required>
        <label for="developer">Entwickler</label>
        <input id="developer" type="text" name="developer" maxlength="255" required>
        <label for="publisher">Publisher</label>
        <input id="publisher" type="text" name="publisher" maxlength="255" required>
        <label for="age">Altersfreigabe</label>
        <select name="age" id="age">
          <option v-for="(ageRating, index) in ageRatings" :key="ageRating" :value="index">{{ageRating}}</option>
        </select>
        <label for="file">Bild hochladen</label>
        <input id="file" type="file" name="file" accept="image/*">
        <fieldset id="imagearea">
          <legend>Vorschaubild</legend>
        </fieldset>
        <input v-if="gameId === undefined" id="submit" type="submit" value="Neues Spiel anlegen">
        <input v-if="gameId !== undefined" id="submit" type="submit" value="Änderungen speichern">
        <input v-if="gameId !== undefined" id="delete" type="submit" value="Spiel löschen">
      </form>
    </main>
  </body>
</template>

<style scoped>
#headline {
  padding-left: 25%;
}

#gameForm {
  display: grid;
  margin: 0 25% 0 25%;
  border: 1px solid black;
  border-radius: 10px;
  padding: 10px;
  gap: 10px;
  grid-template-columns: 24% 24% 24% 24%;
  grid-template-rows: auto;
  grid-template-areas:
    "title-label title-label platform-label platform-label"
    "title title platform platform"
    "description-label description-label description-label description-label"
    "description description description description"
    "genre-label genre-label publication-label age-label"
    "genre genre publication age"
    "developer-label developer-label publisher-label publisher-label"
    "developer developer publisher publisher"
    "file-label file-label file-label file-label"
    "file file preview-image preview-image"
    "delete delete submit submit"
}

#submit,
#age,
#publication,
#delete {
  height: fit-content;
  width: fit-content
}

textarea {
  overflow-y: scroll;
  height: 150px;
  resize: none;
  padding: 10px 20px 10px 10px;
  text-align: justify;
  overflow-x: hidden;
}

#gameImage {
  max-width: 100%;
  max-height: 100%;
}

#imagearea {
  grid-area: preview-image;
  border: 1px solid hsl(0, 0%, 75%);
  border-radius: 10px;
  padding: 10px;
  height: 300px;
  text-align: center;
}

label[for="title"] {
  grid-area: title-label;
}

label[for="platform"] {
  grid-area: platform-label;
}

label[for="description"] {
  grid-area: description-label;
}

label[for="genre"] {
  grid-area: genre-label;
}

label[for="publication"] {
  grid-area: publication-label;
}

label[for="developer"] {
  grid-area: developer-label;
}

label[for="publisher"] {
  grid-area: publisher-label;
}

label[for="age"] {
  grid-area: age-label;
}

label[for="file"] {
  grid-area: file-label;
}

#title {
  grid-area: title;
}

#platform-grid {
  grid-area: platform;
}

#description {
  grid-area: description;
}

#genre-grid {
  grid-area: genre;
}

#publication {
  grid-area: publication;
}

#developer {
  grid-area: developer;
}

#publisher {
  grid-area: publisher;
}

#age {
  grid-area: age;
}

#file {
  grid-area: file;
}

#submit {
  grid-area: submit;
  justify-self: end;
}

#delete {
  grid-area: delete;
}
</style>
