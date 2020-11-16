import java.rmi.Naming;
/**El cliente ClienteMatricesRMI obtiene una referencia al objeto remoto 
 * utilizando el método lookup(), esta referencia es utilizada para 
 * invocar los métodos remotos. */
public class ClienteMatricesRMI {
    // Tamaño de la matriz
    static final int N = 4;
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

        // obtiene una referencia que "apunta" al objeto remoto asociado a la URL
        InterfaceMatricesRMI r = (InterfaceMatricesRMI)Naming.lookup(url);
        
        // 1.- Inicializando las matrices
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                A[i][j] = 2 * i - j;
                B[i][j] = 2 * i + j;               
            }
        } 
        r.imprimir_matriz(A, N, N, "A");
        r.imprimir_matriz(B, N, N, "B");
        
        // transpone la matriz B, la matriz traspuesta queda en B
        for (int i = 0; i < N; i++){
            for (int j = 0; j < i; j++){
                int t = B[i][j];
                B[i][j] = B[j][i];
                B[j][i] = t;
            }
        }

        r.imprimir_matriz(B, N, N, "B transpuesta");
        
        A1 = r.parte_matriz(A, 0, N);
        A2 = r.parte_matriz(A, N/2, N);
        B1 = r.parte_matriz(B, 0, N); 
        B2 = r.parte_matriz(B, N/2, N);

        r.imprimir_matriz(A1, N/2, N, "A1");
        r.imprimir_matriz(A2, N/2, N, "A2");
        r.imprimir_matriz(B1, N/2, N, "B1");
        r.imprimir_matriz(B2, N/2, N, "B2");

        C1 = r.multiplica_matrices(A1, B1, N);
        C2 = r.multiplica_matrices(A1, B2, N);
        C3 = r.multiplica_matrices(A2, B1, N);
        C4 = r.multiplica_matrices(A2, B2, N);

        r.imprimir_matriz(C1, N/2, N/2, "C1");
        r.imprimir_matriz(C2, N/2, N/2, "C2");
        r.imprimir_matriz(C3, N/2, N/2, "C3");
        r.imprimir_matriz(C4, N/2, N/2, "C4");

        C= r.acomoda_matriz(C, C1, 0, 0, N);
        C=r.acomoda_matriz(C, C2, 0, N/2, N);
        C=r.acomoda_matriz(C, C3, N/2, 0, N);
        C=r.acomoda_matriz(C, C4, N/2, N/2, N);
        
        if (N == 4){

            r.imprimir_matriz(A, N, N, "A");
            r.imprimir_matriz(B, N, N, "B transpuesta");
            r.imprimir_matriz(C, N, N, "C");
            System.out.println("checksum = " + r.checksum(C));

        } else {
            System.out.println("checksum = " + r.checksum(C));
        }
    // fin main
    }
// fin clase ClienteMatricesRMI    
}
