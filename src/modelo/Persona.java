
package modelo;

import java.sql.Date;

public class Persona {

    private int id;
    private String nombre;
    private String apPat;
    private String apMat;
    private String dni;
    private String correo;
    private String genero;
    private Date fechaNac;

    // Constructores, Getters y Setters vacíos y completos (Generar con Alt+Insert en NetBeans)
    public Persona() {
    }

    public Persona(int id, String nombre, String apPat, String apMat, String dni, String correo, String genero, Date fechaNac) {
        this.id = id;
        this.nombre = nombre;
        this.apPat = apPat;
        this.apMat = apMat;
        this.dni = dni;
        this.correo = correo;
        this.genero = genero;
        this.fechaNac = fechaNac;
    }

    // IMPORTANTE: Genera los Getters y Setters aquí para todos los campos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPat() {
        return apPat;
    }

    public void setApPat(String apPat) {
        this.apPat = apPat;
    }

    public String getApMat() {
        return apMat;
    }

    public void setApMat(String apMat) {
        this.apMat = apMat;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
}
