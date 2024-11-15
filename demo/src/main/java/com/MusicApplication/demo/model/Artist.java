package com.MusicApplication.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String artistName;
    @Enumerated(EnumType.ORDINAL)
    private EnumArtist artistType;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Music> musics = new ArrayList<>();

    public Artist(){}

    public Artist(String name, EnumArtist type) {
        this.artistName = name;
        this.artistType = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public EnumArtist getArtistType() {
        return artistType;
    }

    public void setArtistType(EnumArtist artistType) {
        this.artistType = artistType;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    @Override
    public String toString() {
        return  "Nome = " + artistName + '\'' +
                "Tipo do artista = " + artistType + '\'' +
                "Músicas = " + musics;
    }
}
