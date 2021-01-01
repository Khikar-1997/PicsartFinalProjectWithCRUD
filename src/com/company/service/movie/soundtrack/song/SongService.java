package com.company.service.movie.soundtrack.song;

import com.company.controller.model.movie.soundtrack.song.SongRequestModel;
import com.company.controller.model.movie.soundtrack.song.SongResponseModel;
import com.company.exceptions.SingerNotFoundException;
import com.company.exceptions.SongNotFoundException;
import com.company.persistance.model.movie.soundtrack.music.MusicGenre;
import com.company.persistance.model.movie.soundtrack.personal.singer.Singer;
import com.company.persistance.model.movie.soundtrack.song.Song;
import com.company.service.movie.soundtrack.personal.SingerService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;

public class SongService {
    public static final String FILE = "song.txt";
    private int id;
    private List<Integer> ids;

    //region Public Methods
    public void create(SongRequestModel songRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            Song song = buildSongFrom(songRequestModel);
            song.getSinger().add(singerById(songRequestModel.getSingerId()));
            SongResponseModel songResponseModel = buildSongResponseModelFrom(song);
            songResponseModel.setId(0);
            String songInfo = songResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), songInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            Song song = buildSongFrom(songRequestModel);
            song.getSinger().add(singerById(songRequestModel.getSingerId()));
            SongResponseModel songResponseModel = buildSongResponseModelFrom(song);
            songResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String songInfo = songResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), songInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<SongResponseModel> selectAllSongs() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> songInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            songInfo.add(stringListIterator.next().split(","));
        }

        List<String[]> singerInfo = new LinkedList<>();
        for (String[] strings1 : songInfo) {
            singerInfo.add((strings1[strings1.length - 1].substring(1, strings1[strings1.length - 1].length() - 1)).split("\\|"));
        }

        List<Singer> singers = new LinkedList<>();
        ids = new LinkedList<>();
        for (String[] strings : singerInfo) {
            int id = Integer.parseInt(strings[0]);
            ids.add(id);
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MusicGenre musicGenre = MusicGenre.valueOf(strings[4]);
            singers.add(new Singer(id, localDateTime, name, surname, musicGenre));
        }

        List<Song> songs = new LinkedList<>();
        int i = 0;
        for (String[] value : songInfo) {
            int id = Integer.parseInt(value[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(value[1]);
            String name = value[2];
            String duration = value[3];
            if (id == i) {
                songs.add(new Song(id, localDateTime, name, duration, new LinkedList<>(Collections.singleton(singers.get(i)))));
            }
            i++;
        }

        List<SongResponseModel> modelList = new LinkedList<>();
        for (Song song : songs) {
            modelList.add(buildSongResponseModelFrom(song));
        }
        return modelList;
    }

    private SongResponseModel selectSongById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> songInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            songInfo.add(stringListIterator.next().split(","));
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
            this.id = singerId;
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
                break;
            }
        }
        if (id != song.getId()) {
            throw new SongNotFoundException(String.format("Song not found for id - %d", id));
        } else {
            return buildSongResponseModelFrom(song);
        }
    }

    public void update(SongRequestModel songRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> songInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            songInfo.add(stringListIterator.next().split(","));
        }
        Song song = new Song();
        for (String[] sonfInf : songInfo) {
            if (id == Integer.parseInt(sonfInf[0])) {
                song.setId(Integer.parseInt(sonfInf[0]));
            }
        }
        if (id != song.getId()) {
            throw new SongNotFoundException(String.format("Song not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                Song updateSong = new Song();
                updateSong.setCreatedAt(now());
                updateSong.setName(songRequestModel.getName());
                updateSong.setDuration(songRequestModel.getDuration());
                updateSong.getSinger().add(singerById(songRequestModel.getSingerId()));
                SongResponseModel songResponseModel = buildSongResponseModelFrom(updateSong);
                songInfo.remove(id);
                songInfo.add(id, new String[]{String.valueOf(songResponseModel.getId()), String.valueOf(songResponseModel.getCreatedAt()), songResponseModel.getName(), songResponseModel.getDuration(), String.valueOf(songResponseModel.getSinger())});
                for (String[] k : songInfo) {
                    String s = k[0] + "," + k[1] + "," + k[2] + "," + k[3] + "," + k[4] + "\n";
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
        List<String[]> songInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            songInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : songInfo) {
            if (id == Integer.parseInt(strings[0])) {
                songInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : songInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedSongById(int id) throws IOException {
        SongResponseModel song = selectSongById(id);
        Singer singer = singerById(this.id);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Song{\nid: " + song.getId())
                .add("createdAt: " + song.getCreatedAt())
                .add("name: " + song.getName())
                .add("duration: " + song.getDuration())
                .add("   Singer{\n    id: " + singer.getId())
                .add("createdAt: " + singer.getCreatedAt())
                .add("name: " + singer.getName())
                .add("surname: " + singer.getSurname())
                .add("MusicGenre: " + singer.getMusicGenre() + "\n    }\n }");
        System.out.println(stringJoiner.toString());
    }

    public void printAllSongs() throws IOException {
        List<SongResponseModel> songs = selectAllSongs();
        int i = 0;
        for (SongResponseModel song : songs) {
            Singer singer = singerById(ids.get(i));
            System.out.println("Song{\nid: " + song.getId() + ",\ncreatedAt: " + song.getCreatedAt() + ",\nname: " + song.getName() +
                    ",\nduration: " + song.getDuration() + ",\n   Singer{id: " + singer.getId() + ",\n    createdAt: " + singer.getCreatedAt()
                    + ",\n    name: " + singer.getName() + ",\n    surname: " + singer.getSurname() + ",\n    MusicGenre: " + singer.getMusicGenre() + "\n    }\n }");
            i++;
        }
    }
    //endregion

    //region Private Methods
    private Song buildSongFrom(SongRequestModel songRequestModel) {
        Song song = new Song();
        song.setName(songRequestModel.getName());
        song.setDuration(songRequestModel.getDuration());
        return song;
    }

    private SongResponseModel buildSongResponseModelFrom(Song song) {
        SongResponseModel songResponseModel = new SongResponseModel();
        songResponseModel.setId(song.getId());
        songResponseModel.setCreatedAt(now());
        songResponseModel.setName(song.getName());
        songResponseModel.setDuration(song.getDuration());
        List<Singer> singers = song.getSinger();
        songResponseModel.setSinger(singers);
        return songResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(SongService.FILE));
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
            }
        }
        if (id != singer.getId()) {
            throw new SingerNotFoundException(String.format("Singer not found for id - %d", id));
        } else {
            return singer;
        }
    }
    //endregion
}