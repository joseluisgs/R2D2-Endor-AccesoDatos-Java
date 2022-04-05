package es.joseluisgs.dam.controller;

import es.joseluisgs.dam.model.Estadistica;
import es.joseluisgs.dam.model.Informe;
import es.joseluisgs.dam.model.Medicion;
import es.joseluisgs.dam.model.Resumen;
import es.joseluisgs.dam.service.CSVService;
import es.joseluisgs.dam.service.JAXBService;
import es.joseluisgs.dam.service.XMLService;
import es.joseluisgs.dam.service.XPATHService;
import es.joseluisgs.dam.utils.FileResources;
import org.jdom2.JDOMException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Lee los ficheros y los procesa
public class R2D2Controller {
    private static R2D2Controller instance;
    private List<Medicion> medicionesCSV;
    private List<Medicion> medicionesXML;
    private List<Medicion> mediciones;
    private Informe informe;

    private R2D2Controller() {
    }

    public static R2D2Controller getInstance() {
        if (instance == null) {
            instance = new R2D2Controller();
        }
        return instance;
    }

    // Para quitra con un predicado y no hacer un for if
    private static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void loadData() {
        loadCSV();
        loadXML();
        // Ahora vamos a fusionar las dios nediciones
        fusionData();
    }

    private void fusionData() {
        // La forma más sencilla, es concatenar los streams y eliminar los repetidos
        Stream<Medicion> combinedStream = Stream.concat(
                medicionesXML.stream(),
                medicionesCSV.stream());
        // Elimino los repetidos, podria hacerlo con for y si no existe lo miro, o buscando de uno en otro... mil formas
        mediciones = combinedStream
                //.filter(distinctByKey(Medicion::getId))
                .distinct() // Necesita el méttodo equals en la clase Medicion
                .collect(Collectors.toList());

        //mediciones.forEach(System.out::println);
        //System.out.println(mediciones.size());
    }

    private void loadXML() {
        XMLService xml = XMLService.getInstance();
        try {
            medicionesXML = xml.loadData();
            //medicionesXML.forEach(System.out::println);
        } catch (IOException | JDOMException | URISyntaxException e) {
            System.err.println("Error al leer el fichero XML");
        }
    }

    private void loadCSV() {
        CSVService csv = CSVService.getInstance();
        try {
            medicionesCSV = csv.loadData();
            //medicionesCSV.forEach(System.out::println);

        } catch (IOException | URISyntaxException e) {
            System.err.println("Error al leer el fichero CSV");
        }
    }

    // Vamos a procesar de 25 en 25
    public void proccessData() {
        informe = new Informe();
        final int PAGE_SIZE = 25;
        final int NUM_PAGES = (int) Math.ceil(mediciones.size() / (double) PAGE_SIZE);
        for (int i = 0; i < NUM_PAGES; i++) {
            //System.out.println("Procesando página " + i);
            // Dois formas de saxcar las paginas, con sublistas o con stream
            //mediciones.stream().skip(i*PAGE_SIZE).limit(PAGE_SIZE).forEach(System.out::println);
            //System.out.println(mediciones.stream().skip(i*PAGE_SIZE).limit(PAGE_SIZE).count());

            // Debemos salvarlo poco a poco por lo que vamos paginando dentro del for...
            // los resultados van a informe
            informe.getEstadisticas()
                    .add(
                            getEstadistica(
                                    // mediciones.subList(i * PAGE_SIZE, Math.min((i + 1) * PAGE_SIZE, mediciones.size()))
                                    mediciones.parallelStream().skip(i * PAGE_SIZE).limit(PAGE_SIZE).collect(Collectors.toList())
                            )
                    );
        }

    }

