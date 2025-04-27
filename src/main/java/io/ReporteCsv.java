package io;

import modelo.DatosReporte;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteCsv implements Reporte {
    private final List<DatosReporte> conversiones;

    public ReporteCsv(List<DatosReporte> conversiones) {
        this.conversiones = conversiones;
    }

    @Override
    public void reporte() {

        try (FileWriter file = new FileWriter("reporte.csv")) {

            String conversionTxt = conversiones.stream()
                    .map(dato ->
                            dato.cantidad() + "\t" +
                                    dato.monedaBase() + "\t" +
                                    dato.equivalencia() + "\t" +
                                    dato.monedaCambio() + "\n")
                    .collect(Collectors.joining());

            file.write(conversionTxt);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
