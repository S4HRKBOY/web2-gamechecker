<script setup>
    import * as gamesRestApi from "@/composables/useGamesRestApi.js"
    import { onMounted } from "vue";

    onMounted(() => {
        const searchButton = document.querySelector(".searchButton");
        searchButton.addEventListener("click", gamesRestApi.getGamesBySearchQuery());
    });
</script>

<template>
    <div class="search">
        <input type="text" class="searchBox" placeholder="Search for games!">
        <button type="submit" class="searchButton">
            <i class="fa fa-search"></i>
        </button>
    </div>
</template>

<style>
    .search{
        grid-area: search;
        width: 66%;
        display: flex;
    }

    .searchBox{
        border: 3px solid #2b2929;
        border-right: none;
        padding: 5px;
        width: 100%;
        border-radius: 5px 0 0 5px;
        outline: none;
        color: #2b2929;
    }

    .searchButton{
        width: 40px;
        height: 36px;
        border: 1px solid #2b2929;
        background: #2b2929;
        text-align: center;
        color: #fff;
        border-radius: 0 5px 5px 0;
        cursor: pointer;
        font-size: 20px;
    }

    .searchButton:hover{
        width: 40px;
        height: 36px;
        border: 1px solid #2b2929;
        background: #fff;
        text-align: center;
        color: #2b2929;
        border-radius: 0 5px 5px 0;
        cursor: pointer;
        font-size: 20px;
    }
</style>