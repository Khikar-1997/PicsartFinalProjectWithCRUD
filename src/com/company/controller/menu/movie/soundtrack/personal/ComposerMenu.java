package com.company.controller.menu.movie.soundtrack.personal;

import com.company.controller.model.movie.soundtrack.personal.composer.ComposerRequestModel;
import com.company.persistance.model.movie.soundtrack.music.MelodyGenre;
import com.company.service.movie.soundtrack.personal.ComposerService;

import java.io.IOException;
import java.util.Scanner;

public class ComposerMenu {
    public static void composerMenuForAdmin() {
        ComposerService composerService = new ComposerService();
        ComposerRequestModel composerRequestModel = new ComposerRequestModel();
        int num;
        do {
            System.out.println("____________________________________");
            System.out.println("              Composer           ");
            System.out.println("   Press 1 to create Composer     ");
            System.out.println("   Press 2 to print all Composers ");
            System.out.println("   Press 3 to print Composer by id ");
            System.out.println("   Press 4 to update Composer ");
            System.out.println("   Press 5 to delete Composer by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Composer name");
                        String name = scanner.nextLine();
                        System.out.println("Please input Composer surname");
                        String surname = scanner.nextLine();
                        System.out.println("Please input Composer melody genre");
                        String melodyGenre = scanner.nextLine();
                        composerRequestModel.setName(name);
                        composerRequestModel.setSurname(surname);
                        composerRequestModel.setMelodyGenre(MelodyGenre.valueOf(melodyGenre));
                        composerService.create(composerRequestModel);
                        System.out.println("Composer successfully created");
                        break;
                    case 2:
                        System.out.println("______________All Composers_____________");
                        composerService.printAllComposers();
                        System.out.println("_______________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Composer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Composer By Id_____________");
                        composerService.printSelectedComposerById(id);
                        System.out.println("________________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Composer id");
                        int updatedId = scanner.nextInt();
                        System.out.println("Please input Composer name");
                        scanner.nextLine();
                        String updatedNmae = scanner.nextLine();
                        System.out.println("Please input Composer surname");
                        String updatedSurname = scanner.nextLine();
                        System.out.println("Please input Composer melody genre");
                        String updatedMelodyGenre = scanner.nextLine();
                        composerRequestModel.setName(updatedNmae);
                        composerRequestModel.setSurname(updatedSurname);
                        composerRequestModel.setMelodyGenre(MelodyGenre.valueOf(updatedMelodyGenre));
                        composerService.update(composerRequestModel,updatedId);
                        System.out.println("Composer successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Composer id");
                        int deletedId = scanner.nextInt();
                        composerService.deleteById(deletedId);
                        System.out.println("Composer successfully deleted");
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

    public static void composerMenuForUser(){
        ComposerService composerService = new ComposerService();
        int num;
        do {
            System.out.println("____________________________________");
            System.out.println("              Composer           ");
            System.out.println("   Press 1 to print all Composers ");
            System.out.println("   Press 2 to print Composer by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Composers_____________");
                        composerService.printAllComposers();
                        System.out.println("_______________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Composer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Composer By Id_____________");
                        composerService.printSelectedComposerById(id);
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

