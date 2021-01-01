package com.company.controller.menu.movie.film;

import com.company.controller.model.movie.film.MovieRequestModel;
import com.company.persistance.model.movie.film.MovieGenre;
import com.company.service.movie.film.MovieService;

import java.io.IOException;
import java.util.Scanner;

public class MovieMenu {
    public static void movieMenuForAdmin() {
        MovieService movieService = new MovieService();
        MovieRequestModel movieRequestModel = new MovieRequestModel();
        int num;
        do {
            System.out.println("___________________________________");
            System.out.println("              Movie           ");
            System.out.println("   Press 1 to create Movie     ");
            System.out.println("   Press 2 to print all Movies ");
            System.out.println("   Press 3 to print Movie by id ");
            System.out.println("   Press 4 to update Movie ");
            System.out.println("   Press 5 to delete Movie by id ");
            System.out.println("   Press 6 to return general menu");
            System.out.println("___________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("Please input Movie name");
                        String name = scanner.nextLine();
                        System.out.println("Please input Movie duration");
                        String duration = scanner.nextLine();
                        System.out.println("Please input Movie genre");
                        String genre = scanner.nextLine();
                        System.out.println("Please input Movie rate");
                        double rate = scanner.nextDouble();
                        System.out.println("Please input Movie actor id");
                        int actorId = scanner.nextInt();
                        System.out.println("Please input Movie producer id");
                        int producerId = scanner.nextInt();
                        System.out.println("Please input Movie soundtrack id");
                        int soundtrackId = scanner.nextInt();
                        System.out.println("Please input Movie trailer id");
                        int trailerId = scanner.nextInt();
                        movieRequestModel.setName(name);
                        movieRequestModel.setDuration(duration);
                        movieRequestModel.setGenre(MovieGenre.valueOf(genre));
                        movieRequestModel.setRate(rate);
                        movieRequestModel.setActorId(actorId);
                        movieRequestModel.setProducerId(producerId);
                        movieRequestModel.setSoundtrackId(soundtrackId);
                        movieRequestModel.setTrailerId(trailerId);
                        movieService.create(movieRequestModel);
                        System.out.println("Movie successfully created");
                        break;
                    case 2:
                        System.out.println("______________All Movies_____________");
                        movieService.printAllMovies();
                        System.out.println("_____________________________________");
                        break;
                    case 3:
                        System.out.println("Please input Movie id");
                        int id = scanner.nextInt();
                        System.out.println("______________Movie By Id_____________");
                        movieService.printSelectedMovieById(id);
                        System.out.println("______________________________________");
                        break;
                    case 4:
                        System.out.println("Please input updated Movie id");
                        int updatedId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input Movie new name");
                        String updatedName = scanner.nextLine();
                        System.out.println("Please input Movie new duration");
                        String updatedDuration = scanner.nextLine();
                        System.out.println("Please input Movie new genre");
                        String newGenre = scanner.nextLine();
                        System.out.println("Please input Movie new rate");
                        double updatedRate = scanner.nextDouble();
                        System.out.println("Please input Movie new actor id");
                        int updatedActorId = scanner.nextInt();
                        System.out.println("Please input Movie new producer id");
                        int updatedProducerId = scanner.nextInt();
                        System.out.println("Please input Movie new soundtrack id");
                        int updatedSoundtrackId = scanner.nextInt();
                        System.out.println("Please input Movie new trailer id");
                        int updatedTrailerId = scanner.nextInt();
                        movieRequestModel.setName(updatedName);
                        movieRequestModel.setDuration(updatedDuration);
                        movieRequestModel.setGenre(MovieGenre.valueOf(newGenre));
                        movieRequestModel.setRate(updatedRate);
                        movieRequestModel.setActorId(updatedActorId);
                        movieRequestModel.setProducerId(updatedProducerId);
                        movieRequestModel.setSoundtrackId(updatedSoundtrackId);
                        movieRequestModel.setTrailerId(updatedTrailerId);
                        movieService.update(movieRequestModel,updatedId);
                        System.out.println("Movie successfully updated");
                        break;
                    case 5:
                        System.out.println("Please input deleted Movie id");
                        int deletedId = scanner.nextInt();
                        movieService.deleteById(deletedId);
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

    public static void movieMenuForUser(){
        MovieService movieService = new MovieService();
        int num;
        do {
            System.out.println("______________________________________");
            System.out.println("              Movie           ");
            System.out.println("   Press 1 to print all Movies ");
            System.out.println("   Press 2 to print Movie by id ");
            System.out.println("   Press 3 to return general menu");
            System.out.println("______________________________________");

            Scanner scanner = new Scanner(System.in);
            num = scanner.nextInt();
            String test = scanner.nextLine();
            try {
                switch (num) {
                    case 1:
                        System.out.println("______________All Movies_____________");
                        movieService.printAllMovies();
                        System.out.println("_______________________________________");
                        break;
                    case 2:
                        System.out.println("Please input Movie id");
                        int id = scanner.nextInt();
                        System.out.println("______________Movie By Id_____________");
                        movieService.printSelectedMovieById(id);
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