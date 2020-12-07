import java.io.*;

public class UsuarioUtils {
    protected static Usuario crearUsuario(Usuario usuario) throws IOException {
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

        System.out.println("Genero:");
        usuario.setGenero(br.readLine());

        return usuario;
    }

    protected static String leerEmail() throws IOException {
        String email;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Introduce Email a consultar");
        email = br.readLine();

        return email;
    }
}
