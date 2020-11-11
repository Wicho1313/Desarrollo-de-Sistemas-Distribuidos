import java.rmi.Naming;
/**La clase ServidorRMI registra en el rmiregistry 
 * una instancia de la clase ClaseRMI utilizando el método rebind().
 */
public class ServidorRMI{
  public static void main(String[] args) throws Exception{
    String url = "rmi://localhost/prueba";
    ClaseRMI obj = new ClaseRMI();

    // registra la instancia en el rmiregistry
    Naming.rebind(url,obj);
  }
  // Fin de clase ServidorRMI
}