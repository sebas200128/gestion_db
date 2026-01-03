
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    Connection con;
    
    public Connection getConnection() {
        try {
            String myBD = "jdbc:mysql://localhost:3306/gestion_db?serverTimezone=UTC";
            con = DriverManager.getConnection(myBD, "root", ""); // Si tienes pass en root, ponlo aquí
        } catch (SQLException e) {
            System.err.println("Error conexión: " + e.toString());
        }
        return con;
    }
}
