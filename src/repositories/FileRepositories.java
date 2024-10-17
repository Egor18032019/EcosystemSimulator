package repositories;

import models.Animal;
import models.EcosystemObject;
import models.Plant;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class FileRepositories implements RepositoriesCommon {

    private final List<EcosystemObject> Objects;
    private final String FileName;

    public FileRepositories(List<EcosystemObject> objects, String FileName) {
        this.Objects = objects;
        this.FileName = "data/" + FileName + ".txt";
    }

    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FileName))) {
            for (EcosystemObject object : Objects) {
                writer.write(object.toString());
                writer.newLine();
            }
            log("Cохранение в файл прошло успешно.Всего объектов: " + Objects.size());
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла.");
            log("Ошибка при сохранении файла.");
            log(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // todo Обдумать
                if (line.startsWith("Plant")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int growthRate = Integer.parseInt(parts[1].split(": ")[1]);
//                    Plant: 2, Growth Rate: 3, Weight: 3
                    int weight = Integer.parseInt(parts[2].split(": ")[1]);
                    Objects.add(new Plant(name, growthRate, weight));
                } else if (line.startsWith("Animal")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int population = Integer.parseInt(parts[1].split(": ")[1]);
                    int eating = Integer.parseInt(parts[2].split(": ")[1]);
                    int reproduction = Integer.parseInt(parts[3].split(": ")[1]);
                    Objects.add(new Animal(name, population, eating, reproduction));
                }
                log("Выгрузили данные симуляции из файла. Всего объектов: " + Objects.size());
            }
        } catch (IOException e) {
            System.out.println("Файл не найден !");
            System.out.println("Будет создан новый файл.");
            log("Создали новый файл для сохранения данных симуляции.");
        }
    }

    @Override
    public void log(String message) {
//todo уровни логирования ?
        File file = new File(FileName + ".log");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Ошибка создания файла", e);
            }
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            writer.write(message + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка открытия файла", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
