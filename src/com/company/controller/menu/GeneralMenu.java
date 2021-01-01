package com.company.controller.menu;

import com.company.controller.menu.movie.film.MovieMenu;
import com.company.controller.menu.movie.personal.ActorMenu;
import com.company.controller.menu.movie.personal.ProducerMenu;
import com.company.controller.menu.movie.soundtrack.music.SoundtrackMenu;
import com.company.controller.menu.movie.soundtrack.personal.ComposerMenu;
import com.company.controller.menu.movie.soundtrack.personal.SingerMenu;
import com.company.controller.menu.movie.soundtrack.song.SongMenu;
import com.company.controller.menu.movie.trailer.TrailerMenu;
import com.company.controller.menu.user.UserMenu;
import com.company.controller.model.user.UserRequestModel;
import com.company.persistance.model.user.Role;
import com.company.service.user.UserService;

import java.io.IOException;
import java.util.Scanner;

public class GeneralMenu {
    public static void menu() {
        UserService userService = new UserService();
        UserRequestModel userRequestModel = new UserRequestModel();
        int num;
        int num1;
        do {
            System.out.println("________________________________");
            System.out.println("          Sing in OR Sing up    ");
            System.out.println("   Press 1 for sing up");
            System.out.println("   Press 2 for sing in");
            System.out.println("   Press 3 for exit");
            System.out.println("________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input User name");
                        String name = scanner.nextLine();
                        System.out.println("Please input User surname");
                        String surname = scanner.nextLine();
                        System.out.println("Please input User role");
                        String role = scanner.nextLine();
                        System.out.println("Please input User email");
                        String email = scanner.nextLine();
                        System.out.println("Please input User username");
                        String username = scanner.nextLine();
                        System.out.println("Please input User password");
                        String password = scanner.nextLine();
                        userRequestModel.setName(name);
                        userRequestModel.setSurname(surname);
                        userRequestModel.setRole(Role.valueOf(role));
                        userRequestModel.setEmail(email);
                        userRequestModel.setUsername(username);
                        userRequestModel.setPassword(userRequestModel.mD5PasswordEncoder(password));
                        userService.create(userRequestModel);
                        System.out.println("Sing up successfully finished");
                        break;
                    case 2:
                        System.out.println("Please input User username");
                        String loginUsername = scanner.nextLine();
                        System.out.println("Please input user password");
                        String loginPassword = scanner.nextLine();
                        userRequestModel.setPassword(loginPassword);
                        System.out.println(userService.login(loginUsername, userRequestModel));
                        System.out.println(userService.login(loginUsername, userRequestModel).equals(Role.ADMIN));
                        if (userService.login(loginUsername, userRequestModel).equals(Role.ADMIN)) {
                            do {
                                System.out.println("______________________________________________");
                                System.out.println("                 Admin menu    ");
                                System.out.println("   Press 1 for opening movie menu");
                                System.out.println("   Press 2 for opening actor menu");
                                System.out.println("   Press 3 for opening producer menu");
                                System.out.println("   Press 4 for opening soundtrack menu");
                                System.out.println("   Press 5 for opening composer menu");
                                System.out.println("   Press 6 for opening singer menu");
                                System.out.println("   Press 7 for opening song menu");
                                System.out.println("   Press 8 for opening trailer menu");
                                System.out.println("   Press 9 for opening user menu");
                                System.out.println("   Press 10 for returning to sing up menu");
                                System.out.println("______________________________________________");
                                num1 = scanner.nextInt();
                                String test1 = scanner.nextLine();
                                switch (num1) {
                                    case 1:
                                        MovieMenu.movieMenuForAdmin();
                                        break;
                                    case 2:
                                        ActorMenu.actorMenuForAdmin();
                                        break;
                                    case 3:
                                        ProducerMenu.producerMenuForAdmin();
                                        break;
                                    case 4:
                                        SoundtrackMenu.soundtrackMenuForAdmin();
                                        break;
                                    case 5:
                                        ComposerMenu.composerMenuForAdmin();
                                        break;
                                    case 6:
                                        SingerMenu.singerMenuForAdmin();
                                        break;
                                    case 7:
                                        SongMenu.songMenuForAdmin();
                                        break;
                                    case 8:
                                        TrailerMenu.trailerMenuForAdmin();
                                        break;
                                    case 9:
                                        UserMenu.userMenuForAdmin();
                                        break;
                                    case 10:
                                        break;
                                    default:
                                        System.out.println("Please write valid order");
                                }
                            } while (num1 != 10);
                        } else {
                            do {
                                System.out.println("______________________________________________");
                                System.out.println("                 User menu    ");
                                System.out.println("   Press 1 for opening movie menu");
                                System.out.println("   Press 2 for opening actor menu");
                                System.out.println("   Press 3 for opening producer menu");
                                System.out.println("   Press 4 for opening soundtrack menu");
                                System.out.println("   Press 5 for opening composer menu");
                                System.out.println("   Press 6 for opening singer menu");
                                System.out.println("   Press 7 for opening song menu");
                                System.out.println("   Press 8 for opening trailer menu");
                                System.out.println("   Press 9 for returning to sing up menu");
                                System.out.println("______________________________________________");
                                num1 = scanner.nextInt();
                                String test1 = scanner.nextLine();
                                switch (num1) {
                                    case 1:
                                        MovieMenu.movieMenuForUser();
                                        break;
                                    case 2:
                                        ActorMenu.actorMenuForUser();
                                        break;
                                    case 3:
                                        ProducerMenu.producerMenuForUser();
                                        break;
                                    case 4:
                                        SoundtrackMenu.soundtrackMenuForUser();
                                        break;
                                    case 5:
                                        ComposerMenu.composerMenuForUser();
                                        break;
                                    case 6:
                                        SingerMenu.singerMenuForUser();
                                        break;
                                    case 7:
                                        SongMenu.songMenuForUser();
                                        break;
                                    case 8:
                                        TrailerMenu.trailerMenuForUser();
                                        break;
                                    case 9:
                                        break;
                                    default:
                                        System.out.println("Please write valid order");
                                }
                            }while (num1 != 9);
                        }
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
