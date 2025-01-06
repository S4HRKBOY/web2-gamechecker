<script setup>
import { inject, ref } from 'vue';

const inputVals = inject('inputVals');

const inputRefs = {
    password: ref(null),
    passwordConfirm: ref(null)
};

defineExpose({validatePasswords});

function validatePasswords() {
    const passwordInput = inputRefs.password.value;
    const passwordConfirmInput = inputRefs.passwordConfirm.value;

    if (passwordInput.value !== passwordConfirmInput.value) {
        passwordConfirmInput.setCustomValidity("Die eingegebenen Passwörter stimmen nicht überein.");
        passwordConfirmInput.reportValidity();

        return false;
    }

    return true;
}

function resetValidity() {
    inputRefs.password.value.setCustomValidity('');
    inputRefs.passwordConfirm.value.setCustomValidity('');
}
</script>

<template>
    <div class="form-input">
        <label for="password">Passwort</label>
        <input
            type="password"
            id="password" 
            name="password" 
            required
            v-model="inputVals.password"
            :ref="inputRefs.password"
            @input="resetValidity"
            >
    </div>
    <div class="form-input">
        <label for="password-confirm">Passwort wiederholen</label>
        <input 
            type="password" 
            id="password-confirm" 
            required
            v-model="inputVals.passwordConfirm"
            :ref="inputRefs.passwordConfirm"
            @input="resetValidity" 
            >
    </div>
</template>

<style scoped>
@import 'src/assets/css/inputs/inputs.css';
</style>