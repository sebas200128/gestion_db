package modelo;

import config.Conexion;
import config.SentenciasSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean validarUsuario(String user, String pass) {
        // REEMPLAZAMOS EL TEXTO POR LA CONSTANTE
        String sql = SentenciasSQL.LOGIN;

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println(e.toString());
            return false;
        }
    }
}
