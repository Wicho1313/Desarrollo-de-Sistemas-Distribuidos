import java.rmi.Naming;

/**El cliente ClienteRMI obtiene una referencia al objeto remoto 
 * utilizando el método lookup(), esta referencia es utilizada para 
 * invocar los métodos remotos. */
public class ClienteRMI{
  public static void main(String args[]) throws Exception{
    // en este caso el objeto remoto se llama "prueba", notar que se utiliza el puerto default 1099
    String url = "rmi://localhost/prueba";

    // obtiene una referencia que "apunta" al objeto remoto asociado a la URL
    InterfaceRMI r = (InterfaceRMI)Naming.lookup(url);

    System.out.println(r.mayusculas("hola"));
    System.out.println("suma=" + r.suma(10,20));

    int[][] m = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
    System.out.println("checksum=" + r.checksum(m));
  }
  // fin clase ClienteRMI
}