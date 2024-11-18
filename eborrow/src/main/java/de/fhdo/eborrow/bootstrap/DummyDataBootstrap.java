package de.fhdo.eborrow.bootstrap;

import de.fhdo.eborrow.domain.AccountBuilder;
import de.fhdo.eborrow.repositories.AccountRepository;
import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import de.fhdo.eborrow.domain.AgeRating;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.Genre;
import de.fhdo.eborrow.domain.Platform;
import de.fhdo.eborrow.repositories.GameRepository;

@Component
public class DummyDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountRepository accountRepository;
    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public DummyDataBootstrap(AccountRepository accountRepository, GameRepository gameRepository, ReviewRepository reviewRepository) {
        this.gameRepository = gameRepository;
        this.accountRepository = accountRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    @Transactional
    private void initData() {
        Game game1 = new Game(1L, "Baldurs Gate 3",
                "Stelle deine Gruppe zusammen und kehre in die Vergessenen Reiche zurück. Erlebe eine Geschichte von Freundschaft und Verrat, von Opfer und Überleben – und die Verlockung absoluter Macht.",
                Arrays.asList(Platform.WINDOWS, Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.XBOX_ONE,
                        Platform.XBOX_SERIES),
                Arrays.asList(Genre.ROLE_PLAYING),
                LocalDate.of(2023, 8, 3), AgeRating.USK_18, "Larian Studios", "Larian Studios",
                readImage("BaldursGate3.jpg"));
        Game game2 = new Game(2L, "Call of Duty: Modern Warfare III",
                "Call of Duty ist eine weltweit bekannte Ego-Shooter-Spielreihe, die die Spieler in intensive und realistische Kriegsszenarien versetzt. Mit atemberaubender Grafik und fesselnden Storylines erleben Spieler historische und moderne Schlachten. Ob im Einzelspieler-Storymodus oder in actiongeladenen Multiplayer-Schlachten – Call of Duty bietet Adrenalin pur.",
                Arrays.asList(Platform.WINDOWS, Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.XBOX_ONE,
                        Platform.XBOX_SERIES),
                Arrays.asList(Genre.SHOOTER),
                LocalDate.of(2023, 11, 10), AgeRating.USK_18, "Sledgehammer Games, Treyarch, Infinity Ward",
                "Activision",
                readImage("callOfDuty3.jpg"));
        Game game3 = new Game(3L, "Elden Ring",
                "Set in the Lands Between, players control a customizable player character on a quest to repair the Elden Ring and become the new Elden Lord.",
                Arrays.asList(Platform.WINDOWS, Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.XBOX_ONE,
                        Platform.XBOX_SERIES),
                Arrays.asList(Genre.ROLE_PLAYING),
                LocalDate.of(2022, 2, 25), AgeRating.USK_16, "From Software", "Bandai Namco",
                readImage("EldenRing.jpg"));

        Game savedGame1 = gameRepository.save(game1);
        Game savedGame2 = gameRepository.save(game2);
        Game savedGame3 = gameRepository.save(game3);

        Review game1Review1 = new Review(1L, "Gut", "Voll gut.", 10, LocalDate.now());
        Review game1Review2 = new Review(2L, "Edel", "Echt edel", 8, LocalDate.now());
        Review game2Review1 = new Review(3L, "Voll gut", "10 von 10", 9, LocalDate.now());
        Review game2Review2 = new Review(4L, "Doof", "Kacke viel zu schwer.", 2, LocalDate.now());

        initAccounts(game1, game2, game3);

        game1Review1.setAccount(accountRepository.findById(1L).get());
        game1Review2.setAccount(accountRepository.findById(1L).get());
        game2Review1.setAccount(accountRepository.findById(1L).get());
        game2Review2.setAccount(accountRepository.findById(1L).get());

        game1Review1.setGame(savedGame1);
        game1Review2.setGame(savedGame1);
        game2Review1.setGame(savedGame2);
        game2Review2.setGame(savedGame2);

        reviewRepository.save(game1Review1);
        reviewRepository.save(game1Review2);
        reviewRepository.save(game2Review1);
        reviewRepository.save(game2Review2);

    }

    private void initAccounts(Game... game) {
        long id = 1L;
		List<Game> acc1GameList = new LinkedList<>();
        acc1GameList.add(game[0]);
        acc1GameList.add(game[2]);
        List<Game> acc2GameList = new LinkedList<>();
        acc2GameList.add(game[1]);
        acc2GameList.add(game[0]);

        var acc1 = new AccountBuilder()
                .setId(id++)
                .setPrename("Max")
                .setSurname("Mustermann")
                .setUsername("mamus")
                .setBirthday(LocalDate.of(2000, 1, 1))
                .setEmail("max.mustermann@dummy.com")
                .setPassword("maxpassword")
                .setProfilePicture(readImage("where_image.png"))
                .setTaggedGames(acc1GameList)
                .build();

        var acc2 = new AccountBuilder()
                .setId(id++)
                .setPrename("John")
                .setSurname("Doe")
                .setUsername("jodoe")
                .setBirthday(LocalDate.of(2023, 12, 31))
                .setEmail("john.doe@dummy.com")
                .setPassword("johnpassword")
                .setProfilePicture(readImage("where_image.png"))
                .setTaggedGames(acc2GameList)
                .build();

        var publisher = new AccountBuilder()
                .setId(id++)
                .setPrename("Publisher")
                .setSurname("Publisher")
                .setUsername("publisher")
                .setBirthday(LocalDate.of(2000, 1, 1))
                .setEmail("publisher@dummy.com")
                .setPassword("publisher")
                .setProfilePicture(readImage("where_image.png"))
                .setPublisher(true)
                .build();

        accountRepository.save(acc1);
        accountRepository.save(acc2); 
        accountRepository.save(publisher);
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
