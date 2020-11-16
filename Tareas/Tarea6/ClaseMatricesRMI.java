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

// fin claseMatricesRMI
}

