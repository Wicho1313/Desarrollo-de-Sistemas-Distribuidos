/*
En este programa los threads acceden a modificar la variable n de forma 
paralela, haciendo que cada vez que se ejecute el programa se imprima
un valor diferente, aunque la velocidad del programa es más rápida. 
*/
class A2 extends Thread
{
  static long n;
  public void run()
  {
    for (int i = 0; i < 100000; i++)
        n++;
  }
  public static void main(String[] args) throws Exception
  {
    A2 t1 = new A2();
    A2 t2 = new A2();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(n);
  }
}