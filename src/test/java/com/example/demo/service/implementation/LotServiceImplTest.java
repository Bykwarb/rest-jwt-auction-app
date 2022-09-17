package com.example.demo.service.implementation;

import com.example.demo.entity.lot.*;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.enums.Role;
import com.example.demo.service.LotService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance. Lifecycle.PER_CLASS)
class LotServiceImplTest {
    private final LotService lotService;
    private final UserRepository userRepository;
    @Autowired
    LotServiceImplTest(LotService lotService, UserRepository userRepository) {
        this.lotService = lotService;
        this.userRepository = userRepository;
    }

    @BeforeAll
    void setUp(){
        User user = new User();
        user.setBalance(0.0);
        user.setEmail("nebykwarb@gmail.com");
        user.setPassword("Xxagrorog123");
        user.setRole(Role.USER);
        user.setUsername("TestUser");
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("TestLot1")
                .setPurchasePrice(111.0)
                .setStartingPrice(11.0)
                .setStartDate(LocalDateTime.now().plusMinutes(100))
                .setEndDate(LocalDateTime.now().plusDays(1).plusMinutes(100))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setDateTime(LocalDateTime.now());
        bid.setBidValue(44.0);
        lot.getBid().add(bid);

        Lot lot2 = new LotBuilder()
                .setUser(user)
                .setName("TestLot2")
                .setPurchasePrice(222.0)
                .setStartingPrice(22.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Bid bid2 = new Bid();
        bid2.setLot(lot2);
        bid2.setBidder(user);
        bid2.setDateTime(LocalDateTime.now());
        bid2.setBidValue(33.0);
        lot2.getBid().add(bid2);

        Lot lot3 = new LotBuilder()
                .setUser(user)
                .setName("TestLot3")
                .setPurchasePrice(333.0)
                .setStartingPrice(33.0)
                .setStartDate(LocalDateTime.now().minusMinutes(1000))
                .setEndDate(LocalDateTime.now().plusDays(1).minusMinutes(1000))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_CARS)
                .build();
        Bid bid3 = new Bid();
        bid3.setLot(lot3);
        bid3.setBidder(user);
        bid3.setDateTime(LocalDateTime.now());
        bid3.setBidValue(22.0);
        lot3.getBid().add(bid3);

        Lot lot4 = new LotBuilder()
                .setUser(user)
                .setName("TestLot4")
                .setPurchasePrice(444.0)
                .setStartingPrice(44.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1).plusDays(22))
                .setStatus(LotStatus.STATUS_STOPPED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Bid bid4 = new Bid();
        bid4.setLot(lot4);
        bid4.setBidder(user);
        bid4.setDateTime(LocalDateTime.now());
        bid4.setBidValue(11.0);
        lot4.getBid().add(bid4);

        user.getUserLots().add(lot);
        user.getUserLots().add(lot2);
        user.getUserLots().add(lot3);
        user.getUserLots().add(lot4);
        userRepository.save(user);

    }

    @Test
    void getLotById() {
        Lot lot = lotService.getLotById(4l);

    }

    @Test
    void deleteLot() {
        Lot lot = lotService.getLotById(1l);
        lotService.deleteLot(lot);
    }

    @Test
    void deleteLotById() {
        lotService.deleteLotById(2l);
    }

    @Test
    void getAllLots() {
        System.out.println(lotService.getAllLots());
    }

    @Test
    void getLotsByCategory() {
        System.out.println(lotService.getLotsByCategory(LotCategory.CATEGORY_TOYS).size());
    }

    @Test
    void getLotsByStatus() {
        System.out.println(lotService.getLotsByStatus(LotStatus.STATUS_STARTED).size());
        System.out.println(lotService.getLotsByStatus(LotStatus.STATUS_STOPPED).size());
    }

    @Test
    void changePurchasePriceInLotByLot() {
        Lot lot = lotService.getLotById(3l);
        lotService.changePurchasePriceInLotByLot(lot, 18.0);
    }

    @Test
    void uploadImageInLotByLot() throws IOException {
        Lot lot = lotService.getLotById(1l);
        lotService.uploadImageInLotByLot(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()), lot);
    }

    @Test
    void changePurchasePriceInLotByLotId() {
        lotService.changePurchasePriceInLotByLotId(3l, 22.0);
    }

    @Test
    void uploadImageInLotByLotId() throws IOException {
        lotService.uploadImageInLotByLotId(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()), 1l);
    }

    @Test
    void getLotsSortedByEndDate() {
        System.out.println(lotService.getLotsSortedByEndDate());
    }

    @Test
    void getLotsSortedByPurchasePrice() {
        System.out.println(lotService.getLotsSortedByPurchasePrice());
    }

    @Test
    void getLotsSortedByBidValue() {
        System.out.println(lotService.getLotsSortedByBidValue());
    }

    @Test
    void sortLotListByEndDate() {
        List<Lot> lots = lotService.sortLotListByEndDate(lotService.getAllLots());
        System.out.println(lots);
    }

    @Test
    void sortLotListByPurchasePrice() {
        List<Lot> lots = lotService.sortLotListByPurchasePrice(lotService.getAllLots());
        System.out.println(lots);
    }

    @Test
    void sortLotListByBidValue() {
        List<Lot> lots = lotService.sortLotListByBidValue(lotService.getAllLots());
        System.out.println(lots);
    }
}