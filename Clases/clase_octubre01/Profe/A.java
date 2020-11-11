/*
Clase A que contiene un thread el cual ejecuta un for donde aumenta un numero
al usar el método syncrhonized hace que los hilos que se crean no modifiquen
la variable n cada que se ejecuta, por lo que entra primero un hilo, ejecuta
y después entra el otro y ejectua, cambiando la variable un thread a la vez
haciendo que el programa sea más lento. (se ejecuta en serie y no en paralelo).
*/
class A extends Thread
{
  static long n;
  static Object obj = new Object();
  public void run()
  {
    for (int i = 0; i < 100000; i++)
    synchronized(obj)
    {
      n++;
    }
  }
  public static void main(String[] args) throws Exception
  {
    A t1 = new A();
    A t2 = new A();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(n);
  }
}