<script setup>
import { ref, onMounted, onBeforeUnmount  } from 'vue';

defineProps({
  buttonText: { type: String },
  items: { type: Array, required: true },
  name: { type: String },
});

const isDropdownOpen = ref(false);
const dropdownRef = ref(null);
const selectedItems = ref([]);


const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

const closeDropdown = (e) => {
  if (dropdownRef.value && !dropdownRef.value.contains(e.target)) {
    isDropdownOpen.value = false;
  }
};

onMounted(() => {
  document.addEventListener('click', closeDropdown);
});

onBeforeUnmount(() => {
  document.removeEventListener('click', closeDropdown);
});

</script>


<template>
  <div class="dropdown" :id="`${name}-grid`" ref="dropdownRef">
    <button :id="`${name}`" @click.prevent="toggleDropdown">{{ buttonText }}</button>
    <div v-if="isDropdownOpen" class="dropdown-content" :id="`${name}-dropdown`">
      <div v-for="item in items" :key="item">
        <label><input type="checkbox" :name="name" :value="item" v-model="selectedItems">
          {{ item }}</label>
      </div>
    </div>
  </div>
</template>

<style>
.dropdown {
  position: relative;
  display: block;
}


.dropdown-content {
  overflow-y: scroll;
  width: 100%;
  height: 200px;
  padding: 10px;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
}

.dropdown-content label {
  display: block;
  margin-top: 10px;
}

button {
  width: 100%;
}
</style>
