public class Principal {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();

        while(true) {
            char e = consumer.mostrarMenu();
            consumer.opcion(e);
        }
    }
}
