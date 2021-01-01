package com.company.service.movie.film;

import com.company.controller.model.movie.film.MovieRequestModel;
import com.company.controller.model.movie.film.MovieResponseModel;
import com.company.controller.model.movie.soundtrack.music.SoundtrackResponseModel;
import com.company.controller.model.movie.soundtrack.personal.composer.ComposerResponseModel;
import com.company.controller.model.movie.soundtrack.song.SongResponseModel;
import com.company.controller.model.movie.trailer.TrailerResponseModel;
import com.company.exceptions.*;
import com.company.persistance.model.movie.film.Movie;
import com.company.persistance.model.movie.film.MovieGenre;
import com.company.persistance.model.movie.personal.Profession;
import com.company.persistance.model.movie.personal.actor.Actor;
import com.company.persistance.model.movie.personal.producer.Producer;
import com.company.persistance.model.movie.soundtrack.music.MelodyGenre;
import com.company.persistance.model.movie.soundtrack.music.MusicGenre;
import com.company.persistance.model.movie.soundtrack.music.Soundtrack;
import com.company.persistance.model.movie.soundtrack.personal.composer.Composer;
import com.company.persistance.model.movie.soundtrack.personal.singer.Singer;
import com.company.persistance.model.movie.soundtrack.song.Song;
import com.company.persistance.model.movie.trailer.Trailer;
import com.company.service.movie.personal.ActorService;
import com.company.service.movie.personal.ProducerService;
import com.company.service.movie.soundtrack.music.SoundtrackService;
import com.company.service.movie.soundtrack.personal.ComposerService;
import com.company.service.movie.soundtrack.personal.SingerService;
import com.company.service.movie.soundtrack.song.SongService;
import com.company.service.movie.trailer.TrailerService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;

public class MovieService {
    public static final String FILE = "movie.txt";
    private int actorId;
    private int producerId;
    private int soundtrackId;
    private int trailerId;
    private int composerId;
    private int singerId;
    private int songId;
    private List<Integer> actorsIds;
    private List<Integer> producersIds;
    private List<Integer> soundtracksIds;
    private List<Integer> trailersIds;
    private List<Integer> composersIds;
    private List<Integer> singersIds;
    private List<Integer> songsIds;

