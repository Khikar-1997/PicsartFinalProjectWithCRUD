package com.company.controller.menu.movie.soundtrack.song;

import com.company.controller.model.movie.soundtrack.song.SongRequestModel;
import com.company.service.movie.soundtrack.song.SongService;

import java.io.IOException;
import java.util.Scanner;

public class SongMenu {
    public static void songMenuForAdmin() {
        SongService songService = new SongService();
        SongRequestModel songRequestModel = new SongRequestModel();
        int num;
        do {
            System.out.println("___________________________________");
            System.out.println("              Song           ");
            System.out.println("   Press 1 to create Song     ");
            System.out.println("   Press 2 to print all Songs ");
            System.out.println("   Press 3 to print Song by id ");
            System.out.println("   Press 4 to update Song ");
            System.out.println("   Press 5 to delete Song by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Song name");
                        String name = scanner.nextLine();
                        System.out.println("Please input Song duration");
                        String duration = scanner.nextLine();
                        System.out.println("Please input Song singer id");
                        int singerId = scanner.nextInt();
                        songRequestModel.setName(name);
                        songRequestModel.setDuration(duration);
                        songRequestModel.setSingerId(singerId);
                        songService.create(songRequestModel);
                        System.out.println("Song successfully created");
                        break;
                    case 2:
                        System.out.println("______________All Songs_____________");
                        songService.printAllSongs();
                        System.out.println("____________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Soundtrack id");
                        int id = scanner.nextInt();
                        System.out.println("______________Song By Id_____________");
                        songService.printSelectedSongById(id);
                        System.out.println("_____________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Song id");
                        int updatedId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input Song name");
                        String updatedName = scanner.nextLine();
                        System.out.println("Please input Song duration");
                        String updatedDuration = scanner.nextLine();
                        System.out.println("Please input Song singer id");
                        int updatedSingerId = scanner.nextInt();
                        songRequestModel.setName(updatedName);
                        songRequestModel.setDuration(updatedDuration);
                        songRequestModel.setSingerId(updatedSingerId);
                        songService.update(songRequestModel,updatedId);
                        System.out.println("Song successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Song id");
                        int deletedId = scanner.nextInt();
                        songService.deleteById(deletedId);
                        System.out.println("Song successfully deleted");
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Please write valid order");
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        } while (num != 6);
    }

    public static void songMenuForUser(){
        SongService songService = new SongService();
        int num;
        do {
            System.out.println("____________________________________");
            System.out.println("              Song           ");
            System.out.println("   Press 1 to print all Songs ");
            System.out.println("   Press 2 to print Song by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Songs_____________");
                        songService.printAllSongs();
                        System.out.println("_______________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Song id");
                        int id = scanner.nextInt();
                        System.out.println("______________Song By Id_____________");
                        songService.printSelectedSongById(id);
                        System.out.println("________________________________________");
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Please write valid order");
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        } while (num != 3);
    }
}