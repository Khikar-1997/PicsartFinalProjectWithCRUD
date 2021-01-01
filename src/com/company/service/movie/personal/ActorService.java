package com.company.service.movie.personal;

import com.company.controller.model.movie.personal.actor.ActorRequestModel;
import com.company.controller.model.movie.personal.actor.ActorResponseModel;
import com.company.exceptions.ActorNotFoundException;
import com.company.persistance.model.movie.personal.Profession;
import com.company.persistance.model.movie.personal.actor.Actor;

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

public class ActorService {
    public static final String FILE = "actor.txt";

    //region Public Methods
    public void create(ActorRequestModel actorRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            Actor actor = buildActorFrom(actorRequestModel);
            ActorResponseModel actorResponseModel = buildActorResponseModelFrom(actor);
            actorResponseModel.setId(0);
            String actorInfo = actorResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), actorInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            Actor actor = buildActorFrom(actorRequestModel);
            ActorResponseModel actorResponseModel = buildActorResponseModelFrom(actor);
            actorResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String actorInfo = actorResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), actorInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<ActorResponseModel> selectAllActors() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> actorInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            actorInfo.add(stringListIterator.next().split(","));
        }
        List<Actor> actors = new LinkedList<>();

        for (String[] value : actorInfo) {
            int id = Integer.parseInt(value[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(value[1]);
            String name = value[2];
            String surname = value[3];
            int age = Integer.parseInt(value[4]);
            Profession profession = Profession.valueOf(value[5]);
            String role = value[6];
            actors.add(new Actor(id, localDateTime, name, surname, age, profession, role));
        }

        List<ActorResponseModel> modelList = new LinkedList<>();
        for (Actor actor : actors) {
            modelList.add(buildActorResponseModelFrom(actor));
        }
        return modelList;
    }

    private ActorResponseModel selectActorById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
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
            return buildActorResponseModelFrom(actor);
        }
    }

    public void update(ActorRequestModel actorRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> actorInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            actorInfo.add(stringListIterator.next().split(","));
        }

        Actor actor = new Actor();
        for (String[] strings : actorInfo) {
            if (id == Integer.parseInt(strings[0])) {
                actor.setId(Integer.parseInt(strings[0]));
                break;
            }
        }

        if (id != actor.getId()) {
            throw new ActorNotFoundException(String.format("Actor not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                actor.setCreatedAt(now());
                actor.setName(actorRequestModel.getName());
                actor.setSurname(actorRequestModel.getSurname());
                actor.setAge(actorRequestModel.getAge());
                actor.setProfession(actorRequestModel.getProfession());
                actor.setRole(actorRequestModel.getRole());
                ActorResponseModel actorResponseModel = buildActorResponseModelFrom(actor);
                actorInfo.remove(id);
                actorInfo.add(id, new String[]{String.valueOf(actorResponseModel.getId()), String.valueOf(actorResponseModel.getCreatedAt()), actorResponseModel.getName(), actorResponseModel.getSurname(), String.valueOf(actorResponseModel.getAge()), String.valueOf(actorResponseModel.getProfession()), actorResponseModel.getRole()});
                for (String[] strings : actorInfo) {
                    String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "," + strings[5] + "," + strings[6] + "\n";
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
        List<String[]> actorInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            actorInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : actorInfo) {
            if (id == Integer.parseInt(strings[0])) {
                actorInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : actorInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "," + strings[5] + "," + strings[6] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedActorById(int id) throws IOException {
        ActorResponseModel actor = selectActorById(id);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("Actor{\nid: " + actor.getId())
                .add("createdAt: " + actor.getCreatedAt())
                .add("name: " + actor.getName())
                .add("surname: " + actor.getSurname())
                .add("age: " + actor.getAge())
                .add("profession: " + actor.getProfession())
                .add("role: " + actor.getRole() + "\n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllActors() throws IOException {
        List<ActorResponseModel> actors = selectAllActors();
        for (ActorResponseModel actor : actors) {
            System.out.println("Actor{\nid: " + actor.getId() + ",\ncreatedAt: " + actor.getCreatedAt() + ",\nname: " + actor.getName() +
                    ",\nurname: " + actor.getSurname() + ",\nage: " + actor.getAge() + ",\nprofession: " + actor.getProfession() + ",\nrole: " +
                    actor.getRole() + "\n}");
        }
    }
    //endregion

    //region Private Methods
    private Actor buildActorFrom(ActorRequestModel actorRequestModel) {
        Actor actor = new Actor();
        actor.setName(actorRequestModel.getName());
        actor.setSurname(actorRequestModel.getSurname());
        actor.setAge(actorRequestModel.getAge());
        actor.setProfession(actorRequestModel.getProfession());
        actor.setRole(actorRequestModel.getRole());
        return actor;
    }

    private ActorResponseModel buildActorResponseModelFrom(Actor actor) {
        ActorResponseModel actorResponseModel = new ActorResponseModel();
        actorResponseModel.setId(actor.getId());
        actorResponseModel.setCreatedAt(now());
        actorResponseModel.setName(actor.getName());
        actorResponseModel.setSurname(actor.getSurname());
        actorResponseModel.setAge(actor.getAge());
        actorResponseModel.setProfession(actor.getProfession());
        actorResponseModel.setRole(actor.getRole());
        return actorResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(ActorService.FILE));
    }
    //endregion
}
