export function getAccountById(id) {
    return fetch(`//localhost:8080/account/${id}`)
        .then(response => response.json())
        .catch((err) => console.error(`Failed to load account data with id ${id}:`, err));
}