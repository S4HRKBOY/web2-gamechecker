<script setup>
import { onMounted, ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import NavigationHeader from '../components/NavigationHeader.vue'
import useGameApi from '@/composables/useGameApi'
import PTH_DEFAULT_GAME_PIC from '@/assets/images/logo.svg'
import PTH_DEFAULT_USER_PIC from '@/assets/images/profile_pic_default.svg'

const router = useRouter()
const route = useRoute()
const gameId = route.params.id
let accountId
let publisher
try {
  accountId = JSON.parse(sessionStorage.getItem('accountId'))
  publisher = JSON.parse(sessionStorage.getItem('publisher'))
} catch (error) {
  console.error(error)
}

const formIsValid = ref(true)
const editReviewMode = ref(false)
const review = ref({
  id: '',
  reviewHeadline: '',
  reviewText: '',
  rating: '',
  reviewDate: '',
  accountDto: {},
})

const game = ref({
  id: '',
  title: '',
  description: '',
  platforms: [],
  genres: [],
  publicationDate: '',
  ageRating: '',
  developer: '',
  publisher: '',
  gameImage: '',
  reviewsDto: [],
})

const {
  hasGame,
  hasReviewed,
  createReview,
  updateReview,
  getRichGameById,
  accountHasGame,
  accountHasReviewed,
  addGame,
  unlistGame,
  deleteReviewById,
} = useGameApi()

onMounted(async () => {
  await accountHasReviewed(accountId, gameId)
  await accountHasGame(accountId, gameId)
  try {
    game.value = await getRichGameById(gameId)
  } catch {
    alert('Ein Fehler ist aufgetreten.')
    router.push(`/home`)
  }
})

const formatDate = (date) => {
  if (date) {
    const [year, month, day] = date.split('-')
    return `${day}.${month}.${year}`
  }
}

const handleGameEdit = () => {
  if (gameId) {
    router.push(`/game/update-game/${gameId}`)
  }
}

const handleAddOrRemove = async () => {
  if (hasGame.value) {
    await unlistGame(accountId, gameId)
    hasGame.value = false
  } else {
    await addGame(accountId, gameId)
    hasGame.value = true
  }
}

const handleCreateReview = async () => {
  const date = new Date()
  date.toISOString().split('T')[0]
  review.value.reviewDate = date
  if (validateBeforeSubmit()) {
    review.value = await createReview({
      reviewData: review.value,
      gameId: gameId,
      accountId: accountId,
    })
    game.value.reviewsDto.push(review.value)
    hasReviewed.value = !hasReviewed.value
  } else {
    alert('Bitte alle Felder ausfüllen.')
  }
}

const handleUpdateReview = async (rev) => {
  if (validateBeforeSubmit()) {
    if (rev.id) {
      const updatedReview = await updateReview({ reviewId: rev.id, reviewData: rev })
      const index = game.value.reviewsDto.findIndex((review) => review.accountDto.id === accountId)
      if (index !== -1) {
        game.value.reviewsDto.splice(index, 1, updatedReview)
      }
      handleEditReview(review)
    } else {
      alert('Kein Review zum aktualisieren vorhanden.')
    }
  } else {
    alert('Bitte alle Felder ausfüllen.')
  }
}

const handleDeleteReview = async (id) => {
  if (confirm('Review wird endgültig gelöscht.')) {
    await deleteReviewById(id)
    game.value.reviewsDto = game.value.reviewsDto.filter((review) => review.id !== id)
    review.value = {
      id: '',
      reviewHeadline: '',
      reviewText: '',
      rating: '',
      reviewDate: '',
      accountDto: {},
    }
    hasReviewed.value = !hasReviewed.value
  }
}

const handleEditReview = async (reviewToEdit) => {
  editReviewMode.value = !editReviewMode.value
  if (editReviewMode.value) {
    review.value = reviewToEdit
  }
}

const handleCancelReview = () => {
  editReviewMode.value = !editReviewMode.value
}

const validateBeforeSubmit = () => {
  formIsValid.value = true
  if (!review.value.reviewHeadline || review.value.reviewHeadline.trim() === '') {
    formIsValid.value = false
  }
  if (!review.value.reviewText || review.value.reviewText.trim() === '') {
    formIsValid.value = false
  }
  if (!review.value.rating || review.value.rating === '') {
    formIsValid.value = false
  }
  return formIsValid.value
}

const sortedReviews = computed(() => {
  const userReview = game.value.reviewsDto.find((review) => review.accountDto?.id === accountId)
  const otherReviews = game.value.reviewsDto.filter((review) => review.accountDto?.id !== accountId)
  otherReviews.sort((a, b) => {
    const dateA = new Date(a.reviewDate)
    const dateB = new Date(b.reviewDate)
    if (dateA !== dateB) {
      return dateB - dateA
    }
    return a.id - b.id
  })
  if (userReview && !editReviewMode.value) {
    return [userReview, ...otherReviews]
  } else {
    return otherReviews
  }
})

function imgToSrc(img) {
  return img ? `data:image/jpeg;base64,${img}` : PTH_DEFAULT_GAME_PIC
}
</script>

<template>
  <NavigationHeader />
  <main>
    <section>
      <h1 class="headline">{{ game.title }}</h1>
      <div class="detailContainer">
        <img id="detailImage" :src="imgToSrc(game.gameImage)" alt="Game Image" />
        <button v-if="publisher" @click="handleGameEdit" id="editGameButton">Bearbeiten</button>
        <button v-if="!hasGame" class="addOrRemoveGameButton" @click="handleAddOrRemove">
          Zur Liste hinzufügen
        </button>
        <button v-else class="addOrRemoveGameButton" @click="handleAddOrRemove">
          Von Liste entfernen
        </button>
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
        <p id="detailDescription">{{ game.description }}</p>
      </div>
    </section>

    <section id="reviewSection">
      <h2 class="headline">Reviews</h2>

      <form
        v-if="(!hasReviewed && !publisher) || editReviewMode"
        class="reviewForm"
        @submit.prevent
      >
        <input
          type="text"
          class="reviewFormHeadline"
          name="reviewFormHeadline"
          placeholder="Titel"
          maxlength="100"
          required
          v-model="review.reviewHeadline"
        />
        <textarea
          class="reviewText"
          name="reviewText"
          maxlength="2000"
          v-model="review.reviewText"
          placeholder="Schreibe ein Review!"
          required
        ></textarea>
        <label for="recommendation">Deine Bewertung?</label>
        <select name="recommendation" class="recommendation" v-model="review.rating" required>
          <option v-for="n in 10" :key="n" :value="n">{{ n }}</option>
        </select>
        <input
          v-if="editReviewMode"
          class="submit"
          type="submit"
          value="Review veröffentlichen"
          @click="handleUpdateReview(review)"
        />
        <input
          v-else
          class="submit"
          type="submit"
          value="Review veröffentlichen"
          @click="handleCreateReview"
        />
        <button v-if="editReviewMode" class="cancel" @click="handleCancelReview">Abbrechen</button>
      </form>

      <article class="reviewContainer" v-for="rev in sortedReviews" :key="rev.id">
        <img
          class="reviewImage"
          :src="
            rev.accountDto?.profilePicture
              ? `data:image/jpg;base64,${rev.accountDto?.profilePicture}`
              : PTH_DEFAULT_USER_PIC
          "
          alt="Profilbild"
        />

        <p class="reviewUsername">{{ rev.accountDto?.username }}</p>
        <p class="reviewDate">
          <time :datetime="rev.reviewDate">{{ formatDate(rev.reviewDate) }}</time>
        </p>
        <h3 class="reviewHeadline">{{ rev.reviewHeadline }}</h3>
        <p class="reviewRating">Bewertung: {{ rev.rating }}/10</p>
        <p class="reviewContent">{{ rev.reviewText }}</p>
        <button
          v-if="rev.accountDto?.id === accountId"
          id="deleteReviewButton"
          @click="handleDeleteReview(rev.id)"
        >
          Löschen
        </button>
        <button
          v-if="rev.accountDto?.id === accountId"
          id="editReviewButton"
          @click="handleEditReview(rev)"
        >
          Bearbeiten
        </button>
      </article>

      <div v-if="game.reviewsDto.length === 0" class="emptyReviewContainer">
        <p>Es sind noch keine Reviews vorhanden.</p>
      </div>
    </section>
  </main>
</template>

<style scoped>
.headline {
  padding-left: 25%;
  padding-right: 25%;
  font-size: 2em;
}

.reviewForm,
.reviewContainer,
.detailContainer,
.emptyReviewContainer {
  display: grid;
  margin: 1% 25% 1% 25%;
  border: 1px solid hsl(0, 0%, 50%);
  padding: 10px;
  gap: 10px;
  box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
}

.detailContainer {
  grid-template-columns: auto 60%;
  grid-template-rows: auto;
  grid-template-areas:
    'editGameButton addOrRemoveGameButton'
    'detailImage detailInfo'
    'detailDescription detailDescription';
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
  align-self: center;
}

textarea {
  overflow-y: scroll;
  min-height: 100px;
  resize: vertical;
  padding: 10px 20px 10px 10px;
  text-align: justify;
  overflow-x: hidden;
}

.reviewForm {
  grid-template-columns: auto auto 150px;
  grid-template-rows: auto;
  grid-template-areas:
    'reviewFormHeadline reviewFormHeadline recommendation-label'
    'reviewText reviewText recommendation'
    'reviewText reviewText .'
    'cancel . submit';
}

label[for='recommendation'] {
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
  color: red;
}

#detailDescription {
  grid-area: detailDescription;
  hyphens: auto;
}

