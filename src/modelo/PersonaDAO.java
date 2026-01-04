package modelo;

import config.Conexion;
import config.SentenciasSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PersonaDAO {

    // Instancia de la clase conexión
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // --- MÉTODO REGISTRAR ---
    public boolean registrar(Persona p) {
        // Usamos la constante de la clase SentenciasSQL
        String sql = SentenciasSQL.REGISTRAR;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            // Asignamos los valores a los ? del SQL
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApPat());
            ps.setString(3, p.getApMat());
            ps.setString(4, p.getDni());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getGenero());
            ps.setDate(7, p.getFechaNac()); // java.sql.Date

            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al Registrar: " + e.toString());
            return false;
        } finally {
            cerrarConexiones();
        }
    }

    // --- MÉTODO LISTAR (Para la tabla) ---
    public List<Persona> listar() {
        List<Persona> lista = new ArrayList<>();
        String sql = SentenciasSQL.LISTAR;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setApPat(rs.getString("apellido_pat"));
                p.setApMat(rs.getString("apellido_mat"));
                p.setDni(rs.getString("dni"));
                p.setCorreo(rs.getString("correo"));
                p.setGenero(rs.getString("genero"));
                p.setFechaNac(rs.getDate("fecha_nacimiento"));

                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al Listar: " + e.toString());
        } finally {
            cerrarConexiones();
        }
        return lista;
    }

    // --- MÉTODO BUSCAR POR ID ---
    public Persona buscarPorId(int id) {
        Persona p = null; // Iniciamos en null para saber si no se encontró
        String sql = SentenciasSQL.BUSCAR_POR_ID;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                p = new Persona();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setApPat(rs.getString("apellido_pat"));
                p.setApMat(rs.getString("apellido_mat"));
                p.setDni(rs.getString("dni"));
                p.setCorreo(rs.getString("correo"));
                p.setGenero(rs.getString("genero"));
                p.setFechaNac(rs.getDate("fecha_nacimiento"));
            }
        } catch (SQLException e) {
            System.err.println("Error al Buscar: " + e.toString());
        } finally {
            cerrarConexiones();
        }
        return p;
    }

    // --- MÉTODO EDITAR DINÁMICO ---
    public boolean editarDato(int id, String columnaBD, String nuevoValor) {
        // Obtenemos el SQL dinámico desde la clase auxiliar
        String sql = SentenciasSQL.getActualizar(columnaBD);

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            // SQL es: UPDATE personas SET columna = ? WHERE id = ?
            ps.setString(1, nuevoValor); // Primer ? es el valor
            ps.setInt(2, id);            // Segundo ? es el ID

            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al Editar: " + e.toString());
            return false;
        } finally {
            cerrarConexiones();
        }
    }

    // --- MÉTODO ELIMINAR ---
    public boolean eliminar(int id) {
        String sql = SentenciasSQL.ELIMINAR;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al Eliminar: " + e.toString());
            return false;
        } finally {
            cerrarConexiones();
        }
    }

    // --- MÉTODO AUXILIAR PARA CERRAR (Buenas prácticas) ---
    private void cerrarConexiones() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("Error cerrando conexiones: " + e.toString());
        }
    }
}
