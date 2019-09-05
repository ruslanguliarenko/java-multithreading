import java.util.concurrent.ConcurrentLinkedQueue;

public class Consumer extends Thread {
    private ConcurrentLinkedQueue<Integer> queue;
    private Mutex mutex;

    public Consumer(ConcurrentLinkedQueue<Integer> queue, Mutex mutex) {
        this.queue = queue;
        this.mutex = mutex;
    }

    @Override
    public void run(){
        while(true) {
            synchronized (mutex) {
                try {
                    process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void process() throws InterruptedException {
        while (!queue.isEmpty()) {

            mutex.setLock(true);

            queue.remove();
            System.out.println("Consumer : " + Thread.currentThread());


            Thread.sleep(250);

            mutex.notifyAll();
            mutex.wait();
        }
        mutex.setLock(false);
        mutex.wait();
    }

}
