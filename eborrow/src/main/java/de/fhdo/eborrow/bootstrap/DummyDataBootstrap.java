package de.fhdo.eborrow.bootstrap;

import de.fhdo.eborrow.domain.*;
import de.fhdo.eborrow.domain.builder.AccountBuilder;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.io.*;
import java.util.*;

import de.fhdo.eborrow.repositories.GameRepository;

@Component
public class DummyDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountRepository accountRepository;
    private final GameRepository gameRepository;

    @Autowired
    public DummyDataBootstrap(AccountRepository accountRepository, GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    @Transactional
    public void initData() {
        Game game1 = new Game(1L, "Baldur's Gate 3",
                "Stelle deine Gruppe zusammen und kehre in die Vergessenen Reiche zurück. Erlebe eine Geschichte von Freundschaft und Verrat, von Opfer und Überleben – und die Verlockung absoluter Macht.",
                Arrays.asList(Platform.MAC_OS, Platform.PLAYSTATION_5, Platform.WINDOWS, Platform.XBOX_ONE,
                        Platform.XBOX_SERIES),
                Arrays.asList(Genre.ROLE_PLAYING, Genre.STRATEGY, Genre.ADVENTURE),
                LocalDate.of(2023, 8, 3), AgeRating.USK_18, "Larian Studios", "Larian Studios",
                readImage("BaldursGate3.jpg"));
        Game game2 = new Game(2L, "Call of Duty: Modern Warfare III",
                "Call of Duty ist eine weltweit bekannte Ego-Shooter-Spielreihe, die die Spieler in intensive und realistische Kriegsszenarien versetzt. Mit atemberaubender Grafik und fesselnden Storylines erleben Spieler historische und moderne Schlachten. Ob im Einzelspieler-Storymodus oder in actiongeladenen Multiplayer-Schlachten – Call of Duty bietet Adrenalin pur.",
                Arrays.asList(Platform.MAC_OS, Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.WINDOWS,
                        Platform.XBOX_ONE,
                        Platform.XBOX_SERIES),
                Arrays.asList(Genre.ACTION, Genre.SHOOTER),
                LocalDate.of(2023, 11, 10), AgeRating.USK_18, "Sledgehammer Games, Treyarch, Infinity Ward",
                "Activision",
                readImage("callOfDuty3.jpg"));
        Game game3 = new Game(3L, "Elden Ring",
                "Das Zwischenland ist Teil eines riesigen Kontinents, auf dem offene Weiten und riesige Dungeons mit komplexer dreidimensionaler Gestaltung nahtlos ineinander übergehen. Erlebt den Reiz, beim Erkunden auf unbekannte und überwältigende Bedrohungen zu stoßen.\r\n"
                        + //
                        "Meistert das Gelände und deckt seine Geheimnisse auf, um euren Feinden gewachsen zu sein, mächtige Endbosse zu bezwingen oder eindringende Spieler in die Falle zu locken.",
                Arrays.asList(Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.WINDOWS, Platform.XBOX_ONE,
                        Platform.XBOX_SERIES),
                Arrays.asList(Genre.ACTION, Genre.ROLE_PLAYING),
                LocalDate.of(2022, 2, 25), AgeRating.USK_16, "FromSoftware", "Bandai Namco",
                readImage("EldenRing.jpg"));
        Game game4 = new Game(4L, "Sekiro: Shadows Die Twice",
                "Erlebe das Sengoku-Japan des späten 16. Jahrhunderts, eine Zeit voller brutaler Konflikte und Kämpfe auf Leben und Tod.\r\n"
                        + //
                        "In dieser düsteren Welt musst du dich mit einem Arsenal an tödlichen prothetischen Werkzeugen sowie mächtigen Ninja-Fähigkeiten überlebensgroßen Feinden stellen. Ninja-Heimlichkeit, vertikale Fortbewegung und gnadenlose Kopf-an-Kopf-Kämpfe verschmelzen hier zu einer blutigen Konfrontation. Übe Rache. Stell deine Ehre wieder her. Töte mit Verstand.",
                Arrays.asList(Platform.PLAYSTATION_4, Platform.WINDOWS, Platform.XBOX_ONE),
                Arrays.asList(Genre.ACTION, Genre.ADVENTURE),
                LocalDate.of(2020, 10, 28), AgeRating.USK_18, "FromSoftware", "Activision",
                readImage("sekiro.jpg"));
        Game game5 = new Game(5L, "Super Mario Odyssey",
                "Schauplatz der Handlung ist in diesem Teil der Reihe ein an die Erde angelehnter Planet sowie der Mond des Planeten. Mario verlässt das Pilz-Königreich und begibt sich mit einem Luftschiff, der Odyssee, auf eine Reise zu mysteriösen neuen Orten und erlebt dort Abenteuer. Das Hauptziel der Handlung besteht darin, Bowser daran zu hindern, Prinzessin Peach zu heiraten, die von Bowser entführt worden ist.",
                Arrays.asList(Platform.NINTENDO_SWITCH),
                Arrays.asList(Genre.ACTION, Genre.ADVENTURE, Genre.PLATFORMER),
                LocalDate.of(2017, 10, 27), AgeRating.USK_6, "Nintendo EPD", "Nintendo",
                readImage("SuperMarioOdyssey.jpg"));
        Game game6 = new Game(6L, "Baldur's Gate II: Schatten von Amn",
                "Entführt. Eingesperrt. Gefoltert. Der Magier Irenicus hält Euch in seiner Festung gefangen und versucht, Euch die Kräfte, die Euer Geburtsrecht sind, zu rauben.\r\n"
                        + //
                        "\r\n" + //
                        "Könnt Ihr dem Bösen in Euch widerstehen, eine Legende heroischen Ausmaßes schaffen und endgültig die dunkle Essenz, die Eure Träume heimsucht, zerstören? Oder werdet Ihr Eure monströse Natur akzeptieren, eine Schneise der Verwüstung durch die Reiche ziehen und als neuer Fürst des Mordes zur Gottheit aufsteigen?",
                Arrays.asList(Platform.WINDOWS, Platform.MAC_OS),
                Arrays.asList(Genre.ROLE_PLAYING, Genre.ADVENTURE),
                LocalDate.of(2000, 9, 24), AgeRating.USK_12, "BioWare", "Interplay",
                readImage("bg2.jpg"));
        Game game7 = new Game(7L, "Baldur's Gate",
                "m Mittelpunkt steht eine epische Geschichte rund um den Hauptcharakter, welche den Helden und seine Begleiter durch viele verschiedene Orte Faerûns führt (entlang der Schwertküste, Amn und Tethyr) und alle vier erschienenen Titel miteinander verbindet. Die Geschichte wird in mehreren Kapiteln erzählt, die durch bestimmte Schlüsselereignisse (Erreichen einer bestimmten Position, Bekämpfen eines bestimmten Gegners, Finden eines bestimmten Gegenstandes) vorangetrieben werden. ",
                Arrays.asList(Platform.WINDOWS, Platform.MAC_OS),
                Arrays.asList(Genre.ROLE_PLAYING, Genre.ADVENTURE),
                LocalDate.of(1998, 12, 21), AgeRating.USK_12, "BioWare", "Interplay Entertainment",
                readImage("bg1.jpg"));

        Review game1Review1 = new Review(null, "Bahnbrechend",
                "Baldur's Gate 3 ist ein Meisterwerk! Die tiefgründige Story, fesselnde Charaktere und taktischen Kämpfe machen es zu einem Must-Play für RPG-Fans. Die Entscheidungen haben echte Konsequenzen, und die Grafik ist atemberaubend. Larian Studios hat einen neuen Maßstab für das Genre gesetzt!",
                10, LocalDate.of(2024, 12, 28));
        Review game1Review2 = new Review(null, "Es hätte so viel mehr sein können!",
                "Die ersten beiden Akte von Baldur's Gate 3 begeistern mit spannender Story und starken Charakteren, sind jedoch nicht frei von Bugs, die den Spielfluss stören. Im dritten Akt wird der Entwicklungsdruck deutlich: unfertige Inhalte, abrupte Übergänge und weiterhin ungelöste Fehler trüben das Erlebnis.",
                5, LocalDate.of(2025, 1, 1));
        Review game2Review1 = new Review(null, "Mega Game",
                "Call of Duty: Modern Warfare 3 bietet eine spannende Kampagne mit packender Story und intensiven Action-Momenten. Der Mehrspieler-Modus überzeugt mit gutem Waffenbalancing, neuen Maps und abwechslungsreichen Spielmodi. Ein großartiger Shooter, der die Reihe gelungen fortsetzt.",
                9, LocalDate.of(2025, 1, 3));
        Review game2Review2 = new Review(null, "Könnte besser sein",
                "Call of Duty: Modern Warfare 3 fühlt sich in der Kampagne oft wie eine Wiederholung der Vorgänger an, ohne neue frische Ideen. Der Mehrspieler-Modus hat noch immer Balance-Probleme, und die Karten wirken uninspiriert. Insgesamt solide, aber nicht bahnbrechend.",
                2, LocalDate.of(2024, 12, 15));

        initAccounts();

        game1Review1.setAccount(accountRepository.findById(1L).get());
        game1Review2.setAccount(accountRepository.findById(2L).get());
        game2Review1.setAccount(accountRepository.findById(1L).get());
        game2Review2.setAccount(accountRepository.findById(2L).get());

        game1.getReviews().add(game1Review1);
        game1.getReviews().add(game1Review2);
        game2.getReviews().add(game2Review1);
        game2.getReviews().add(game2Review2);

        Game savedGame1 = gameRepository.save(game1);
        Game savedGame2 = gameRepository.save(game2);
        Game savedGame3 = gameRepository.save(game3);
        gameRepository.save(game4);
        gameRepository.save(game5);
        gameRepository.save(game6);
        gameRepository.save(game7);

        assignTaggedGames(savedGame1, savedGame3, savedGame2);
    }

    private void initAccounts() {
        long id = 1L;

        Account acc1 = new AccountBuilder()
                .setId(id++)
                .setPrename("Max")
                .setSurname("Mustermann")
                .setUsername("mamus")
                .setBirthday(LocalDate.of(2000, 1, 1))
                .setEmail("max.mustermann@dummy.com")
                .setPassword("maxpassword")
                .build();

        Account acc2 = new AccountBuilder()
                .setId(id++)
                .setPrename("John")
                .setSurname("Doerian")
                .setUsername("jodoe")
                .setBirthday(LocalDate.of(2023, 12, 31))
                .setEmail("john.doe@dummy.com")
                .setPassword("johnpassword")
                .setProfilePicture(readImage("where_image.png"))
                .build();

        Account publisher = new AccountBuilder()
                .setId(id++)
                .setPrename("Publisher")
                .setSurname("Publisher")
                .setUsername("publisher")
                .setEmail("publisher@dummy.com")
                .setPassword("publisher")
                .setProfilePicture(readImage("no_games.png"))
                .setPublisher(true)
                .build();

        accountRepository.save(acc1);
        accountRepository.save(acc2);
        accountRepository.save(publisher);
    }

    private void assignTaggedGames(Game... savedGames) {
        Set<Game> acc1GameList = new HashSet<>();
        acc1GameList.add(savedGames[2]);
        Set<Game> acc2GameList = new HashSet<>();
        acc2GameList.add(savedGames[0]);
        acc2GameList.add(savedGames[1]);
        acc2GameList.add(savedGames[2]);

        Account acc1 = accountRepository.findById(1L).get();
        Account acc2 = accountRepository.findById(2L).get();

        acc1.setTaggedGames(acc1GameList);
        acc2.setTaggedGames(acc2GameList);

        accountRepository.save(acc1);
        accountRepository.save(acc2);
    }

    /*
     * Helper class to save dummy image data in db
     */
    private byte[] readImage(String img) {
        byte[] image = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (img != null) {
            String file = Objects.requireNonNull(classLoader.getResource("dummyImages/" + img)).getFile();

            try (InputStream fis = new FileInputStream(file)) {
                image = fis.readAllBytes();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

}
