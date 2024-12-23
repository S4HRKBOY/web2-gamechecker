'use strict'

import Account from "../entities/Account.js";

export async function getAccountById(id) {
    try {
        const response = await fetch(`//localhost:8080/account/${id}`);
        const json = await response.json();

        return new Account(json);
    } catch (err) {
        console.error(`Failed to load account data with id ${id}:`, err);
        throw err;
    }
}

export async function updateAccount(account) {
    try {
        const response = await fetch(`//localhost:8080/account/edit/${account.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(account)
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            throw new Error(errorResponse.message);
        }
    } catch (err) {
        console.error(`Failed to update account with id ${account.id}:`, err);
        throw err;
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
        throw err;
    }
}