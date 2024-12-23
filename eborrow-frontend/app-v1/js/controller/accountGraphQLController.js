'use strict'

import Account from "../entities/Account.js";

// The same functions as in accountRestController.js but with GraphQL instead of REST
const ACCOUNT_SELECTION_SET = [
    "id",
    "prename",
    "surname",
    "username",
    "birthday",
    "email",
    "password",
    "profilePicture",
    "publisher"
];

export async function getAccountById(id, fields = []) {
    if (fields.length === 0) {
        fields = ACCOUNT_SELECTION_SET;
    }

    const query = `
        {
            account(id: ${id}) {
                ${fields.join("\n")}
            }
        }`;

    try {
        const response = await fetchGraphQL(query);
        const json = await response.json();
        if (json.errors) {
            const errorMessages = bundleErrorMessages(json)
            throw new Error(errorMessages);
        }


        return new Account(json.data.account);
    } catch (error) {
        console.error(`Failed to load account data with id ${id}:`, error);
        throw error;
    }
}

export async function updateAccount(account, fields = []) {
    if (fields.length === 0) {
        fields = ACCOUNT_SELECTION_SET;
    }

    const mutation = `
        mutation {
            updateAccount(account: {
                id: ${account.id}
                prename: "${account.prename}"
                surname: "${account.surname}"
                birthday: ${account.birthday ? `"${account.birthday}"` : null}
                username: "${account.username}"
                email: "${account.email}"
                password: "${account.password}"
                profilePicture: ${account.profilePicture ? `"${account.profilePicture}"` : null}
            }) {
                ${fields.join("\n")}
            }
        }`;

    try {
        const response = await fetchGraphQL(mutation);
        const json = await response.json();
        if (json.errors) {
            const errorMessages = bundleErrorMessages(json)
            throw new Error(errorMessages);
        }

        return new Account(json.data.updateAccount);
    } catch (error) {
        console.error(`Failed to update account with id ${account.id}:`, error);
        throw error;
    }

}

export async function isEmailTaken(email) {
    const query = `
        {
            emailTaken(email: "${email}")
        }`;

    try {
        const response = await fetchGraphQL(query);
        const json = await response.json();
        if (json.errors) {
            const errorMessages = bundleErrorMessages(json);
            throw new Error(errorMessages);
        }

        return json.data.emailTaken;
    } catch (error) {
        console.error(`Failed to check if email is already taken`, error);
        throw error;
    }
}

export async function isUsernameTaken(username) {
    const query = `
        {
            usernameTaken(username: "${username}")
        }`;

    try {
        const response = await fetchGraphQL(query);
        const json = await response.json();
        if (json.errors) {
            const errorMessages = bundleErrorMessages(json);
            throw new Error(errorMessages);
        }

        return json.data.usernameTaken;
    } catch (error) {
        console.error(`Failed to check if username is already taken`, error);
        throw error;
    }
}

async function fetchGraphQL(query) {
    return await fetch("//localhost:8080/graphql", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify({ query })
    });
}

function bundleErrorMessages(json) {
    const errorMessages = json.errors.reduce((acc, error, index) => {
        acc[`error ${index + 1}`] = error.message;
        return acc;
    }, {});
    return JSON.stringify(errorMessages, null, 2);
}