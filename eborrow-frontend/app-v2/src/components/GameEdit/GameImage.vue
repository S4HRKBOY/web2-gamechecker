<script setup>
const {modelValue} = defineProps({
  modelValue: { type: String },
});
const emit = defineEmits(['update:modelValue']);

const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = () => {
      emit('update:modelValue', reader.result.split(',')[1]);
    };
    reader.readAsDataURL(file);
  }
};
</script>

<template>
  <input id="file" type="file" name="file" accept="image/*" @change="handleImageUpload">
  <fieldset id="imagearea" >
    <legend>Vorschaubild</legend>
    <img v-if="modelValue" id="gameImage" :src="`data:image/jpg;base64,${modelValue}`" alt="Game Image">
  </fieldset>
</template>

<style scoped>
#imagearea {
  grid-area: preview-image;
  border: 1px solid hsl(0, 0%, 75%);
  border-radius: 10px;
  padding: 10px;
  height: 300px;
  text-align: center;
}

#gameImage {
  max-width: 100%;
  max-height: 100%;
}
</style>
