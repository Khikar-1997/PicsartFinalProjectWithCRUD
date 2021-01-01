package com.company.service.movie.soundtrack.personal;

import com.company.controller.model.movie.soundtrack.personal.composer.ComposerRequestModel;
import com.company.controller.model.movie.soundtrack.personal.composer.ComposerResponseModel;
import com.company.exceptions.ComposerNotFoundException;
import com.company.persistance.model.movie.soundtrack.music.MelodyGenre;
import com.company.persistance.model.movie.soundtrack.personal.composer.Composer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringJoiner;

public class ComposerService {
    public static final String FILE = "composer.txt";

    //region Public Methods
    public void create(ComposerRequestModel composerRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            Composer composer = buildComposerFrom(composerRequestModel);
            ComposerResponseModel composerResponseModel = buildComposerResponseModelFrom(composer);
            composerResponseModel.setId(0);
            String composerInfo = composerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), composerInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            Composer composer = buildComposerFrom(composerRequestModel);
            ComposerResponseModel composerResponseModel = buildComposerResponseModelFrom(composer);
            composerResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String composerInfo = composerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), composerInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<ComposerResponseModel> selectAllComposers() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> composerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            composerInfo.add(stringListIterator.next().split(","));
        }
        List<Composer> composers = new LinkedList<>();

        for (String[] value : composerInfo) {
            int id = Integer.parseInt(value[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(value[1]);
            String name = value[2];
            String surname = value[3];
            MelodyGenre melodyGenre = MelodyGenre.valueOf(value[4]);
            composers.add(new Composer(id, localDateTime, name, surname, melodyGenre));
        }

        List<ComposerResponseModel> modelList = new LinkedList<>();
        for (Composer composer : composers) {
            modelList.add(buildComposerResponseModelFrom(composer));
        }
        return modelList;
    }

    private ComposerResponseModel selectComposerById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
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
                break;
            }
        }
        if (id != composer.getId()) {
            throw new ComposerNotFoundException(String.format("Composer not found for id - %d", id));
        } else {
            return buildComposerResponseModelFrom(composer);
        }
    }

    public void update(ComposerRequestModel composerRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> composerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            composerInfo.add(stringListIterator.next().split(","));
        }

        Composer composer = new Composer();
        for (String[] strings : composerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                composer.setId(Integer.parseInt(strings[0]));
            }
        }

        if (id != composer.getId()) {
            throw new ComposerNotFoundException(String.format("Composer not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                composer.setCreatedAt(now());
                composer.setName(composerRequestModel.getName());
                composer.setSurname(composerRequestModel.getSurname());
                composer.setMelodyGenre(composerRequestModel.getMelodyGenre());
                ComposerResponseModel composerResponseModel = buildComposerResponseModelFrom(composer);
                composerInfo.remove(id);
                composerInfo.add(id, new String[]{String.valueOf(composerResponseModel.getId()), String.valueOf(composerResponseModel.getCreatedAt()), composerResponseModel.getName(), composerResponseModel.getSurname(), String.valueOf(composerResponseModel.getMelodyGenre())});
                for (String[] strings : composerInfo) {
                    String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "\n";
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
        List<String[]> composerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            composerInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : composerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                composerInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : composerInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedComposerById(int id) throws IOException {
        ComposerResponseModel composer = selectComposerById(id);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Composer{\nid: " + composer.getId())
                .add("createdAt: " + composer.getCreatedAt())
                .add("name: " + composer.getName())
                .add("surname: " + composer.getSurname())
                .add("MelodyGenre: " + composer.getMelodyGenre() + "\n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllComposers() throws IOException {
        List<ComposerResponseModel> composers = selectAllComposers();
        for (ComposerResponseModel composer : composers) {
            System.out.println("Composer{\nid: " + composer.getId() + ",\ncreatedAt: " + composer.getCreatedAt() + ",\nname: " + composer.getName() +
                    ",\nsurname: " + composer.getSurname() + ",\nMelodyGenre: " + composer.getMelodyGenre() + "\n}");
        }
    }
    //endregion

    //region Private Methods
    private Composer buildComposerFrom(ComposerRequestModel composerRequestModel) {
        Composer composer = new Composer();
        composer.setName(composerRequestModel.getName());
        composer.setSurname(composerRequestModel.getSurname());
        composer.setMelodyGenre(composerRequestModel.getMelodyGenre());
        return composer;
    }

    private ComposerResponseModel buildComposerResponseModelFrom(Composer composer) {
        ComposerResponseModel composerResponseModel = new ComposerResponseModel();
        composerResponseModel.setId(composer.getId());
        composerResponseModel.setCreatedAt(now());
        composerResponseModel.setName(composer.getName());
        composerResponseModel.setSurname(composer.getSurname());
        composerResponseModel.setMelodyGenre(composer.getMelodyGenre());
        return composerResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(ComposerService.FILE));
    }
    //endregion
}