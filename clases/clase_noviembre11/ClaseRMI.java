import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**la clase ClaseRMI va a contener el código de los métodos 
 * definidos en la interface InterfaceRMI. 
 * la clase ClaseRMI es subclase de UnicastRemoteObject
 * e implementa la interface InterfaceRMI. */

public class ClaseRMI extends UnicastRemoteObject implements InterfaceRMI{
  
    // es necesario que el contructor default de la clase ClaseRMI invoque el
    // constructor de la super-clase
    public ClaseRMI() throws RemoteException{
        super( );
    }

    // recibe una cadena y devuelve la misma cadena en mayúscula
    public String mayusculas(String s) throws RemoteException{
        return s.toUpperCase();
    }
    // recibe dos enteros y revuelve la suma de ellos
    public int suma(int a,int b){
        return a + b;
    }
    // recibe una matriz y devuelve la suma de todos sus elementos
    public long checksum(int[][] m) throws RemoteException{
        long s = 0;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                s += m[i][j];
        return s;
    }
  // fin de la ClaseRMI
}
