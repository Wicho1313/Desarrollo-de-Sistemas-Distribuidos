import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Servidor3{

    final static int nums = 10000;
    final static int bytes = 8;

    static void read(DataInputStream f,byte[] b,int posicion,int longitud) throws Exception
  {
    while (longitud > 0)
    {
      int n = f.read(b,posicion,longitud);
      posicion += n;
      longitud -= n;
    }
  }
    public static void main(String[] args) throws Exception{

        long tiempoInicio = System.currentTimeMillis();

        ServerSocket servidor = new ServerSocket(9000);

        Socket conexion = servidor.accept();

        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        byte[] a = new byte[nums*bytes];
        read(entrada,a,0,nums*bytes);
        ByteBuffer b = ByteBuffer.wrap(a);
        for (int i = 1; i <= nums; i++)
            System.out.println(b.getDouble());    

        entrada.close();
        conexion.close();

        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo en ms");
        System.out.println(tiempoFinal - tiempoInicio);
    }
    
}