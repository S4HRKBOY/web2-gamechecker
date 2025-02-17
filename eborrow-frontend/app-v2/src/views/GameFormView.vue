<script setup>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from 'vue-router'

import NavigationHeader from '../components/NavigationHeader.vue';
import useGameApi from "@/composables/useGameApi";
import CustomDropdown from "../components/GameEdit/CustomDropdown.vue";

const route = useRoute();
const router = useRouter();
const gameId = route.params.id;

const formIsValid = ref(true);
const hiddenFileInput = ref(null);

const game = ref({
  id: '',
  title: '',
  description:'',
  platforms: [],
  genres: [],
  publicationDate: '',
  ageRating: '',
  developer: '',
  publisher: '',
  gameImage: '',
})

const {
  platforms,
  genres,
  ageRatings,
  newGameId,
  getAllPlatforms,
  getAllGenres,
  getAllAgeRatings,
  getGameById,
  updateGame,
  deleteGameById,
  createGame } = useGameApi();

onMounted(async () => {
  await getAllPlatforms();
  await getAllGenres();
  await getAllAgeRatings();
  if (gameId) {
    try {
      game.value = await getGameById(gameId);
    }
    catch {
      alert("Ein Fehler ist aufgetreten.");
      router.push(`/home`);
    }
  };
});

const handleSave = async () => {
  if (validateBeforeSubmit()) {
    if (game.value.id) {
      await updateGame({ id: gameId, gameData: game.value });
      router.push(`/game/${gameId}`);
    } else {
      alert("Kein Spiel zum aktualisieren vorhanden.")
    }
  } else {
    alert("Bitte alle Felder ausfüllen.")
  }
};

const handleDelete = async () => {
  if (gameId) {
    if (confirm("Spiel wird endgültig gelöscht.")) {
      await deleteGameById(gameId);
      router.push("/home");
    }
  }
};

const handleCreate = async () => {
  if (validateBeforeSubmit()) {
    await createGame(game.value);
    router.push(`/game/${newGameId.value}`)
  } else {
    alert("Bitte alle Felder ausfüllen.")
  }
};

const validateBeforeSubmit = () => {
  formIsValid.value = true;
  if (!game.value.title || game.value.title.trim() === '') {
    formIsValid.value = false;
  }
  if (!game.value.description || game.value.description.trim() === '') {
    formIsValid.value = false;
  }
  if (!game.value.platforms || game.value.platforms.length === 0) {
    formIsValid.value = false;
  }
  if (!game.value.genres || game.value.genres.length === 0) {
    formIsValid.value = false;
  }
  if (!game.value.publicationDate) {
    formIsValid.value = false;
  }
  if (!game.value.ageRating) {
    formIsValid.value = false;
  }
  if (!game.value.developer || game.value.developer.trim() === '') {
    formIsValid.value = false;
  }
  if (!game.value.publisher || game.value.publisher.trim() === '') {
    formIsValid.value = false;
  }
  return formIsValid.value;
}

const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    if (!file.type.startsWith("image/")) {
      alert("Bitte eine gültige Bilddatei auswählen.");
      return;
    }
    const reader = new FileReader();
    reader.onload = () => {
      game.value.gameImage = reader.result.split(",")[1];
    };
    reader.readAsDataURL(file);
  }
};

const triggerFileInput = () => {
  if (hiddenFileInput.value) {
    hiddenFileInput.value.click();
  }
};

const handleImageDelete = () => {
  if (game.value.gameImage) {
    game.value.gameImage = '';
  }
}

const handleCancel = () => {
  if (gameId) {
    router.push(`/game/${gameId}`)
  } else {
    router.push(`/home`)
  }
}

</script>

