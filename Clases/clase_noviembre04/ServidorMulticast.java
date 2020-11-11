import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class ServidorMulticast{
    /* Envia_mensaje() el cual recibe como parámetros un arreglo de bytes (el mensaje), 
        la dirección IP clase D que identifica el grupo al cual se enviará el mensaje, y el número de puerto.
    */
    static void envia_mensaje(byte[] buffer, String ip, int puerto) throws IOException{
        try{
            DatagramSocket socket = new DatagramSocket();
            // Obtenemos el grupo correspondiente a la IP, invocando el método estático getByName() de la clase InetAddress.
            InetAddress grupo = InetAddress.getByName(ip);  
            // Para crear un paquete con el mensaje creamos una instancia de la clase DatagramPacket. 
            // Entonces enviamos el paquete utilizando el método send() de la clase DatagramSocket.
            DatagramPacket paquete = new DatagramPacket(buffer,buffer.length,grupo,puerto);
            socket.send(paquete);
            // Finalmente cerramos el socket invocando el método close().
            socket.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
            
    }

    public static void main(String[] args) throws Exception{
        /* Enviando un string */
        envia_mensaje("hola".getBytes(),"230.0.0.0",50000);
        
        /* Enviando 5 doubles 
        "Empacaremos" los números utilizando un objeto ByteBuffer. Cinco números punto flotante de 64 bits 
        ocupan 5x8 bytes (64 bits=8 bytes). 
        Entonces vamos a crear un objeto de tipo ByteBuffer con una capacidad de 40 bytes: 
        */
        ByteBuffer b = ByteBuffer.allocate(5*8);
        /* Utilizamos el método putDouble para agregar cinco números al objeto ByteBuffer: */
        b.putDouble(1.1);
        b.putDouble(1.2);
        b.putDouble(1.3);
        b.putDouble(1.4);
        b.putDouble(1.5);
        /*
         * Para enviar el paquete de números, convertimos el objeto BytetBuffer a un arreglo de bytes 
         * utilizando el método array() de la clase ByteBuffer. Entonces enviamos el arreglo de bytes 
         * utilizando el método envia_mensaje(), en este caso el mensaje se envía al grupo identificado 
         * por la dirección IP 230.0.0.0 a través del puerto 50000:
         */
        envia_mensaje(b.array(),"230.0.0.0",50000);
    }
}