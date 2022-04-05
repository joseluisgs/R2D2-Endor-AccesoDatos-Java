package es.joseluisgs.dam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    // Uso el equals para luego poder obtener los elementos distintos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicion)) return false;
        Medicion medicion = (Medicion) o;
        return id == medicion.id && Double.compare(medicion.NO2, NO2) == 0 && Double.compare(medicion.temperatura, temperatura) == 0 && Double.compare(medicion.CO, CO) == 0 && Double.compare(medicion.ozone, ozone) == 0 && Objects.equals(fecha, medicion.fecha) && Objects.equals(tipo, medicion.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, tipo, NO2, temperatura, CO, ozone);
    }
}
