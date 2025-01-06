'use strict';

export async function fetchAccountId(username, password) {
    try {
        const response = await fetch(`//localhost:8080/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({ username, password })
        });

        if (response.status === 404) {
            return null;
        }

        return await response.json();
    } catch (err) {
        console.error(`Failed to fetch account id with given username ${username}`, err);
        throw err;
    }
}

export async function getAccountById(id, withGames = false) {
    const queryParam = withGames ? "?with-games" : "";
    try {
        const response = await fetch(`//localhost:8080/account/${id}${queryParam}`);
        const json = await response.json();

        return json;
    } catch (err) {
        console.error(`Failed to load account data with id ${id}:`, err);
        throw err;
    }
}

export async function createAccount(account) {
    try {
        const response = await fetch(`//localhost:8080/account/create-account`, {
            method: "POST",
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
        console.error(`Failed to create account:`, err);
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

export async function deleteAccount(id) {
    try {
        const response = await fetch(`//localhost:8080/account/delete-account/${id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            throw new Error(errorResponse.message);
        }
    } catch (err) {
        console.error(`Failed to delete account with id ${id}:`, err);
        throw err;
    }
}

export async function unlistGameFromAccount(accountId, gameId) {
    try {
        const response = await fetch(`//localhost:8080/account/unlist-game`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                "account-Id": accountId,
                "game-Id": gameId
            })
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            throw new Error(errorResponse.message);
        }
    } catch (err) {
        console.error(`Failed to unlist game with id ${gameId} from account:`, err);
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

export async function isEmailUsedByOtherAccount(id, email) {
    try {
        const response = await fetch(`//localhost:8080/account/email-taken/${id}?email=${email}`);

        return await response.json();
    } catch (err) {
        console.error(`Failed to check if email is already used by a different account`, err);
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

export async function isUsernameUsedByOtherAccount(id, username) {
    try {
        const response = await fetch(`//localhost:8080/account/username-taken/${id}?username=${username}`);

        return await response.json();
    } catch (err) {
        console.error(`Failed to check if username is already used by a different account`, err);
        throw err;
    }
}