export default class Account {
    constructor({ id, prename, surname, username, birthday, email, password, profilePicture, publisher }) {
        this.id = id;
        this.prename = prename;
        this.surname = surname;
        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
        this.publisher = publisher;
    }
}