import java.io.*;
import java.net.*;

public class Ejercicio {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("sisdis.sytes.net", 10000);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            dos.writeInt(1);
            dos.writeInt(20);
            dos.writeDouble(200.0);

            System.out.println(dis.readInt());

            dos.close();
            dis.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}