#reviewSection {
  padding-bottom: 50px;
}

.reviewContainer {
  grid-template-columns: 4% auto auto;
  grid-template-rows: auto;
  grid-template-areas:
    'reviewImage reviewUsername reviewDate'
    'reviewHeadline reviewHeadline reviewRating'
    'reviewContent reviewContent reviewContent'
    'deleteReviewButton . editReviewButton';
}

.reviewImage {
  width: 40px;
  height: 40px;
  grid-area: reviewImage;
  object-fit: cover;
  object-position: 50%;
  border-radius: 50%;
  box-shadow: rgba(192, 192, 192) 1.95px 1.95px 2.6px;
}

.reviewUsername {
  grid-area: reviewUsername;
  align-self: center;
  letter-spacing: 1px;
  font-size: larger;
  padding-left: 10px;
  color: rgba(0, 0, 0, 0.8);
}

.reviewDate {
  grid-area: reviewDate;
  justify-self: end;
}

.reviewHeadline {
  grid-area: reviewHeadline;
  hyphens: auto;
}

.reviewRating {
  grid-area: reviewRating;
  justify-self: end;
}

.reviewContent {
  grid-area: reviewContent;
  hyphens: auto;
}

.reviewContainer p,
.reviewContainer time {
  margin: 0;
}
</style>
