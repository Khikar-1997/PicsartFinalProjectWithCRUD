package com.company.controller.menu.movie.soundtrack.personal;

import com.company.controller.model.movie.soundtrack.personal.singer.SingerRequestModel;
import com.company.persistance.model.movie.soundtrack.music.MusicGenre;
import com.company.service.movie.soundtrack.personal.SingerService;

import java.io.IOException;
import java.util.Scanner;

public class SingerMenu {
    public static void singerMenuForAdmin() {
        SingerService singerService = new SingerService();
        SingerRequestModel singerRequestModel = new SingerRequestModel();
        int num;
        do {
            System.out.println("____________________________________");
            System.out.println("              Singer           ");
            System.out.println("   Press 1 to create Singer     ");
            System.out.println("   Press 2 to print all Singers ");
            System.out.println("   Press 3 to print Singer by id ");
            System.out.println("   Press 4 to update Singer ");
            System.out.println("   Press 5 to delete Singer by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Singer name");
                        String name = scanner.nextLine();
                        System.out.println("Please input Singer surname");
                        String surname = scanner.nextLine();
                        System.out.println("Please input Singer music genre");
                        String musicGenre = scanner.nextLine();
                        singerRequestModel.setName(name);
                        singerRequestModel.setSurname(surname);
                        singerRequestModel.setMusicGenre(MusicGenre.valueOf(musicGenre));
                        singerService.create(singerRequestModel);
                        System.out.println("Singer successfully created");
                        break;
                    case 2:
                        System.out.println("__________________________All Singers____________________________");
                        singerService.printAllSingers();
                        System.out.println("_________________________________________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Singer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Singer By Id_____________");
                        singerService.printSelectedSingerById(id);
                        System.out.println("________________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Singer id");
                        int updatedId = scanner.nextInt();
                        System.out.println("Please input Singer name");
                        scanner.nextLine();
                        String updatedNmae = scanner.nextLine();
                        System.out.println("Please input Singer surname");
                        String updatedSurname = scanner.nextLine();
                        System.out.println("Please input Singer music genre");
                        String updatedMusicGenre = scanner.nextLine();
                        singerRequestModel.setName(updatedNmae);
                        singerRequestModel.setSurname(updatedSurname);
                        singerRequestModel.setMusicGenre(MusicGenre.valueOf(updatedMusicGenre));
                        singerService.update(singerRequestModel,updatedId);
                        System.out.println("Singer successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Singer id");
                        int deletedId = scanner.nextInt();
                        singerService.deleteById(deletedId);
                        System.out.println("Singer successfully deleted");
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

    public static void singerMenuForUser(){
        SingerService singerService = new SingerService();
        int num;
        do {
            System.out.println("____________________________________");
            System.out.println("              Singer           ");
            System.out.println("   Press 1 to print all Singers ");
            System.out.println("   Press 2 to print Singer by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Singers_____________");
                        singerService.printAllSingers();
                        System.out.println("_______________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Singer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Singer By Id_____________");
                        singerService.printSelectedSingerById(id);
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