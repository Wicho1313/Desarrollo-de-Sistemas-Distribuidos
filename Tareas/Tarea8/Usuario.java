import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Usuario {
    public Usuario() {
        this.foto = null;
    }

    private String email;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String fecha_nacimiento;
    private String telefono;
    private String genero;
    private byte[] foto;

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

    public void Imprimir_datos() {
        System.out.println( "Email: " + email + "\n" +
                            "Nombre: " + nombre + "\n" +
                            "Apellido Paterno: " + apellido_paterno + "\n" +
                            "Apellido Materno: " + apellido_materno + "\n" + 
                            "Fecha de nacimiento: " + fecha_nacimiento + "\n" + 
                            "Telefono: " + telefono + "\n" + 
                            "Genero: " + genero + "\n" +
                            "Foto: null");
    }
    /** */
    private static Usuario crearUsuario(Usuario usuario) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Email:");
        usuario.setEmail(br.readLine());

        System.out.println("Nombre:");
        usuario.setNombre(br.readLine());

        System.out.println("Apellido Paterno:");
        usuario.setApellidoPaterno(br.readLine());

        System.out.println("Apellido Materno:");
        usuario.setApellidoMaterno(br.readLine());

        System.out.println("Fecha de nacimiento:");
        usuario.setFechaNacimiento(br.readLine());

        System.out.println("Telefono:");
        usuario.setTelefono(br.readLine());

        System.out.println("Genero (M/F):");
        usuario.setGenero(br.readLine());

        return usuario;
    }

    protected static String Consultar_usuario() throws IOException {
        String email;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduce el e-mail del Usuario que se quiere consultar");
        email = br.readLine();

        return email;
    }
    static class Respuesta {
        public Respuesta(int responseCode, String message) {
            this.responseCode = responseCode;
            this.message = message;
        }
    
        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        } 
        
        public void setMessage(String message) {
            this.message = message;
        }
        
        public int getResponseCode() { return this.responseCode; }
        public String getMessage() { return this.message; }
    
        private int responseCode;
        private String message;
    }
    static class Servicio {
        
        static Respuesta hacerConsulta(String cuerpo, String metodo, String endpoint, String parametro) {
            try {
                URL url = new URL(URL_MAQUINA + endpoint);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                
                conexion.setDoOutput(true);
                conexion.setRequestMethod(metodo);
                conexion.setRequestProperty(REQUEST_KEY, VALUE_KEY);
    
                if(cuerpo.length() > 0 && parametro.length() > 0) { 
                    String parametros = parametro + "=" + URLEncoder.encode(cuerpo, "UTF-8");
                    OutputStream os = conexion.getOutputStream();
                    os.write(parametros.getBytes(), 0, parametros.getBytes().length);
                    os.flush();
                    os.close();
                }
    
                if(conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
                    return new Respuesta(400, "{message: 'No encontrado'}");
    
                BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String respuestaServidor;
                String respuesta = "";
                while((respuestaServidor = br.readLine()) != null) respuesta += respuestaServidor;
                conexion.disconnect();
    
                return new Respuesta(conexion.getResponseCode(), respuesta);
            } catch(Exception e) { e.printStackTrace(); }
            
            return new Respuesta(404, "No encontrado");
        }
    
        private final static String URL_MAQUINA = "http://13.65.184.75:8080/Servicio/rest/ws/";
        private final static String REQUEST_KEY = "Content-Type";
        private final static String VALUE_KEY = "application/x-www-form-urlencoded";
    }
    static class Error {
        String message;
    
        public Error(String message) {
            this.message = message;
        }
    }
    static class Menu {
        public Menu() {}    
    
        protected char mostrarMenu() {
            Scanner s = new Scanner(System.in);
    
            System.out.println("a. Alta usuario");
            System.out.println("b. Consulta usuario");
            System.out.println("c. Borra usuario");
            System.out.println("d. Borra todos los usuarios");
            System.out.println("e. Salir");
    
            char seleccion = s.nextLine().charAt(0);
    
            return seleccion; 
        }
    
        protected void opcion(char op) {
            switch(op) {
                case 'a':
                    altaUsuario();
                    break;
                case 'b':
                    consultaUsuario();
                    break;
                case 'c':
                    borrarUsuario();
                    break;    
                case 'd':
                    borrarTodos();
                    break;
                case 'e':
                    System.exit(0);
                    break;
                default:
                    System.out.println("No hay mas opciones");
                    break;
            }
        }
    
        private void altaUsuario() {
            GsonBuilder builder = new GsonBuilder();
            builder.serializeNulls();
    
            Gson gson = builder.create();
    
            try {
                Usuario usuario = new Usuario();
                usuario = crearUsuario(usuario);
                String cuerpo = gson.toJson(usuario);
                Respuesta response = Servicio.hacerConsulta(cuerpo, POST_METHOD, "alta", "usuario");
                if(response.getResponseCode() != 400) {
                    System.out.println(response.getMessage());
                } else {
                    Error error = gson.fromJson(response.getMessage(), Error.class);
                    System.out.println(error.message);
                }
            } catch(Exception e) { e.printStackTrace(); }
        }
    
        private void consultaUsuario() {
            Gson gson = new Gson();
    
            try {
                String cuerpo = Consultar_usuario();
                Respuesta response = Servicio.hacerConsulta(cuerpo, GET_METHOD, "consulta", "email");
                
                if(response.getResponseCode() != 400) {
                    Usuario usuario = gson.fromJson(response.getMessage(), Usuario.class);
                    usuario.Imprimir_datos();
                } else {
                    Error error = gson.fromJson(response.getMessage(), Error.class);
                    System.out.println(error.message);
                }
            } catch(Exception e) { e.printStackTrace(); }
        }
    
        private void borrarUsuario() {
            Gson gson = new Gson();
    
            try {
                String cuerpo = Consultar_usuario();
                Respuesta response = Servicio.hacerConsulta(cuerpo, POST_METHOD, "borra", "email");
                
                if(response.getResponseCode() != 400) {
                    System.out.println(response.getMessage());
                } else {
                    Error error = gson.fromJson(response.getMessage(), Error.class);
                    System.out.println(error.message);
                }
            } catch(Exception e) { e.printStackTrace(); }
        }
    
        private void borrarTodos() {
            Gson gson = new Gson();
            
            try {
                Respuesta response = Servicio.hacerConsulta("", POST_METHOD, "borrar", "");
                if(response.getResponseCode() != 400) {
                    System.out.println(response.getMessage());
                } else {
                    Error error = gson.fromJson(response.getMessage(), Error.class);
                    System.out.println(error.message);
                }
            } catch(Exception e) { e.printStackTrace(); }
        }
    
        private final String POST_METHOD = "POST";
        private final String GET_METHOD = "GET";
    }
    public static void main(String[] args) {
        Menu Menu = new Menu();
    
        while(true) {
            char e = Menu.mostrarMenu();
            Menu.opcion(e);
        }
    // fin main
    }
// fin class Usuario    
}