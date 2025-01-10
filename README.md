# web2-wise2425-web4

Dieses Dokument soll einen kurzen Einstieg in die verschiedenen Teilprojekte geben.

Ein kurzer Hinweis vorweg: Aufgrund von Änderungen in der Projektstruktur gab es eine Namensänderung des Projekts von E-Borrow auf GameTracker und in GitHub heißt es GameChecker.

Das Projektwiki mit Projektsteckbrief befindet sich hier https://github.com/S4HRKBOY/web2-gamechecker/wiki/Dokumentation

## Projektstruktur

Das gesamte Praktikum für das Modul befindet sich auf dem main Branch. Es ist wie folgt aufgebaut: 

```
├── eborrow               # Spring Boot Backend, Server Rendering, Thymeleaf-Templates, REST-API, GraphQL-API(Praktikum 3- 6)
│   
└── eborrow-app           # Klick-Prototyp (Praktikum 1)
│   
└── eborrow-frontend     
    │
    └── app-v1            # CSR (Praktikum 7)
    │
    └── app-v2            # vue.js Frontend (Praktikum 8)

```

## Einstiegsdokument

Dieser Teil der Dokumentation soll Ausschluss darüber geben, wie die Anwendung gebaut und gestartet geben kann. Des Weiteren
soll dargelegt werden, welcher Teil (Die verschiedenen Teilprojekte im Praktikum) der Anwendung welche Funktionalitäten abdecken.

### Bauen der Anwendung 

Das Backend ist eine Java Spring Anwendung, welche als Build-Tool Maven verwendet. Von daher kann das Backend mit einem einfachen Befehl gebaut werden.

```
mvn clean package
```

Alternativ kann die Anwendung auch über die IDE gestartet werden z.B. durch eine entsprechende Konfiguration in InteliJ.
Das Frontend (frontend/app-v2) ist eine Vue-Anwendung, welche Vite als Build-Tool verwendet.
Bauen der Anwendung:

```
npm install
```

### Starten der verschiedenen Anwendungen

Das Backend kann durch eine IDE-Konfiguration gestartet werden. Alternativ kann nach dem Bauen auch die Java Anwendung gestartet werden.

```
java -jar .\eborrow-0.0.1-SNAPSHOT.jar
```

Die erste Version des Frontends kann durch einen Live-Server von z.B. VS-Code gestartet werden.

Die zweite Version des Frontends kann mit den entsprechenden NPM-Befehlen gebaut werden (Bauen der Entwicklungsumgebung und Bauen der Build-Artefakte):

```
npm run dev
```

```
npm run build
```

### Standard Ports (localhost)

* Spring Backend und Thymeleaf: 8080 (Anpassbar in den application.properties), localhost:8080/thymeleaf/home als Startseite
* Frontend-v1 Konfigurierter Live-Server Port (Standard 5500), eborrow-frontend\app-v1\html\start_page.html als Startseite
* Frontend-v2 Konfigurierter Port von vite (Standard 5173, kann geändert werden innerhalb von vite.config.js: ```server: {port: 5090}```), lädt automatisch die Login Page

### Bootstrap Zugänge

Da sich das Projekt hauptsächlich um eine Test-Anwendung handelt, werden entsprechende Benutzer bei dem Starten des Backends zu Testzwecken simuliert und durch ein Bootstrap initialisiert.

| Username | Passwort | 
| ---- | ---- | 
| mamus | maxpassword | 
| jodoe | johnpassword | 
| publisher | publisher | 

Der Account publisher besitzt Redakteur-Status 

### Funktionalitäten der verschiedenen Stände

* **Klick-Prototyp** besitzt grundlegende klickbare Seiten für die wichtigen Seiten der Anwendung (Detailseite eines Spiels, Startseite, Profil, Profil bearbeiten, Login + Registrierung, Formular um ein Spiel zu bearbeiten). Je Seite existieren auch entsprechende CSS-Styles.

* Die **Thymeleaf Anwendung** implementiert 3 ausgewählte Views: http://localhost:8080/thymeleaf/home, http://localhost:8080/thymeleaf/game/{id}, http://localhost:8080/thymeleaf/account/{id} (Einmal die Startseite, die Detailseite eines Spiels und das Profil)

    * Innerhalb des HTML sind nicht implementierte Features disabled
    * Innerhalb der Start und Detailseite wird ein eingeloggter Account für Testzwecke gemocked (der Account mit ID = 1), für das Profil kann eine beliebige ID verwendet werden

* Das **Frontend-v1** implementiert  3 ausgewählte Views: eborrow-frontend\app-v1\html\gameForm.html, eborrow-frontend\app-v1\html\profile_edit_page.html, eborrow-frontend\app-v1\html\start_page.html

    * Für Demonstrationszwecke linkt ein Spiel auf die Spiel barbeiten-Seite und der Profil-Button im Header auf die Profil bearbeiten-Seite
    * Das Löschen eines Accounts velinkt auf die Startseite, das Absenden eines Accounts öffnet die Profilübersicht aus Thymeleaf
    * Auch hier wird ein eingeloggter Redakteur-Account (ID = 3) "simuliert", um die Anwendung zu ermöglichen (innerhalb der gameForm)
    * → Für die Profilbearbeitung wird der Account mit ID = 2 verwendet, um nach der Löschung auf die Startseite zu verlinken

* Das **Frontend-v2** implementiert alle Views, welche auch innerhalb des Klickprototyp umgesetzt wurden und macht diese funktionstüchtig (mit Vue.js und implementierte Logik für das Routing und Anbindung an entsprechende Backend-Schnittstellen)

### Schnittstellen

Die REST-Schnittstellen können im Code gefunden werden (unter dem Package ``de/fhdo/eborrow/restapi``).
Die GraphQL-Schemata werden innerhalb der Spring Anwendung durch eine entsprechende Konfiguration bereitgestellt (``src/main/resources/graphql/schema.graphqls``).

### Tools

* In-Memory H2-Datenbank: ../h2-console
* GraphQL-Querybuilder: ../graphiql

### Einen Account zum Redakteur setzen

Der Redakteur-Status eines Accounts kann über REST oder GraphQL gesetzt werden mithilfe folgender Endpunkte:
* **REST** ```http://localhost:8080/admin/account/set-publisher-status/1?publisher={true oder false}```
* **GraphQL** mit entsprechender Query
    ```
    setPublisherStatus(id: "1", publisher: true oder false) {
        id
    }
    ```
