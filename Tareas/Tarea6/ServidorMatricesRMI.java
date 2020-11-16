import java.rmi.Naming;

public class ServidorMatricesRMI {
    
    public static void main(String[] args) throws Exception{
        String url = "rmi://localhost/matrices";
        ClaseMatricesRMI obj = new ClaseMatricesRMI();
    
        // registra la instancia en el rmiregistry
        Naming.rebind(url,obj);
    
    // fin main
    }
// fin clase ServidorMatricesRMI
}