    //region Public Methods
    public void create(MovieRequestModel movieRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            MovieResponseModel movieResponseModel = buildMovieResponseModelFrom(createMovie(movieRequestModel));
            movieResponseModel.setId(0);
            String movieInfo = movieResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), movieInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            MovieResponseModel movieResponseModel = buildMovieResponseModelFrom(createMovie(movieRequestModel));
            movieResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String movieInfo = movieResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), movieInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<MovieResponseModel> selectAllMovies() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> movieInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            movieInfo.add(stringListIterator.next().split(","));
        }

        List<String[]> actorInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            actorInfo.add((strings[strings.length - 4].substring(1, strings[strings.length - 4].length() - 1)).split("_"));
        }

        List<Actor> actors = new LinkedList<>();
        actorsIds = new LinkedList<>();
        for (String[] strings : actorInfo) {
            int id = Integer.parseInt(strings[0]);
            actorsIds.add(id);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            int age = Integer.parseInt(strings[4]);
            Profession profession = Profession.valueOf(strings[5]);
            String role = strings[6];
            actors.add(new Actor(id, createdAt, name, surname, age, profession, role));
        }

        List<String[]> producerInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            producerInfo.add((strings[strings.length - 3].substring(1, strings[strings.length - 3].length() - 1)).split("_"));
        }

        List<Producer> producers = new LinkedList<>();
        producersIds = new LinkedList<>();
        for (String[] strings : producerInfo) {
            int id = Integer.parseInt(strings[0]);
            producersIds.add(id);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            int age = Integer.parseInt(strings[4]);
            Profession profession = Profession.valueOf(strings[5]);
            producers.add(new Producer(id, createdAt, name, surname, age, profession));
        }

        List<String[]> soundtrackInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            soundtrackInfo.add((strings[strings.length - 2]).split("`"));
        }

        List<String[]> songInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            songInfo.add((strings[strings.length - 2]).split("_"));
        }

        List<String[]> composerInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            composerInfo.add((strings[strings.length - 1]).split("\\|"));
        }

        List<Composer> composers = new LinkedList<>();
        composersIds = new LinkedList<>();
        for (String[] strings : composerInfo) {
            int id = Integer.parseInt(strings[0]);
            composersIds.add(id);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MelodyGenre melodyGenre = MelodyGenre.valueOf(strings[4]);
            composers.add(new Composer(id, createdAt, name, surname, melodyGenre));
        }

        List<String[]> singerInfo = new LinkedList<>();
        for (String[] strings : songInfo) {
            singerInfo.add((strings[strings.length - 1].substring(1, strings[strings.length - 1].length() - 1)).split("\\|"));
        }

        List<Singer> singers = new LinkedList<>();
        singersIds = new LinkedList<>();
        for (String[] strings : singerInfo) {
            int id = Integer.parseInt(strings[0]);
            singersIds.add(id);
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MusicGenre musicGenre = MusicGenre.valueOf(strings[4]);
            singers.add(new Singer(id, localDateTime, name, surname, musicGenre));
        }

        List<Song> songs = new LinkedList<>();
        songsIds = new LinkedList<>();
        int i1 = 0;
        for (String[] strings : songInfo) {
            int id = Integer.parseInt(strings[0]);
            songsIds.add(id);
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String duration = strings[3];
            songs.add(new Song(id, localDateTime, name, duration, new LinkedList<>(Collections.singleton(singers.get(i1)))));
            i1++;
        }

        List<Soundtrack> soundtracks = new LinkedList<>();
        soundtracksIds = new LinkedList<>();
        int i = 0;
        for (String[] strings : soundtrackInfo) {
            int id = Integer.parseInt(strings[0]);
            soundtracksIds.add(id);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String duration = strings[2];
            soundtracks.add(new Soundtrack(id, createdAt, duration, songs.get(i), composers.get(i)));
            i++;
        }

        List<String[]> trailerInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            trailerInfo.add((strings[strings.length - 1]).split("_"));
        }

        List<Trailer> trailer = new LinkedList<>();
        trailersIds = new LinkedList<>();
        for (String[] strings : trailerInfo) {
            int id = Integer.parseInt(strings[0]);
            trailersIds.add(id);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String duration = strings[2];
            trailer.add(new Trailer(id, createdAt, duration));
        }

        List<Movie> movies = new LinkedList<>();
        int k = 0;
        for (String[] strings : movieInfo) {
            int id = Integer.parseInt(strings[0]);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String duration = strings[3];
            MovieGenre genre = MovieGenre.valueOf(strings[4]);
            double rate = Double.parseDouble(strings[5]);
            movies.add(new Movie(id, createdAt, name, duration, genre, rate, trailer.get(k), new LinkedList<>(Collections.singleton(actors.get(k))), new LinkedList<>(Collections.singleton(producers.get(k))), soundtracks.get(k)));
            k++;
        }

        List<MovieResponseModel> modelList = new LinkedList<>();
        for (Movie movie : movies) {
            modelList.add(buildMovieResponseModelFrom(movie));
        }
        return modelList;
    }

    private MovieResponseModel selectMovieById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> movieInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            movieInfo.add(stringListIterator.next().split(","));
        }

        List<String[]> actorInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            if (Integer.parseInt(strings[0]) == id)
                actorInfo.add((strings[strings.length - 4].substring(1, strings[strings.length - 4].length() - 1)).split("_"));
        }

        List<Actor> actors = new LinkedList<>();
        for (String[] strings : actorInfo) {
            int actorId = Integer.parseInt(strings[0]);
            this.actorId = actorId;
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            int age = Integer.parseInt(strings[4]);
            Profession profession = Profession.valueOf(strings[5]);
            String role = strings[6];
            actors.add(new Actor(actorId, createdAt, name, surname, age, profession, role));
        }

        List<String[]> producerInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            if (Integer.parseInt(strings[0]) == id)
                producerInfo.add((strings[strings.length - 3].substring(1, strings[strings.length - 3].length() - 1)).split("_"));
        }

        List<Producer> producers = new LinkedList<>();
        for (String[] strings : producerInfo) {
            int producerId = Integer.parseInt(strings[0]);
            this.producerId = producerId;
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            int age = Integer.parseInt(strings[4]);
            Profession profession = Profession.valueOf(strings[5]);
            producers.add(new Producer(producerId, createdAt, name, surname, age, profession));
        }

        List<String[]> soundtrackInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            if (Integer.parseInt(strings[0]) == id)
                soundtrackInfo.add((strings[strings.length - 2]).split("`"));
        }

        List<String[]> songInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            songInfo.add((strings[strings.length - 2]).split("_"));
        }

        List<String[]> composerInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            composerInfo.add((strings[strings.length - 1]).split("\\|"));
        }

        List<Composer> composers = new LinkedList<>();
        for (String[] strings : composerInfo) {
            int composerId = Integer.parseInt(strings[0]);
            this.composerId = composerId;
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MelodyGenre melodyGenre = MelodyGenre.valueOf(strings[4]);
            composers.add(new Composer(composerId, createdAt, name, surname, melodyGenre));
        }

        List<String[]> singerInfo = new LinkedList<>();
        for (String[] strings : songInfo) {
            singerInfo.add((strings[strings.length - 1].substring(1, strings[strings.length - 1].length() - 1)).split("\\|"));
        }

        List<Singer> singers = new LinkedList<>();
        for (String[] strings : singerInfo) {
            int singerId = Integer.parseInt(strings[0]);
            this.singerId = singerId;
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MusicGenre musicGenre = MusicGenre.valueOf(strings[4]);
            singers.add(new Singer(singerId, localDateTime, name, surname, musicGenre));
        }

        List<Song> songs = new LinkedList<>();
        int i1 = 0;
        for (String[] strings : songInfo) {
            int songId = Integer.parseInt(strings[0]);
            this.songId = songId;
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String duration = strings[3];
            songs.add(new Song(songId, localDateTime, name, duration, new LinkedList<>(Collections.singleton(singers.get(i1)))));
            i1++;
        }

        Soundtrack soundtrack = null;
        int i = 0;
        for (String[] strings : soundtrackInfo) {
            int soundtrackId = Integer.parseInt(strings[0]);
            this.soundtrackId = soundtrackId;
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String duration = strings[2];
            soundtrack = new Soundtrack(soundtrackId, createdAt, duration, songs.get(i), composers.get(i));
            i++;
        }

        List<String[]> trailerInfo = new LinkedList<>();
        for (String[] strings : movieInfo) {
            if (Integer.parseInt(strings[0]) == id)
                trailerInfo.add((strings[strings.length - 1]).split("_"));
        }

        Trailer trailer = null;
        for (String[] strings : trailerInfo) {
            int trailerId = Integer.parseInt(strings[0]);
            this.trailerId = trailerId;
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String duration = strings[2];
            trailer = new Trailer(trailerId, createdAt, duration);
        }

        Movie movie = new Movie();
        for (String[] strings : movieInfo) {
            if (id == Integer.parseInt(strings[0])) {
                movie.setId(Integer.parseInt(strings[0]));
                movie.setCreatedAt(LocalDateTime.parse(strings[1]));
                movie.setName(strings[2]);
                movie.setDuration(strings[3]);
                movie.setGenre(MovieGenre.valueOf(strings[4]));
                movie.setRate(Double.parseDouble(strings[5]));
                movie.setActors(actors);
                movie.setProducers(producers);
                movie.setSoundtrack(soundtrack);
                movie.setTrailer(trailer);
            }
        }
        if (id != movie.getId()) {
            throw new MovieNotFoundException(String.format("Movie not found for id - %d", id));
        } else {
            return buildMovieResponseModelFrom(movie);
        }
    }

    public void update(MovieRequestModel movieRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> movieInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            movieInfo.add(stringListIterator.next().split(","));
        }

        Movie movie = new Movie();
        for (String[] strings : movieInfo) {
            if (id == Integer.parseInt(strings[0])) {
                movie.setId(Integer.parseInt(strings[0]));
            }
        }
        if (id != movie.getId()) {
            throw new MovieNotFoundException(String.format("Movie not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                movie.setCreatedAt(now());
                movie.setName(movieRequestModel.getName());
                movie.setDuration(movieRequestModel.getDuration());
                movie.setGenre(movieRequestModel.getGenre());
                movie.setRate(movie.getRate());
                Soundtrack soundtrackById = soundtrackById(movieRequestModel.getSoundtrackId());
                movie.getActors().add(actorById(movieRequestModel.getActorId()));
                movie.getProducers().add(producerById(movieRequestModel.getProducerId()));
                Trailer trailerById = trailerById(movieRequestModel.getTrailerId());

                movie.setSoundtrack(soundtrackById);
                movie.setTrailer(trailerById);
                MovieResponseModel movieResponseModel = buildMovieResponseModelFrom(movie);
                movieInfo.remove(id);
                movieInfo.add(id, new String[]{String.valueOf(movieResponseModel.getId()), String.valueOf(movieResponseModel.getCreatedAt()), movieResponseModel.getName(), movieResponseModel.getDuration(), String.valueOf(movieResponseModel.getGenre()), String.valueOf(movieResponseModel.getRate()), String.valueOf(movieResponseModel.getActors()), String.valueOf(movieResponseModel.getProducers()), String.valueOf(movieResponseModel.getSoundtrack()), String.valueOf(movieResponseModel.getTrailer())});
                for (String[] k : movieInfo) {
                    String s = k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4] + "," + k[5] + "," + k[6] + "," + k[7] + "," + k[8] + "," + k[9] + "\n";
                    Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
                }
            }
        }
    }

    public void deleteById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> movieInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            movieInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : movieInfo) {
            if (id == Integer.parseInt(strings[0])) {
                movieInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] k : movieInfo) {
                String s = k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4] + "," + k[5] + "," + k[6] + "," + k[7] + "," + k[8] + "," + k[9] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedMovieById(int id) throws IOException {
        MovieResponseModel movie = selectMovieById(id);
        Actor actor = actorById(this.actorId);
        Producer producer = producerById(this.producerId);
        Soundtrack soundtrack = soundtrackById(this.soundtrackId);
        Trailer trailer = trailerById(this.trailerId);
        Song song = songById(this.songId);
        Singer singer = singerById(this.singerId);
        Composer composer = composerById(this.composerId);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Movie{\nid: " + movie.getId())
                .add("createdAt: " + movie.getCreatedAt())
                .add("name: " + movie.getName())
                .add("duration: " + movie.getDuration())
                .add("rate: " + movie.getDuration())
                .add("  Actor{\n   id: " + actor.getId())
                .add("   createdAt: " + actor.getCreatedAt())
                .add("   name: " + actor.getName())
                .add("   surname: " + actor.getSurname())
                .add("   age: " + actor.getAge())
                .add("   profession: " + actor.getProfession())
                .add("   role: " + actor.getRole() + "\n   }")
                .add("  Producer{\n   id: " + producer.getId())
                .add("   createdAt: " + producer.getCreatedAt())
                .add("   name: " + producer.getName())
                .add("   surname: " + producer.getSurname())
                .add("   age: " + producer.getAge())
                .add("   profession: " + producer.getProfession() + "\n   }")
                .add("  Soundtrack{\n   id: " + soundtrack.getId())
                .add("   createdAt: " + soundtrack.getCreatedAt())
                .add("   duration: " + soundtrack.getDuration())
                .add("      Song{\n       id: " + song.getId())
                .add("       createdAt: " + song.getCreatedAt())
                .add("       name: " + song.getName())
                .add("       duration: " + song.getDuration())
                .add("         Singer{\n          id: " + singer.getId())
                .add("          createdAt: " + singer.getCreatedAt())
                .add("          name: " + singer.getName())
                .add("          surname: " + singer.getSurname())
                .add("          MusicGenre: " + singer.getMusicGenre() + "\n          } \n       }")
                .add("         Composer{\n          id: " + composer.getId())
                .add("          createdAt: " + composer.getCreatedAt())
                .add("          name: " + composer.getName())
                .add("          surname: " + composer.getSurname())
                .add("          MelodyGenre: " + composer.getMelodyGenre() + "\n          } \n   }")
                .add("  Trailer{\n   id: " + trailer.getId())
                .add("   createdAt: " + trailer.getCreatedAt())
                .add("   duration: " + trailer.getDuration() + "\n   } \n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllMovies() throws IOException {
        List<MovieResponseModel> movies = selectAllMovies();
        int i = 0;
        for (MovieResponseModel movie : movies) {
            Actor actor = actorById(this.actorsIds.get(i));
            Producer producer = producerById(this.producersIds.get(i));
            Soundtrack soundtrack = soundtrackById(this.soundtracksIds.get(i));
            Trailer trailer = trailerById(this.trailersIds.get(i));
            Song song = songById(this.songsIds.get(i));
            Singer singer = singerById(this.singersIds.get(i));
            Composer composer = composerById(this.composersIds.get(i));
            System.out.println("Movie{\nid: " + movie.getId() + ",\ncreatedAt: " + movie.getCreatedAt() + ",\nname: " + movie.getName() +
                    ",\nduration: " + movie.getDuration() + ",\nrate: " + movie.getDuration() + ",\n  Actor{\n   id: " + actor.getId()
                    + ",\n   createdAt: " + actor.getCreatedAt() + ",\n   name: " + actor.getName() + ",\n   surname: " + actor.getSurname()
                    + ",\n   age: " + actor.getAge() + ",\n   profession: " + actor.getProfession() + ",\n   role: " + actor.getRole() + "\n   }"
                    + ",\n  Producer{\n   id: " + producer.getId() + ",\n   createdAt: " + producer.getCreatedAt() + ",\n   name: " + producer.getName()
                    + ",\n   surname: " + producer.getSurname() + ",\n   age: " + producer.getAge() + ",\n   profession: " + producer.getProfession() + "\n   }"
                    + ",\n  Soundtrack{\n   id: " + soundtrack.getId() + ",\n   createdAt: " + soundtrack.getCreatedAt() + ",\n   duration: " + soundtrack.getDuration()
                    + ",\n      Song{\n       id: " + song.getId() + ",\n       createdAt: " + song.getCreatedAt() + ",\n       name: " + song.getName()
                    + ",\n       duration: " + song.getDuration() + ",\n         Singer{\n          id: " + singer.getId() + ",\n          createdAt: " + singer.getCreatedAt()
                    + ",\n          name: " + singer.getName() + ",\n          surname: " + singer.getSurname() + ",\n          MusicGenre: " + singer.getMusicGenre() + "\n          } \n       }"
                    + ",\n         Composer{\n          id: " + composer.getId() + ",\n          createdAt: " + composer.getCreatedAt() + ",\n          name: " + composer.getName()
                    + ",\n          surname: " + composer.getSurname() + ",\n          MelodyGenre: " + composer.getMelodyGenre() + "\n          } \n   }"
                    + ",\n  Trailer{\n   id: " + trailer.getId() + ",\n   createdAt: " + trailer.getCreatedAt() + ",\n   duration: " + trailer.getDuration() + "\n   } \n}" + "\n");
            i++;
        }
    }
    //endregion

    //region Private Methods
    private Movie buildMovieFrom(MovieRequestModel movieRequestModel) {
        Movie movie = new Movie();
        movie.setName(movieRequestModel.getName());
        movie.setDuration(movieRequestModel.getDuration());
        movie.setGenre(movieRequestModel.getGenre());
        movie.setRate(movieRequestModel.getRate());
        return movie;
    }

    private MovieResponseModel buildMovieResponseModelFrom(Movie movie) {
        List<Actor> actors = movie.getActors();
        List<Producer> producers = movie.getProducers();

        SongResponseModel songResponseModel = new SongResponseModel();
        songResponseModel.setId(movie.getSoundtrack().getSong().getId());
        songResponseModel.setCreatedAt(movie.getSoundtrack().getSong().getCreatedAt());
        songResponseModel.setName(movie.getSoundtrack().getSong().getName());
        songResponseModel.setDuration(movie.getSoundtrack().getSong().getDuration());
        Song song = movie.getSoundtrack().getSong();

        List<Singer> singers = song.getSinger();
        songResponseModel.setSinger(singers);

        ComposerResponseModel composerResponseModel = new ComposerResponseModel();
        composerResponseModel.setId(movie.getSoundtrack().getComposer().getId());
        composerResponseModel.setCreatedAt(movie.getSoundtrack().getComposer().getCreatedAt());
        composerResponseModel.setName(movie.getSoundtrack().getComposer().getName());
        composerResponseModel.setSurname(movie.getSoundtrack().getComposer().getSurname());
        composerResponseModel.setMelodyGenre(movie.getSoundtrack().getComposer().getMelodyGenre());

        SoundtrackResponseModel soundtrackResponseModel = new SoundtrackResponseModel();
        soundtrackResponseModel.setId(movie.getSoundtrack().getId());
        soundtrackResponseModel.setCreatedAt(movie.getSoundtrack().getCreatedAt());
        soundtrackResponseModel.setDuration(movie.getSoundtrack().getDuration());
        soundtrackResponseModel.setSong(songResponseModel);
        soundtrackResponseModel.setComposer(composerResponseModel);

        TrailerResponseModel trailerResponseModel = new TrailerResponseModel();
        trailerResponseModel.setId(movie.getTrailer().getId());
        trailerResponseModel.setCreatedAt(movie.getTrailer().getCreatedAt());
        trailerResponseModel.setDuration(movie.getTrailer().getDuration());

        MovieResponseModel movieResponseModel = new MovieResponseModel();
        movieResponseModel.setId(movie.getId());
        movieResponseModel.setCreatedAt(now());
        movieResponseModel.setName(movie.getName());
        movieResponseModel.setDuration(movie.getDuration());
        movieResponseModel.setGenre(movie.getGenre());
        movieResponseModel.setRate(movie.getRate());
        movieResponseModel.setActors(actors);
        movieResponseModel.setProducers(producers);
        movieResponseModel.setSoundtrack(soundtrackResponseModel);
        movieResponseModel.setTrailer(trailerResponseModel);
        return movieResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(MovieService.FILE));
    }

    private Producer producerById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(ProducerService.FILE)).listIterator();
        List<String[]> producerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            producerInfo.add(stringListIterator.next().split(","));
        }

        Producer producer = new Producer();
        for (String[] strings : producerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                producer.setId(Integer.parseInt(strings[0]));
                producer.setCreatedAt(LocalDateTime.parse(strings[1]));
                producer.setName(strings[2]);
                producer.setSurname(strings[3]);
                producer.setAge(Integer.parseInt(strings[4]));
                producer.setProfession(Profession.valueOf(strings[5]));
                break;
            }
        }
        if (id != producer.getId()) {
            throw new ProducerNotFoundException(String.format("Producer not found for id - %d", id));
        } else {
            return producer;
        }
    }

    private Actor actorById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(ActorService.FILE)).listIterator();
        List<String[]> actorInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            actorInfo.add(stringListIterator.next().split(","));
        }

        Actor actor = new Actor();
        for (String[] strings : actorInfo) {
            if (id == Integer.parseInt(strings[0])) {
                actor.setId(Integer.parseInt(strings[0]));
                actor.setCreatedAt(LocalDateTime.parse(strings[1]));
                actor.setName(strings[2]);
                actor.setSurname(strings[3]);
                actor.setAge(Integer.parseInt(strings[4]));
                actor.setProfession(Profession.valueOf(strings[5]));
                actor.setRole(strings[6]);
                break;
            }
        }
        if (id != actor.getId()) {
            throw new ActorNotFoundException(String.format("Actor not found for id - %d", id));
        } else {
            return actor;
        }
    }

    private Soundtrack soundtrackById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(SoundtrackService.FILE)).listIterator();
        List<String[]> soundtrackInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            soundtrackInfo.add(stringListIterator.next().split(","));
        }

        List<String> songString = new LinkedList<>();
        List<String[]> songInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            if (Integer.parseInt(strings[0]) == id)
                songString.add(strings[strings.length - 2]);
        }

        for (int i = 0; i < 1; i++) {
            songInfo.add(songString.get(i).split("_"));
        }

        List<String> composerString = new LinkedList<>();
        List<String[]> composerInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            if (Integer.parseInt(strings[0]) == id)
                composerString.add(strings[strings.length - 1]);
        }

        for (int i = 0; i < 1; i++) {
            composerInfo.add(composerString.get(i).split("\\|"));
        }

        Composer composer = null;
        for (String[] strings : composerInfo) {
            int composerId = Integer.parseInt(strings[0]);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MelodyGenre melodyGenre = MelodyGenre.valueOf(strings[4]);
            composer = new Composer(composerId, createdAt, name, surname, melodyGenre);
        }

        List<String> singerString = new LinkedList<>();
        List<String[]> singerInfo = new LinkedList<>();

        for (String[] strings : songInfo) {
            singerString.add(strings[strings.length - 1].substring(1, strings[strings.length - 1].length() - 1));
        }

        for (int i = 0; i < 1; i++) {
            singerInfo.add(singerString.get(i).split("\\|"));
        }

        List<Singer> singers = new LinkedList<>();

        for (String[] strings : singerInfo) {
            int singerId = Integer.parseInt(strings[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MusicGenre musicGenre = MusicGenre.valueOf(strings[4]);
            singers.add(new Singer(singerId, localDateTime, name, surname, musicGenre));
        }

        Song song = null;
        for (String[] strings : songInfo) {
            int songId = Integer.parseInt(strings[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String duration = strings[3];
            song = new Song(songId, localDateTime, name, duration, singers);
        }

        Soundtrack soundtrack = new Soundtrack();
        for (String[] strings : soundtrackInfo) {
            if (id == Integer.parseInt(strings[0])) {
                soundtrack.setId(Integer.parseInt(strings[0]));
                soundtrack.setCreatedAt(LocalDateTime.parse(strings[1]));
                soundtrack.setDuration(strings[2]);
                soundtrack.setComposer(composer);
                soundtrack.setSong(song);
                break;
            }
        }
        if (id != soundtrack.getId()) {
            throw new SoundtrackNotFoundException(String.format("Song not found for id - %d", id));
        } else {
            return soundtrack;
        }
    }

    private Composer composerById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(ComposerService.FILE)).listIterator();
        List<String[]> composerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            composerInfo.add(stringListIterator.next().split(","));
        }

        Composer composer = new Composer();
        for (String[] strings : composerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                composer.setId(Integer.parseInt(strings[0]));
                composer.setCreatedAt(LocalDateTime.parse(strings[1]));
                composer.setName(strings[2]);
                composer.setSurname(strings[3]);
                composer.setMelodyGenre(MelodyGenre.valueOf(strings[4]));
            }
        }
        if (id != composer.getId()) {
            throw new ComposerNotFoundException(String.format("Composer not found for id - %d", id));
        } else {
            return composer;
        }
    }

    public Song songById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(SongService.FILE)).listIterator();
        List<String[]> songInfo = new LinkedList<>();
        int count = 0;
        while (stringListIterator.hasNext()) {
            songInfo.add(stringListIterator.next().split(","));
            count++;
        }

        List<String> strings = new LinkedList<>();
        List<String[]> singerInfos = new LinkedList<>();
        for (String[] strings1 : songInfo) {
            if (Integer.parseInt(strings1[0]) == id)
                strings.add(strings1[strings1.length - 1].substring(1, strings1[strings1.length - 1].length() - 1));
        }

        for (int i = 0; i < 1; i++) {
            singerInfos.add(strings.get(i).split("\\|"));
        }

        List<Singer> singers = new LinkedList<>();

        Singer singer = null;
        for (String[] singerInfo : singerInfos) {
            int singerId = Integer.parseInt(singerInfo[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(singerInfo[1]);
            String name = singerInfo[2];
            String surname = singerInfo[3];
            MusicGenre musicGenre = MusicGenre.valueOf(singerInfo[4]);
            singer = new Singer(singerId, localDateTime, name, surname, musicGenre);
        }
        singers.add(singer);

        Song song = new Song();
        for (String[] sonfInf : songInfo) {
            if (id == Integer.parseInt(sonfInf[0])) {
                song.setId(Integer.parseInt(sonfInf[0]));
                song.setCreatedAt(LocalDateTime.parse(sonfInf[1]));
                song.setName(sonfInf[2]);
                song.setDuration(sonfInf[3]);
                song.setSinger(singers);
            }
        }
        if (id != song.getId()) {
            throw new SongNotFoundException(String.format("Song not found for id - %d", id));
        } else {
            return song;
        }
    }

    private Trailer trailerById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(TrailerService.FILE)).listIterator();
        List<String[]> trailerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            trailerInfo.add(stringListIterator.next().split(","));
        }

        Trailer trailer = new Trailer();
        for (String[] strings : trailerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                trailer.setId(Integer.parseInt(strings[0]));
                trailer.setCreatedAt(LocalDateTime.parse(strings[1]));
                trailer.setDuration(strings[2]);
                break;
            }
        }
        if (id != trailer.getId()) {
            throw new TrailerNotFoundException(String.format("Trailer not found for id - %d", id));
        } else {
            return trailer;
        }
    }

    private Singer singerById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(SingerService.FILE)).listIterator();
        List<String[]> singerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            singerInfo.add(stringListIterator.next().split(","));
        }

        Singer singer = new Singer();
        for (String[] strings : singerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                singer.setId(Integer.parseInt(strings[0]));
                singer.setCreatedAt(LocalDateTime.parse(strings[1]));
                singer.setName(strings[2]);
                singer.setSurname(strings[3]);
                singer.setMusicGenre(MusicGenre.valueOf(strings[4]));
                break;
            }
        }
        if (id != singer.getId()) {
            throw new SingerNotFoundException(String.format("Singer not found for id - %d", id));
        } else {
            return singer;
        }
    }

    private Movie createMovie(MovieRequestModel movieRequestModel) throws IOException {
        Movie movie = buildMovieFrom(movieRequestModel);
        Soundtrack soundtrackById = soundtrackById(movieRequestModel.getSoundtrackId());
        Trailer trailerById = trailerById(movieRequestModel.getTrailerId());
        movie.getActors().add(actorById(movieRequestModel.getActorId()));
        movie.getProducers().add(producerById(movieRequestModel.getProducerId()));
        movie.setSoundtrack(soundtrackById);
        movie.setTrailer(trailerById);
        return movie;
    }
    //endregion
}