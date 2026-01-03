package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.frmPrincipal;
import vista.frmRegistrar;
import vista.frmEditar;

public class ControladorPrincipal implements ActionListener {
    private frmPrincipal vista;

    public ControladorPrincipal(frmPrincipal vista) {
        this.vista = vista;
        // IMPORTANTE: Escuchar los botones del menú principal
        this.vista.btnFrmRegistrar.addActionListener(this); 
        this.vista.btnFrmEditar.addActionListener(this);
    }
    
    public void iniciar() {
        vista.setTitle("Menú Principal");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // OPCIÓN 1: IR A REGISTRAR
        if (e.getSource() == vista.btnFrmRegistrar) {
            frmRegistrar reg = new frmRegistrar(); // 1. Crear Vista
            ControladorPersonas ctrl = new ControladorPersonas(reg); // 2. Crear Controlador y pasarle la vista
            ctrl.iniciarRegistrar(); // 3. Iniciar
            vista.dispose(); // 4. Cerrar menú actual
        }
        
        // OPCIÓN 2: IR A EDITAR
        if (e.getSource() == vista.btnFrmEditar) {
            frmEditar edit = new frmEditar(); // 1. Crear Vista
            ControladorPersonas ctrl = new ControladorPersonas(edit); // 2. Crear Controlador (Constructor de Editar)
            ctrl.iniciarEditar(); // 3. Iniciar
            vista.dispose(); // 4. Cerrar menú actual
        }
    }
}