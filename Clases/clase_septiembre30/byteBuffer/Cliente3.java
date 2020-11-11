import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Cliente3{

    final static int nums = 10000;
    final static int bytes = 8;
    static double dato = 0;

    public static void main(String[] args) throws Exception{
        
        long tiempoInicio = System.currentTimeMillis();
        
        Socket conexion = new Socket("localhost",9000);

        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());

        ByteBuffer b = ByteBuffer.allocate(nums*bytes);
        for (int i = 1; i <= nums;i++)
            b.putDouble(++dato);
            
        byte[] a = b.array();
        salida.write(a);

        salida.close();
        conexion.close();    

        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo en ms");
        System.out.println(tiempoFinal - tiempoInicio);
    }

}