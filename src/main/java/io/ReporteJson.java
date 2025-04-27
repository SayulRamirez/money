package io;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.DatosReporte;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReporteJson implements Reporte {
    private final Gson gson;

    private final List<DatosReporte> conversiones;

    public ReporteJson(List<DatosReporte> conversiones) {
        this.conversiones = conversiones;
        this.gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void reporte() {

        try (FileWriter file = new FileWriter("reporte.json")) {
            file.write(gson.toJson(conversiones));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
