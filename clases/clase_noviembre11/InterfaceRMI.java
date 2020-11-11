import java.rmi.Remote;
import java.rmi.RemoteException;

/**La interface incluye los prototipos de los métodos a exportar */

public interface InterfaceRMI extends Remote{

    /**mayusculas(), recibe como parámetro una cadena de caracteres 
     * y regresa la misma cadena convertida a mayúsculas. */
    public String mayusculas(String name) throws RemoteException;

    /**suma(), recibe como parámetros dos enteros y regresa la suma. */
    public int suma(int a,int b) throws RemoteException;

    /**checksum(), recibe como parámetro una matriz de enteros 
     * y regresa la suma de todos los elementos de la matriz. */
    public long checksum(int[][] m) throws RemoteException;
}
