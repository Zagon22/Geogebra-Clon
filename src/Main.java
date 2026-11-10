import Modelo.GestorObjetos;
import Vista.VistaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GestorObjetos modelo = new GestorObjetos();
            VistaPrincipal vista = new VistaPrincipal(modelo);
            new Controlador(modelo, vista);
            vista.setVisible(true);
        });
    }
}