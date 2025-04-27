package vista;

import componente.Boton;
import componente.Combo;
import componente.Tabla;
import conexion.Conexion;
import io.*;
import modelo.Conversion;
import modelo.DatosReporte;
import modelo.Moneda;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.Vector;

public class Conversor extends JFrame {


    public Conversor() {
        super("Ejemplo con Panel Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JPanel formulario = new JPanel(new FlowLayout());

        JTextField txtValor = new JTextField();
        txtValor.setPreferredSize(new Dimension(100, 30));
        txtValor.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        txtValor.setBorder(null);
        txtValor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtValor.setBorder(new BorderUIResource.LineBorderUIResource(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtValor.setBorder(null);
            }
        });
        formulario.add(txtValor);

        Conexion conexion = new Conexion();
        Vector<Moneda> monedas = conexion.obtenerMonedas();

        Combo<Moneda> cmbDivisaInicial = new Combo<>(200, 30);
        cmbDivisaInicial.agregarOpciones(monedas);
        formulario.add(cmbDivisaInicial);

        formulario.add(new JLabel("a"));

        Combo<Moneda> cmbDivisaFinal = new Combo<>(200, 30);
        cmbDivisaFinal.agregarOpciones(monedas);
        formulario.add(cmbDivisaFinal);

        Boton btnConvertir = new Boton("Convertir", 100, 30);
        formulario.add(btnConvertir);

        panelPrincipal.add(formulario);

        JPanel pnlResultado = new JPanel(new FlowLayout());

        JLabel lblResultado = new JLabel("Resultado", SwingConstants.CENTER);
        lblResultado.setPreferredSize(new Dimension(300, 30));
        lblResultado.setFont(new Font("Times New Roman", Font.BOLD, 20));
        pnlResultado.add(lblResultado);

        panelPrincipal.add(pnlResultado);

        JPanel pnlTabla = new JPanel(new FlowLayout());

        Tabla tabla = new Tabla();

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setPreferredSize(new Dimension(650, 400));

        pnlTabla.add(scrollPane);
        panelPrincipal.add(pnlTabla);

        JPanel pnlGenerarArchivo = new JPanel(new BorderLayout());

        JPanel pnlIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Boton btnLimpiar = new Boton("Limpiar", 200, 30);
        pnlIzquierdo.add(btnLimpiar);
        btnLimpiar.addActionListener(e -> tabla.limpiar());

        JPanel pnlDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Combo<String> cmbArchivo = new Combo<>(200, 30);

        cmbArchivo.agregarOpciones(new Vector<>(List.of(Reporte.txt, Reporte.json, Reporte.csv)));
        pnlDerecho.add(cmbArchivo);

        Boton btnArchivo = new Boton("Descargar conversión", 200, 30);
        pnlDerecho.add(btnArchivo);
        btnArchivo.addActionListener(e -> {

            String extension = cmbArchivo.obtenerItemSelecionada();

            List<DatosReporte> datos = tabla.obtenerDatos();

            GenerarReporte generarReporte = new GenerarReporte();
            switch (extension) {
                case Reporte.txt -> generarReporte.generarReporte(new ReporteTxt(datos));
                case Reporte.json -> generarReporte.generarReporte(new ReporteJson(datos));
                case Reporte.csv -> generarReporte.generarReporte(new ReporteCsv(datos));
            }

            JOptionPane.showMessageDialog(this, "Reporte generado");
        });

        pnlGenerarArchivo.add(pnlIzquierdo, BorderLayout.WEST);
        pnlGenerarArchivo.add(pnlDerecho, BorderLayout.EAST);


        btnConvertir.addActionListener(event -> {

            String valor = txtValor.getText().trim();

            try {
                if (Double.parseDouble(valor) <= 0)
                    throw new IllegalArgumentException("El valor debe de ser mayor a cero");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Debes de ingresar un valor númerico");
                return;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                return;
            }

            Conversion conversion = conexion.convertir(new Conversion(
                    cmbDivisaInicial.obtenerItemSelecionada(),
                    cmbDivisaFinal.obtenerItemSelecionada(),
                    valor
            ));

            tabla.agregarDato(conversion);
        });
        panelPrincipal.add(pnlGenerarArchivo);
        add(panelPrincipal);

    }
}
