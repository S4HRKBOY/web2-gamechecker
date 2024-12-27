'use strict'

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

export async function fetchAccountId(username, password) {
    const query = `
        {
            accountId(username: "${username}", password: "${password}")
        }`;

    try {
        const response = await fetchGraphQL(query);
        const json = await response.json();

        if (!json.errors) {
            return json.data.accountId;
        }

        if (json.errors.length === 1 && json.errors[0].extensions.classification === "NOT_FOUND") {
            return null;
        }

        const errorMessages = bundleErrorMessages(json);
        throw new Error(errorMessages);
    } catch (error) {
        console.error(`Failed to fetch account id with given username ${username}`, error);
        throw error;
    }
}

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

        return json.data.account;
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

export async function deleteAccount(id) {
    const mutation = `
        mutation {
            deleteAccount(id: ${id})
        }`;

    try {
        const response = await fetchGraphQL(mutation);
        const json = await response.json();
        if (json.errors) {
            const errorMessages = bundleErrorMessages(json)
            throw new Error(errorMessages);
        }

        const deletionSuccessful = json.data.deleteAccount;
        return deletionSuccessful;
    } catch (error) {
        console.error(`Failed to delete account with id ${id}:`, error);
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

export async function isEmailUsedByOtherAccount(id, email) {
    const query = `
        {
            emailUsedByOtherAccount(id: ${id}, email: "${email}")
        }`;

    try {
        const response = await fetchGraphQL(query);
        const json = await response.json();
        if (json.errors) {
            const errorMessages = bundleErrorMessages(json);
            throw new Error(errorMessages);
        }

        return json.data.emailUsedByOtherAccount;
    } catch (error) {
        console.error(`Failed to check if email is already used by a different account`, error);
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

export async function isUsernameUsedByOtherAccount(id, username) {
    const query = `
        {
            usernameUsedByOtherAccount(id: ${id}, username: "${username}")
        }`;

    try {
        const response = await fetchGraphQL(query);
        const json = await response.json();
        if (json.errors) {
            const errorMessages = bundleErrorMessages(json);
            throw new Error(errorMessages);
        }

        return json.data.usernameUsedByOtherAccount;
    } catch (error) {
        console.error(`Failed to check if username is already used by a different account`, error);
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