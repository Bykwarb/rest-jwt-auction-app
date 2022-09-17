package com.example.demo.entity.lot;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Lob
    private byte[] content;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = Lot.class)
    private Lot lot;

    public Image() {
    }

    public Image(String fileName, byte[] content, Lot lot) {
        this.fileName = fileName;
        this.content = content;
        this.lot = lot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", content=" + Arrays.toString(content) +
                ", lot=" + lot.getId() +
                '}';
    }
}
