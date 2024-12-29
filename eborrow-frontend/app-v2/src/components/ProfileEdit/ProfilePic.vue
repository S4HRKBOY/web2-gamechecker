<script setup>
import { ref, computed } from "vue";
import PTH_DEFAULT_PROFILE_PIC from "@/assets/images/profile_pic_default.svg";

const fileSelectInput = ref(null);
const loadedImage = ref(null);
const srcDisplayedImage = computed(() => loadedImage.value ? `data:image/jpeg;base64,${loadedImage.value}` : PTH_DEFAULT_PROFILE_PIC);

function onProfilePicChange(event) {
    const fileInput = event.target;
    const file = fileInput.files[0]; // Get the selected file

    if (!file) {
        fileInput.value = ""; // Clear the invalid file input
        loadedImage.value = null;

        return;
    }

    if (!validateProfilePic(file)) {
        return;
    }

    loadImage(event.target.files[0])
        .then((img) => loadedImage.value = img)
        .catch((err) => console.error(err));
}

function loadImage(file) {
    if (!file) {
        return Promise.reject("No file selected.");
    }

    // Check if the file is an image
    if (!file.type.startsWith('image/')) {
        return Promise.reject("Selected file is not an image.");
    }

    return new Promise((resolve, reject) => {
        const reader = new FileReader();

        // Set the image preview once the file is read
        reader.onload = (e) => {
            const dataUrl = e.target.result;    // Full url; data:image/jpeg;base64,.....
            const base64Data = dataUrl.split(",")[1]; // Only the base64 data of the image
            resolve(base64Data); // Resolve the promise when the file is read
        };

        reader.onerror = () => {
            reject("There was an error when reading the image file!");
        }

        reader.readAsDataURL(file); // Read the file as a data URL
    });
}

function validateInputs() {
    return validateProfilePic(fileSelectInput.value.files[0]);
}

function validateProfilePic(file) {
    if (file && !file.type.startsWith("image/")) {
        alert("Bitte wählen Sie eine gültige Bilddatei aus.");
        fileInput.value = ""; // Clear the invalid file input
        loadedImage.value = null;

        return false;
    }

    return true;
}

defineExpose({
    loadedImage,
    validateInputs
});
</script>

<template>
    <section class="set-profile-pic">
        <fieldset>
            <figure class="profile-pic">
                <img :src="srcDisplayedImage" alt="Profilbild">
            </figure>
            <div class="form-input">
                <label for="profile-pic-fileselect">Profilbild</label>
                <input 
                    type="file" 
                    ref="fileSelectInput"
                    @change="onProfilePicChange"
                    id="profile-pic-fileselect" 
                    name="profile-pic-fpath"
                    accept="image/*"
                    >
            </div>
        </fieldset>
    </section>
</template>

<style scoped>
.set-profile-pic {
    grid-area: set-profile-pic;
}

.profile-pic {
    justify-self: center;
}

.profile-pic img {
    width: 288px;
    height: 288px;
    object-fit: cover;
    object-position: 50%;
    border-radius: 50%;
}

.form-input {
    display: flex;
    justify-content: space-evenly;
}
</style>