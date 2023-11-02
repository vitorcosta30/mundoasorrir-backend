package com.mundoasorrir.mundoasorrirbackend.Domain.File;

import jakarta.persistence.*;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    //private Long owner;

    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] data;


    public File() {}

    public File(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }


}
