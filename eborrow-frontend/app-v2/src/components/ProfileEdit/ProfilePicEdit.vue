<script setup>
import { ref, computed } from "vue";
import PTH_DEFAULT_PROFILE_PIC from "@/assets/images/profile_pic_default.svg";

const fileSelectInput = ref(null);
const loadedImage = ref(null);
const srcDisplayedImage = computed(() => {
    return loadedImage.value ? `data:image/jpeg;base64,${loadedImage.value}` : PTH_DEFAULT_PROFILE_PIC;
});

function populateInputs(account) {
    if (account.profilePicture) {
        loadedImage.value = account.profilePicture;
    }
}

function onProfilePicChange(event) {
    const fileInput = event.target;
    const file = fileInput.files[0]; // Get the selected file

    if (!file) {
        clearProfilePic(fileInput);

        return;
    }

    if (!validateProfilePic(file)) {
        alert("Bitte wählen Sie eine gültige Bilddatei aus.");

        return;
    }

    loadImage(event.target.files[0])
        .then((img) => loadedImage.value = img)
        .catch((err) => console.error(err));
}

function clearProfilePic() {
    fileSelectInput.value = ""; // Clear the invalid file input
    loadedImage.value = null;
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
        fileSelectInput.value.value = ""; // Clear the invalid file input
        loadedImage.value = null;

        return false;
    }

    return true;
}

defineExpose({
    loadedImage,
    populateInputs,
    validateInputs
});
</script>

<template>
    <div class="set-profile-pic">
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
                    @cancel="clearProfilePic"
                    id="profile-pic-fileselect" 
                    name="profile-pic-fpath"
                    accept="image/*">
            </div>
        </fieldset>
    </div>
</template>

<style scoped>
.set-profile-pic {
    flex: 1;
}

.profile-pic img {
    width: 16vw;
    height: 16vw;
    object-fit: cover;
    object-position: 50%;
    border-radius: 50%;
}

.form-input {
    display: flex;
    justify-content: space-evenly;
}
</style>