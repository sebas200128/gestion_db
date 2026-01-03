
package modelo;
import config.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PersonaDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Persona p) {
        String sql = "INSERT INTO personas (nombre, apellido_pat, apellido_mat, dni, correo, genero, fecha_nacimiento) VALUES (?,?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApPat());
            ps.setString(3, p.getApMat());
            ps.setString(4, p.getDni());
            ps.setString(5, p.getCorreo());
            ps.setString(6, p.getGenero());
            ps.setDate(7, p.getFechaNac());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public Persona buscarPorId(int id) {
        Persona p = new Persona();
        String sql = "SELECT * FROM personas WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setApPat(rs.getString("apellido_pat"));
                p.setApMat(rs.getString("apellido_mat"));
                p.setDni(rs.getString("dni"));
                p.setCorreo(rs.getString("correo"));
                p.setGenero(rs.getString("genero"));
                p.setFechaNac(rs.getDate("fecha_nacimiento"));
            } else {
                return null; // No existe
            }
        } catch (SQLException e) {
            System.err.println(e.toString());
        }
        return p;
    }

    // Edición dinámica según el combo seleccionado
    public boolean editarDato(int id, String columnaBD, String nuevoValor) {
        // NOTA: Las columnas no se pueden pasar como parámetro '?' en PreparedStatement por seguridad estricta,
        // pero validaremos la columna antes de concatenar.
        String sql = "UPDATE personas SET " + columnaBD + " = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nuevoValor);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM personas WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return false;
        }
    }

    public List<Persona> listar() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM personas";
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
            System.err.println(e.toString());
        }
        return lista;
    }
}
