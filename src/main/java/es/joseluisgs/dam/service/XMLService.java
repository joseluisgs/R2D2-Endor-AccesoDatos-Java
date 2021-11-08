package es.joseluisgs.dam.service;

import es.joseluisgs.dam.model.Medicion;
import es.joseluisgs.dam.utils.FileResources;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class XMLService {
    private static XMLService instance;
    private final static String XML_FILE = "data/data03.xml";
    private List<Medicion> mediciones;

    private XMLService() {
    }

    public static XMLService getInstance() {
        if (instance == null) {
            instance = new XMLService();
        }
        return instance;
    }

    public List<Medicion> loadData() throws IOException, JDOMException, URISyntaxException {
        FileResources res = FileResources.getInstance();
        SAXBuilder sax = new SAXBuilder();
        // XML is a local file
        Document doc = sax.build(res.getFileFromResource(XML_FILE));
        // Voy al directorio raíz
        Element rootNode = doc.getRootElement();
        // Cojo el elemento resouces
        Element resources = rootNode.getChild("resources");
        // me quedo con sus resouces hijos
        List<Element> resourceList = resources.getChildren("resource");
        System.out.println("Cantidad de recursos: " +resourceList.size());
        // Ahora proceso cada uno
        return resourceList.parallelStream().map(this::parse).collect(Collectors.toList());
    }

    private Medicion parse(Element resource) {
/*        <resource>
<str name="ayto:particles"/>
<str name="ayto:NO2">116.0</str>
<str name="ayto:type">AirQualityObserved</str>
<str name="ayto:latitude">43.449</str>
<str name="ayto:temperature">22.9</str>
<str name="ayto:altitude"/>
<str name="ayto:speed"/>
<str name="ayto:CO">0.1</str>
<date name="dc:modified">2021-08-27T07:47:39Z</date>
<str name="dc:identifier">3047</str>
<str name="ayto:longitude">-3.8677</str>
<str name="ayto:odometer"/>
<str name="ayto:course"/>
<str name="ayto:ozone">120.0</str>
<str name="uri">http://datos.santander.es/api/datos/sensores_smart_mobile/3047.xml</str>
</resource>*/

        Medicion med = new Medicion();
        // Elementos str
        List<Element> strs = resource.getChildren("str");
        // El 1 es NO2 // Otra forma es hacerlo con XPATH
        med.setNO2(Double.parseDouble(strs.get(1).getText()));
        med.setTipo(strs.get(2).getText());
        med.setCO(Double.parseDouble(strs.get(7).getText()));
        med.setId(Integer.parseInt(strs.get(8).getText()));
        med.setOzone(Double.parseDouble(strs.get(12).getText()));
        // Ahira recorro los str
        // Elemento date
        med.setFecha(resource.getChild("date").getValue());
        return med;
    }
}
