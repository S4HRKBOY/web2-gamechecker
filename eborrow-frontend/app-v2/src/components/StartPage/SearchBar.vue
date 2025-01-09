<script setup>
    import { defineEmits, reactive } from "vue";

    const emit = defineEmits(['apply-search']);
    const query = reactive({searchQuery:""});

    function applySearch(){
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
        width: 60%;
        display: flex;
    }

    .searchBox{
        border: 2px solid #2b2929;
        border-right: none;
        padding: 5px;
        width: 100%;
        border-radius: 5px 0 0 5px;
        outline: none;
        color: #2b2929;
        box-shadow: rgba(0, 0, 0, 0.16) 0px 3px 6px, rgba(0, 0, 0, 0.23) 0px 3px 6px;
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
