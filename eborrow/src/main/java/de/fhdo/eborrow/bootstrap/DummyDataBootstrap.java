package de.fhdo.eborrow.bootstrap;

import de.fhdo.eborrow.domain.Review;
import de.fhdo.eborrow.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

import de.fhdo.eborrow.domain.AgeRating;
import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.domain.Genre;
import de.fhdo.eborrow.domain.Platform;
import de.fhdo.eborrow.repositories.GameRepository;

@Component
public class DummyDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private GameRepository gameRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public DummyDataBootstrap(GameRepository gameRepository, ReviewRepository reviewRepository) {
        this.gameRepository = gameRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    private void initData() {
        Game game1 = new Game(1L, "Baldurs Gate 3",
                "Stelle deine Gruppe zusammen und kehre in die Vergessenen Reiche zurück. Erlebe eine Geschichte von Freundschaft und Verrat, von Opfer und Überleben – und die Verlockung absoluter Macht.",
                Arrays.asList(Platform.WINDOWS, Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.XBOX_ONE, Platform.XBOX_SERIES),
                Arrays.asList(Genre.ROLE_PLAYING),
                LocalDate.of(2023, 8, 3), AgeRating.USK_18, "Larian Studios", "Larian Studios", readImage("BaldursGate3.jpg"));
        Game game2 = new Game(2L, "Call of Duty: Modern Warfare III",
                "Call of Duty ist eine weltweit bekannte Ego-Shooter-Spielreihe, die die Spieler in intensive und realistische Kriegsszenarien versetzt. Mit atemberaubender Grafik und fesselnden Storylines erleben Spieler historische und moderne Schlachten. Ob im Einzelspieler-Storymodus oder in actiongeladenen Multiplayer-Schlachten – Call of Duty bietet Adrenalin pur.",
                Arrays.asList(Platform.WINDOWS, Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.XBOX_ONE, Platform.XBOX_SERIES),
                Arrays.asList(Genre.SHOOTER),
                LocalDate.of(2023, 11, 10), AgeRating.USK_18, "Sledgehammer Games, Treyarch, Infinity Ward", "Activision",
                readImage("callOfDuty3.jpg"));
        Game game3 = new Game(3L, "Elden Ring",
                "Set in the Lands Between, players control a customizable player character on a quest to repair the Elden Ring and become the new Elden Lord.",
                Arrays.asList(Platform.WINDOWS, Platform.PLAYSTATION_4, Platform.PLAYSTATION_5, Platform.XBOX_ONE, Platform.XBOX_SERIES),
                Arrays.asList(Genre.ROLE_PLAYING),
                LocalDate.of(2022, 2, 25), AgeRating.USK_16, "From Software", "Bandai Namco", readImage("EldenRing.jpg"));

        Review game1Review1 = new Review(1L, "Gut", "Voll gut.", 10, LocalDate.now());
        Review game1Review2 = new Review(2L, "Edel", "Echt edel", 8, LocalDate.now());
        Review game2Review1 = new Review(3L, "Voll gut", "10 von 10", 9, LocalDate.now());
        Review game2Review2 = new Review(4L, "Doof", "Kacke viel zu schwer.", 2, LocalDate.now());

        reviewRepository.save(game1Review1);
        reviewRepository.save(game1Review2);
        reviewRepository.save(game2Review1);
        reviewRepository.save(game2Review2);

        game1.addReview(game1Review1);
        game1.addReview(game1Review2);
        game2.addReview(game2Review1);
        game2.addReview(game2Review2);

        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);
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
