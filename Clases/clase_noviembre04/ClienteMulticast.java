import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class ClienteMulticast{
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

  public static void main(String[] args) throws Exception{
    /**Para obtener el grupo invocamos el método getByName() de la clase InetAddress: */
    InetAddress grupo = InetAddress.getByName("230.0.0.0");
    /**Luego obtenemos un socket asociado al puerto 50000, creando una instancia de la clase MulticastSocket: */
    MulticastSocket socket = new MulticastSocket(50000);
    /**Para que el cliente pueda recibir los mensajes enviados al grupo 230.0.0.0 unimos el socket al grupo 
     * utilizando el método joinGroup() de la clase MulticastSocket: */
    socket.joinGroup(grupo);
    /**Entonces el cliente puede recibir los mensajes enviados al grupo por el servidor. */
    System.out.println("Esperando datagrama...");

    /* recibe una string */
    byte[] a = recibe_mensaje(socket, 4);
    System.out.println(new String(a,"UTF-8"));

    /* recibe 5 doubles */
    byte[] buffer = recibe_mensaje(socket,5*8);
    ByteBuffer b = ByteBuffer.wrap(buffer);

    for (int i = 0; i < 5; i++)
      System.out.println(b.getDouble());

    socket.leaveGroup(grupo);
    socket.close();
  }
}