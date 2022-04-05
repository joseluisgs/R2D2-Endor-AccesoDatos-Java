package es.joseluisgs.dam.service;

import es.joseluisgs.dam.model.Medicion;
import es.joseluisgs.dam.utils.FileResources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVService {
    private final static String CSV_FILE = "data/data03.csv";
    private static CSVService instance;
    private List<Medicion> mediciones;

    private CSVService() {
    }

    public static CSVService getInstance() {
        if (instance == null) {
            instance = new CSVService();
        }
        return instance;
    }

    public List<Medicion> loadData() throws IOException, URISyntaxException {
        FileResources res = FileResources.getInstance();
        Stream<String> lineas = Files.lines(res.getFileFromResource(CSV_FILE).toPath());
        // Skip para saltarnos la primera lineas
        return lineas.skip(1).parallel().map(this::parse).collect(Collectors.toList());

        // Me salto la primera linea y proceso las demas, solo me interesan los datos de la medicion. Voy poco a poco
        /*lineas.skip(1).forEach(l -> {

            // Lo demás paso...
            //tokenizer.nextToken(); // particles
            //tokenizer.nextToken(); // course
            //tokenizer.nextToken(); // speed
            //tokenizer.nextToken(); // uri
            listaMediciones.add(med);
        });*/
        // return mediciones;
    }

    private Medicion parse(String linea) {
        // dc:identifier,dc:modified,ayto:type,ayto:latitude,ayto:longitude,ayto:NO2,ayto:ozone,ayto:odometer,ayto:altitude,ayto:temperature,ayto:CO,ayto:particles,ayto:course,ayto:speed,"uri "
        // StringTokenizer tokenizer = new StringTokenizer(linea, ",");
        String[] datos = linea.split(",");
        Medicion med = new Medicion();
        med.setId(Integer.parseInt(datos[0]));
        med.setFecha(datos[1]);
        med.setTipo(datos[2]);
        med.setNO2(Double.parseDouble(datos[5]));
        med.setOzone(Double.parseDouble(datos[6]));
        med.setTemperatura(Double.parseDouble(datos[9]));
        med.setCO(Double.parseDouble(datos[10]));
        return med;
    }


}
