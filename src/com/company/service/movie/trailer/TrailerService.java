package com.company.service.movie.trailer;

import com.company.exceptions.TrailerNotFoundException;
import com.company.persistance.model.movie.trailer.Trailer;
import com.company.controller.model.movie.trailer.TrailerRequestModel;
import com.company.controller.model.movie.trailer.TrailerResponseModel;

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

public class TrailerService {
    public static final String FILE = "trailer.txt";

    //region Public Methods
    public void create(TrailerRequestModel trailerRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            Trailer trailer = buildTrailerFrom(trailerRequestModel);
            TrailerResponseModel trailerResponseModel = buildTrailerResponseModelFrom(trailer);
            trailerResponseModel.setId(0);
            String trailerInfo = trailerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), trailerInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            Trailer trailer = buildTrailerFrom(trailerRequestModel);
            TrailerResponseModel trailerResponseModel = buildTrailerResponseModelFrom(trailer);
            trailerResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String trailerInfo = trailerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), trailerInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<TrailerResponseModel> selectAllTrailers() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> trailerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            trailerInfo.add(stringListIterator.next().split(","));
        }
        List<Trailer> trailers = new LinkedList<>();

        for (String[] value : trailerInfo) {
            int id = Integer.parseInt(value[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(value[1]);
            String duration = value[2];
            trailers.add(new Trailer(id, localDateTime, duration));
        }

        List<TrailerResponseModel> modelList = new LinkedList<>();
        for (Trailer trailer : trailers) {
            modelList.add(buildTrailerResponseModelFrom(trailer));
        }
        return modelList;
    }

    private TrailerResponseModel selectTrailerById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
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
            return buildTrailerResponseModelFrom(trailer);
        }
    }

    public void update(TrailerRequestModel trailerRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> trailerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            trailerInfo.add(stringListIterator.next().split(","));
        }

        Trailer trailer = new Trailer();
        int count = 0;
        for (String[] strings : trailerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                trailer.setId(Integer.parseInt(strings[0]));
                break;
            }
            count++;
        }

        if (id != trailer.getId()) {
            throw new TrailerNotFoundException(String.format("Trailer not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                trailer.setCreatedAt(now());
                trailer.setDuration(trailerRequestModel.getDuration());
                TrailerResponseModel trailerResponseModel = buildTrailerResponseModelFrom(trailer);
                trailerInfo.remove(count);
                trailerInfo.add(count, new String[]{String.valueOf(trailerResponseModel.getId()), String.valueOf(trailerResponseModel.getCreatedAt()), trailerResponseModel.getDuration()});
                for (String[] strings : trailerInfo) {
                    String s = strings[0] + "," + strings[1] + "," + strings[2] + "\n";
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
        List<String[]> trailerInfo = new LinkedList<>();
        int i = 0;
        while (stringListIterator.hasNext()) {
            trailerInfo.add(stringListIterator.next().split(","));
            i++;
        }

        int count = 0;
        for (String[] strings : trailerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                trailerInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : trailerInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedTrailerById(int id) throws IOException {
        TrailerResponseModel trailer = selectTrailerById(id);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Trailer{\nid: " + trailer.getId())
                .add("createdAt: " + trailer.getCreatedAt())
                .add("duration: " + trailer.getDuration() + "\n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllTrailers() throws IOException {
        List<TrailerResponseModel> trailers = selectAllTrailers();
        for (TrailerResponseModel trailer : trailers) {
            System.out.println("Trailer{\nid: " + trailer.getId() + ",\ncreatedAt: " + trailer.getCreatedAt() + ",\nduration: " + trailer.getDuration() + "\n}");
        }
    }
    //endregion

    //region Private Methods
    private Trailer buildTrailerFrom(TrailerRequestModel trailerRequestModel) {
        Trailer trailer = new Trailer();
        trailer.setDuration(trailerRequestModel.getDuration());
        return trailer;
    }

    private TrailerResponseModel buildTrailerResponseModelFrom(Trailer trailer) {
        TrailerResponseModel trailerResponseModel = new TrailerResponseModel();
        trailerResponseModel.setId(trailer.getId());
        trailerResponseModel.setCreatedAt(now());
        trailerResponseModel.setDuration(trailer.getDuration());
        return trailerResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(TrailerService.FILE));
    }
    //endregion
}



