import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.Scanner;

public class Consumer {
    public Consumer() {}    

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
            usuario = UsuarioUtils.crearUsuario(usuario);
            String cuerpo = gson.toJson(usuario);
            ResponseModel response = GenericServices.hacerConsulta(cuerpo, POST_METHOD, "alta", "usuario");
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
            String cuerpo = UsuarioUtils.leerEmail();
            ResponseModel response = GenericServices.hacerConsulta(cuerpo, GET_METHOD, "consulta", "email");
            
            if(response.getResponseCode() != 400) {
                Usuario usuario = gson.fromJson(response.getMessage(), Usuario.class);
                System.out.println(usuario.toString());
            } else {
                Error error = gson.fromJson(response.getMessage(), Error.class);
                System.out.println(error.message);
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private void borrarUsuario() {
        Gson gson = new Gson();

        try {
            String cuerpo = UsuarioUtils.leerEmail();
            ResponseModel response = GenericServices.hacerConsulta(cuerpo, POST_METHOD, "borra", "email");
            
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
            ResponseModel response = GenericServices.hacerConsulta("", POST_METHOD, "borrar", "");
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