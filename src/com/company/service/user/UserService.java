package com.company.service.user;

import com.company.controller.model.user.UserRequestModel;
import com.company.controller.model.user.UserResponseModel;
import com.company.exceptions.UserNotFoundException;
import com.company.persistance.model.user.Role;
import com.company.persistance.model.user.User;

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

public class UserService {
    public static final String FILE = "user.txt";

    //region Public Methods
    public void create(UserRequestModel userRequestModel) throws IOException {
        if (!(new File(FILE).isFile())) {
            new PrintWriter(FILE, StandardCharsets.UTF_8);
            User user = buildUserFrom(userRequestModel);
            UserResponseModel userResponseModel = buildUserResponseModelFrom(user);
            userResponseModel.setId(0);
            String userInfo = userResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), userInfo.getBytes(), StandardOpenOption.APPEND);
        } else {
            String[] split = readFromFile().get(readFromFile().size() - 1).split(",");
            User user = buildUserFrom(userRequestModel);
            UserResponseModel userResponseModel = buildUserResponseModelFrom(user);
            userResponseModel.setId(Integer.parseInt(split[0]) + 1);
            String userInfo = userResponseModel.toStringWithoutTextInfo() + "\n";
            Files.write(Paths.get(FILE), userInfo.getBytes(), StandardOpenOption.APPEND);
        }
    }

    private List<UserResponseModel> selectAllUsers() throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> userInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            userInfo.add(stringListIterator.next().split(","));
        }
        List<User> users = new LinkedList<>();

        for (String[] value : userInfo) {
            int id = Integer.parseInt(value[0]);
            LocalDateTime localDateTime = LocalDateTime.parse(value[1]);
            String name = value[2];
            String surname = value[3];
            Role role = Role.valueOf(value[4]);
            String email = value[5];
            String username = value[6];
            String password = value[7];
            users.add(new User(id, localDateTime, name, surname, role, email, username, password));
        }

        List<UserResponseModel> modelList = new LinkedList<>();
        for (User user : users) {
            modelList.add(buildUserResponseModelFrom(user));
        }
        return modelList;
    }

    public UserResponseModel selectUserById(int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> userInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            userInfo.add(stringListIterator.next().split(","));
        }

        User user = new User();
        for (String[] strings : userInfo) {
            if (id == Integer.parseInt(strings[0])) {
                user.setId(Integer.parseInt(strings[0]));
                user.setCreatedAt(LocalDateTime.parse(strings[1]));
                user.setName(strings[2]);
                user.setSurname(strings[3]);
                user.setRole(Role.valueOf(strings[4]));
                user.setEmile(strings[5]);
                user.setUsername(strings[6]);
                user.setPassword(strings[7]);
                break;
            }
        }
        if (id != user.getId()) {
            throw new UserNotFoundException(String.format("User not found for id - %d", id));
        } else {
            return buildUserResponseModelFrom(user);
        }
    }

    public void update(UserRequestModel userRequestModel, int id) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        PrintWriter printWriter = new PrintWriter(FILE);
        printWriter.write("");
        printWriter.close();
        List<String[]> userInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            userInfo.add(stringListIterator.next().split(","));
        }

        User user = new User();
        for (String[] strings : userInfo) {
            if (id == Integer.parseInt(strings[0])) {
                user.setId(Integer.parseInt(strings[0]));
                break;
            }
        }

        if (id != user.getId()) {
            throw new UserNotFoundException(String.format("User not found for id - %d", id));
        } else {
            if (!(new File(FILE).isFile())) {
                throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
            } else {
                user.setCreatedAt(now());
                user.setName(userRequestModel.getName());
                user.setSurname(userRequestModel.getSurname());
                user.setRole(userRequestModel.getRole());
                user.setEmile(userRequestModel.getEmail());
                user.setUsername(userRequestModel.getUsername());
                user.setPassword(userRequestModel.getPassword());
                UserResponseModel userResponseModel = buildUserResponseModelFrom(user);
                userInfo.remove(id);
                userInfo.add(id, new String[]{String.valueOf(userResponseModel.getId()), String.valueOf(userResponseModel.getCreatedAt()), userResponseModel.getName(), userResponseModel.getSurname(), String.valueOf(userResponseModel.getRole()), userResponseModel.getEmail(), userResponseModel.getUsername(), userResponseModel.getPassword()});
                for (String[] strings : userInfo) {
                    String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "," + strings[5] + "," + strings[6] + "," + strings[7] + "\n";
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
        List<String[]> userInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            userInfo.add(stringListIterator.next().split(","));
        }

        int count = 0;
        for (String[] strings : userInfo) {
            if (id == Integer.parseInt(strings[0])) {
                userInfo.remove(count);
                break;
            }
            count++;
        }

        if (!(new File(FILE).isFile())) {
            throw new FileNotFoundException(String.format("There is no file with name - %s", FILE));
        } else {
            for (String[] strings : userInfo) {
                String s = strings[0] + "," + strings[1] + "," + strings[2] + "," + strings[3] + "," + strings[4] + "," + strings[5] + "," + strings[6] + "," + strings[7] + "\n";
                Files.write(Paths.get(FILE), s.getBytes(), StandardOpenOption.APPEND);
            }
        }
    }

    public void printSelectedUserById(int id) throws IOException {
        UserResponseModel user = selectUserById(id);
        StringJoiner stringJoiner = new StringJoiner(",\n");
        stringJoiner.add("User{\nid: " + user.getId())
                .add("createdAt: " + user.getCreatedAt())
                .add("name: " + user.getName())
                .add("surname: " + user.getSurname())
                .add("role: " + user.getRole())
                .add("email: " + user.getEmail())
                .add("username: " + user.getUsername())
                .add("password: " + user.getPassword() + "\n}");
        System.out.println(stringJoiner.toString());
    }

    public void printAllActors() throws IOException {
        List<UserResponseModel> users = selectAllUsers();
        for (UserResponseModel user : users) {
            System.out.println("User{\nid: " + user.getId() + ",\ncreatedAt: " + user.getCreatedAt() + ",\nname: " + user.getName() +
                    ",\nsurname: " + user.getSurname() + ",\nrole: " + user.getRole() + ",\nemail: " + user.getEmail() + ",\nusername: " +
                    user.getUsername() + ",\npassword: " + user.getPassword() + "\n}");
        }
    }

    public Role login(String username, UserRequestModel userRequestModel) throws IOException {
        ListIterator<String> stringListIterator = readFromFile().listIterator();
        List<String[]> userInfo = new LinkedList<>();
        while (stringListIterator.hasNext()) {
            userInfo.add(stringListIterator.next().split(","));
        }

        User user = new User();
        for (String[] strings : userInfo) {
            if (username.equals(strings[strings.length - 2])) {
                user.setId(Integer.parseInt(strings[0]));
                user.setCreatedAt(LocalDateTime.parse(strings[1]));
                user.setName(strings[2]);
                user.setSurname(strings[3]);
                user.setRole(Role.valueOf(strings[4]));
                user.setEmile(strings[5]);
                user.setUsername(strings[6]);
                user.setPassword(strings[7]);
                break;
            }
        }
        if (!(username.equals(user.getUsername()))) {
            throw new UserNotFoundException(String.format("User not found by username - %s", username));
        } else {
            User user1 = new User();
            user1.setPassword(userRequestModel.mD5PasswordEncoder(userRequestModel.getPassword()));
            if (user.getPassword().equals(user1.getPassword())){
                return user.getRole();
            } else {
                throw new RuntimeException("Invalid password.Try again.");
            }
        }
    }
    //endregion

    //region Private Methods
    private User buildUserFrom(UserRequestModel userRequestModel) {
        User user = new User();
        user.setName(userRequestModel.getName());
        user.setSurname(userRequestModel.getSurname());
        user.setRole(userRequestModel.getRole());
        user.setEmile(userRequestModel.getEmail());
        user.setUsername(userRequestModel.getUsername());
        user.setPassword(userRequestModel.mD5PasswordEncoder(userRequestModel.getPassword()));
        return user;
    }

    private UserResponseModel buildUserResponseModelFrom(User user) {
        UserResponseModel userResponseModel = new UserResponseModel();
        userResponseModel.setId(user.getId());
        userResponseModel.setCreatedAt(now());
        userResponseModel.setName(user.getName());
        userResponseModel.setSurname(user.getSurname());
        userResponseModel.setRole(user.getRole());
        userResponseModel.setEmail(user.getEmile());
        userResponseModel.setUsername(user.getUsername());
        userResponseModel.setPassword(user.getPassword());
        return userResponseModel;
    }

    private LocalDateTime now() {
        return LocalDateTime.now();
    }

    private List<String> readFromFile() throws IOException {
        return Files.readAllLines(Paths.get(UserService.FILE));
    }
    //endregion
}
