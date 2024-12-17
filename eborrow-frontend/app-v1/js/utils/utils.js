export function loadImage(file) {
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
            resolve(e.target.result); // Resolve the promise when the file is read
        };

        reader.onerror = () => {
            reject("There was an error when reading the image file!");
        }

        reader.readAsDataURL(file); // Read the file as a data URL
    });
}