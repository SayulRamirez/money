package modelo;

public class Conversion {

    private final Moneda monedaBase;

    private final Moneda monedaCambio;

    private final String cantidad;

    private String equivalencia;

    public Conversion(Moneda monedaBase, Moneda monedaCambio, String cantidad) {
        this.monedaBase = monedaBase;
        this.monedaCambio = monedaCambio;
        this.cantidad = cantidad;
    }

    public String getNombreBase() {
        return this.monedaBase.nombre();
    }

    public String getCodigoBase() {
        return this.monedaBase.codigo();
    }

    public String getNombreCambio() {
        return this.monedaCambio.nombre();
    }

    public String getCodigoCambio() {
        return this.monedaCambio.codigo();
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }
}