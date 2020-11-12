import java.io.IOException;
import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.DatagramSocket;
import java.lang.*;


public class Chat { 

    static class Worker extends Thread{
    
        public void run() {
            // En un ciclo infinito se recibirán los mensajes enviados al grupo 
            // 230.0.0.0 a través del puerto 50000 y se desplegarán en la pantalla.
            for (;;) {
                try {
                    /**Para obtener el grupo invocamos el método getByName() de la clase InetAddress: */
                    InetAddress grupo = InetAddress.getByName("230.0.0.0");
                    /**Luego obtenemos un socket asociado al puerto 50000, creando una instancia de la clase MulticastSocket: */
                    MulticastSocket socket = new MulticastSocket(50000);
                    /**Para que el cliente pueda recibir los mensajes enviados al grupo 230.0.0.0 unimos el socket al grupo 
                     * utilizando el método joinGroup() de la clase MulticastSocket: */
                    socket.joinGroup(grupo);
                    /**Entonces el cliente puede recibir los mensajes enviados al grupo por el servidor. */
                    // System.out.println("Esperando datagrama...");
    
                    /* recibe una string */
                    byte[] a = recibe_mensaje(socket, 100);
                    System.out.println(new String(a,"UTF-8"));
    
                    socket.leaveGroup(grupo);
                    socket.close();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        // fin run()
        }
    }
    public static void main(String[] args) throws Exception{
    
        Worker w = new Worker();
        w.start();
        String nombre = args[0];
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));        
        // En un ciclo infinito se leerá los mensajes del teclado y se enviarán
        // al grupo 230.0.0.0 a través del puerto 50000.
        for (;;) {
            String msg = b.readLine();
            String salida = "-" + nombre + " escribe: " + msg;
            /* Enviando un string */
            envia_mensaje(salida.getBytes(),"230.0.0.0",50000); 

        }
    // Fin main
    }

    /* Recibe_mensaje() al cual pasamos 
    * como parámetros un socket de tipo MulticastSocket y la longitud 
    * del mensaje a recibir (número de bytes). */
    static byte[] recibe_mensaje(MulticastSocket socket,int longitud) throws IOException{
        /**Creamos un paquete vacío como una instancia de la clase DatagramPacket;
         * pasamos como parámetros un arreglo de bytes vacío y el tamaño del arreglo. */
        byte[] buffer = new byte[longitud];
        DatagramPacket paquete = new DatagramPacket(buffer,buffer.length);
        /**Para recibir el paquete invocamos el método recive() de la clase MulticastSocket. 
         * El método recibe_mensaje() regresa el mensaje recibido. */
        socket.receive(paquete);
        return buffer;
    }
    
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
    // fin clase chat
}