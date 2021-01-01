package com.company.controller.menu.movie.personal;

import com.company.controller.model.movie.personal.producer.ProducerRequestModel;
import com.company.persistance.model.movie.personal.Profession;
import com.company.service.movie.personal.ProducerService;

import java.io.IOException;
import java.util.Scanner;

public class ProducerMenu {
    public static void producerMenuForAdmin() {
        ProducerService producerService = new ProducerService();
        ProducerRequestModel producerRequestModel = new ProducerRequestModel();
        int num;
        do {
            System.out.println("_____________________________________");
            System.out.println("              Producer           ");
            System.out.println("   Press 1 to create Producer     ");
            System.out.println("   Press 2 to print all Producers ");
            System.out.println("   Press 3 to print Producer by id ");
            System.out.println("   Press 4 to update Producer ");
            System.out.println("   Press 5 to delete Producer by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("_____________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Producer name");
                        String name = scanner.nextLine();
                        System.out.println("Please input Producer surname");
                        String surname = scanner.nextLine();
                        System.out.println("Please input Producer age");
                        int age = scanner.nextInt();
                        System.out.println("Please input Producer profession");
                        String profession = scanner.nextLine();
                        producerRequestModel.setName(name);
                        producerRequestModel.setSurname(surname);
                        producerRequestModel.setAge(age);
                        producerRequestModel.setProfession(Profession.valueOf(profession));
                        producerService.create(producerRequestModel);
                        System.out.println("Actor successfully created");
                        break;
                    case 2:
                        System.out.println("______________All Producers_____________");
                        producerService.printAllProducers();
                        System.out.println("________________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Producer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Producer By Id_____________");
                        producerService.printSelectedProducerById(id);
                        System.out.println("_________________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Producer id");
                        int updatedId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input Producer new name");
                        String updatedName = scanner.nextLine();
                        System.out.println("Please input Producer new surname");
                        String updatedSurname = scanner.nextLine();
                        System.out.println("Please input Producer new age");
                        int updatedAge = scanner.nextInt();
                        System.out.println("Please input Producer new profession");
                        String updatedProfession = scanner.nextLine();
                        producerRequestModel.setName(updatedName);
                        producerRequestModel.setSurname(updatedSurname);
                        producerRequestModel.setAge(updatedAge);
                        producerRequestModel.setProfession(Profession.valueOf(updatedProfession));
                        producerService.update(producerRequestModel,updatedId);
                        System.out.println("Actor successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Producer id");
                        int deletedId = scanner.nextInt();
                        producerService.deleteById(deletedId);
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

    public static void producerMenuForUser(){
        ProducerService producerService = new ProducerService();
        int num;
        do {
            System.out.println("______________________________________");
            System.out.println("              Producer           ");
            System.out.println("   Press 1 to print all Producers ");
            System.out.println("   Press 2 to print Producer by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("______________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Producers_____________");
                        producerService.printAllProducers();
                        System.out.println("________________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Producer id");
                        int id = scanner.nextInt();
                        System.out.println("______________Producer By Id_____________");
                        producerService.printSelectedProducerById(id);
                        System.out.println("_________________________________________");
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