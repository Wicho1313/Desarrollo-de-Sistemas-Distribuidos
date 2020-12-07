public class Usuario {
    public Usuario() {
        this.foto = null;
    }

    String getEmail() { return this.email; }
    String getNombre() { return this.nombre; }
    String getApellidoPaterno() { return this.apellido_paterno; }
    String getApellidoMaterno() { return this.apellido_materno; }
    String getFechaNacimiento() { return this.fecha_nacimiento; }
    String getTelefono() { return this.telefono; }
    String getGenero() { return this.genero; }
    byte[] getFoto() { return this.foto; }

    void setEmail(String email) { this.email = email; }
    void setNombre(String nombre) { this.nombre = nombre; }
    void setApellidoPaterno(String apellidoPaterno) { this.apellido_paterno = apellidoPaterno; }
    void setApellidoMaterno(String apellidoMaterno) { this.apellido_materno = apellidoMaterno; }
    void setFechaNacimiento(String fechaNacimiento) { this.fecha_nacimiento = fechaNacimiento; }
    void setTelefono(String telefono) { this.telefono = telefono; }
    void setGenero(String genero) { this.genero = genero; }
    void setFoto(byte[] foto) { this.foto = foto; }

    public String toString() {
        return "Email: " + email + "\n" +
                "Nombre: " + nombre + "\n" +
                "Apellido Paterno: " + apellido_paterno + "\n" +
                "Apellido Materno: " + apellido_materno + "\n" + 
                "Fecha de nacimiento: " + fecha_nacimiento + "\n" + 
                "Telefono: " + telefono + "\n" + 
                "Genero: " + genero + "\n" +
                "Foto: null";
    }

    private String email;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String fecha_nacimiento;
    private String telefono;
    private String genero;
    private byte[] foto;
}