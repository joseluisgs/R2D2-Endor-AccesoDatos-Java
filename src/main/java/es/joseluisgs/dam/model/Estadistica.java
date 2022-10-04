package es.joseluisgs.dam.model;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "estadistica")
public class Estadistica {

    // Si comento los campos, si tiene sentido el tipo
    @XmlElement(name = "NO2")
    Resumen NO2 = new Resumen("NO2");
    @XmlElement(name = "Temperatura")
    Resumen Temperatura = new Resumen("Temperatura");
    @XmlElement(name = "CO")
    Resumen CO = new Resumen("CO");
    @XmlElement(name = "Ozone")
    Resumen Ozone = new Resumen("Ozone");
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String fecha;


}
