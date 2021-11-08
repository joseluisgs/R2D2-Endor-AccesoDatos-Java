package es.joseluisgs.dam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicion {
    private int id;
    private String fecha;
    private String tipo;
    private double NO2;
    private double temperatura;
    private double CO;
    private double ozone;


}
