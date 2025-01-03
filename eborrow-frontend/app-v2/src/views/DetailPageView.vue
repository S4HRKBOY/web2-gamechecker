<script setup>
import { onMounted, ref, reactive } from "vue";
import { useRoute, useRouter } from 'vue-router'
import { reviewsDto } from '../domain/game.js'
import NavigationHeader from '../components/NavigationHeader.vue';
import useGameApi from "@/composables/useGameApi";

const router = useRouter();
const route = useRoute();
const gameId = route.params.id;
const accountId = JSON.parse(sessionStorage.getItem('accountId'));
const publisher = JSON.parse(sessionStorage.getItem('publisher'));

let formIsValid = ref(true);
let editReviewMode = ref(false);
let review = reactive(reviewsDto());

const { game,
  hasGame,
  hasReviewed,
  createReview,
  updateReview,
  getGameById,
  accountHasGame,
  accountHasReviewed,
  addGame,
  unlistGame,
  deleteReviewById } = useGameApi();


onMounted(async () => {
  await accountHasReviewed(accountId, gameId);
  await accountHasGame(accountId, gameId);
  await getGameById(gameId);
  //console.log(game);
});

const formatDate = (date) => {
  if (date) {
    const [year, month, day] = date.split('-');
    return `${day}.${month}.${year}`;
  }
};

const handleGameEdit = () => {
  if (gameId) {
    router.push(`/game/update-game/${gameId}`);
  }
};

const handleAddOrRemove = async () => {
  if (hasGame.value) {
    await unlistGame(accountId, gameId);
    hasGame.value = false;
  }
  else {
    await addGame(accountId, gameId);
    hasGame.value = true;
  }
}

const handleCreateReview = async () => {
  const date = new Date();
  date.toISOString().split('T')[0];
  review.reviewDate= date;
  if (validateBeforeSubmit()) {
    review = await createReview({reviewData: review, gameId: gameId, accountId: accountId});
    console.log("review");
    console.log(review)
    game.reviewsDto.push(review);
    hasReviewed.value = !hasReviewed.value;
  } else {
    alert("Bitte alle Felder ausfüllen.")
  }
};

const handleUpdateReview = async (rev) => {
  if (validateBeforeSubmit()) {
    if (review.id) {
      const updatedReview = await updateReview({ reviewId: review.id, reviewData: review });
      Object.assign(rev, updatedReview);
      handleEditReview(review);
    } else {
      alert("Kein Review zum aktualisieren vorhanden.")
    }
  } else {
    alert("Bitte alle Felder ausfüllen.")
  }
};

const handleDeleteReview = async (id) => {
  if (confirm("Review wird endgültig gelöscht.")) {
    await deleteReviewById(id);
    await getGameById(gameId);
    game.reviewsDto = game.reviewsDto.filter(review => review.id !== id);
    review = reactive(reviewsDto());
    hasReviewed.value = !hasReviewed.value;
  }
};

const handleEditReview = async (reviewToEdit) => {
  editReviewMode.value = !editReviewMode.value;
  Object.assign(review, reviewToEdit);
};

const handleCancelReview = () => {
  editReviewMode.value = !editReviewMode.value;
}

const validateBeforeSubmit = () => {
  formIsValid.value = true;
  if (!review.reviewHeadline || review.reviewHeadline.trim() === '') {
    formIsValid.value = false;
  }
  if (!review.reviewText || review.reviewText.trim() === '') {
    formIsValid.value = false;
  }
  if (!review.rating || review.rating === '') {
    formIsValid.value = false;
  }
  return formIsValid.value;
}
</script>

