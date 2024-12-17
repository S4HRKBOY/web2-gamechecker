'use strict'

export async function getAccountById(id) {
    try {
        const response = await fetch(`//localhost:8080/account/${id}`);

        return await response.json();
    } catch (err) {
        console.error(`Failed to load account data with id ${id}:`, err);
    }
}

export async function isEmailTaken(email) {
    try {
        const response = await fetch(`//localhost:8080/account/email-taken?email=${email}`);

        return await response.json();
    } catch (err) {
        console.error(`Failed to check if email is already taken`, err);
    }
}

export async function isUsernameTaken(username) {
    try {
        const response = await fetch(`//localhost:8080/account/username-taken?username=${username}`);

        return await response.json();
    } catch (err) {
        console.error(`Failed to check if username is already taken`, err);
    }
}