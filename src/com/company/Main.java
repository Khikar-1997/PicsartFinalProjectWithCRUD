package com.company;

import com.company.controller.menu.GeneralMenu;
import com.company.controller.menu.movie.film.MovieMenu;
import com.company.controller.menu.movie.soundtrack.music.SoundtrackMenu;
import com.company.controller.menu.movie.soundtrack.personal.ComposerMenu;
import com.company.controller.menu.movie.trailer.TrailerMenu;
import com.company.controller.model.user.UserRequestModel;
import com.company.service.movie.soundtrack.music.SoundtrackService;
import com.company.service.user.UserService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
//
//        MovieService movieService = new MovieService();
//
//        movieService.deleteById(0);
//        System.out.println(movieService.selectAllMovies());

        UserRequestModel userRequestModel = new UserRequestModel();
//        userRequestModel.setName(scanner.nextLine());
//        userRequestModel.setSurname(scanner.nextLine());
//        userRequestModel.setRole(Role.valueOf(scanner.nextLine()));
//        userRequestModel.setEmail(scanner.nextLine());
//        userRequestModel.setUsername(scanner.nextLine());
//        userRequestModel.setPassword(scanner.nextLine());
//        userRequestModel.setPassword("Khikar7991!");
//        System.out.println(userRequestModel.mD5PasswordEncoder(userRequestModel.getPassword()));

//        userRequestModel.setUsername("Khikar23");
//        System.out.println(userRequestModel.getUsername());

//        userRequestModel.setEmail("harutyunyan.xikar@mail.ru");
//        System.out.println(userRequestModel.getEmail());

        UserService userService = new UserService();
//        userService.create(userRequestModel);
//        userService.printSelectedUserById(0);
//        System.out.println(userService.selectUserById(0).getEmail());
//        System.out.println(userService.login(scanner.nextLine(), userRequestModel).toString());

//        TrailerMenu trailerMenu = new TrailerMenu();
//        trailerMenu.trailerMenuForAdmin();

//        SoundtrackMenu.soundtrackMenuForAdmin();
        GeneralMenu.menu();
    }
}
