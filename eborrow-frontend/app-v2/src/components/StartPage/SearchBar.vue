<script setup>
    import { defineEmits, reactive } from "vue";

    const emit = defineEmits(['apply-search']);
    const query = reactive({searchQuery:""});

    function applySearch(){
        console.log(query.searchQuery);
        emit('apply-search', query.searchQuery);
        query.searchQuery = "";
    }

</script>

<template>
    <div class="search">
        <input v-model="query.searchQuery" type="text" class="searchBox" placeholder="Search for games!">
        <button @click="applySearch" type="submit" class="searchButton">
            <i class="fa fa-search"></i>
        </button>
    </div>
</template>

<style scoped>
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