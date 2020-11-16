import java.rmi.Naming;

public class ServidorMatricesRMI {
    
    public static void main(String[] args) throws Exception{
        // url con el puerto 1099 por defecto
        String url = "rmi://localhost/matrices";
        // creamos instancia de la clase donde se contiene las definiciones de 
        // nuestros métodos
        ClaseMatricesRMI obj = new ClaseMatricesRMI();
        
        if (args.length != 1) {
            System.err.println("Modo de ejecucion:");
            System.err.println("java ServidorMatricesRMI <nodo>");
            System.exit(0);
        }
        
        int nodo = Integer.valueOf(args[0]);
        
        // registra la instancia en el rmiregistry
        Naming.rebind(url + nodo, obj);
    
    // fin main
    }
// fin clase ServidorMatricesRMI
}
