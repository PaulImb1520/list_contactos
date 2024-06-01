package com.example.imbaquingo;

public class Contacto {
    private String nombre;
    private String apellido;
    private String telefono;
    private String sexo;

    public Contacto(String nombre, String apellido, String telefono, String sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.sexo = sexo;
    }
    public String getNombre() { return  nombre;}
    public String getApellido(){return  apellido;}
    public String getTelefono(){return  telefono;}
    public String getSexo(){return  sexo;}
}
