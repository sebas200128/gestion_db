package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Persona;
import modelo.PersonaDAO;
import vista.frmEditar;
import vista.frmRegistrar;
import vista.frmPrincipal;

public class ControladorPersonas implements ActionListener {

    private PersonaDAO dao = new PersonaDAO();
    private Persona p = new Persona();
    private frmRegistrar vistaReg;
    private frmEditar vistaEdit;

    // Constructor para Registrar
    public ControladorPersonas(frmRegistrar vistaReg) {
        this.vistaReg = vistaReg;
        this.vistaReg.btnRegistrar.addActionListener(this);
        this.vistaReg.btnVolver.addActionListener(this);
    }

    // Constructor para Editar
    public ControladorPersonas(frmEditar vistaEdit) {
        this.vistaEdit = vistaEdit;
        this.vistaEdit.btnBuscarID.addActionListener(this);
        this.vistaEdit.btnEditar.addActionListener(this);
        this.vistaEdit.btnEliminar.addActionListener(this);
        this.vistaEdit.btnMostrar.addActionListener(this);
        this.vistaEdit.btnVolver.addActionListener(this);
    }

    public void iniciarRegistrar() {
        vistaReg.setTitle("Registrar");
        vistaReg.setLocationRelativeTo(null);
        vistaReg.setVisible(true);
    }

    public void iniciarEditar() {
        vistaEdit.setTitle("Editar Registros");
        vistaEdit.setLocationRelativeTo(null);

        // --- CÓDIGO NUEVO: LLENAR EL COMBOBOX ---
        vistaEdit.cbxBuscarDato.removeAllItems(); // Limpiamos por si acaso
        vistaEdit.cbxBuscarDato.addItem("Nombre");
        vistaEdit.cbxBuscarDato.addItem("Apellido Paterno");
        vistaEdit.cbxBuscarDato.addItem("Apellido Materno");
        vistaEdit.cbxBuscarDato.addItem("DNI");
        vistaEdit.cbxBuscarDato.addItem("Correo");
        // ----------------------------------------

        vistaEdit.setVisible(true);
        listarTabla(); // Esto ahora cargará la tabla con los títulos correctos
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Lógica EXCLUSIVA para la ventana REGISTRAR
        if (vistaReg != null) {
            if (e.getSource() == vistaReg.btnRegistrar) {
                registrar();
            } else if (e.getSource() == vistaReg.btnVolver) {
                volver(vistaReg);
            }
        }

        // Lógica EXCLUSIVA para la ventana EDITAR
        if (vistaEdit != null) {
            if (e.getSource() == vistaEdit.btnBuscarID) {
                buscar();
            } else if (e.getSource() == vistaEdit.btnEditar) {
                editar();
            } else if (e.getSource() == vistaEdit.btnEliminar) {
                eliminar();
            } else if (e.getSource() == vistaEdit.btnMostrar) {
                listarTabla();
            } else if (e.getSource() == vistaEdit.btnVolver) {
                volver(vistaEdit);
            }
        }
    }

    private void registrar() {
        // Validar vacíos
        if (vistaReg.txtNombre.getText().isEmpty() || vistaReg.txtDNI.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llene todos los campos");
            return;
        }

        p.setNombre(vistaReg.txtNombre.getText());
        p.setApPat(vistaReg.txtApellidoPat.getText());
        p.setApMat(vistaReg.txtApellidoMat.getText());
        p.setDni(vistaReg.txtDNI.getText());
        p.setCorreo(vistaReg.txtCorreo.getText());
        p.setGenero(vistaReg.cbxGenero.getSelectedItem().toString());

        // Convertir fecha
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date parsed = format.parse(vistaReg.txtAñoNacimiento.getText());
            p.setFechaNac(new Date(parsed.getTime()));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto (dd/MM/yyyy)");
            return;
        }

        if (dao.registrar(p)) {
            JOptionPane.showMessageDialog(null, "Registrado Exitosamente");
            limpiarRegistrar();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar");
        }
    }

    private void buscar() {
        if (vistaEdit.txtBuscarId.getText().isEmpty()) {
            return;
        }
        int id = Integer.parseInt(vistaEdit.txtBuscarId.getText());
        p = dao.buscarPorId(id);
        if (p != null) {
            JOptionPane.showMessageDialog(null, "ID Encontrado: " + p.getNombre());
            // Aquí podrías llenar campos si quisieras mostrar la info actual
        } else {
            JOptionPane.showMessageDialog(null, "ID no existe");
        }
    }

    private void editar() {
        if (vistaEdit.txtBuscarId.getText().isEmpty()) {
            return;
        }

        int id = Integer.parseInt(vistaEdit.txtBuscarId.getText());
        String seleccion = vistaEdit.cbxBuscarDato.getSelectedItem().toString();
        String nuevoValor = vistaEdit.txtEditarDato.getText();
        String columnaBD = "";

        // Mapear selección a columna de BD
        switch (seleccion) {
            case "Nombre":
                columnaBD = "nombre";
                break;
            case "Apellido Paterno":
                columnaBD = "apellido_pat";
                break;
            case "Apellido Materno":
                columnaBD = "apellido_mat";
                break;
            case "DNI":
                columnaBD = "dni";
                break;
            case "Correo":
                columnaBD = "correo";
                break;
            default:
                columnaBD = "nombre";
        }

        if (dao.editarDato(id, columnaBD, nuevoValor)) {
            JOptionPane.showMessageDialog(null, "Editado correctamente");
            listarTabla();
            limpiarEditar();
        }
    }

    private void eliminar() {
        if (vistaEdit.txtBuscarId.getText().isEmpty()) {
            return;
        }
        int id = Integer.parseInt(vistaEdit.txtBuscarId.getText());
        if (dao.eliminar(id)) {
            JOptionPane.showMessageDialog(null, "Eliminado");
            listarTabla();
            limpiarEditar();
        }
    }

    private void listarTabla() {
        List<Persona> lista = dao.listar();

        // 1. Definimos los nombres de las columnas
        String[] titulos = {"ID", "Nombre", "Ap. Paterno", "Ap. Materno", "DNI", "Correo", "Género", "Nacimiento"};

        // 2. Creamos el modelo con esos títulos
        DefaultTableModel model = new DefaultTableModel(null, titulos);

        // 3. Llenamos las filas
        for (Persona per : lista) {
            Object[] fila = new Object[8];
            fila[0] = per.getId();
            fila[1] = per.getNombre();
            fila[2] = per.getApPat();
            fila[3] = per.getApMat();
            fila[4] = per.getDni();
            fila[5] = per.getCorreo();
            fila[6] = per.getGenero();
            fila[7] = per.getFechaNac();
            model.addRow(fila);
        }

        // 4. Asignamos el modelo a la tabla visual
        vistaEdit.tblDatos.setModel(model);
    }

    private void volver(javax.swing.JFrame actual) {
        frmPrincipal prin = new frmPrincipal();
        ControladorPrincipal cp = new ControladorPrincipal(prin);
        cp.iniciar();
        actual.dispose();
    }

    private void limpiarRegistrar() {
        vistaReg.txtNombre.setText("");
        vistaReg.txtApellidoPat.setText("");
        vistaReg.txtApellidoMat.setText("");
        vistaReg.txtDNI.setText("");
        vistaReg.txtCorreo.setText("");
        vistaReg.txtAñoNacimiento.setText("");
        vistaReg.cbxGenero.setSelectedIndex(0);
    }

    private void limpiarEditar() {
        vistaEdit.txtBuscarId.setText("");
        vistaEdit.txtEditarDato.setText("");
    }
}