<template>

  <body>
    <NavigationHeader />
    <main>
      <section>
        <h1 class="headline">{{ game.title }}</h1>
        <div class="detailContainer">
          <img v-if="game.gameImage" id="detailImage" :src="`data:image/jpg;base64,${game.gameImage}`" alt="Game Image">
          <img v-else id="detailImage" src="../assets/images/dummy-image.jpg" alt="Game Image">
          <button v-if="publisher" @click="handleGameEdit" id="editGameButton">Bearbeiten</button>
          <button v-if="!hasGame" class="addOrRemoveGameButton" @click="handleAddOrRemove">Zur Liste hinzufügen</button>
          <button v-else class="addOrRemoveGameButton" @click="handleAddOrRemove">Von Liste entfernen</button>
          <table id="detailInfo">
            <tbody>
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
            </tbody>
          </table>
          <p id="detailDescription"> {{ game.description }}
          </p>
        </div>
      </section>

      <section>
        <h2 class="headline">Reviews</h2>

        <form v-if="(!hasReviewed && !publisher) || editReviewMode" class="reviewForm" @submit.prevent>
          <input type="text" class="reviewFormHeadline" name="reviewFormHeadline" placeholder="Titel" maxlength="100"
            required v-model="review.reviewHeadline">
          <textarea class="reviewText" name="reviewText" maxlength="2000" v-model="review.reviewText"
            required></textarea>
          <label for="recommendation">Deine Bewertung?</label>
          <select name="recommendation" class="recommendation" v-model="review.rating" required>
            <option v-for="n in 10" :key=n :value=n>{{ n }}</option>
          </select>
          <input v-if="editReviewMode" class="submit" type="submit" value="Review veröffentlichen"
            @click="handleUpdateReview(review)">
          <input v-else class="submit" type="submit" value="Review veröffentlichen" @click="handleCreateReview">
          <input v-if="editReviewMode" class="cancel" type="submit" value="Abbrechen"
            @click="handleCancelReview">
        </form>

        <div v-if="!editReviewMode">
          <div class="reviewContainer" v-for="rev in game.reviewsDto" :key="rev.id">
            <img v-if="review.accountDto.profilePicture" class="reviewImage"
              :src="`data:image/jpg;base64,${rev.accountDto.profilePicture}`" alt="Profilbild">
            <img v-else class="reviewImage" src="../assets/images/profile_pic_default.svg" alt="Profilbild">

            <p class="reviewUsername">{{ rev.accountDto.username }}</p>
            <time class="reviewDate">{{ formatDate(rev.reviewDate) }}</time>
            <h3 class="reviewHeadline">{{ rev.reviewHeadline }}</h3>
            <p class="reviewRating">Bewertung: {{ rev.rating }}/10</p>
            <p class="reviewContent"> {{ rev.reviewText }}
            </p>
            <button v-if="rev.accountDto.id === accountId" id="deleteReviewButton"
              @click="handleDeleteReview(rev.id)">Löschen</button>
            <button v-if="rev.accountDto.id === accountId" id="editReviewButton"
              @click="handleEditReview(rev)">Bearbeiten</button>
          </div>
        </div>

      </section>
    </main>
  </body>
</template>

<style scoped>
.headline {
  padding-left: 25%;
  padding-right: 25%;
}

.reviewForm,
.reviewContainer,
.detailContainer {
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

textarea {
  overflow-y: scroll;
  height: 100px;
  resize: none;
  padding: 10px 20px 10px 10px;
  text-align: justify;
  overflow-x: hidden;
}

.reviewForm {
  grid-template-columns: auto auto 150px;
  grid-template-rows: auto;
  grid-template-areas:
    "reviewFormHeadline reviewFormHeadline recommendation-label"
    "reviewText reviewText recommendation"
    "reviewText reviewText ."
    "cancel . submit"
}

label[for="recommendation"] {
  grid-area: recommendation-label;
  justify-self: end;
}

.recommendation {
  grid-area: recommendation;
  justify-self: end;
}

.reviewFormHeadline {
  grid-area: reviewFormHeadline;
}

.reviewText {
  grid-area: reviewText;
}

.submit {
  grid-area: submit;
}

.cancel {
  grid-area: cancel;
  max-width: fit-content;
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

#detailDescription {
  grid-area: detailDescription;
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
