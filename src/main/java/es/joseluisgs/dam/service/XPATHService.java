package es.joseluisgs.dam.service;


import es.joseluisgs.dam.model.Resumen;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XPATHService {
    private static XPATHService instance;
    private static Document document;
    // sinq ue sirva de precedentes voy a usar XPATH con JDOM
    private static org.jdom2.Document dom;



    private XPATHService() {
    }

    public static XPATHService getInstance(Document domDocument) {
        if (instance == null) {
            instance = new XPATHService();
        }
        instance.document = domDocument;
        instance.dom = instance.convertDOMtoJDOM(domDocument);
        return instance;
    }

    public List<Resumen> getInfo(String elementTag) throws IOException {
        //XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        //xmlOutputter.output(dom, System.out);


        XPathFactory xpath = XPathFactory.instance();
        XPathExpression<Element> expr = xpath.compile("//"+elementTag, Filters.element());
        List<Element> values = expr.evaluate(this.dom);
        List<Resumen> list = new ArrayList<>();
        values.forEach(element -> {
            Resumen resumen = new Resumen();
            //System.out.println(element.getValue());
            resumen.setMaxValue(Double.parseDouble(element.getChild("maxValue").getValue()));
            resumen.setMaxDate(element.getChild("maxDate").getValue());
            resumen.setMinValue(Double.parseDouble(element.getChild("minValue").getValue()));
            resumen.setMinDate(element.getChild("minDate").getValue());
            resumen.setAverageValue(Double.parseDouble(element.getChild("averageValue").getValue()));

           //System.out.println(resumen);
           list.add(resumen);
        });
        return list;
    }

    // Fucniones axisiales

    public org.jdom2.Document convertDOMtoJDOM(org.w3c.dom.Document input) {
        DOMBuilder builder = new DOMBuilder();
        org.jdom2.Document output = builder.build(input);
        return output;
    }

//    public org.w3c.dom.Document convertJDOMToDOM(org.jdom.Document jdomDoc) throws JDOMException {
//
//        DOMOutputter outputter = new DOMOutputter();
//        return outputter.output(jdomDoc);
//    }

    private void printDOM() {
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            xmlOutputter.output(dom, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
