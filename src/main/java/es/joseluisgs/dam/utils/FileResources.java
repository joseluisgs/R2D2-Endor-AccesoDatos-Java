package es.joseluisgs.dam.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileResources {
    private static FileResources instance;

    private FileResources() {
    }

    public static FileResources getInstance() {
        if (instance == null) {
            instance = new FileResources();
        }
        return instance;
    }


    public InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("Fichero no encontrado " + fileName);
        } else {
            return inputStream;
        }
    }

    public void makeDir(String dir) throws IOException, URISyntaxException {
        Path source = Paths.get(this.getClass().getResource("/").getPath());
        Path newFolder = Paths.get(source.toAbsolutePath() + dir);
        Files.createDirectories(newFolder);
    }

    public void deleteFiles(String dir) throws IOException, URISyntaxException {
        Path source = Paths.get(this.getClass().getResource("/").getPath());
        Path folder = Paths.get(source.toAbsolutePath() + dir);
        Files.walk(folder)
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    public File getPath(String file) throws IOException, URISyntaxException {
        Path source = Paths.get(this.getClass().getResource("/").getPath());
        Path fileName = Paths.get(source.toAbsolutePath() + file);
        return fileName.toFile();
    }


    public File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("Fichero no encotrado " + fileName);
        } else {
            // Falla si hay espacios o carcateres raros...
            return new File(resource.toURI());
        }
    }
    public void printInputStream(InputStream is) {
        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printFile(File file) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
