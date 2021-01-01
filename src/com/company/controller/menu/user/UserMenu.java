package com.company.controller.menu.user;

import com.company.controller.model.user.UserRequestModel;
import com.company.persistance.model.user.Role;
import com.company.service.user.UserService;

import java.io.IOException;
import java.util.Scanner;

public class UserMenu {
    public static void userMenuForAdmin() {
        UserService userService = new UserService();
        UserRequestModel userRequestModel = new UserRequestModel();
        int num;
        do {
            System.out.println("___________________________________");
            System.out.println("              User           ");
            System.out.println("   Press 1 to print all Users ");
            System.out.println("   Press 2 to print User by id ");
            System.out.println("   Press 3 to update User ");
            System.out.println("   Press 4 to delete User by id ");
            System.out.println("   Press 5 to return login menu");
            System.out.println("___________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Users_____________");
                        userService.printAllActors();
                        System.out.println("_____________________________________");
                        break;
                    case 2:
                        System.out.println("Please input User id");
                        int id = scanner.nextInt();
                        System.out.println("______________User By Id_____________");
                        userService.printSelectedUserById(id);
                        System.out.println("______________________________________");
                        break;
                    case 3:
                        System.out.println("Please input updated User id");
                        int updatedId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input User name");
                        String updatedName = scanner.nextLine();
                        System.out.println("Please input User surname");
                        String updatedSurname = scanner.nextLine();
                        System.out.println("Please input User role");
                        String updatedRole = scanner.nextLine();
                        System.out.println("Please input User email");
                        String updatedEmail = scanner.nextLine();
                        System.out.println("Please input User username");
                        String updatedUsername = scanner.nextLine();
                        System.out.println("Please input User password");
                        String updatedPassword = scanner.nextLine();
                        userRequestModel.setName(updatedName);
                        userRequestModel.setSurname(updatedSurname);
                        userRequestModel.setRole(Role.valueOf(updatedRole));
                        userRequestModel.setEmail(updatedEmail);
                        userRequestModel.setUsername(updatedUsername);
                        userRequestModel.setPassword(updatedPassword);
                        userService.update(userRequestModel, updatedId);
                        System.out.println("User successfully updated");
                        break;
                    case 4:
                        System.out.println("Please input deleted User id");
                        int deletedId = scanner.nextInt();
                        userService.deleteById(deletedId);
                        System.out.println("User successfully deleted");
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Please write valid order");
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
        } while (num != 5);
    }
}
