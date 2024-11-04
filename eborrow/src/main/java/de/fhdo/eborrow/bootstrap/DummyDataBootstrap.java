package de.fhdo.eborrow.bootstrap;

import de.fhdo.eborrow.domain.account.Admin;
import de.fhdo.eborrow.domain.account.PaymentOption;
import de.fhdo.eborrow.domain.account.Publisher;
import de.fhdo.eborrow.domain.account.User;
import de.fhdo.eborrow.domain.account.builder.AdminBuilder;
import de.fhdo.eborrow.domain.account.builder.PaymentOptionBuilder;
import de.fhdo.eborrow.domain.account.builder.PublisherBuilder;
import de.fhdo.eborrow.domain.account.builder.UserBuilder;
import de.fhdo.eborrow.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.io.*;
import java.util.Objects;

import de.fhdo.eborrow.domain.Game;
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

    private void initData() {
        Game game1 = new Game(1L, "Baldurs Gate 3",
                "Stelle deine Gruppe zusammen und kehre in die Vergessenen Reiche zurück. Erlebe eine Geschichte von Freundschaft und Verrat, von Opfer und Überleben – und die Verlockung absoluter Macht.",
                1, "Rollenspiel",
                LocalDate.of(2023, 8, 3), 18, "Larian Studios", "Larian Studios", readImage("BaldursGate3.jpg"));

        gameRepository.save(game1);

        initAccounts();
    }

    private void initAccounts() {
        User user = new UserBuilder()
                .setId(1L)
                .setPrename("Max")
                .setSurname("Mustermann")
                .setUsername("mamus")
                .setBirthday(LocalDate.of(2000, 1, 1))
                .setEmail("max.mustermann@dummy.com")
                .setPassword("password")
                .setProfilePicture(readImage("where_image.png"))
                .setPaymentOption(new PaymentOptionBuilder()
                        .setId(1L)
                        .setIban("DE00111122223333444455")
                        .setAccountOwnerPrename("Max")
                        .setAccountOwnerSurname("Mustermann")
                        .setAccountOwnerStreet("Musterstraße")
                        .setAccountOwnerHousenumber((byte) 1)
                        .setAccountOwnerZipCode((byte) 12345)
                        .setAccountOwnerResidence("Musterstadt")
                        .setIsActive(true)
                        .build())
                .build();

        Admin admin = new AdminBuilder()
                .setId(1L)
                .setPrename("Admin")
                .setSurname("Admin")
                .setUsername("admin")
                .setEmail("admin@dummy.com")
                .setPassword("admin")
                .build();

        Publisher publisher = new PublisherBuilder()
                .setId(1L)
                .setPrename("Publisher")
                .setSurname("Publisher")
                .setUsername("publisher")
                .setEmail("publisher@dummy.com")
                .setPassword("publisher")
                .build();

        accountRepository.save(user);
        accountRepository.save(admin);
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
