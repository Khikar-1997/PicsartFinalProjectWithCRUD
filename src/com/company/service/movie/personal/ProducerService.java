package com.company.service.movie.personal;

import com.company.controller.model.movie.personal.producer.ProducerRequestModel;
import com.company.controller.model.movie.personal.producer.ProducerResponseModel;
import com.company.exceptions.ProducerNotFoundException;
import com.company.persistance.model.movie.personal.Profession;
import com.company.persistance.model.movie.personal.producer.Producer;

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

public class ProducerService {
    public static final String FILE = "producer.txt";

    //region Public Methods
    public void create(ProducerRequestModel producerRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            Producer producer = buildProducerFrom(producerRequestModel);
            ProducerResponseModel producerResponseModel = buildProducerResponseModelFrom(producer);
            producerResponseModel.setId(0);
            String producerInfo = producerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), producerInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            Producer producer = buildProducerFrom(producerRequestModel);
            ProducerResponseModel producerResponseModel = buildProducerResponseModelFrom(producer);
            producerResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String producerInfo = producerResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), producerInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<ProducerResponseModel> selectAllProducers() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> producerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            producerInfo.add(stringListIterator.next().split(","));
        }
        List<Producer> producers = new LinkedList<>();

        for (String[] value : producerInfo) {
            int id = Integer.parseInt(value[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(value[1]);
            String name = value[2];
            String surname = value[3];
            int age = Integer.parseInt(value[4]);
            Profession profession = Profession.valueOf(value[5]);
            producers.add(new Producer(id, localDateTime, name, surname, age, profession));
        }

        List<ProducerResponseModel> modelList = new LinkedList<>();
        for (Producer producer : producers) {
            modelList.add(buildProducerResponseModelFrom(producer));
        }
        return modelList;
    }

    private ProducerResponseModel selectProducerById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
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
            return buildProducerResponseModelFrom(producer);
        }
    }

    public void update(ProducerRequestModel producerRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> producerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            producerInfo.add(stringListIterator.next().split(","));
        }

        Producer producer = new Producer();
        for (String[] strings : producerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                producer.setId(Integer.parseInt(strings[0]));
                break;
            }
        }

        if (id != producer.getId()) {
            throw new ProducerNotFoundException(String.format("Producer not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                producer.setCreatedAt(now());
                producer.setName(producerRequestModel.getName());
                producer.setSurname(producerRequestModel.getSurname());
                producer.setAge(producerRequestModel.getAge());
                producer.setProfession(producerRequestModel.getProfession());
                ProducerResponseModel producerResponseModel = buildProducerResponseModelFrom(producer);
                producerInfo.remove(id);
                producerInfo.add(id, new String[]{String.valueOf(producerResponseModel.getId()), String.valueOf(producerResponseModel.getCreatedAt()), producerResponseModel.getName(), producerResponseModel.getSurname(), String.valueOf(producerResponseModel.getAge()), String.valueOf(producerResponseModel.getProfession())});
                for (String[] strings : producerInfo) {
                    String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "," + strings[5] + "\n";
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
        List<String[]> producerInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            producerInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : producerInfo) {
            if (id == Integer.parseInt(strings[0])) {
                producerInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : producerInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "," + strings[5] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedProducerById(int id) throws IOException {
        ProducerResponseModel producer = selectProducerById(id);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Producer{\nid: " + producer.getId())
                .add("createdAt: " + producer.getCreatedAt())
                .add("name: " + producer.getName())
                .add("surname: " + producer.getSurname())
                .add("age: " + producer.getAge())
                .add("profession: " + producer.getProfession() + "\n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllProducers() throws IOException {
        List<ProducerResponseModel> producers = selectAllProducers();
        for (ProducerResponseModel producer : producers) {
            System.out.println("Producer{\nid: " + producer.getId() + ",\ncreatedAt: " + producer.getCreatedAt() + ",\nname: " + producer.getName() +
                    ",\nsurname: " + producer.getSurname() + ",\nage: " + producer.getAge() + ",\nprofession: " + producer.getProfession() + "\n}");
        }
    }
    //endregion

    //region Private Methods
    private Producer buildProducerFrom(ProducerRequestModel producerRequestModel) {
        Producer producer = new Producer();
        producer.setName(producerRequestModel.getName());
        producer.setSurname(producerRequestModel.getSurname());
        producer.setAge(producerRequestModel.getAge());
        producer.setProfession(producerRequestModel.getProfession());
        return producer;
    }

    private ProducerResponseModel buildProducerResponseModelFrom(Producer producer) {
        ProducerResponseModel producerResponseModel = new ProducerResponseModel();
        producerResponseModel.setId(producer.getId());
        producerResponseModel.setCreatedAt(now());
        producerResponseModel.setName(producer.getName());
        producerResponseModel.setSurname(producer.getSurname());
        producerResponseModel.setAge(producer.getAge());
        producerResponseModel.setProfession(producer.getProfession());
        return producerResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(ProducerService.FILE));
    }
    //endregion
}

