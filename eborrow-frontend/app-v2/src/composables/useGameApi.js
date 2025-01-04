import { ref, reactive } from "vue";
import axios from 'axios';
import { gameDto, reviewsDto } from '../domain/game.js'

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 8000,
  headers: {
    Accept: 'application/json',
  },
});

export default function useGameApi() {
  const game = reactive(gameDto());
  const platforms = ref([]);
  const genres = ref([]);
  const ageRatings = ref([]);
  const hasGame = ref(false);
  const hasReviewed = ref(false);
  const newGameId = ref(null);
  const review = reactive(reviewsDto());

  //eiter use await or then, not both
  const getRichGameById = async (id) => {
    try {
      const response = await axiosInstance.get(`/game/${id}`)
      Object.assign(game, response.data);
    }
    catch (error) {
      console.log(error);
    }
  };

  const getGameById = async (id) => {
    try {
      const response = await axiosInstance.get(`/game/get-game/${id}`)
      Object.assign(game, response.data);
    }
    catch (error) {
      console.log(error);
    }
  };

  const deleteGameById = async (id) => {
    try {
      await axiosInstance.delete(`/game/delete-game/${id}`)
    }
    catch (error) {
      console.log(error);
    };
  };

  const createGame = async (gameData) => {
    try {
      const response = await axiosInstance.post(`/game/create-game`, gameData);
      console.log(response.data);
      newGameId.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  const updateGame = async ({ id, gameData }) => {
    try {
      await axiosInstance.put(`/game/update-game/${id}`, gameData)
    } catch (error) {
      console.log(error);
    };
  };

  const getAllPlatforms = async () => {
    try {
      const response = await axiosInstance.get(`/game/all-platforms`);
      platforms.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  const getAllGenres = async () => {
    try {
      const response = await axiosInstance.get(`/game/all-genres`);
      genres.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  const getAllAgeRatings = async () => {
    try {
      const response = await axiosInstance.get(`/game/all-age-ratings`);
      ageRatings.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  const accountHasGame = async (accountId, gameId) => {
    try {
      const response = await axiosInstance.get(`/account/account-has-game/${accountId}/${gameId}`);
      hasGame.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  const accountHasReviewed = async (accountId, gameId) => {
    try {
      const response = await axiosInstance.get(`/review/exists-by-account-and-game/${accountId}/${gameId}`);
      hasReviewed.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  const addGame = async (accountId, gameId) => {
    try {
      await axiosInstance.put(`/account/add-game`, {
        'account-Id': accountId,
        'game-Id': gameId
      });
    } catch (error) {
      console.log(error);
    };
  };

  const unlistGame = async (accountId, gameId) => {
    try {
      await axiosInstance.put(`/account/unlist-game`, {
        'account-Id': accountId,
        'game-Id': gameId
      });
    } catch (error) {
      console.log(error);
    };
  }

  const createReview = async ({ reviewData, gameId, accountId }) => {
    try {
      const response = await axiosInstance.post(`/review/create-review?gameId=`
        + gameId + `&accountId=` + accountId,
        reviewData
      );
      return response.data;
    }
    catch (error) {
      console.log(error);
    }
  };

  const deleteReviewById = async (id) => {
    try {
      await axiosInstance.delete(`/review/delete-review/${id}`)
    }
    catch (error) {
      console.log(error);
    };
  };

  const updateReview = async ({ reviewId, reviewData }) => {
    try {
      const response = await axiosInstance.put(`/review/update-review/${reviewId}`, reviewData);
      return response.data;
    }
    catch (error) {
      console.log(error);
    };
  }

  const getReviewById = async (id) => {
    try {
      const response = await axiosInstance.get(`/review/${id}`);
      Object.assign(review, response.data);
    } catch (error) {
      console.log(error);
    };
  }


  return {
    game,
    platforms,
    genres,
    ageRatings,
    hasGame,
    hasReviewed,
    newGameId,
    review,
    getGameById,
    getRichGameById,
    deleteGameById,
    createGame,
    updateGame,
    getAllPlatforms,
    getAllGenres,
    getAllAgeRatings,
    accountHasGame,
    accountHasReviewed,
    addGame,
    unlistGame,
    createReview,
    deleteReviewById,
    updateReview,
    getReviewById
  };
}


