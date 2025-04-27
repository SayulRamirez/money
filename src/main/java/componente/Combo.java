package componente;

import modelo.Moneda;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

public class Combo<T> extends JComboBox<T> {

    public Combo(int ancho, int alto) {
        super();
        setPreferredSize(new Dimension(ancho, alto));
        setFont(new Font("Times New Roman", Font.PLAIN, 20));
    }

    /**
     * Agrega varias opciones a un {@link JComboBox} si no contiene opciones, es decir,
     * esta vacio simplemente continuara vacío, pero si es null lanzara un {@link NullPointerException}
     * @param opciones {@link Set} opciones a cargar en el combo.
     * @throws NullPointerException si las opciones es igual a null;
     */
    public void agregarOpciones(Vector<T> opciones) {
        Objects.requireNonNull(opciones, "No hay elementos en para cargar");

        if (!opciones.isEmpty())
            opciones.forEach(this::addItem);
    }

    public T obtenerItemSelecionada() {
        return (T) getSelectedItem();
    }
}
