package com.company.service.movie.soundtrack.music;

import com.company.controller.model.movie.soundtrack.music.SoundtrackRequestModel;
import com.company.controller.model.movie.soundtrack.music.SoundtrackResponseModel;
import com.company.controller.model.movie.soundtrack.personal.composer.ComposerResponseModel;
import com.company.controller.model.movie.soundtrack.song.SongResponseModel;
import com.company.exceptions.ComposerNotFoundException;
import com.company.exceptions.SingerNotFoundException;
import com.company.exceptions.SongNotFoundException;
import com.company.exceptions.SoundtrackNotFoundException;
import com.company.persistance.model.movie.soundtrack.music.MelodyGenre;
import com.company.persistance.model.movie.soundtrack.music.MusicGenre;
import com.company.persistance.model.movie.soundtrack.music.Soundtrack;
import com.company.persistance.model.movie.soundtrack.personal.composer.Composer;
import com.company.persistance.model.movie.soundtrack.personal.singer.Singer;
import com.company.persistance.model.movie.soundtrack.song.Song;
import com.company.service.movie.soundtrack.personal.ComposerService;
import com.company.service.movie.soundtrack.personal.SingerService;
import com.company.service.movie.soundtrack.song.SongService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;

public class SoundtrackService {
    public static final String FILE = "soundtrack.txt";
    private int songId;
    private int singerId;
    private int composerId;
    private List<Integer> songsIds;
    private List<Integer> singersIds;
    private List<Integer> composersIds;

