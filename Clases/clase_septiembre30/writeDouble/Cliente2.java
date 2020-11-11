import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Cliente2 {
    public static void main(String[] args) throws Exception{
        
        double dato = 0;
        long tiempoInicio = System.currentTimeMillis();
    
            for (int i = 1; i<=10000; i++){
            
                //se crea el socket
                Socket conexion = new Socket("localhost",50000);
            
                //se crea las transmisiones de entrada y salida para recibir y enviar datos
                DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
            
                System.out.println(i);
                salida.writeDouble(dato++);
            
                salida.close();
                conexion.close();
            }
        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo en ms");
        System.out.println(tiempoFinal - tiempoInicio);
        
    }

}