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

import de.fhdo.eborrow.domain.Game;
import de.fhdo.eborrow.repositories.GameRepository;

@Component
public class DummyDataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private GameRepository   gameRepository;
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
				Arrays.asList("Windows", "PlayStation 4", "Playstation 5", "Xbox One", "Xbox Series"),
				"Rollenspiel",
                LocalDate.of(2023, 8, 3), 18, "Larian Studios", "Larian Studios", readImage("BaldursGate3.jpg"));
		Game game2 = new Game(2L, "Call of Duty: Modern Warfare III",
				"Call of Duty ist eine weltweit bekannte Ego-Shooter-Spielreihe, die die Spieler in intensive und realistische Kriegsszenarien versetzt. Mit atemberaubender Grafik und fesselnden Storylines erleben Spieler historische und moderne Schlachten. Ob im Einzelspieler-Storymodus oder in actiongeladenen Multiplayer-Schlachten – Call of Duty bietet Adrenalin pur.",
				Arrays.asList("Windows", "PlayStation 4", "Playstation 5", "Xbox One", "Xbox Series"), "Ego-Shooter",
				LocalDate.of(2023, 11, 10), 18, "Sledgehammer Games, Treyarch, Infinity Ward", "Activision",
				readImage("callOfDuty3.jpg"));
		Game game3 = new Game(2L, "Elden Ring", "Set in the Lands Between, players control a customizable player character on a quest to repair the Elden Ring and become the new Elden Lord.",
				Arrays.asList("Windows", "PlayStation 4", "Playstation 5", "Xbox One", "Xbox Series"), "Rollenspiel", LocalDate.of(2022, 2, 25), 16, "From Software", "Bandai Namco", readImage("EldenRing.jpg"));

		Review game1Review1 = new Review(1L, "Voll gut.", 10, LocalDate.now());
		Review game1Review2 = new Review(2L, "Echt edel", 8, LocalDate.now());
		Review game2Review1 = new Review(3L, "10 von 10", 9, LocalDate.now());
		Review game2Review2 = new Review(4L, "Kacke viel zu schwer.", 2, LocalDate.now());

		reviewRepository.save(game1Review1);
		reviewRepository.save(game1Review2);
		reviewRepository.save(game2Review1);
		reviewRepository.save(game2Review2);

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
