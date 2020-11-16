import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**La clase ClaseRMI va a contener el código de los métodos 
 * definidos en la interface InterfaceMatricesRMI. 
 * la clase ClaseRMI es subclase de UnicastRemoteObject
 * e implementa la interface InterfaceMatricesRMI. */
public class ClaseMatricesRMI extends UnicastRemoteObject implements InterfaceMatricesRMI {
    
    // es necesario que el contructor default de la clase ClaseRMI invoque el
    // constructor de la super-clase
    public ClaseMatricesRMI() throws RemoteException{
        super( );
    }

    
    public int[][] multiplica_matrices(int[][] A, int[][] B, int N) throws RemoteException {

        int[][] C = new int[N/2][N/2];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                for (int k = 0; k < N; k++)
                    C[i][j] += A[i][k] * B[j][k];
        return C;     
    }

    public int[][] parte_matriz(int[][] A,int inicio, int N) throws RemoteException {
        
        int[][] M = new int[N/2][N];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    
    }

    public int[][] acomoda_matriz(int[][] C,int[][] A, int renglon, int columna, int N) throws RemoteException {
        
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                C[i + renglon][j + columna] = A[i][j];

        // imprimir_matriz(C, N, N, "C");
        return C;
    }

    public void imprimir_matriz(int[][] m, int filas, int columnas, String s) throws RemoteException {
       
        System.out.println("\nImprimiendo " + s);
        for (int i = 0; i< filas; i++){
            for (int j = 0; j < columnas; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
    }
    public long checksum(int[][] m) throws RemoteException{
        
        long s = 0;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                s += m[i][j];
        return s;
    }
// fin claseMatricesRMI
}
