import java.rmi.Remote;
import java.rmi.RemoteException;

/**La interface incluye los prototipos de los métodos a exportar */
public interface InterfaceMatricesRMI extends Remote{
    
    /**Declarado dentro de la Interface.
     * Método que multiplica 2 matrices de tamaño [N/2][N].
     * @param A
     * @param B
     * @return AxB[N/2][N/2]
     * @throws RemoteException
     */
    public int[][] multiplica_matrices(int[][] A, int[][] B, int N) throws RemoteException;
    
    /**Declarado dentro de la Interface.
     * Método que divide una matriz A dado un renglón inicial.
     * @param A
     * @param inicio
     * @return An[N/2][N]
     */
    public int[][] parte_matriz(int[][] A, int inicio, int N) throws RemoteException;

    /**
     * Imprime una matriz
     * @param m
     * @param filas
     * @param columnas
     * @param s
     */
    public void imprimir_matriz(int[][] m, int filas, int columnas, String s) throws RemoteException;

    /**
     * Calcula el checksum de una matriz
     * @param m
     * @return long (checksum)
     * @throws RemoteException
     */
    public long checksum(int[][] m) throws RemoteException;
// fin interface
}
