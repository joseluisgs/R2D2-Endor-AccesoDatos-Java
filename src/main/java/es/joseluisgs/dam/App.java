package es.joseluisgs.dam;

import es.joseluisgs.dam.controller.R2D2Controller;

import java.net.URISyntaxException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws URISyntaxException {
        System.out.println("Hola RD-D2 Data DAM");

        var ini = System.currentTimeMillis();

        R2D2Controller controller = R2D2Controller.getInstance();

        controller.loadData();

        controller.proccessData();

        controller.saveData();

        var fin = System.currentTimeMillis();

        System.out.println("Tiempo total: " + (fin - ini) + " ms");


    }
}
