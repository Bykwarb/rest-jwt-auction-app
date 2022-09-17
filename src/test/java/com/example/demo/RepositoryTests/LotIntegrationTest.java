package com.example.demo.RepositoryTests;

import com.example.demo.entity.lot.*;
import com.example.demo.entity.User;
import com.example.demo.repository.lot.BidRepository;
import com.example.demo.repository.lot.ImageRepository;
import com.example.demo.repository.lot.LotRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LotIntegrationTest {
    private final UserRepository userRepository;
    private final BidRepository bidRepository;
    private final LotRepository lotRepository;
    private final ImageRepository imageRepository;
    @Autowired
    public LotIntegrationTest(UserRepository userRepository, BidRepository bidRepository, LotRepository lotRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
        this.imageRepository = imageRepository;
    }
    @Test
    public void createLotWithBidAndImageButBidAndImageNotSaved() throws IOException {
        User user = new User("test1@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Lego")
                .setPurchasePrice(22.0)
                .setStartingPrice(5.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image = new Image();
        image.setLot(lot);
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setBidValue(15.5);
        bid.setDateTime(LocalDateTime.now());
        lot.setBid(List.of(bid));
        lot.setImages(List.of(image));
        lotRepository.save(lot);
        assertTrue(lotRepository.existsById(1l) && bidRepository.existsById(1l) && imageRepository.existsById(1l));
        assertTrue(Objects.nonNull(bidRepository.getByLot(lot)));
        assertTrue(Objects.nonNull(imageRepository.getByLot(lot)));
    }
    @Test
    public void createLotButLotSavedFromUser() throws IOException {
        User user = new User("test2@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Bebra")
                .setPurchasePrice(228.5)
                .setStartingPrice(45.4)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image = new Image();
        image.setLot(lot);
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setBidValue(15.4);
        bid.setDateTime(LocalDateTime.now());
        lot.setBid(List.of(bid));
        lot.setImages(List.of(image));
        user.setUserLots(List.of(lot));
        userRepository.save(user);
        assertTrue(lotRepository.existsById(1l) && bidRepository.existsById(1l) && imageRepository.existsById(1l));
        assertTrue(Objects.nonNull(lotRepository.getByCreator(user)));
        assertTrue(Objects.nonNull(bidRepository.getByLot(lotRepository.getByCreator(user).get(0))));
        assertTrue(Objects.nonNull(imageRepository.getByLot(lotRepository.getByCreator(user).get(0))));
    }
    @Test
    public void deleteBidButNotDeletedUser() throws IOException {
        User user = new User("test3@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Cucumber")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image = new Image();
        image.setLot(lot);
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setBidValue(15.0);
        bid.setDateTime(LocalDateTime.now());
        lot.setBid(List.of(bid));
        lot.setImages(List.of(image));
        user.setUserLots(List.of(lot));
        userRepository.save(user);
        bidRepository.deleteById(1l);
        assertTrue(!bidRepository.existsById(1l));
        assertTrue(userRepository.existsById(1l));
    }

    @Test
    @Transactional
    public void deleteBidButNotDeleteLot() throws IOException {
        User user = new User("test4@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Cucumber")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image = new Image();
        image.setLot(lot);
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setBidValue(15.0);
        bid.setDateTime(LocalDateTime.now());
        lot.getBid().add(bid);
        lot.getImages().add(image);
        user.getUserLots().add(lot);
        userRepository.save(user);
        List<Lot> lot2 = lotRepository.getByCreator(user);
        lot2.get(0).getBid().remove(0);
        lotRepository.save(lot2.get(0));
        assertTrue(
                lotRepository.existsById(1l)
                && imageRepository.existsById(1l)
                        && !bidRepository.existsById(1l)
        );
        assertEquals(lot2.get(0).getBid(), lotRepository.getByCreator(user).get(0).getBid());
    }
    @Test
    @Transactional
    public void getBidsFromLot() throws IOException {
        User user = new User("test5@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Cucumber")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image = new Image();
        image.setLot(lot);
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setBidValue(15.0);
        bid.setDateTime(LocalDateTime.now());
        lot.getBid().add(bid);
        lot.getImages().add(image);
        user.getUserLots().add(lot);
        userRepository.save(user);
        List<Lot> lot1 = lotRepository.getByCreator(user);
        assertEquals(lot.getBid().get(0).getBidValue(), lot1.get(0).getBid().get(0).getBidValue());
    }

    @Test
    public void deleteLot() throws IOException {
        User user = new User("test6@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Cucumber")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image = new Image();
        image.setLot(lot);
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setBidValue(15.0);
        bid.setDateTime(LocalDateTime.now());
        lot.setBid(List.of(bid));
        lot.setImages(List.of(image));
        user.setUserLots(List.of(lot));
        userRepository.save(user);
        lotRepository.deleteById(1l);
    }

    @Test
    public void deleteUser() throws IOException {
        User user = new User("test6@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Cucumber")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image = new Image();
        image.setLot(lot);
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setBidder(user);
        bid.setBidValue(15.0);
        bid.setDateTime(LocalDateTime.now());
        lot.setBid(List.of(bid));
        lot.setImages(List.of(image));
        user.setUserLots(List.of(lot));
        userRepository.save(user);

        User user2 = new User("test6_2@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user2);
        Lot lot2 = new LotBuilder()
                .setUser(user2)
                .setName("Cucumber2")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image2 = new Image();
        image2.setLot(lot2);
        image2.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid2 = new Bid();
        bid2.setLot(lot2);
        bid2.setBidder(user2);
        bid2.setBidValue(15.0);
        bid2.setDateTime(LocalDateTime.now());
        lot2.setBid(List.of(bid2));
        lot2.setImages(List.of(image2));
        user2.setUserLots(List.of(lot2));

        userRepository.save(user2);

        User user3 = new User("test6_3@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user3);
        Lot lot3 = new LotBuilder()
                .setUser(user3)
                .setName("Cucumber3")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        Image image3 = new Image();
        image3.setLot(lot3);
        image3.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        Bid bid3 = new Bid();
        bid3.setLot(lot3);
        bid3.setBidder(user3);
        bid3.setBidValue(15.0);
        bid3.setDateTime(LocalDateTime.now());
        lot3.setBid(List.of(bid3));
        lot3.setImages(List.of(image3));
        user3.setUserLots(List.of(lot3));
        userRepository.save(user3);
        userRepository.deleteById(1l);

    }

    @Test
    public void createImageButLotIsExist() throws IOException {
        User user = new User("test6@gmail.com", "Xxagrorog123", Role.ADMIN);
        userRepository.save(user);
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName("Cucumber")
                .setPurchasePrice(228.0)
                .setStartingPrice(45.0)
                .setStartDate(LocalDateTime.now())
                .setEndDate(LocalDateTime.now().plusDays(1))
                .setStatus(LotStatus.STATUS_STARTED)
                .setCategory(LotCategory.CATEGORY_TOYS)
                .build();
        user.getUserLots().add(lot);
        userRepository.save(user);
        Image image = new Image();
        image.setLot(lotRepository.findById(1l).get());
        image.setContent(Files.readAllBytes(new File("C:/Users/nebyk/Desktop/images.jpg").toPath()));
        lot.getImages().add(image);
        lotRepository.save(lot);
    }


}
