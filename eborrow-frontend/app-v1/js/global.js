import * as accountController from "./controller/accountRestController.js";
import { renderHeader } from "./pages/partials/header.js";

// The default profile picture
export const SRC_DEFAULT_PROFILE_PIC = "../resources/images/profile_pic_default.svg";

// The logged in account, currently hardcoded, will change in app-v2
export const ID_ACCOUNT_TO_FETCH = 1;

accountController.getAccountById(ID_ACCOUNT_TO_FETCH)
    .then(renderHeader);