package com.MusicApplication.demo.repository;

import com.MusicApplication.demo.model.Artist;
import com.MusicApplication.demo.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByArtistNameContainingIgnoreCase(String name);

    @Query("SELECT m FROM Artist a JOIN a.musics m WHERE a.artistName ILIKE %:name")
    List<Music> searchMusicsByArtist(String name);
}
