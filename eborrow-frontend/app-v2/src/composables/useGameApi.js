import { ref } from "vue";
import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 8000,
  headers: {
    Accept: 'application/json',
  },
});

export default function useGameApi() {
  const platforms = ref([]);
  const genres = ref([]);
  const ageRatings = ref([]);
  const hasGame = ref(false);
  const hasReviewed = ref(false);
  const newGameId = ref(null);

  const getRichGameById = async (id) => {
    try {
      const response = await axiosInstance.get(`/game/${id}`)
      return response.data;
    }
    catch (error) {
      console.error(`Failed to load game with id ${id}:`, error);
      throw error;
    }
  };

  const getGameById = async (id) => {
    try {
      const response = await axiosInstance.get(`/game/get-game/${id}`)
      return response.data;
    }
    catch (error) {
      console.error(`Failed to load game with id ${id}:`, error);
      throw error;
    }
  };

  const deleteGameById = async (id) => {
    try {
      await axiosInstance.delete(`/game/delete-game/${id}`)
    }
    catch (error) {
      console.error(`Failed to delete game with id ${id}:`, error);
    }
  };

  const createGame = async (gameData) => {
    try {
      const response = await axiosInstance.post(`/game/create-game`, gameData);
      newGameId.value = response.data;
    }
    catch (error) {
      console.error(`Failed to create game:`, error);
    }
  };

  const updateGame = async ({ id, gameData }) => {
    try {
      await axiosInstance.put(`/game/update-game/${id}`, gameData)
    } catch (error) {
      console.error(`Failed to update game:`, error);
    }
  };

  const getAllPlatforms = async () => {
    try {
      const response = await axiosInstance.get(`/game/all-platforms`);
      platforms.value = response.data;
    } catch (error) {
      console.error(`Failed to get all platforms:`, error);
    }
  };

  const getAllGenres = async () => {
    try {
      const response = await axiosInstance.get(`/game/all-genres`);
      genres.value = response.data;
    } catch (error) {
      console.error(`Failed to get all genres:`, error);
    }
  };

  const getAllAgeRatings = async () => {
    try {
      const response = await axiosInstance.get(`/game/all-age-ratings`);
      ageRatings.value = response.data;
    } catch (error) {
      console.error(`Failed to get all age ratings:`, error);
    }
  };

  const accountHasGame = async (accountId, gameId) => {
    try {
      const response = await axiosInstance.get(`/account/account-has-game/${accountId}/${gameId}`);
      hasGame.value = response.data;
    } catch (error) {
      console.error(`Failed to check if account has game:`, error);
    }
  };

  const accountHasReviewed = async (accountId, gameId) => {
    try {
      const response = await axiosInstance.get(`/review/exists-by-account-and-game/${accountId}/${gameId}`);
      hasReviewed.value = response.data;
    } catch (error) {
      console.error(`Failed to check if account has reviewed the game:`, error);
    }
  };

  const addGame = async (accountId, gameId) => {
    try {
      await axiosInstance.put(`/account/add-game`, {
        'account-Id': accountId,
        'game-Id': gameId
      });
    } catch (error) {
      console.error(`Failed to add game to account :`, error);
    }
  };

  const unlistGame = async (accountId, gameId) => {
    try {
      await axiosInstance.put(`/account/unlist-game`, {
        'account-Id': accountId,
        'game-Id': gameId
      });
    } catch (error) {
      console.error(`Failed to unlist game from account:`, error);
    }
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
      console.error(`Failed to create review:`, error);
    }
  };

  const deleteReviewById = async (id) => {
    try {
      await axiosInstance.delete(`/review/delete-review/${id}`)
    }
    catch (error) {
      console.error(`Failed to delete review with id: ${id}:`, error);
    }
  };

  const updateReview = async ({ reviewId, reviewData }) => {
    try {
      const response = await axiosInstance.put(`/review/update-review/${reviewId}`, reviewData);
      return response.data;
    }
    catch (error) {
      console.error(`Failed to update review with id: ${reviewId}`, error);
    }
  }


  return {
    platforms,
    genres,
    ageRatings,
    hasGame,
    hasReviewed,
    newGameId,
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
  };
}


