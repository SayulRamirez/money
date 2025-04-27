package componente;

import modelo.Conversion;
import modelo.DatosReporte;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tabla extends JTable {

    private final DefaultTableModel modelo;

    private long contador;

    public Tabla() {
        contador = 1;

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setFont(new Font("Times New Roman", Font.PLAIN, 20));
        setRowHeight(30);

        modelo = new DefaultTableModel(new Object[][]{}, new Object[]{"ID", "CANTIDA", "M INICIAL", "EQUIVALENTE", "M FINAL"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setModel(modelo);

        JTableHeader tableHeader = getTableHeader();
        tableHeader.setFont(new Font("Times New Roman", Font.BOLD, 20));
        tableHeader.setBackground(new Color(40, 60, 85));
        tableHeader.setForeground(new Color(255, 255, 255));
        tableHeader.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 110)));

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component componente = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {

                    if (row % 2 == 0) componente.setBackground(new Color(240, 240, 240));
                    else componente.setBackground(Color.WHITE);

                } else {
                    componente.setBackground(new Color(184, 207, 229));
                }
                return componente;
            }
        });
    }

    public void agregarDato(Conversion conversion) {
        modelo.addRow(new Object[]{
                contador,
                conversion.getCantidad(),
                conversion.getNombreBase(),
                conversion.getEquivalencia(),
                conversion.getNombreCambio()
        });

        contador++;
    }

    public void limpiar() {
        modelo.getDataVector().clear();
        contador = 1;
        repaint();
    }

    public List<DatosReporte> obtenerDatos() {
        List<DatosReporte> listaDatos = new ArrayList<>();

        for (int i = 0; i < modelo.getRowCount(); i++) {
                DatosReporte conversion = new DatosReporte(modelo.getValueAt(i, 1).toString(),
                        modelo.getValueAt(i, 2).toString(),
                        modelo.getValueAt(i, 3).toString(),
                        modelo.getValueAt(i, 4).toString());


            listaDatos.add(conversion);
        }
        return listaDatos;
    }
}
