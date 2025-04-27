package conexion;

import com.google.gson.*;
import modelo.Conversion;
import modelo.Moneda;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;

public class Conexion {

    private final HttpClient client;

    private static final String pathBase = "https://v6.exchangerate-api.com/v6/";

    private static final String KEY = "d88905c2ab894f27f5101d9e";

    public Conexion() {
        client = HttpClient.newHttpClient();
    }

    private HttpRequest crearPeticion(String endpoint) {
        return HttpRequest.newBuilder(URI.create(pathBase.concat(endpoint)))
                .header("Authorization", "Bearer ".concat(KEY))
                .build();
    }

    public Vector<Moneda> obtenerMonedas() {
        HttpRequest request = crearPeticion("codes");

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        return jsonObject.getAsJsonArray("supported_codes")
                .asList()
                .stream()
                .map(json -> {
                    JsonArray codigo = json.getAsJsonArray();
                    return new Moneda(
                            codigo.get(0).getAsString(),
                            codigo.get(1).getAsString()
                    );
                })
                .sorted(Comparator.comparing(Moneda::nombre))
                .collect(Collectors.toCollection(Vector::new));
    }

    public Conversion convertir(Conversion peticion) {
        final String uri = "pair/"
                .concat(peticion.getCodigoBase()).concat("/")
                .concat(peticion.getCodigoCambio()).concat("/")
                .concat(peticion.getCantidad());

        HttpRequest httpRequest = crearPeticion(uri);

        String response = client
                .sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

        peticion.setEquivalencia(jsonObject.get("conversion_result").getAsString());

        return peticion;
    }
}
