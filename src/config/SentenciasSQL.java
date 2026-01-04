/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author Sebastian
 */
public class SentenciasSQL {

    // --- USUARIOS ---
    public static final String LOGIN = "SELECT * FROM usuarios WHERE usuario = ? AND password = ?";

    // --- PERSONAS (CRUD) ---
    public static final String REGISTRAR = "INSERT INTO personas (nombre, apellido_pat, apellido_mat, dni, correo, genero, fecha_nacimiento) VALUES (?,?,?,?,?,?,?)";

    public static final String LISTAR = "SELECT * FROM personas";

    public static final String BUSCAR_POR_ID = "SELECT * FROM personas WHERE id = ?";

    public static final String ELIMINAR = "DELETE FROM personas WHERE id = ?";

    // Para la edición dinámica, como la columna cambia, podemos usar un método estático
    public static String getActualizar(String nombreColumna) {
        return "UPDATE personas SET " + nombreColumna + " = ? WHERE id = ?";
    }
}