<template>
  <NavigationHeader />
  <main>
    <div class="headline">
      <h1 v-if="gameId === undefined" id="headline">Spiel anlegen</h1>
      <h1 v-else id="headline">Spiel bearbeiten</h1>
      <input type="submit" value="Abbrechen" id="cancel" @click="handleCancel">
    </div>
    <form id="gameForm" @submit.prevent>
      <label for="title">Spieletitel</label>
      <input id="title" type="text" name="title" maxlength="150" v-model="game.title" required>
      <label for="platform">Plattform</label>
      <CustomDropdown id="platform-grid" buttonText="Verfügbare Plattformen auswählen" :items="platforms"
        v-model="game.platforms">
      </CustomDropdown>
      <label for="description">Beschreibung</label>
      <textarea id="description" name="description" v-model="game.description" maxlength="5000" required></textarea>
      <label for="genre">Genre</label>
      <CustomDropdown id="genre-grid" buttonText="Genre auswählen" :items="genres" v-model="game.genres">
      </CustomDropdown>
      <label for="publication">Veröffentlichung</label>
      <input id="publication" type="date" name="publication" v-model="game.publicationDate" required>
      <label for="developer">Entwickler</label>
      <input id="developer" type="text" name="developer" maxlength="100" v-model="game.developer" required>
      <label for="publisher">Publisher</label>
      <input id="publisher" type="text" name="publisher" maxlength="100" v-model="game.publisher" required>
      <label for="age">Altersfreigabe</label>
      <select name="age" id="age" v-model="game.ageRating">
        <option v-for="ageRating in ageRatings" :key="ageRating" :value="ageRating">{{ ageRating }}</option>
      </select>
      <label for="file" @click.prevent>Bild hochladen</label>
      <input id="hidden-file" type="file" name="file" accept="image/*" @change="handleImageUpload"
        ref="hiddenFileInput">
      <input id="file" type="button" name="file" @click="triggerFileInput" value="Bild auswählen" />
      <fieldset id="imagearea">
        <legend>Vorschaubild</legend>
        <img v-if="game.gameImage" id="gameImage" :src="`data:image/jpg;base64,${game.gameImage}`" alt="Game Image" />
      </fieldset>
      <input v-if="game.gameImage" id="delete-image" type="button" value="Bild entfernen" @click="handleImageDelete">
      <input v-if="gameId === undefined" id="submit" type="submit" value="Neues Spiel anlegen" @click="handleCreate">
      <input v-if="gameId !== undefined" id="submit" type="submit" value="Änderungen speichern" @click="handleSave">
      <input v-if="gameId !== undefined" id="delete" type="submit" value="Spiel löschen" @click="handleDelete">
    </form>
  </main>
</template>

<style scoped>
#headline {
  padding-left: 25%;
}

.headline {
  display: flex;
}

#gameForm {
  display: grid;
  margin: 0 25% 5% 25%;
  border: 1px solid hsl(0, 0%, 50%);
  box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
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
    "file-label file-label delete-image delete-image"
    "file file preview-image preview-image"
    "delete delete cancel submit"
}

#submit,
#age,
#publication,
#delete,
#cancel {
  height: fit-content;
  width: fit-content;
}

textarea {
  overflow-y: scroll;
  min-height: 150px;
  resize: vertical;
  padding: 10px 20px 10px 10px;
  text-align: justify;
  overflow-x: hidden;
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
  height: 1.5em;
  align-content: end;
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

#delete-image {
  grid-area: delete-image;
  width: fit-content;
  justify-self: end;
  height: fit-content;
}

#submit {
  grid-area: submit;
  justify-self: end;
}

#delete {
  grid-area: delete;
  color: red;
}

#cancel {
  display: flex;
  align-self: self-end;
  margin: 25px;
}

#imagearea {
  grid-area: preview-image;
  border: 1px solid hsl(0, 0%, 75%);
  padding: 10px;
  height: 300px;
  text-align: center;
}

#gameImage {
  max-width: 100%;
  max-height: 100%;
}

#hidden-file {
  display: none;
}

#file {
  height: fit-content;
  grid-area: file;
  width: 50%;
}
</style>
