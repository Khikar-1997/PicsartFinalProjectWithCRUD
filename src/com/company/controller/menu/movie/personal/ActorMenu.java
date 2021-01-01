package com.company.controller.menu.movie.personal;

import com.company.controller.model.movie.personal.actor.ActorRequestModel;
import com.company.persistance.model.movie.personal.Profession;
import com.company.service.movie.personal.ActorService;

import java.io.IOException;
import java.util.Scanner;

public class ActorMenu {
    public static void actorMenuForAdmin() {
        ActorService actorService = new ActorService();
        ActorRequestModel actorRequestModel = new ActorRequestModel();
        int num;
        do {
            System.out.println("___________________________________");
            System.out.println("              Actor           ");
            System.out.println("   Press 1 to create Actor     ");
            System.out.println("   Press 2 to print all Actors ");
            System.out.println("   Press 3 to print Actor by id ");
            System.out.println("   Press 4 to update Actor ");
            System.out.println("   Press 5 to delete Actor by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("___________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Actor name");
                        String name = scanner.nextLine();
                        System.out.println("Please input Actor surname");
                        String surname = scanner.nextLine();
                        System.out.println("Please input Actor age");
                        int age = scanner.nextInt();
                        System.out.println("Please input Actor profession");
                        String profession = scanner.nextLine();
                        System.out.println("Please input Actor role");
                        String role = scanner.nextLine();
                        actorRequestModel.setName(name);
                        actorRequestModel.setSurname(surname);
                        actorRequestModel.setAge(age);
                        actorRequestModel.setProfession(Profession.valueOf(profession));
                        actorRequestModel.setRole(role);
                        actorService.create(actorRequestModel);
                        System.out.println("Actor successfully created");
                        break;
                    case 2:
                        System.out.println("______________All Actors_____________");
                        actorService.printAllActors();
                        System.out.println("_____________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Actor id");
                        int id = scanner.nextInt();
                        System.out.println("______________Actor By Id_____________");
                        actorService.printSelectedActorById(id);
                        System.out.println("______________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Actor id");
                        int updatedId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input Actor new name");
                        String updatedName = scanner.nextLine();
                        System.out.println("Please input Actor new surname");
                        String updatedSurname = scanner.nextLine();
                        System.out.println("Please input Actor new age");
                        int updatedAge = scanner.nextInt();
                        System.out.println("Please input Actor new profession");
                        String updatedProfession = scanner.nextLine();
                        System.out.println("Please input Actor new role");
                        String updatedRole = scanner.nextLine();
                        actorRequestModel.setName(updatedName);
                        actorRequestModel.setSurname(updatedSurname);
                        actorRequestModel.setAge(updatedAge);
                        actorRequestModel.setProfession(Profession.valueOf(updatedProfession));
                        actorRequestModel.setRole(updatedRole);
                        actorService.update(actorRequestModel,updatedId);
                        System.out.println("Actor successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Actor id");
                        int deletedId = scanner.nextInt();
                        actorService.deleteById(deletedId);
                        System.out.println("Actor successfully deleted");
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

    public static void actorMenuForUser(){
        ActorService actorService = new ActorService();
        int num;
        do {
            System.out.println("______________________________________");
            System.out.println("              Actor           ");
            System.out.println("   Press 1 to print all Actors ");
            System.out.println("   Press 2 to print Actor by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("______________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Actors_____________");
                        actorService.printAllActors();
                        System.out.println("_______________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Actor id");
                        int id = scanner.nextInt();
                        System.out.println("______________Actor By Id_____________");
                        actorService.printSelectedActorById(id);
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


