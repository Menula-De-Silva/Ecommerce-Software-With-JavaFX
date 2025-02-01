package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class userdata {
    private static final String FILE_NAME = "userdata.txt";
    private static List<user> users = new ArrayList<>();

    static {
        loadFromFile();
    }

    public static void addUser(user user) {
        users.add(user);
        saveToFile();
    }

    public static List<user> getUsers() {
        return users;
    }

    private static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (List<user>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}