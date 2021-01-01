package com.company.service.movie.soundtrack.personal;

import com.company.controller.model.movie.soundtrack.personal.singer.SingerRequestModel;
import com.company.controller.model.movie.soundtrack.personal.singer.SingerResponseModel;
import com.company.exceptions.SingerNotFoundException;
import com.company.persistance.model.movie.soundtrack.music.MusicGenre;
import com.company.persistance.model.movie.soundtrack.personal.singer.Singer;

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

public class SingerService {
    public static final String FILE = "singer.txt";

    //region Public Methods
    public void create(SingerRequestModel singerRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            Singer singer = buildSingerFrom(singerRequestModel);
            SingerResponseModel singerResponseModel = buildSingerResponseModelFrom(singer);
            singerResponseModel.setId(0);
            String singerInfo = singerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), singerInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            Singer singer = buildSingerFrom(singerRequestModel);
            SingerResponseModel singerResponseModel = buildSingerResponseModelFrom(singer);
            singerResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String singerInfo = singerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), singerInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<SingerResponseModel> selectAllSingers() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> singerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            singerInfo.add(stringListIterator.next().split(","));
        }
        List<Singer> singers = new LinkedList<>();

        for (String[] value : singerInfo) {
            int id = Integer.parseInt(value[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(value[1]);
            String name = value[2];
            String surname = value[3];
            MusicGenre musicGenre = MusicGenre.valueOf(value[4]);
            singers.add(new Singer(id, localDateTime, name, surname, musicGenre));
        }

        List<SingerResponseModel> modelList = new LinkedList<>();
        for (Singer singer : singers) {
            modelList.add(buildSingerResponseModelFrom(singer));
        }
        return modelList;
    }

    private SingerResponseModel selectSingerById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
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
            return buildSingerResponseModelFrom(singer);
        }
    }

    public void update(SingerRequestModel singerRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> singerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            singerInfo.add(stringListIterator.next().split(","));
        }

        Singer singer = new Singer();
        for (String[] strings : singerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                singer.setId(Integer.parseInt(strings[0]));
                break;
            }
        }

        if (id != singer.getId()) {
            throw new SingerNotFoundException(String.format("Singer not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                singer.setCreatedAt(now());
                singer.setName(singerRequestModel.getName());
                singer.setSurname(singerRequestModel.getSurname());
                singer.setMusicGenre(singerRequestModel.getMusicGenre());
                SingerResponseModel singerResponseModel = buildSingerResponseModelFrom(singer);
                singerInfo.remove(id);
                singerInfo.add(id, new String[]{String.valueOf(singerResponseModel.getId()), String.valueOf(singerResponseModel.getCreatedAt()), singerResponseModel.getName(), singerResponseModel.getSurname(), String.valueOf(singerResponseModel.getMusicGenre())});
                for (String[] strings : singerInfo) {
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
        List<String[]> singerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            singerInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : singerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                singerInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : singerInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedSingerById(int id) throws IOException {
        SingerResponseModel singer = selectSingerById(id);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Singer{\nid: " + singer.getId())
                .add("createdAt: " + singer.getCreatedAt())
                .add("name: " + singer.getName())
                .add("surname: " + singer.getSurname())
                .add("MusicGenre: " + singer.getMusicGenre() + "\n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllSingers() throws IOException {
        List<SingerResponseModel> singers = selectAllSingers();
        for (SingerResponseModel singer : singers) {
            System.out.println("Singer{\nid: " + singer.getId() + ",\ncreatedAt: " + singer.getCreatedAt() + ",\nname: " + singer.getName() +
                    ",\nsurname: " + singer.getSurname() + ",\nMusicGenre: " + singer.getMusicGenre() + "\n}");
        }
    }
    //endregion

    //region Private Methods
    private Singer buildSingerFrom(SingerRequestModel singerRequestModel) {
        Singer singer = new Singer();
        singer.setName(singerRequestModel.getName());
        singer.setSurname(singerRequestModel.getSurname());
        singer.setMusicGenre(singerRequestModel.getMusicGenre());
        return singer;
    }

    private SingerResponseModel buildSingerResponseModelFrom(Singer singer) {
        SingerResponseModel singerResponseModel = new SingerResponseModel();
        singerResponseModel.setId(singer.getId());
        singerResponseModel.setCreatedAt(now());
        singerResponseModel.setName(singer.getName());
        singerResponseModel.setSurname(singer.getSurname());
        singerResponseModel.setMusicGenre(singer.getMusicGenre());
        return singerResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(SingerService.FILE));
    }
    //endregion
}