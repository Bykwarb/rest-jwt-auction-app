package com.example.demo.controller;

import com.example.demo.dto.LotDto;
import com.example.demo.entity.User;
import com.example.demo.entity.lot.*;
import com.example.demo.service.LotService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lot")
public class LotController {

    private final UserService userService;
    private final LotService lotService;
    private final ObjectMapper objectMapper;

    public LotController(UserService userService, LotService lotService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.lotService = lotService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public String createLot(Principal principal,
                            @RequestParam("lotName") String lotName,
                            @RequestParam("p-price") Double purchasePrice,
                            @RequestParam("s-price") Double startingPrice,
                            @RequestParam("category")LotCategory category,
                            @RequestParam("status")LotStatus lotStatus,
                            @RequestParam("s-date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime sDate,
                            @RequestParam("e-date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eDate,
                            @RequestParam("images")MultipartFile[] files) throws IOException {

        User user = userService.getUserByEmail(principal.getName());
        Lot lot = new LotBuilder()
                .setUser(user)
                .setName(lotName)
                .setPurchasePrice(purchasePrice)
                .setStartingPrice(startingPrice)
                .setCategory(category)
                .setStatus(lotStatus)
                .setStartDate(sDate)
                .setEndDate(eDate)
                .build();
        for (int i = 0; i < files.length; i++){
            Image image = new Image();
            image.setLot(lot);
            image.setContent(files[0].getBytes());
            image.setFileName("gvalue" + UUID.randomUUID());
            lot.getImages().add(image);
        }
        lotService.createLot(lot);
        return "/";
    }

    @GetMapping("/{category}/lot_{id}")
    public ResponseEntity<Lot> showLot(@PathVariable("id")Long id, @PathVariable("category") String category){
        LotCategory lotCategory = LotCategory.valueOfLabel(category);
        Lot lot = lotService.getByIdAndCategory(id, lotCategory);
        return new ResponseEntity<>(lot, HttpStatus.OK);
    }

    @GetMapping("/lots/{category}")
    public ResponseEntity<List<Lot>> showLotsByCategory(@PathVariable("category") String category, Model model){
        LotCategory lotCategory = LotCategory.valueOfLabel(category);
        List<Lot> lots = lotService.getLotsByCategory(lotCategory);
        return ResponseEntity.ok(lots);
    }

}
