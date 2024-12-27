<script setup>
    import NavigationHeader from '../components/NavigationHeader.vue';
    import SearchBar from '../components/StartPage/SearchBar.vue';
    import SortPanel from '../components/StartPage/SortPanel.vue';
    import * as gamesRestApi from "../composables/useGamesRestApi.js"
    import { onMounted } from "vue";
    import { useRoute } from "vue-router";
    const route = useRoute();
    onMounted(() => {
        console.log("Id in route is: " + route.params.id);
        gamesRestApi.initGamesOverview();
    });
</script>

<template>
    <NavigationHeader/>
    <div class="main-page-container">
        <SearchBar />
        <SortPanel />
        <div class="overview">
            <table class="overview-table-container">
            </table>
        </div>
    </div>
</template>


<style scoped>
.main-page-container {
    display: grid;
    font-family: 'Open Sans', sans-serif;
    grid-template-areas: "search"
                         "sort"
                         "overview";
    grid-template-columns: auto;
    grid-template-rows: 100px 100px auto;
    align-items: center;
    justify-items: center;
}

.overview{
    grid-area: overview;
    width: 66%;
    margin-top: 1%;
}

.overview-table-container{
    width: 100%;
    height: 100%;
}

.overview-entry{
    width:  100%;
    height: 10%;
    margin: 5%;
    display: grid;
    grid-template-areas: "overview-image overview-description";
    grid-template-columns: 50% 50%;
    grid-template-rows: auto;
    align-items: center;

    justify-items: normal;
    justify-content: space-between;
    align-items: center;
}

.overview-image{
    grid-area: overview-image;
    max-height: 250px;
    display: block;
}

.overview-image img{
    max-width: 100%;
    max-height: 250px;
    display: block;
}

.overview-description{
    grid-area: overview-description;
    place-self: center;
    place-content: center;
    display: block;
}
</style>