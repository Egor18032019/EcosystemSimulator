package repositories;

import models.Animal;
import models.EcosystemObject;
import models.Plant;

import java.io.*;
import java.util.List;

public class FileRepositories implements RepositoriesCommon {

    private final List<EcosystemObject> objects;
    private final String filename;

    public FileRepositories(List<EcosystemObject> objects, String filename) {
        this.objects = objects;
        this.filename = "data/" + filename;
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (EcosystemObject object : objects) {
                writer.write(object.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла.");
            System.out.println(e.getMessage());
        }
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // todo Обдумать
                if (line.startsWith("Plant")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int growthRate = Integer.parseInt(parts[1].split(": ")[1]);
//                    Plant: 2, Growth Rate: 3, Weight: 3
                    int weight = Integer.parseInt(parts[2].split(": ")[1]);
                    objects.add(new Plant(name, growthRate, weight));
                } else if (line.startsWith("Animal")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int population = Integer.parseInt(parts[1].split(": ")[1]);
                    int eating = Integer.parseInt(parts[2].split(": ")[1]);
                    int reproduction = Integer.parseInt(parts[3].split(": ")[1]);
                    objects.add(new Animal(name, population, eating, reproduction));
                }
            }
        } catch (IOException e) {
            System.out.println("Файл не найден !");
            System.out.println("Будет создан новый файл.");
        }
    }
}
