import java.util.concurrent.ConcurrentLinkedQueue;

public class Producer extends  Thread {
    private ConcurrentLinkedQueue<Integer> queue;
    private  int maxSize;
    private Mutex producerMutex;

    public Producer(ConcurrentLinkedQueue<Integer> queue,Mutex producerMutex, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
        this.producerMutex = producerMutex;
    }

    @Override
    public void  run(){
        while (true) {
            synchronized (producerMutex) {
                try {
                    process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void process() throws InterruptedException {
        while (queue.size() < maxSize) {
            producerMutex.setLock(true);

            queue.add(0);
            System.out.println("Producer " + " " + Thread.currentThread());


            Thread.sleep(600);

            producerMutex.notifyAll();
            producerMutex.wait();

        }
        producerMutex.setLock(false);
        producerMutex.wait();
    }

}
