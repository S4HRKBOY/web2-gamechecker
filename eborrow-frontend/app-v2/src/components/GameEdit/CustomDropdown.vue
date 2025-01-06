<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';

const props = defineProps({
  buttonText: { type: String },
  items: { type: Array, required: true },
  modelValue: { type: Array },
});

const isDropdownOpen = ref(false);
const dropdownRef = ref(null);
const emit = defineEmits(['update:modelValue']);

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

const handleSelectionChange = (event, item) => {

  let newSelection = [...props.modelValue];
  if (event.target.checked) {
    newSelection.push(item);
  } else {
    newSelection = newSelection.filter(i => i !== item);
  }
  emit('update:modelValue', newSelection);
};

</script>


<template>
  <div class="dropdown" ref="dropdownRef">
    <input type="button" id="dropdown-button" @click.prevent="toggleDropdown" :value="buttonText">
    <div v-show="isDropdownOpen" class="dropdown-content">
      <div v-for="item in items" :key="item">
        <label>
          <input type="checkbox" :value="item" :checked="modelValue.includes(item)" @change="handleSelectionChange($event, item)">
          {{ item }}
        </label>
      </div>
    </div>
  </div>
</template>

<style scoped>
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

#dropdown-button{
  width: 100%;
}
</style>
