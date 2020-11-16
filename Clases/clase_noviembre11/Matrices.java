
class Matrices {
    static final int N = 4;

    static void acomoda_matriz(int[][] C,int[][] A,int renglon,int columna) {
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                C[i + renglon][j + columna] = A[i][j];
            
        imprimir_matriz(C, N, N, "C");
        
    }
    
    static int[][] parte_matriz(int[][] A,int inicio) {
        int[][] M = new int[N/2][N];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N; j++)
                M[i][j] = A[i + inicio][j];
        return M;
    }

    public static int[][] multiplica_matrices(int[][] A,int[][] B) {
        int[][] C = new int[N/2][N/2];
        for (int i = 0; i < N/2; i++)
            for (int j = 0; j < N/2; j++)
                for (int k = 0; k < N; k++)
                    C[i][j] += A[i][k] * B[j][k];
        return C;
    }

    // Método imprimir matriz, recibe una matriz a imprimir y sus filas y columnas a imprimir
    public static void imprimir_matriz(int[][] m, int filas, int columnas, String s) {
       
        System.out.println("\nImprimiendo " + s);
        for (int i = 0; i< filas; i++){
            for (int j = 0; j < columnas; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
    }
    // recibe una matriz y devuelve la suma de todos sus elementos
    public static long checksum(int[][] m){
        long s = 0;
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                s += m[i][j];
        return s;
    }

    public static void main(String[] args) {
        int[][] A = new int[N][N];
        int[][] B = new int[N][N];
        // 1.- Inicializando las matrices
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                A[i][j] = 2 * i + j;
                B[i][j] = 2 * i - j;                
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

        imprimir_matriz(A, N, N, "A");
        imprimir_matriz(B, N, N, "B");

        int[][] A1 = parte_matriz(A,0);
        int[][] A2 = parte_matriz(A,N/2);
        int[][] B1 = parte_matriz(B,0);
        int[][] B2 = parte_matriz(B,N/2);

        imprimir_matriz(A1, N/2, N, "A1");
        imprimir_matriz(A2, N/2, N, "A2");
        imprimir_matriz(B1, N/2, N, "B1");
        imprimir_matriz(B2, N/2, N, "B2");

        int[][] C1 = multiplica_matrices(A1,B1);
        int[][] C2 = multiplica_matrices(A1,B2);
        int[][] C3 = multiplica_matrices(A2,B1);
        int[][] C4 = multiplica_matrices(A2,B2);

        imprimir_matriz(C1, N/2, N/2, "C1");
        imprimir_matriz(C2, N/2, N/2, "C2");
        imprimir_matriz(C3, N/2, N/2, "C3");
        imprimir_matriz(C4, N/2, N/2, "C4");

        int[][] C = new int[N][N];
        acomoda_matriz(C,C1,0,0);
        acomoda_matriz(C,C2,0,N/2);
        acomoda_matriz(C,C3,N/2,0);
        acomoda_matriz(C,C4,N/2,N/2);

        System.out.println("\nChecksum = " + checksum(C));
        

    // FIN MAIN    
    }
// FIN CLASE MATRICES    
}