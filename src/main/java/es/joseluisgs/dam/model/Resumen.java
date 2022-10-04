package es.joseluisgs.dam.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Resumen {
    @XmlAttribute
    private String id = UUID.randomUUID().toString();
    @XmlAttribute
    private String tipo;
    // Por cada valor que nos piden
    private double maxValue;
    private String maxDate;
    private double minValue;
    private String minDate;
    private double averageValue;

    public Resumen(String tipo) {
        this.tipo = tipo;
    }

    public String toMarkDown() {
        return "Valores {" +
                "maxValue=" + maxValue +
                ", maxDate='" + maxDate + '\'' +
                ", minValue=" + minValue +
                ", minDate='" + minDate + '\'' +
                ", averageValue=" + averageValue +
                '}';
    }
}
