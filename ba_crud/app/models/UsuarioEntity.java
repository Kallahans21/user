package com.ba;


import java.util.Date;
import java.util.List;
import java.sql.ResultSet;


public class UsuarioEntity {
    private Integer id;
    private String nombre;
    private Date fechaNacimiento;
    private boolean activo;

    // Constructor vacío
    public UsuarioEntity() {
    }

    // Constructor con parámetros
    public UsuarioEntity(Integer id, String nombre, Date fechaNacimiento, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
    }

    // Setters y Getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    // Método estático para convertir un ResultSet en una lista de UsuarioEntity
    public static List<UsuarioEntity> fromResultSet(ResultSet rs) throws SQLException {
        List<UsuarioEntity> usuarios = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            Date fechaNacimiento = rs.getDate("fecha_nacimiento");
            boolean activo = rs.getBoolean("activo");

            UsuarioEntity usuario = new UsuarioEntity(id, nombre, fechaNacimiento, activo);
            usuarios.add(usuario);
        }
        return usuarios;
    }

}
