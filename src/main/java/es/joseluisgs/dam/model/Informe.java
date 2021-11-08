package es.joseluisgs.dam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "informe")
public class Informe {
    @XmlElementWrapper(name = "estadisticas")
    @XmlElement(name = "estadistica")
    private List<Estadistica> estadisticas = new ArrayList<Estadistica>();
}
