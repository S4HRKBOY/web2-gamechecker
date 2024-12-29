import { ref, reactive } from "vue";
import axios from 'axios';
import { Game } from '../domain/game.js'

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 8000,
  headers: {
    Accept: 'application/json',
  },
});

export default function useGameApi() {
  const game = reactive(Game());
  const platforms = ref([]);
  const genres = ref([]);
  const ageRatings = ref([]);
  const hasGame = ref(false);
  const hasReviewed = ref(false);
  const newGameId = ref(null);

  //eiter use await or then, not both
  const getGameById = async (id) => {
    try {
      const response = await axiosInstance.get(`/game/${id}`)
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

  const updateGame = async ({id, gameData}) => {
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

  const accountHasGame = async () => {
    try {
      const response = await axiosInstance.get(`/account/account-has-game`);
      hasGame.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  const accountHasReviewed = async () => {
    try {
      const response = await axiosInstance.get(`/review/exists-by-account-and-game`);
      hasReviewed.value = response.data;
    } catch (error) {
      console.log(error);
    };
  };

  return {
    game,
    platforms,
    genres,
    ageRatings,
    hasGame,
    hasReviewed,
    newGameId,
    getGameById,
    deleteGameById,
    createGame,
    updateGame,
    getAllPlatforms,
    getAllGenres,
    getAllAgeRatings,
    accountHasGame,
    accountHasReviewed,
  };
}

