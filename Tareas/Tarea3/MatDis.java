import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.lang.Thread;

class MatDis{
    
    static Object lock = new Object();
    static int N = 1000;
    static int i = 0, j = 0, k=0;
    // Declaramos matrices originales
    static double[][] A;
    static double[][] B;
    static double[][] C;
    

    static class Worker extends Thread{
        Socket conexion;

        Worker(Socket conexion){
        
            this.conexion = conexion;
        }
        
        public void run(){
            // La lógica se ejecuta dentro de un bloque try-catch
            try {
                // Declaramos la matriz A1 y A2, B1, B2
                double[][] A1 = new double[N/2][N];
                double[][] A2 = new double[N/2][N];
                double[][] B1 = new double[N/2][N];
                double[][] B2 = new double[N/2][N];
                //Creamos la transmisión de entrada para recibir las matrices
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());                
                // Creamos la transmisión de salida para enviar la matrices 
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                // Recibe en la variable x el nodo 
                int x = entrada.readInt();
                // Enviar las matrices dependiendo el nodo
                if (x == 1){
                    for(i = 0; i < (N/2); i++){
                        for(j = 0; j < N; j++){
                            A1 [i][j] = A [i][j];
                            B1 [i][j] = B [i][j];
                            salida.writeDouble(A1[i][j]); // 2. Enviar la matriz A1 al nodo 1
                            salida.writeDouble(B1[i][j]); // 3. Enviar la matriz B1 al nodo 1
                        }
                    }
  
                    if (N == 4){ 
                    //Mensaje del nodo 0: 
                    System.out.println("\nSe conecto el nodo 1.\nEnvio la matriz A1 al nodo 1: ");
                    imprimir_matriz(A1,N/2,N);
                    System.out.println("\nEnvio la matriz B1 al nodo 1: ");
                    imprimir_matriz(B1,N/2,N);
                    }
                } else if (x == 2){
                    for(i = 0; i < (N/2); i++){
                        for(j = 0; j < N; j++){
                            A1 [i][j] = A [i][j];
                            salida.writeDouble(A1[i][j]); // 4. Enviar la matriz A1 al nodo 2.
                        }
                    }
                    for(i = (N/2); i < N; i++){
                        for(j = 0; j < N; j++){
                            B2 [i - (N/2)][j] = B [i][j];
                            salida.writeDouble(B2[i - (N/2)][j]); // 5. Enviar la matriz B2 al nodo 2.
                        }
                    }
                    //Mensaje del nodo 0: 
                    if (N==4){
                    System.out.println("\nSe conecto el nodo 2.\nEnvio la matriz A1 al nodo 2: ");
                    imprimir_matriz(A1,N/2,N);
                    System.out.println("\nEnvio la matriz B2 al nodo 2: ");
                    imprimir_matriz(B2,N/2,N);
                    }
                } else if (x == 3){
                    for(i = (N/2); i < N; i++){
                        for(j = 0; j < N;j++){
                            A2 [i - (N/2)][j] = A [i][j];
                            salida.writeDouble(A2[i - (N/2)][j]); // 6. Enviar la matriz A2 al nodo 3.
                        }
                    }       
                     
                    for(i = 0; i < (N/2); i++){
                        for(j = 0; j < N; j++){
                            B1 [i][j] = B [i][j];
                            salida.writeDouble(B1[i][j]); // 7. Enviar la matriz B1 al nodo 3.
                        }
                    }
                    if (N == 4){
                        //Mensaje del nodo 0: 
                        System.out.println("\nSe conecto el nodo 3.\nEnvio la matriz A2 al nodo 3: ");
                        imprimir_matriz(A2,N/2,N);
                        System.out.println("\nEnvio la matriz B1 al nodo 3: ");
                        imprimir_matriz(B1,N/2,N);
                    }
                } else if (x == 4){                    
                    for(i = (N/2); i < N; i++){
                        for(j = 0; j < N;j++){
                            A2 [i - (N/2)][j] = A [i][j];
                            B2 [i - (N/2)][j] = B [i][j];
                            salida.writeDouble(A2[i - (N/2)][j]); // 8. Enviar la matriz A2 al nodo 4.
                            salida.writeDouble(B2[i - (N/2)][j]); // 9. Enviar la matriz B2 al nodo 4.
                        }
                    }    
                    //Mensaje del nodo 0: 
                    if (N == 4){
                        System.out.println("\nSe conecto el nodo 4.\nEnvio la matriz A2 al nodo 4: ");
                        imprimir_matriz(A2,N/2,N);
                        System.out.println("\nEnvio la matriz B2 al nodo 4: ");
                        imprimir_matriz(B2,N/2,N);
                    }
                }                
                // Depende el nodo, recibe dentro de un bloque synchronized mediante el objeto "lock":
                synchronized(lock){
                    if(x == 1){             // 10. Recibe la matriz C1 del nodo 1
                        double[][] C1 = new double[N/2][N/2];
                        for(i = 0; i < (N/2); i++){
                            for(j = 0; j < (N/2); j++){
                                C1[i][j] = entrada.readDouble(); // Lo que recibe lo asigna al bloque C1
                                C[i][j] = C1[i][j]; // El bloque lo coloca en la matriz C (original)
                            }
                        }
                        // Mensaje del nodo 0.
                        if (N ==4){
                            System.out.println("\nRecibi la matriz C1 del nodo 1");
                            imprimir_matriz(C,N,N);
                        } 
                    }else if (x == 2){      // 11. Recibe la matriz C2 del nodo 2
                        double[][] C2 = new double[N/2][N];
                        for(i = 0; i < (N/2); i++){
                            for(j = (N/2); j < N; j++){
                                C2[i][j] = entrada.readDouble();
                                C[i][j] = C2[i][j];
                            }
                        }    
                        // Mensaje del nodo 0.
                        if (N ==4){
                            System.out.println("\nRecibi la matriz C2 del nodo 2");
                            imprimir_matriz(C,N,N);
                        }
                    }else if (x == 3){      // 12. Recibe la matriz C3 del nodo 3
                        double[][] C3 = new double[N][N/2];
                        for(i = (N/2); i < N; i++){
                            for(j = 0; j < (N/2); j++){
                                C3[i - (N/2)][j] = entrada.readDouble();
                                C[i][j] = C3[i - (N/2)][j];
                            }
                        }                                 
                        // Mensaje del nodo 0.
                        if (N ==4){
                            System.out.println("\nRecibi la matriz C3 del nodo 3");
                            imprimir_matriz(C,N,N);
                        }
                    }else if (x == 4){      // 13. Recibe la matriz C4 del nodo 4
                        double[][] C4 = new double[N][N];
                        for(i = (N/2); i < N; i++){
                            for(j = (N/2); j < N; j++){
                                C4[i - (N/2)][j - (N/2)] = entrada.readDouble();
                                C[i][j] = C4[i - (N/2)][j - (N/2)];
                            }
                        }
                        // Mensaje del nodo 0.
                        if (N ==4){
                            System.out.println("\nRecibi la matriz C4 del nodo 4");
                            imprimir_matriz(C,N,N);
                        }

                    }

                }            
                // Mensaje de término de conexión
                System.out.print("\nTermina conexion con el nodo: " + x);
                // Cerramos la conexion y los flujos de entrada y salida
                entrada.close();
                salida.close();
                conexion.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
  
    public static void main(String[] args) throws Exception {
        if (args.length != 1){
        System.err.println("Modo de ejecucion:");
        System.err.println("java MatDis <nodo>");
        System.exit(0);
        }
        int nodo = Integer.valueOf(args[0]);
        // El nodo es el argumento de cuando se ejecuta el programa
        if (nodo == 0){
            A = new double[N][N];
            B = new double[N][N];
            C = new double[N][N];
            // 1.- Inicializando las matrices
            for (i = 0; i < N; i++){
                for (j = 0; j < N; j++){
                    A[i][j] = 2 * i + j;
                    B[i][j] = 2 * i - j;
                    C[i][j] = 0;
                    
                }
            }                
            // transpone la matriz B, la matriz traspuesta queda en B
            for (i = 0; i < N; i++){
                for (j = 0; j < i; j++){
                    double t = B[i][j];
                    B[i][j] = B[j][i];
                    B[j][i] = t;
                }
            }
            if (N == 4){
                // imprimiendo A
                System.out.println("\nMatriz A: ");
                imprimir_matriz(A,N,N);
                // imprimiendo la matriz B
                System.out.println("\nMatriz B 'Transpuesta': ");
                imprimir_matriz(B,N,N);
            }
            // Mensaje del nodo 0 de espera
            System.out.println("\nEsperando por conexiones...");
            // creamos el servidor 
            ServerSocket servidor = new ServerSocket(5000);
            // aceptaremos 4 clientes
            Worker[] w = new Worker[4];
            
            int s = 0;
            while (s != 4){
                // Una conexión por cada nodo
                Socket conexion = servidor.accept();
                w[s] = new Worker(conexion);
                w[s].start();
                s++;
            }
            // Esperamos a que se ejecute el hilo
            int z = 0;
            while (z != 4){
                w[z].join();
                z++;
            }
            // si N = 4 imprimimos las matrices
            
            // mensaje del nodo 0
            System.out.println("\nHe dejado de recibir concexiones... ");
            // cerramos el servidor
            servidor.close();
            // 14. Calcular el checksum de la matriz C.
            double checksum = 0;
            for( i = 0; i < N; i++){
                for( j = 0; j < N; j++){
                    checksum += C[i][j];
                }
            }// 15. Desplegar el checksum de la matriz C.
            System.out.println("El check sum es: " + checksum + " ");
            // 16. Si N=4 entonces desplegar la matriz C=AxB
            if (N == 4){
                System.out.println("Desplegando matriz C = A x B");
                for( i = 0; i < N; i++) {
                    for( j = 0; j < N; j++) {
                        System.out.print(C[i][j] + " ");
                    } 
                    System.out.println("");
                }
        
                System.out.println("");                
            }
        
        }else{
            // Declaramos las matrices donde se recibirán las originales
            double[][] rA = new double[N/2][N];
            double[][] rB = new double[N/2][N];
            // declaramos la matriz en donde guardaremos el producto de A1 x B1
            double[][] pC = new double[N/2][N/2];
            if(nodo == 1){
                // creamos la concexión con el socket
                Socket conexion = new Socket("localhost", 5000);
                // Creamos las transmisiones de entrada y salida
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                // Enviamos al servidor (nodo 0) el numero de nodo en el que estamos
                salida.writeInt(nodo);
                                 
                for (i = 0; i < (N/2); i++){
                    for (j = 0; j < N; j++){
                        // 1. Recibir del nodo 0 la matriz A1.
                        rA[i][j] = entrada.readDouble(); 
                         // 2. Recibir del nodo 0 la matriz B1.
                        rB[i][j] = entrada.readDouble(); 
                
                    }
                }
                
                //Mensaje del nodo 1
                if (N == 4){
                    System.out.println("\nRecibi matriz A1 del nodo 0: ");
                    imprimir_matriz(rA,N/2,N);
                    System.out.println("\nRecibi matriz B1 del nodo 0: ");
                    imprimir_matriz(rB,N/2,N);
                }
                // 3. Realizar el producto C1=A1xB1.
                for ( i = 0; i < (N/2); i++){
                    for (j = 0; j < (N/2); j++){
                        for (k = 0; k < N; k++){
                            pC[i][j] += rA[i][k] * rB[j][k];
                        }
                    }
                }    
                // 4. Enviar la matriz C1 al nodo 0.
                for( i = 0; i < (N / 2); i++){
                    for( j = 0; j < (N / 2); j++){  
                        salida.writeDouble(pC[i][j]);
                    }
                }
                // mensaje
                if (N ==4){
                    System.out.println("\nEnvio la matriz C1 al nodo 0: ");
                    imprimir_matriz(pC,N/2,N/2);
                } 
                // cerramos las transmisiones de entrada, salida y la conexión
                entrada.close();
                salida.close();
                conexion.close();
            }else if (nodo == 2){
                // creamos la concexión con el socket
                Socket conexion = new Socket("localhost", 5000);
                // Creamos las transmisiones de entrada y salida
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                // Enviamos al servidor (nodo 0) el numero de nodo en el que estamos
                salida.writeInt(nodo);
                //Empezamos a recibir las matrices que envió el nodo 0. 
                for(i = 0; i < (N/2); i++){
                    for(j = 0; j < N; j++){
                        // 1. Recibir del nodo 0 la matriz A1.
                        rA[i][j] = entrada.readDouble();
                    }    
                }
                for(i = (N/2); i < N; i++){
                    for(j = 0; j < N; j++){
                        // 2. Recibir del nodo 0 la matriz B2.
                        rB[i-(N/2)][j] = entrada.readDouble();
                    }    
                } 
                if (N ==4) { 
                    //Mensaje del nodo 2
                    System.out.println("\nRecibi matriz A1 del nodo 0: ");
                    imprimir_matriz(rA,N/2,N);
                    System.out.println("\nRecibi matriz B2 del nodo 0: ");
                    imprimir_matriz(rB,N/2,N);
                }
                // 3. Realizar el producto C2=A1xB2.
                for ( i = 0; i < (N/2); i++){
                    for (j = 0; j < (N/2); j++){
                        for (k = 0; k < N; k++){
                            pC[i][j] += rA[i][k] * rB[j][k];
                        }
                    }
                }
                // 4. Enviar la matriz C2 al nodo 0.
                for( i = 0; i < (N / 2); i++){
                    for( j = 0; j < (N / 2); j++){
                        salida.writeDouble(pC[i][j]);               
                    }
                }
                // mensaje
                if (N ==4) { 
                    System.out.println("\nEnvio la matriz C2 al nodo 0: ");
                    imprimir_matriz(pC,N/2,N/2);
                }
                // cerramos las transmisiones de entrada, salida y la conexión
                entrada.close();
                salida.close();
                conexion.close();
            }else if (nodo == 3){
                // creamos la concexión con el socket
                Socket conexion = new Socket("localhost", 5000);
                // Creamos las transmisiones de entrada y salida
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                // Enviamos al servidor (nodo 0) el numero de nodo en el que estamos
                salida.writeInt(nodo);
                 
                for( i = (N/2); i < N; i++){
                    for( j = 0; j < N; j++){
                        // 1. Recibir del nodo 0 la matriz A2.
                        rA[i-(N/2)][j] = entrada.readDouble();
                    }
                }
                for( i = 0; i < (N/2); i++){
                    for( j = 0; j < N; j++){
                        // 1. Recibir del nodo 0 la matriz A2.
                        // rA[i-(N/2)][j] = entrada.readDouble();
                        // 2. Recibir del nodo 0 la matriz B1.
                        rB[i][j] = entrada.readDouble();
                    }
                }
                 
                //Mensaje del nodo 3
                if (N ==4){
                    System.out.println("\nRecibi matriz A2 del nodo 0: ");
                    imprimir_matriz(rA,N/2,N);
                    System.out.println("\nRecibi matriz B1 del nodo 0: ");
                    imprimir_matriz(rB,N/2,N);
                }

                // 3. Realizar el producto C3=A2xB1.
                for ( i = 0; i < (N/2); i++){
                    for (j = 0; j < (N/2); j++){
                        for (k = 0; k < N; k++){
                            pC[i][j] += rA[i][k] * rB[j][k];
                        }
                    }
                }
                // 4. Enviar la matriz C3 al nodo 0.
                for(i = 0; i < (N/2); i++){
                    for(j = 0; j < (N/2); j++){
                        salida.writeDouble(pC[i][j]);
                    }
                }
                // mensaje
                if (N ==4){
                    System.out.println("\nEnvio la matriz C3 al nodo 0: ");
                    imprimir_matriz(pC,N/2,N/2);
                }

                // cerramos flujos de entrada, salida y la conexión.
                entrada.close();
                salida.close();
                conexion.close();
            }else if (nodo == 4){
                // creamos la concexión con el socket
                Socket conexion = new Socket("localhost", 5000);
                // Creamos las transmisiones de entrada y salida
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                // Enviamos al servidor (nodo 4) el numero de nodo en el que estamos
                salida.writeInt(nodo);
                 
                for(i = (N/2); i < N; i++){
                    for(j = 0; j < N; j++){
                        // 1. Recibir del nodo 0 la matriz A2.
                        rA[i-(N/2)][j] = entrada.readDouble();
                        // 2. Recibir del nodo 0 la matriz B2.
                        rB[i-(N/2)][j] = entrada.readDouble();
                    }
                }
                //Mensaje del nodo 3
                if (N ==4){
                    System.out.println("\nRecibi matriz A2 del nodo 0: ");
                    imprimir_matriz(rA,N/2,N);
                    System.out.println("\nRecibi matriz B2 del nodo 0: ");
                    imprimir_matriz(rB,N/2,N);
                }

                // 3. Realizar el producto C4=A2xB2.
                for ( i = 0; i < (N/2); i++){
                    for (j = 0; j < (N/2); j++){
                        for (k = 0; k < N; k++){
                            pC[i][j] += rA[i][k] * rB[j][k];
                        }
                    }
                }
                // 4. Enviar la matriz C4 al nodo 0.
                for( i = 0; i < (N/2); i++){
                    for( j = 0; j < (N/2); j++){
                        salida.writeDouble(pC[i][j]);
                    }
                }
                // mensaje
                if (N ==4){
                    System.out.println("\nEnvio la matriz C4 al nodo 0: ");
                    imprimir_matriz(pC,N/2,N/2);
                } 
                // Cerramos los flujos de entrada y salida, así como la conexión
                entrada.close();
                salida.close();
                conexion.close();
            }
                     
        }
    }
    // Método imprimir matriz, recibe una matriz a imprimir y sus filas y columnas a imprimir
    private static void imprimir_matriz(double[][] m, double filas, double columnas) {
        for (i = 0; i< filas; i++){
            for (j = 0; j < columnas; j++){
                System.out.print(m[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
