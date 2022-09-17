package com.example.demo.controller;

import com.example.demo.dto.LotDto;
import com.example.demo.entity.lot.Lot;
import com.example.demo.repository.lot.ImageRepository;
import com.example.demo.service.LotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/utils")
public class UtilRestController {

    private final LotService lotService;
    private final ImageRepository imageRepository;
    public UtilRestController(LotService lotService, ImageRepository imageRepository) {
        this.lotService = lotService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/set-start-page")
    public ResponseEntity<List<LotDto>> setContentOnStartPage(){
        List<Lot> lots = lotService.setUpStartPage();
        List<LotDto> lotDtos = new ArrayList<>();
        lots.forEach(e -> {
            LotDto lotDto = new LotDto();
            lotDto.setId(e.getId().toString());
            lotDto.setStartDate(e.getStartDate().toString());
            lotDto.setEndDate(e.getEndDate().toString());
            lotDto.setPurchasePrice(e.getPurchasePrice().toString());
            if (!e.getBid().isEmpty()){
                lotDto.setLastBid(e.getBid().get(e.getBid().size()-1).getBidValue().toString());
            }else {
                lotDto.setLastBid(e.getStartingPrice().toString());
            }
            lotDto.setName(e.getName());
            if (!e.getImages().isEmpty()){
                lotDto.setPreviewImage(e.getImages().get(e.getImages().size() -1).getId().toString());
            }
            lotDtos.add(lotDto);
        });
        return ResponseEntity.ok(lotDtos);
    }

    @GetMapping(value = "/get-image/{id}", produces = {"image/jpg", "image/jpeg", "image/png", "image/bmp"})
    public ResponseEntity<byte[]> getImageAsByteArray(@PathVariable("id") Long imageId){
        byte[] media = imageRepository.findById(imageId).get().getContent();
        return new ResponseEntity<>(media, HttpStatus.OK);
    }

}
