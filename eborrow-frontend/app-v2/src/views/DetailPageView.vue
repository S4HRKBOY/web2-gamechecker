<script setup>
import { onMounted } from "vue";
import { useRoute, useRouter } from 'vue-router'
import NavigationHeader from '../components/NavigationHeader.vue';
import useGameApi from "@/composables/useGameApi";

const router = useRouter();
const route = useRoute();
const gameId = route.params.id;
const accountId = JSON.parse(sessionStorage.getItem('accountId'));
const publisher = JSON.parse(sessionStorage.getItem('publisher'));

const { game, hasGame, hasReviewed, getGameById, accountHasGame, accountHasReviewed, addGame, unlistGame } = useGameApi();

onMounted(async () => {
  await accountHasReviewed(accountId, gameId);
  await accountHasGame(accountId, gameId);
  await getGameById(gameId);
  console.log(game);
});

console.log("Publisher "+ publisher);
console.log("hasGame" + hasGame.value);

const formatDate = (date) => {
  const [year, month, day] = date.split('-');
  return `${day}.${month}.${year}`;
};

const handleEdit = () => {
  if (gameId) {
    router.push(`/game/update-game/${gameId}`);
  }
};

const handleAddOrRemove = async () => {
  console.log("hasGame" + hasGame.value);
  if(hasGame.value) {
    await unlistGame(accountId, gameId);
    hasGame.value = false;
  }
  else {
    await addGame(accountId, gameId);
    hasGame.value = true;
  }
}


</script>

<template>

  <body>
    <NavigationHeader />
    <main>
      <h1 class="headline">{{ game.title }}</h1>
      <div class="detailContainer">
        <img v-if="game.gameImage" id="detailImage" :src="`data:image/jpg;base64,${game.gameImage}`" alt="Game Image">
        <img v-else id="detailImage" src="../assets/images/dummy-image.jpg" alt="Game Image">
        <button v-if="publisher" @click="handleEdit" id="editGameButton">Bearbeiten</button>
        <button v-if="!hasGame" class="addOrRemoveGameButton" @click="handleAddOrRemove">Zu Liste hinzufügen</button>
        <button v-else class="addOrRemoveGameButton" @click="handleAddOrRemove">Von Liste entfernen</button>
        <table id="detailInfo">
          <tr>
            <td>Plattform:</td>
            <td>{{ game.platforms.join(', ') }}</td>
          </tr>
          <tr>
            <td>Genre:</td>
            <td>{{ game.genres.join(', ') }}</td>
          </tr>
          <tr>
            <td>Veröffentlichung:</td>
            <td>{{ formatDate(game.publicationDate) }}</td>
          </tr>
          <tr>
            <td>Entwickler:</td>
            <td>{{ game.developer }}</td>
          </tr>
          <tr>
            <td>Publisher:</td>
            <td>{{ game.publisher }}</td>
          </tr>
          <tr>
            <td>Altersfreigabe:</td>
            <td>{{ game.ageRating }}</td>
          </tr>
        </table>
        <p id="detailDescription"> {{ game.description }}
        </p>
      </div>

      <!--section>
        <h2 class="headline">Reviews</h2>
        <form class="reviewForm">
          <input type="text" id="reviewFormHeadline" name="reviewFormHeadline" placeholder="Titel" maxlength="100"
            required>
          <textarea id="reviewText" name="reviewText" maxlength="2000" required></textarea>
          <label for="recommendation">Deine Bewertung?</label>
          <select name="recommendation" id="recommendation" required>
            <option value="">--</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
          </select>
          <input id="submit" type="submit" value="Review veröffentlichen">
        </form>
        <div class="reviewContainer">
          <img class="reviewImage" src="../images/profile_pic_default.svg" alt="Profilbild">
          <p class="reviewUsername">codfan#1</p>
          <time class="reviewDate">07.11.2024</time>
          <h3 class="reviewHeadline">Mega Game</h3>
          <p class="reviewRating">Bewertung: 9/10</p>
          <p class="reviewContent">"Call of Duty: Modern Warfare 3 bietet eine spannende Kampagne mit
            packender Story und intensiven Action-Momenten. Der Mehrspieler-Modus überzeugt mit gutem
            Waffenbalancing, neuen Maps und abwechslungsreichen Spielmodi. Ein großartiger Shooter, der die
            Reihe gelungen fortsetzt."
          </p>
          <button id="deleteReviewButton">Löschen</button>
          <button id="editReviewButton">Bearbeiten</button>
        </div>
        <div class="reviewContainer">
          <img class="reviewImage" src="../images/profile_pic_default.svg" alt="Profilbild">
          <p class="reviewUsername">gamer89</p>
          <time class="reviewDate">07.11.2024</time>
          <h3 class="reviewHeadline">Könnte besser sein</h3>
          <p class="reviewRating">Bewertung: 3/10</p>
          <p class="reviewContent">"Call of Duty: Modern Warfare 3 fühlt sich in der Kampagne oft wie eine
            Wiederholung der Vorgänger an, ohne neue frische Ideen. Der Mehrspieler-Modus hat noch immer
            Balance-Probleme, und die Karten wirken uninspiriert. Insgesamt solide, aber nicht bahnbrechend."
          </p>
        </div>
      </section!-->
    </main>
  </body>
