import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

public class Ejercicio2 {
    static class Coordenada {
      int x;
      int y;
      int z;
    }

    public static void main(String[] args) throws Exception {
        byte[] a = lee_archivo("coordenadas.txt");
        String recv = new String(a, StandardCharsets.UTF_8);
        Gson j = new Gson();

        Coordenada[] s = j.fromJson(recv, Coordenada[].class);

        // int xMax = 0;
        // int zMax = 0;
        // int yMax = 0;

        float xP = 0;
        float yP = 0;
        float zP = 0;

        for(int i = 0; i < 999; i++) {
            // if(xMax < s[i].x)
            //     xMax = s[i].x;
            // if(yMax < s[i].y)
            //     yMax = s[i].y;
            // if(zMax < s[i].z)
            //     zMax = s[i].z;
            
            xP += s[i].x;
            yP += s[i].y;
            zP += s[i].z;
        }

        System.out.println((xP / 1000) + " " + (yP / 1000) + " " + (zP / 1000));
    }

    static byte[] lee_archivo(String nombre_archivo) throws Exception {
      FileInputStream f = new FileInputStream(nombre_archivo);
      byte[] buffer;
      try
      {
        buffer = new byte[f.available()];
        f.read(buffer);
      }
      finally
      {
        f.close();
      }
      return buffer;
    }
}