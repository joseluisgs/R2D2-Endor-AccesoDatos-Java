package es.joseluisgs.dam.service;

import es.joseluisgs.dam.model.Estadistica;
import es.joseluisgs.dam.model.Informe;
import es.joseluisgs.dam.model.Medicion;
import es.joseluisgs.dam.model.Resumen;
import es.joseluisgs.dam.utils.FileResources;
import jakarta.xml.bind.*;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class JAXBService<T> {
    private static JAXBService instance;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    private JAXBService() {
    }

    public static JAXBService getInstance() {
        if (instance == null) {
            instance = new JAXBService();
        }
        return instance;
    }

    private void convertObjectToXML(Informe items) throws JAXBException {
        // Creamos el contexto
        JAXBContext context = JAXBContext.newInstance(Informe.class);
        // Marshall --> Object to XML
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    }

    public void writeXMLFile(String fileName, Informe informe) throws JAXBException, IOException, URISyntaxException {
        FileResources resources = FileResources.getInstance();
        File file = resources.getPath(fileName);
        convertObjectToXML(informe);
        this.marshaller.marshal(informe, file);
        System.out.println("Fichero XML generado con éxito");
    }

    public Document toDOM(Informe informe) throws JAXBException, ParserConfigurationException {
        // Creamos el documento con JDOM vacío
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
        Document domDocument = domBuilder.newDocument();

        convertObjectToXML(informe);
        this.marshaller.marshal(informe, domDocument);
        return domDocument;
    }

    public void printXML(Informe informe) throws JAXBException {
        convertObjectToXML(informe);
        this.marshaller.marshal(informe, System.out);
    }

    private Informe convertXMLToObject(String uri) throws JAXBException {
        // Creamos el contexto
        JAXBContext context = JAXBContext.newInstance(Informe.class);
        this.unmarshaller = context.createUnmarshaller();
        // Unmarshall --> XML toObject
        return (Informe) this.unmarshaller.unmarshal(new File(uri));
    }

    // Obtiene el esquema de la clase
    public void writeXSDFile(String fileName) throws IOException, URISyntaxException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(Informe.class, Estadistica.class, Medicion.class, Resumen.class);
        context.generateSchema(new MySchemaOutputResolver(fileName));
    }

    class MySchemaOutputResolver extends SchemaOutputResolver {
        FileResources resources = FileResources.getInstance();
        File file;


        MySchemaOutputResolver(String fileName) throws IOException, URISyntaxException {
            file = resources.getPath(fileName);
        }

        public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
            return new StreamResult(file);
        }
    }

}