    //region Public Methods
    public void create(SoundtrackRequestModel soundtrackRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            Soundtrack soundtrack = buildSoundtrackFrom(soundtrackRequestModel);
            Song songById = songById(soundtrackRequestModel.getSongId());
            Composer composerById = composerById(soundtrackRequestModel.getComposerId());
            soundtrack.setSong(songById);
            soundtrack.setComposer(composerById);
            SoundtrackResponseModel soundtrackResponseModel = buildSoundtrackResponseModelFrom(soundtrack);
            soundtrackResponseModel.setId(0);
            String soundtrackInfo = soundtrackResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), soundtrackInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            Soundtrack soundtrack = buildSoundtrackFrom(soundtrackRequestModel);
            Song songById = songById(soundtrackRequestModel.getSongId());
            Composer composerById = composerById(soundtrackRequestModel.getComposerId());
            soundtrack.setSong(songById);
            soundtrack.setComposer(composerById);
            SoundtrackResponseModel soundtrackResponseModel = buildSoundtrackResponseModelFrom(soundtrack);
            soundtrackResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String soundtrackInfo = soundtrackResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), soundtrackInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    public List<SoundtrackResponseModel> selectAllSoundtracks() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> soundtrackInfo = new LinkedList<>();
        int count = 0;
        while (stringListIterator.hasNext()) {
            soundtrackInfo.add(stringListIterator.next().split(","));
            count++;
        }

        List<String> songString = new LinkedList<>();
        List<String[]> songInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            songString.add(strings[strings.length - 2]);
        }

        for (int i = 0; i < count; i++) {
            songInfo.add(songString.get(i).split("_"));
        }

        List<String> composerString = new LinkedList<>();
        List<String[]> composerInfo = new LinkedList<>();
        for (String[] strings : soundtrackInfo) {
            composerString.add(strings[strings.length - 1]);
        }

        for (int i = 0; i < count; i++) {
            composerInfo.add(composerString.get(i).split("\\|"));
        }

        List<Composer> composers = new LinkedList<>();
        composersIds = new LinkedList<>();
        Composer composer = null;
        for (String[] strings : composerInfo) {
            int id = Integer.parseInt(strings[0]);
            composersIds.add(id);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MelodyGenre melodyGenre = MelodyGenre.valueOf(strings[4]);
            composer = new Composer(id, createdAt, name, surname, melodyGenre);
            composers.add(composer);
        }

        List<String> singerString = new LinkedList<>();
        List<String[]> singerInfo = new LinkedList<>();

        for (String[] strings : songInfo) {
            singerString.add(strings[strings.length - 1].substring(1, strings[strings.length - 1].length() - 1));
        }

        for (int i = 0; i < count; i++) {
            singerInfo.add(singerString.get(i).split("\\|"));
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
        int i = 0;
        for (String[] strings : soundtrackInfo) {
            int id = Integer.parseInt(strings[0]);
            LocalDateTime createdAt = LocalDateTime.parse(strings[1]);
            String duration = strings[2];
            soundtracks.add(new Soundtrack(id, createdAt, duration, songs.get(i), composers.get(i)));
            i++;
        }

        List<SoundtrackResponseModel> modelList = new LinkedList<>();
        for (Soundtrack soundtrack : soundtracks) {
            modelList.add(buildSoundtrackResponseModelFrom(soundtrack));
        }
        return modelList;
    }

    public SoundtrackResponseModel selectSoundtrackById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
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
            this.composerId = composerId;
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
            this.singerId = singerId;
            LocalDateTime localDateTime = LocalDateTime.parse(strings[1]);
            String name = strings[2];
            String surname = strings[3];
            MusicGenre musicGenre = MusicGenre.valueOf(strings[4]);
            singers.add(new Singer(singerId, localDateTime, name, surname, musicGenre));
        }

        Song song = null;
        for (String[] strings : songInfo) {
            int songId = Integer.parseInt(strings[0]);
            this.songId = songId;
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
            throw new SoundtrackNotFoundException(String.format("Soundtrack not found for id - %d", id));
        } else {
            return buildSoundtrackResponseModelFrom(soundtrack);
        }
    }

    public void update(SoundtrackRequestModel soundtrackRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> soundtrackInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            soundtrackInfo.add(stringListIterator.next().split(","));
        }
        Soundtrack soundtrack = new Soundtrack();
        for (String[] strings : soundtrackInfo) {
            if (id == Integer.parseInt(strings[0])) {
                soundtrack.setId(Integer.parseInt(strings[0]));
                break;
            }
        }
        if (id != soundtrack.getId()) {
            throw new SoundtrackNotFoundException(String.format("Soundtrack not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                soundtrack.setCreatedAt(now());
                soundtrack.setDuration(soundtrackRequestModel.getDuration());
                Song songById = songById(soundtrackRequestModel.getSongId());
                Composer composerById = composerById(soundtrackRequestModel.getComposerId());
                soundtrack.setSong(songById);
                soundtrack.setComposer(composerById);
                SoundtrackResponseModel soundtrackResponseModel = buildSoundtrackResponseModelFrom(soundtrack);
                soundtrackInfo.remove(id);
                soundtrackInfo.add(id, new String[]{String.valueOf(soundtrackResponseModel.getId()), String.valueOf(soundtrackResponseModel.getCreatedAt()), soundtrackResponseModel.getDuration(), String.valueOf(soundtrackResponseModel.getSong()), String.valueOf(soundtrackResponseModel.getComposer())});
                for (String[] k : soundtrackInfo) {
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
        List<String[]> soundtrackInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            soundtrackInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : soundtrackInfo) {
            if (id == Integer.parseInt(strings[0])) {
                soundtrackInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : soundtrackInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedSoundtrackById(int id) throws IOException {
        SoundtrackResponseModel soundtrack = selectSoundtrackById(id);
        Composer composer = composerById(this.composerId);
        Song song = songById(this.songId);
        Singer singer = singerById(this.singerId);

        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Soundtrack{\nid: " + soundtrack.getId())
                .add("createdAt: " + soundtrack.getCreatedAt())
                .add("duration: " + soundtrack.getDuration())
                .add("   Song{\n    id: " + song.getId())
                .add("    createdAt: " + song.getCreatedAt())
                .add("    name: " + song.getName())
                .add("    duration: " + song.getDuration())
                .add("      Singer{\n       id: " + singer.getId())
                .add("       createdAt: " + singer.getCreatedAt())
                .add("       name: " + singer.getName())
                .add("       surname: " + singer.getSurname())
                .add("       MusicGenre: " + singer.getMusicGenre() + "\n       } \n    }")
                .add("      Composer{\n       id: " + composer.getId())
                .add("       createdAt: " + composer.getCreatedAt())
                .add("       name: " + composer.getName())
                .add("       surname: " + composer.getSurname())
                .add("       MelodyGenre: " + composer.getMelodyGenre() + "\n       } \n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllSoundtracks() throws IOException {
        List<SoundtrackResponseModel> soundtracks = selectAllSoundtracks();
        int i = 0;
        for (SoundtrackResponseModel soundtrack : soundtracks) {
            Song song = songById(this.songsIds.get(i));
            Singer singer = singerById(this.singersIds.get(i));
            Composer composer = composerById(this.composersIds.get(i));
            System.out.println(",\nSoundtrack{\nid: " + soundtrack.getId() + ",\ncreatedAt: " + soundtrack.getCreatedAt() + ",\nduration: " + soundtrack.getDuration()
                    + ",\n   Song{\n    id: " + song.getId() + ",\n    createdAt: " + song.getCreatedAt() + ",\n    name: " + song.getName()
                    + ",\n    duration: " + song.getDuration() + ",\n      Singer{\n       id: " + singer.getId() + ",\n       createdAt: " + singer.getCreatedAt()
                    + ",\n       name: " + singer.getName() + ",\n       surname: " + singer.getSurname() + ",\n       MusicGenre: " + singer.getMusicGenre() + "\n       } \n    }"
                    + ",\n      Composer{\n       id: " + composer.getId() + ",\n       createdAt: " + composer.getCreatedAt() + ",\n       name: " + composer.getName()
                    + ",\n       surname: " + composer.getSurname() + ",\n       MelodyGenre: " + composer.getMelodyGenre() + "\n       } \n}");
            i++;
        }
    }
    //endregion

    //region Private Methods
    private Soundtrack buildSoundtrackFrom(SoundtrackRequestModel soundtrackRequestModel) {
        Soundtrack soundtrack = new Soundtrack();
        soundtrack.setDuration(soundtrackRequestModel.getDuration());
        return soundtrack;
    }

    private SoundtrackResponseModel buildSoundtrackResponseModelFrom(Soundtrack soundtrack) {
        SongResponseModel songResponseModel = new SongResponseModel();
        songResponseModel.setId(soundtrack.getSong().getId());
        songResponseModel.setCreatedAt(soundtrack.getSong().getCreatedAt());
        songResponseModel.setName(soundtrack.getSong().getName());
        songResponseModel.setDuration(soundtrack.getSong().getDuration());
        Song song = soundtrack.getSong();

        List<Singer> singers = song.getSinger();
        songResponseModel.setSinger(singers);

        ComposerResponseModel composerResponseModel = new ComposerResponseModel();
        composerResponseModel.setId(soundtrack.getComposer().getId());
        composerResponseModel.setCreatedAt(soundtrack.getComposer().getCreatedAt());
        composerResponseModel.setName(soundtrack.getComposer().getName());
        composerResponseModel.setSurname(soundtrack.getComposer().getSurname());
        composerResponseModel.setMelodyGenre(soundtrack.getComposer().getMelodyGenre());

        SoundtrackResponseModel soundtrackResponseModel = new SoundtrackResponseModel();
        soundtrackResponseModel.setId(soundtrack.getId());
        soundtrackResponseModel.setCreatedAt(now());
        soundtrackResponseModel.setDuration(soundtrack.getDuration());
        soundtrackResponseModel.setSong(songResponseModel);
        soundtrackResponseModel.setComposer(composerResponseModel);
        return soundtrackResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(SoundtrackService.FILE));
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

    private Song songById(int id) throws IOException {
        ListIterator<String> stringListIterator = Files.readAllLines(Paths.get(SongService.FILE)).listIterator();
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
    //endregion
}