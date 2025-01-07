"use strict";

// The default profile picture
export const PATH_DEFAULT_PROFILE_PIC = "../resources/images/profile_pic_default.svg";

// The logged in account, currently hardcoded, will change in app-v2
let idAccountToFetch = 3;

export function getActiveAccountId() {
    return idAccountToFetch;
}

export function setActiveAccountId(id) {
    idAccountToFetch = id;
}
