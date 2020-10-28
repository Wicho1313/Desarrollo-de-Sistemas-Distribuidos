import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.lang.Thread;
import java.nio.ByteBuffer;

class pi{
    
    static Object lock = new Object();
    static double pi = 0;
    
    static class Worker extends Thread{
        Socket conexion;

        Worker(Socket conexion){
        
            this.conexion = conexion;
        }
        
        public void run(){
            // Algoritmo 1 //
            try {
                // 1. Crear streams de entrada y salida
                DataInputStream entrada = new DataInputStream(conexion.getInputStream());
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
                // 2. Declarar la variable "x" de tipo double.
                double x = 0;
                // 3. Recibir en la variable "x" la suma calculada por el cliente.
                x = entrada.readDouble();
                System.out.println("Recibi la suma calculada por el cliente: "+x);
                // 4. En un bloque synchronized mediante el objeto "lock":
                synchronized(lock){
                    // 4.1 Asignar a la variable "pi" la expresión: x+pi
                    pi = x + pi;
                }

                // 5. Cerrar los streams de entrada y salida.
                entrada.close();
                salida.close();
                // 6. Cerrar la conexión "conexion".
                conexion.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
  
  public static void main(String[] args) throws Exception {
    if (args.length != 1){
      System.err.println("Uso:");
      System.err.println("java pi <nodo>");
      System.exit(0);
    }
    int nodo = Integer.valueOf(args[0]);
    if (nodo == 0){

        // Algoritmo 2 //

        // 1. Declarar una variable "servidor" de tipo ServerSocket.
        // 2. Crear un socket servidor utilizando el puerto 50000 y asignarlo a la variable "servidor".
        ServerSocket servidor = new ServerSocket(50000);
        // 3. Declarar un vector "w" de tipo Worker con 3 elementos.
        Worker[] w = new Worker[3];
        // 4. Declarar una variable entera "i" y asignarle cero.
        int i = 0;
        // 5. En un ciclo
        // 5.1 Si la variable "i" es igual a 3, entonces salir del ciclo.
        while (i != 3){
    
            // 5.2 Declarar una variable "conexion" de tipo Socket.
            Socket conexion;        
            // 5.3 Invocar el método servidor.accept() y asignar el resultado a la variable "conexion".
            conexion = servidor.accept();
            // 5.4 Crear una instancia de la clase Worker, pasando como parámetro la variable "conexion". 
            // Asignar la instancia al elemento w[i].
            w[i] = new Worker(conexion);
            // 5.5 Invocar el método w[i].start()
            w[i].start();
            // 5.6 Incrementar la variable "i".
            i++;
            // 5.7 Ir al paso 5.1
        }

        // 6. Declarar la variable "suma" de tipo double y asignarle cero.
        double suma = 0;
        // 7. Declarar la variable "i" de tipo entero y asignarle cero.
        i = 0;
        // 8. En un ciclo:
        // 8.1 Si la variable "i" es igual a 10000000, entonces salir del ciclo.
        while (i != 10000000){

            // 8.2 Asignar a la variable "suma" la expresión:  4.0/(8*i+1)+ suma
            suma = 4.0/(8 * i + 1) + suma;
            // 8.3 Incrementar la variable "i".
            i++;
            // 8.4 Ir al paso 8.1
        }

        // 9. En un bloque synchronized mediante el objeto "lock":
        synchronized (lock){
            // 9.1 Asignar a la variable "pi" la expresión: suma+pi
            pi = suma + pi;
        }

        // 10. Declarar una variable "i" entera y asignarle cero.
        i = 0;
        // 11. En un ciclo:
        // 11.1 Si la variable "i" es igual a 3, entonces salir del ciclo.
        while (i != 3){

            // 11.2 Invocar el método w[i].join()
            w[i].join();
            // 11.3 Incrementar la variable "i".
            i++;
            // 11.4 Ir al paso 11.1
        }

        servidor.close();
        // 12. Desplegar el valor de la variable "pi".
        System.out.println("El valor de la variable pi es: "+pi);
    
    }else{
      
        // Algoritmo 3 // -> cliente
        // 1. Declarar la variable "conexion" de tipo Socket y asignarle null.
        Socket conexion = null;
        // 2. Realizar la conexión con el servidor implementando re-intento. Asignar el socket a la variable "conexion".
        for(;;)
            try{
                conexion = new Socket("localhost",50000);
                break;
            }catch (Exception e){
                Thread.sleep(100);
            }
        // 3. Crear los streams de entrada y salida.
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        // 4. Declarar la variable "suma" de tipo double y asignarle cero.
        double suma = 0;
        // 5. Declarar una variable "i" de tipo entero y asignarle cero.
        int i = 0;
        // 6. En un ciclo:
        // 6.1 Si la variable "i" es igual a 10000000, entonces salir del ciclo.
        while (i != 10000000){
            // 6.2 Asignar a la variable "suma" la expresión:  4.0/(8*i+2*(nodo-1)+3)+suma
            suma = 4.0 / ( 8 * i + 2 * (nodo - 1) + 3) + suma;
            // 6.3 Incrementar la variable "i".
            i++;
            // 6.4 Ir al paso 6.1
        }

        // 7. Asignar a la variable "suma" la expresión:  nodo%2==0?suma:-suma
        suma = nodo % 2 == 0 ? suma: (-1) * suma;
        // 8. Enviar al servidor el valor de la variable "suma".
        salida.writeDouble(suma);
        // 9. Cerrar los streams de entrada y salida.
        entrada.close();
        salida.close();
        // 10. Cerrar la conexión "conexion".        
        conexion.close();
    }
  }
}