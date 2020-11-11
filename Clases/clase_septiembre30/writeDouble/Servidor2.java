import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Servidor2{
    public static void main(String[] args) throws Exception{

    //se crea el servidor
    ServerSocket servidor = new ServerSocket(50000);
    
    //se toma el tiempo
    long tiempoInicio = System.currentTimeMillis();

    for(int i = 1; i <= 10000; i++){
        //Se crea el socket
        Socket conexion = servidor.accept();

        //Se crea las transmisiones de entrada y salida para enviar y recibir datos
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        // recibe un numero punto flotante
        double x = entrada.readDouble();
        System.out.println(x);

        entrada.close();
        conexion.close();
    }
    long tiempoFinal = System.currentTimeMillis();
    System.out.println("El tiempo en ms");
    System.out.println(+tiempoFinal - tiempoInicio);

    }

}