package com.company.controller.menu.movie.soundtrack.music;

import com.company.controller.model.movie.soundtrack.music.SoundtrackRequestModel;
import com.company.service.movie.soundtrack.music.SoundtrackService;

import java.io.IOException;
import java.util.Scanner;

public class SoundtrackMenu {
    public static void soundtrackMenuForAdmin() {
        SoundtrackService soundtrackService = new SoundtrackService();
        SoundtrackRequestModel soundtrackRequestModel = new SoundtrackRequestModel();
        int num;
        do {
            System.out.println("______________________________________");
            System.out.println("              Soundtrack           ");
            System.out.println("   Press 1 to create Soundtrack     ");
            System.out.println("   Press 2 to print all Soundtracks ");
            System.out.println("   Press 3 to print Soundtrack by id ");
            System.out.println("   Press 4 to update Soundtrack ");
            System.out.println("   Press 5 to delete Soundtrack by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("______________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Soundtrack duration");
                        String duration = scanner.nextLine();
                        System.out.println("Please input Soundtrack song id");
                        int songId = scanner.nextInt();
                        System.out.println("Please input Soundtrack composer id");
                        int composerId = scanner.nextInt();
                        soundtrackRequestModel.setDuration(duration);
                        soundtrackRequestModel.setSongId(songId);
                        soundtrackRequestModel.setComposerId(composerId);
                        soundtrackService.create(soundtrackRequestModel);
                        System.out.println("Soundtrack successfully created");
                        break;
                    case 2:
                        System.out.println("______________All Soundtracks_____________");
                        soundtrackService.printAllSoundtracks();
                        System.out.println("__________________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Soundtrack id");
                        int id = scanner.nextInt();
                        System.out.println("______________Soundtrack By Id_____________");
                        soundtrackService.printSelectedSoundtrackById(id);
                        System.out.println("___________________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Soundtrack id");
                        int updatedId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input Soundtrack duration");
                        String updatedDuration = scanner.nextLine();
                        System.out.println("Please input Soundtrack song id");
                        int updatedSongId = scanner.nextInt();
                        System.out.println("Please input Soundtrack composer id");
                        int updatedComposerId = scanner.nextInt();
                        soundtrackRequestModel.setDuration(updatedDuration);
                        soundtrackRequestModel.setSongId(updatedSongId);
                        soundtrackRequestModel.setComposerId(updatedComposerId);
                        soundtrackService.update(soundtrackRequestModel,updatedId);
                        System.out.println("Soundtrack successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Soundtrack id");
                        int deletedId = scanner.nextInt();
                        soundtrackService.deleteById(deletedId);
                        System.out.println("Soundtrack successfully deleted");
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

    public static void soundtrackMenuForUser(){
        SoundtrackService soundtrackService = new SoundtrackService();
        int num;
        do {
            System.out.println("______________________________________");
            System.out.println("              Soundtrack           ");
            System.out.println("   Press 1 to print all Soundtracks ");
            System.out.println("   Press 2 to print Soundtrack by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("______________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Soundtracks_____________");
                        soundtrackService.printAllSoundtracks();
                        System.out.println("_______________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Soundtrack id");
                        int id = scanner.nextInt();
                        System.out.println("______________Soundtrack By Id_____________");
                        soundtrackService.printSelectedSoundtrackById(id);
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

