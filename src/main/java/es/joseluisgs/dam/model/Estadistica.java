package es.joseluisgs.dam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "estadistica")
public class Estadistica {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String fecha;

    // Si comento los campos, si tiene sentido el tipo
    @XmlElement(name = "NO2")
    Resumen NO2 = new Resumen("NO2");

    @XmlElement(name = "Temperatura")
    Resumen Temperatura = new Resumen("Temperatura");

    @XmlElement(name = "CO")
    Resumen CO = new Resumen("CO");

    @XmlElement(name = "Ozone")
    Resumen Ozone = new Resumen("Ozone");


}
