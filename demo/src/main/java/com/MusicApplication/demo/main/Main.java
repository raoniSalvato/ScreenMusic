package com.MusicApplication.demo.main;

import com.MusicApplication.demo.model.Artist;
import com.MusicApplication.demo.model.EnumArtist;
import com.MusicApplication.demo.model.Music;
import com.MusicApplication.demo.repository.MusicRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private MusicRepository repository;

    public Main(MusicRepository repository){
        this.repository = repository;
    }

    public void displayMenu(){
        var option = -1;
        while (option != 0){
            var menu = """
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por artistas
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            option = scanner.nextInt();

            switch(option){
                case 1:
                    registerArtists();
                    break;
                case 2:
                    registerMusic();
                    break;
                case 3:
                    listMusics();
                    break;
                case 4:
                    searchMusicByArtistc();
                    break;
                case 0:
                    System.out.println("Saindo.....");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void registerArtists() {
        var newRegister = "s";
        while (newRegister.equalsIgnoreCase("s")) {
            System.out.println("Informe o nome do artista:");
            var name = scanner.next();
            scanner.nextLine();
            System.out.println("Informe o tipo desse artista: (solo (0), dupla (1), banda (2))");
            String type = scanner.nextLine();

            EnumArtist enumArtist = null;
            switch (type) {
                case "0" -> enumArtist = EnumArtist.SOLO;
                case "1" -> enumArtist = EnumArtist.DUPLA;
                case "2" -> enumArtist = EnumArtist.BANDA;
                default -> {
                    System.out.println("Tipo inválido. Tente novamente.");
                    continue;
                }
            }
            Artist artist = new Artist(name, enumArtist);
            repository.save(artist);
            System.out.println("Cadastrar novo artista? (s/n)");
            newRegister = scanner.nextLine();
        }
    }

    private void registerMusic() {
        System.out.println("Cadastrar música de que artista: ");
        var name = scanner.next();
        scanner.nextLine();
        Optional<Artist> artist = repository.findByArtistNameContainingIgnoreCase(name);

        if(artist.isPresent()){
            System.out.println("Digite o titulo da música: ");
            var musicName = scanner.nextLine();
            Music music = new Music(musicName);
            music.setArtist(artist.get());
            artist.get().getMusics().add(music);
            repository.save(artist.get());
        }else {
            System.out.println("Artista nâo encontrado");
        }
    }

    private void listMusics() {
        List<Artist> artistList = repository.findAll();
        artistList.forEach(artist -> {
            System.out.println("Artista: " + artist.getArtistName());
            artist.getMusics().forEach(music -> System.out.println("\t- " + music.getMusicTitle()));
        });
    }

    private void searchMusicByArtistc() {
        System.out.println("Buscar músicas de que artista? ");
        var artistName = scanner.next();
        scanner.nextLine();
        List<Music> musics = repository.searchMusicsByArtist(artistName);

        if (musics.isEmpty()) {
            System.out.println(String.format("Nenhuma música encontrada para o artista '%s'.", artistName));
        } else {
            musics.forEach(music -> System.out.println("- " + music.getMusicTitle()));
        }
    }

}
