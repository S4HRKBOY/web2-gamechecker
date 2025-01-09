<script setup>
    import * as gamesRestApi from "@/composables/useGamesRestApi.js"
    import { onMounted, ref, defineEmits, reactive } from "vue";

    const genres = ref([]);
    const platforms = ref([]);

    const emit = defineEmits(['apply-filter']);

    const filterInfo = reactive({
        genre: "All",
        platform: "All",
        developer: ""
    });

    function applyFilter(){
        emit('apply-filter', filterInfo);
        filterInfo.genre = "All";
        filterInfo.platform = "All";
        filterInfo.developer = "";
    }

    onMounted(async () => {
        genres.value = await gamesRestApi.getAvailableGenres();
        platforms.value = await gamesRestApi.getAvailablePlatforms();
    });

</script>

<template>
    <div class="sort">
        <div class="filter-bar">
            <label for="genres">Genres: </label>
            <select v-model="filterInfo.genre" name="genres" id="genres" class="genre-filter">
                <option value="All">All</option>
                <option v-for="genre in genres" v-bind:key="genre" v-bind:value="genre">{{genre}}</option>
            </select>
            <label for="platform">Plattform: </label>
            <select v-model="filterInfo.platform" name="platform" id="platform" class="platform-filter">
                <option value="All">All</option>
                <option v-for="platform in platforms" v-bind:key="platform" v-bind:value="platform">{{platform}}</option>
            </select>
            <label for="dev">Entwickler: </label>
            <input v-model="filterInfo.developer" type="text" id="dev" name="developer" class="dev-input">
            <button @click="applyFilter" type="submit" class="apply-filter">
                <i class="fa fa-filter"></i>
            </button>
        </div>
    </div>
</template>

<style scoped>
.sort{
    grid-area: sort;
    width: 60%;
}

.filter-bar{
    width: 100%;
    height: 100px;
    border: 2px solid #2b2929;
    border-radius: 5px;
    background-color: aliceblue;
    display: grid;
    grid-template-areas:"genre-label  platform-label  dev-label  apply-filter"
                        "genre-filter platform-filter dev-filter apply-filter";
    grid-template-columns: 25% 25% 25% auto;
    grid-template-rows: 50% auto;
    align-items: center;
    justify-content: space-evenly;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
}

.filter-bar label[for="genres"]{
    grid-area: genre-label;
}

.filter-bar label[for="platform"]{
    grid-area: platform-label;
}

.filter-bar label[for="dev"]{
    grid-area: dev-label;
}

.genre-filter{
    grid-area: genre-filter;
}

.platform-filter{
    grid-area: platform-filter;
}

.dev-filter{
    grid-area: dev-filter;
}

.apply-filter{
    grid-area: apply-filter;
    justify-self: stretch;
    border: 1px solid #2b2929;
    background: #2b2929;
    border-radius: 15px;
    cursor: pointer;
    color: #fff;
}

.apply-filter:hover{
    grid-area: apply-filter;
    border: 1px solid #2b2929;
    background: #fff;
    border-radius: 15px;
    cursor: pointer;
    color: #2b2929;
}
</style>
