package componente;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Boton extends JButton {

    public Boton(String texto, int ancho, int alto) {
        super(texto);
        setPreferredSize(new Dimension(ancho, alto));
        setFont(new Font("Times New Roman", Font.BOLD, 20));
        setBorder(null);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        setFocusPainted(false);
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBorder(new BorderUIResource.LineBorderUIResource(Color.BLUE));
                setBackground(Color.BLUE);
                setForeground(Color.WHITE);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBorder(null);
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }
        });
    }
}