</template>

<style scoped>
.headline {
  padding-left: 25%;
  padding-right: 25%;
}

.detailContainer,
.reviewForm,
.reviewContainer {
  display: grid;
  margin: 1% 25% 1% 25%;
  border: 1px solid black;
  border-radius: 10px;
  padding: 10px;
  gap: 10px;
}

.detailContainer {
  grid-template-columns: auto 60%;
  grid-template-rows: auto;
  grid-template-areas:
    "editGameButton addOrRemoveGameButton"
    "detailImage detailInfo"
    "detailDescription detailDescription"
}

#editGameButton {
  justify-self: left;
  grid-area: editGameButton;
  height: min-content;
}

.addOrRemoveGameButton {
  grid-area: addOrRemoveGameButton;
  justify-self: end;
  height: 2.2em;
  width: 16.2em;
}

th,
td {
  padding: 5px;
  vertical-align: top;
}

#detailInfo {
  grid-area: detailInfo;
  align-self: start;
}

#detailImage {
  max-width: 100%;
  max-height: 300px;
  grid-area: detailImage;
  justify-self: center;
}

#detailDescription {
  grid-area: detailDescription;
}

.reviewForm {
  grid-template-columns: auto auto 150px;
  grid-template-rows: auto;
  grid-template-areas:
    "reviewFormHeadline reviewFormHeadline recommendation-label"
    "reviewText reviewText recommendation"
    "reviewText reviewText ."
    ". . submit"
}

#reviewFormHeadline {
  grid-area: reviewFormHeadline;
}

#reviewText {
  grid-area: reviewText;
}

label[for="recommendation"] {
  grid-area: recommendation-label;
  justify-self: end;
}

#recommendation {
  grid-area: recommendation;
  justify-self: end;
}

#submit {
  grid-area: submit;
}

#editReviewButton {
  grid-area: editReviewButton;
  justify-self: end;
  height: fit-content;
  margin-top: 20px;
}

#deleteReviewButton {
  grid-area: deleteReviewButton;
  justify-self: left;
  height: fit-content;
  margin-top: 20px;
}

textarea {
  overflow-y: scroll;
  height: 100px;
  resize: none;
  padding: 10px 20px 10px 10px;
  text-align: justify;
  overflow-x: hidden;
}

.reviewContainer {
  grid-template-columns: 3% auto auto;
  grid-template-rows: auto;
  grid-template-areas:
    "reviewImage reviewUsername reviewDate"
    "reviewHeadline reviewHeadline reviewRating"
    "reviewContent reviewContent reviewContent"
    "deleteReviewButton . editReviewButton"
}

.reviewImage {
  max-width: 30px;
  max-height: 30px;
  grid-area: reviewImage;
}

.reviewUsername {
  grid-area: reviewUsername;
}

.reviewDate {
  grid-area: reviewDate;
  justify-self: end;
}

.reviewHeadline {
  grid-area: reviewHeadline;

}

.reviewRating {
  grid-area: reviewRating;
  justify-self: end;
}

.reviewContent {
  grid-area: reviewContent;
}

.reviewContainer p,
.reviewContainer time {
  margin: 0;
}
</style>
