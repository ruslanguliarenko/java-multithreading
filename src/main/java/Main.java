import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static void main(String[] args){

        Mutex producerMutex = new Mutex();
        Mutex consumerMutex = new Mutex();

        int maxSize = 10;

        ConcurrentLinkedQueue<Integer> integers = new ConcurrentLinkedQueue<>();

        Monitor monitor = new Monitor(integers, consumerMutex, producerMutex, 2, 8);

        Producer producer1 = new Producer(integers, producerMutex, maxSize);
        Producer producer2 = new Producer(integers, producerMutex, maxSize);

        Consumer consumer1 = new Consumer(integers, consumerMutex);
        Consumer consumer2 = new Consumer(integers, consumerMutex);
        Consumer consumer3 = new Consumer(integers, consumerMutex);

        consumer1.start();
        consumer2.start();
        consumer3.start();

        producer1.start();
        producer2.start();

        monitor.start();

    }
}