    private Estadistica getEstadistica(List<Medicion> list) {
        // Mil maneras de hacerlo...
        // Por ejemplo así por cada medicion
//            DoubleSummaryStatistics stats = list.stream().mapToDouble(Medicion::getNO2).summaryStatistics();
//            System.out.println(stats);
        // Ahora buscamos las fechas por esos resultados...

        // O así por cada medicion, pero como quiero la fecha lo voy a hacer así


        /*stream.max((m1, m2) -> Double.compare(m1.getNO2(), m2.getNO2()))
                .ifPresent(max -> System.out.println("Maximum found is " + max.getId() + " with value " + max.getNO2()));
        stream.min((m1, m2) -> Double.compare(m1.getNO2(), m2.getNO2()))
                .ifPresent(min -> System.out.println("Min found is " + min.getId() + " with value " + min.getNO2()));*/

        Estadistica es = new Estadistica();
        es.setId(UUID.randomUUID().toString());
        es.setFecha(Instant.now().toString());

        // NO2
        // Max
        list.parallelStream().max(Comparator.comparingDouble(Medicion::getNO2))
                .ifPresent(max -> {
                    es.getNO2().setMaxValue(max.getNO2());
                    es.getNO2().setMaxDate(max.getFecha());
                });
        // Min
        list.parallelStream().min(Comparator.comparingDouble(Medicion::getNO2))
                .ifPresent(min -> {
                    es.getNO2().setMinValue(min.getNO2());
                    es.getNO2().setMinDate(min.getFecha());
                });
        // Media
        list.parallelStream().mapToDouble(Medicion::getNO2).average()
                .ifPresent(x -> es.getNO2().setAverageValue(x));

        // Temperatura
        list.parallelStream().max(Comparator.comparingDouble(Medicion::getTemperatura))
                .ifPresent(max -> {
                    es.getTemperatura().setMaxValue(max.getTemperatura());
                    es.getTemperatura().setMaxDate(max.getFecha());
                });
        list.stream().min(Comparator.comparingDouble(Medicion::getTemperatura))
                .ifPresent(min -> {
                    es.getTemperatura().setMinValue(min.getTemperatura());
                    es.getTemperatura().setMinDate(min.getFecha());
                });
        list.parallelStream().mapToDouble(Medicion::getTemperatura).average()
                .ifPresent(x -> es.getTemperatura().setAverageValue(x));

        // CO2
        list.parallelStream().max(Comparator.comparingDouble(Medicion::getCO))
                .ifPresent(max -> {
                    es.getCO().setMaxValue(max.getCO());
                    es.getCO().setMaxDate(max.getFecha());
                });
        list.parallelStream().min(Comparator.comparingDouble(Medicion::getCO))
                .ifPresent(min -> {
                    es.getCO().setMinValue(min.getCO());
                    es.getCO().setMinDate(min.getFecha());
                });
        list.parallelStream().mapToDouble(Medicion::getCO).average()
                .ifPresent(x -> es.getCO().setAverageValue(x));

        // Ozone
        list.parallelStream().max(Comparator.comparingDouble(Medicion::getOzone))
                .ifPresent(max -> {
                    es.getOzone().setMaxValue(max.getOzone());
                    es.getOzone().setMaxDate(max.getFecha());
                });
        list.parallelStream().min(Comparator.comparingDouble(Medicion::getOzone))
                .ifPresent(min -> {
                    es.getOzone().setMinValue(min.getOzone());
                    es.getOzone().setMinDate(min.getFecha());
                });
        list.parallelStream().mapToDouble(Medicion::getOzone).average()
                .ifPresent(x -> es.getOzone().setAverageValue(x));

        return es;
    }

    private void printXML() {
        //informe.getEstadisticas().forEach(System.out::println);
        // Cargamos JAXB
        JAXBService jaxb = JAXBService.getInstance();
        try {
            jaxb.printXML(informe);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private void saveXML(String dir) {
        String fileName = dir + "/" + "informe.xml";
        JAXBService jaxb = JAXBService.getInstance();
        try {
            jaxb.writeXMLFile(fileName, informe);
        } catch (JAXBException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void saveXSD(String dir) {
        String fileName = dir + "/" + "esquemas.xsd";
        JAXBService jaxb = JAXBService.getInstance();
        try {
            jaxb.writeXSDFile(fileName);
        } catch (JAXBException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        printXML();
        FileResources resources = FileResources.getInstance();
        final String dir = "/res";
        try {
            // Creamos el directorio si no existe
            resources.makeDir(dir);
            // Borramos todos los ficheros
            resources.deleteFiles(dir);
            // salvamos el informe
            saveXML(dir);

            // Salvamos el XSD
            saveXSD(dir);

            // Vamos con el xPATH
            XPATHService xpath = XPATHService.getInstance(JAXBService.getInstance().toDOM(informe));

            List<Resumen> listNO2 = xpath.getInfo("NO2");
            List<Resumen> listOzone = xpath.getInfo("Ozone");

            saveMarkdown(dir, listNO2, listOzone);

        } catch (IOException | URISyntaxException | JAXBException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        // Lo primero que vamos a hacer es salvar el informe en nuestro directorio res03
    }

    private void saveMarkdown(String dir, List<Resumen> listNO2, List<Resumen> listOzone) throws IOException, URISyntaxException {
        String fileName = dir + "/" + "informe.md";
        FileResources resources = FileResources.getInstance();
        File file = resources.getPath(fileName);
        final PrintWriter f = new PrintWriter(new FileWriter(file, true));
        try {
            f.println("# RESUMEN DE DATOS");
            f.println("## NO2");
            listNO2.forEach(x -> f.println("- " + x.toMarkDown()));
            f.println("");
            f.println("##Ozone");
            listOzone.forEach(x -> f.println("- " + x.toMarkDown()));
        } finally {
            try {
                f.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }


    }
}
