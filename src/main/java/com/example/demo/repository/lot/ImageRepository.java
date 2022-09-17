package com.example.demo.repository.lot;

import com.example.demo.entity.lot.Image;
import com.example.demo.entity.lot.Lot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> getByLot(Lot lot);
}
