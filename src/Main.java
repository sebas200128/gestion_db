
import controlador.ControladorLogin;
import modelo.UsuarioDAO;
import vista.frmLogin;

public class Main {

    public static void main(String[] args) {

        // Inicializamos MVC del Login
        frmLogin vistaL = new frmLogin();
        UsuarioDAO modeloL = new UsuarioDAO();
        ControladorLogin controlL = new ControladorLogin(vistaL, modeloL);

        controlL.iniciar();
    }

}
