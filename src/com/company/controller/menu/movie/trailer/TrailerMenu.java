package com.company.controller.menu.movie.trailer;

import com.company.controller.model.movie.trailer.TrailerRequestModel;
import com.company.service.movie.trailer.TrailerService;

import java.io.IOException;
import java.util.Scanner;

public class TrailerMenu {
    public static void trailerMenuForAdmin() {
        TrailerService trailerService = new TrailerService();
        TrailerRequestModel trailerRequestModel = new TrailerRequestModel();
        int num;
        do {
            System.out.println("____________________________________");
            System.out.println("              Trailer           ");
            System.out.println("   Press 1 to create Trailer     ");
            System.out.println("   Press 2 to print all Trailers ");
            System.out.println("   Press 3 to print Trailer by id ");
            System.out.println("   Press 4 to update Trailer ");
            System.out.println("   Press 5 to delete Trailer by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Trailer duration");
                        String duration = scanner.nextLine();
                        trailerRequestModel.setDuration(duration);
                        trailerService.create(trailerRequestModel);
                        System.out.println("Trailer successfully created");
                        break;
                    case 2:
                        System.out.println("______________All Trailers_____________");
                        trailerService.printAllTrailers();
                        System.out.println("_______________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Trailer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Trailer By Id_____________");
                        trailerService.printSelectedTrailerById(id);
                        System.out.println("________________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Trailer id");
                        int updatedId = scanner.nextInt();
                        System.out.println("Please input Trailer duration");
                        scanner.nextLine();
                        String updatedDuration = scanner.nextLine();
                        trailerRequestModel.setDuration(updatedDuration);
                        trailerService.update(trailerRequestModel,updatedId);
                        System.out.println("Trailer successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Trailer id");
                        int deletedId = scanner.nextInt();
                        trailerService.deleteById(deletedId);
                        System.out.println("Trailer successfully deleted");
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

    public static void trailerMenuForUser(){
        TrailerService trailerService = new TrailerService();
        int num;
        do {
            System.out.println("____________________________________");
            System.out.println("              Trailer           ");
            System.out.println("   Press 1 to print all Trailers ");
            System.out.println("   Press 2 to print Trailer by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Trailers_____________");
                        trailerService.printAllTrailers();
                        System.out.println("_______________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Trailer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Trailer By Id_____________");
                        trailerService.printSelectedTrailerById(id);
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
