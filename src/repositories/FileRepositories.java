package repositories;

import logging.Logger;
import models.Animal;
import models.EcosystemObject;
import models.NaturalEnvironment;
import models.Plant;
import utils.Const;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FileRepositories implements RepositoriesCommon {
    private final String FileName;
    private static final Logger logger = Logger.getLogger();

    public FileRepositories(String fileName) {
        this.FileName = Const.DATA_DIRECTORY + fileName + ".txt";

    }

    @Override
    public void create(Map<String, EcosystemObject> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {
            for (EcosystemObject object : objects.values()) {
                writer.write(object.toString());
                writer.newLine();
            }
            logger.log("Файл для хранения данных: " + FileName);
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла.");
            logger.log("Ошибка при создании файла.");
            logger.log(e.getMessage());
        }
    }

    @Override
    public Map<String, EcosystemObject> read() {
        Map<String, EcosystemObject> storage = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Plant")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int growthRate = Integer.parseInt(parts[1].split(": ")[1]);
                    int weight = Integer.parseInt(parts[2].split(": ")[1]);
                    storage.put(name, new Plant(name, growthRate, weight));
                } else if (line.startsWith("Animal")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int population = Integer.parseInt(parts[1].split(": ")[1]);
                    int eating = Integer.parseInt(parts[2].split(": ")[1]);
                    int reproduction = Integer.parseInt(parts[3].split(": ")[1]);
//                    objects.add(new Animal(name, population, eating, reproduction));
                    storage.put(name, new Animal(name, population, eating, reproduction));
                } else if (line.startsWith("NaturalEnvironment")) {
                    String[] parts = line.split(", ");
                    int temperature = Integer.parseInt(parts[0].split("=")[1]);
                    int humidity = Integer.parseInt(parts[1].split("=")[1]);
                    int waterAvailable = Integer.parseInt(parts[2].split("=")[1].replace("}", ""));
                    NaturalEnvironment naturalEnvironment = new NaturalEnvironment(temperature, humidity, waterAvailable);
                    storage.put(naturalEnvironment.getName(), naturalEnvironment);
                }
            }
            logger.log("Выгрузили данные симуляции из файла. Всего объектов: " + storage.size());
        } catch (IOException e) {
            String message = "При попытки выгрузки данных симуляции из файла " + FileName + " произошла ошибка " + e.getMessage() + ".";
            logger.log(message);
            logger.log("Будет возращен пустой список данных.");
        }
        return storage;
    }

    @Override
    public void update(EcosystemObject object) {
        File file = new File(FileName);

        if (!file.exists()) {
            String message = "Не найден файл для сохранения данных симуляции! Будет создан новый.";
            System.out.println(message);
            logger.log(message);
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            writer.write(object.toString() + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка открытия файла", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.log("Ошибка закрытия файла." + e.getMessage());
                }
            }
        }
    }

    @Override
    public void delete() {
        File fileToDelete = new File(FileName);

        if (!fileToDelete.exists()) {
            System.out.println("Файл не найден.");
            logger.log("Файл не найден.");
            return;
        }

        // Удаляем файл
        boolean deleted = fileToDelete.delete();
        if (deleted) {
            logger.log("Файл успешно удален.");
            System.out.println("Файл успешно удален.");
        } else {
            logger.log(String.format("Ошибка при удалении файла: %s", fileToDelete.getAbsolutePath()));
            System.out.println("Ошибка при попытке удаления файла.");
        }
    }

    @Override
    public void save(Map<String, EcosystemObject> storage) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {
            for (EcosystemObject object : storage.values()) {
                writer.write(object.toString());
                writer.newLine();
            }
            logger.log("Сохранили данные симуляции из файла. Всего объектов: " + storage.size());
        } catch (IOException e) {
            String message = "При попытке сохранения данных симуляции в файл " + FileName + " произошла ошибка " + e.getMessage() + ".";
            System.out.println(message);
            logger.log(message);
        }
    }
}
