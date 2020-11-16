import java.rmi.Naming;
/**El cliente ClienteMatricesRMI obtiene una referencia al objeto remoto 
 * utilizando el método lookup(), esta referencia es utilizada para 
 * invocar los métodos remotos. */
public class ClienteMatricesRMI {
    // Tamaño de la matriz
    static final int N = 500;
    // Declaración de matrices originales
    static int[][] A = new int[N][N];
    static int[][] B = new int[N][N];
    static int[][] C = new int[N][N];

    // Declaración de matrices partidas
    static int[][] A1;
    static int[][] A2;
    static int[][] B1;
    static int[][] B2;
    // Declaración de las matrices multiplicadas
    static int[][] C1;
    static int[][] C2;
    static int[][] C3;
    static int[][] C4;
    public static void main(String[] args) throws Exception {
        
        // en este caso el objeto remoto se llama "matrices", notar que se utiliza el puerto default 1099
        String url = "rmi://localhost/matrices";
        
        // Inicializando las matrices
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                A[i][j] = 2 * i - j;
                B[i][j] = 2 * i + j;               
            }
        } 
        
        // transpone la matriz B, la matriz traspuesta queda en B
        for (int i = 0; i < N; i++){
            for (int j = 0; j < i; j++){
                int t = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = t;
            }
        }
        
        // obtiene una referencia que "apunta" al objeto remoto asociado a la URL
        InterfaceMatricesRMI r = (InterfaceMatricesRMI)Naming.lookup(url);

        A1 = parte_matriz(A, 0, N);
        A2 = parte_matriz(A, N/2, N);
        B1 = parte_matriz(B, 0, N); 
        B2 = parte_matriz(B, N/2, N);

        C1 = r.multiplica_matrices(A1, B1, N);
        C2 = r.multiplica_matrices(A1, B2, N);
        C3 = r.multiplica_matrices(A2, B1, N);
        C4 = r.multiplica_matrices(A2, B2, N);

        acomoda_matriz(C, C1, 0, 0);
        acomoda_matriz(C, C2, 0, N/2);
        acomoda_matriz(C, C3, N/2, 0);
        acomoda_matriz(C, C4, N/2, N/2);
        
        if (N == 4){

            imprimir_matriz(A, N, N, "A");
            imprimir_matriz(B, N, N, "B transpuesta");
            imprimir_matriz(C, N, N, "C");
            System.out.println("checksum = " + checksum(C));

        } else {
            System.out.println("checksum = " + checksum(C));
        }
    // fin main
    }    
    /**Método acomoda_matriz
     * Permite construir la matriz C a partir de las matrices C1, C2, C3 y C4.
     * @param C
     * @param A
     * @param renglon
     * @param columna
     */
    static void acomoda_matriz (int[][] C,int[][] A, int renglon, int columna) {
        
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                C[i + renglon][j + columna] = A[i][j];
    // fin método acomoda matriz
    }
    /**
     * Imprime una matriz
     * @param m
     * @param filas
     * @param columnas
     * @param s
     * @throws RemoteException
     */
    static void imprimir_matriz(int[][] m, int filas, int columnas, String s) {
       
        System.out.println("\nImprimiendo " + s);
        for (int i = 0; i< filas; i++){
            for (int j = 0; j < columnas; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
    // fin método imprimir matriz
    }

    /**
     * Método que calcula el checksum
     * @param m
     * @return long checksum
     */
    static long checksum(int[][] m) {
        
        long s = 0;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                s += m[i][j];
        return s;
    // fin método checksum    
    }
    /**Declarado dentro de la Interface.
     * Método que divide una matriz A dado un renglón inicial.
     * @param A
     * @param inicio
     * @return An[N/2][N]
     */
    static int[][] parte_matriz(int[][] A,int inicio, int N) {
        
        int[][] M = new int[N/2][N];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    
    }
// fin clase ClienteMatricesRMI    
}
