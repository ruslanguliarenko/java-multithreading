import java.util.concurrent.ConcurrentLinkedQueue;

public class Monitor extends Thread {
    private ConcurrentLinkedQueue<Integer> queue;
    private Mutex consumerMutex;
    private Mutex producerMutex;
    private int minQueueSize;
    private int maxQueueSize;

    public Monitor(ConcurrentLinkedQueue<Integer> queue, Mutex consumerMutex, Mutex producerMutex, int minQueueSize, int maxQueueSize){
        this.queue = queue;
        this.consumerMutex = consumerMutex;
        this.producerMutex = producerMutex;
        this.minQueueSize = minQueueSize;
        this.maxQueueSize = maxQueueSize;
    }

    @Override
    public void run(){
        while (true) {
            int size = queue.size();

            if (size < minQueueSize) {
                executeService(producerMutex);
            } else if (size > maxQueueSize) {
                executeService(consumerMutex);
            }
        }
    }
    private void executeService(Mutex mutex){
        if (!mutex.isLock()) {
            synchronized (mutex) {
                mutex.notifyAll();
            }
        }
    }
}
