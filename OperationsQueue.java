import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OperationsQueue {
    private final List<Integer> operations = new ArrayList<>();
    private final Lock lock = new ReentrantLock(true);
    private boolean endofoperation = false, OnProcess1 = false, OnProcess2 = false;

    public void addSimulation(int totalSimulation) {

        // Add 50 random numbers in the operations list. The number will be range from -100 to 100. It cannot be zero.
        for (int i = 0; i < totalSimulation; i++) {
            int random = (int) (Math.random() * 200) - 100;
            if (random != 0) {
                operations.add(random);
                System.out.println(i + ". New operation added: " + random);
            }
            // add small delay to simulate the time taken for a new customer to arrive
            try {
                Thread.sleep((int) (Math.random() * 80));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        endofoperation = true;
    }
    public void add(int amount) {
        lock.lock();
        try {
            operations.add(amount);
            ProcessDone();
        } finally {
            lock.unlock();
        }
    }
    public synchronized void ProcessDone() {
        if(OnProcess1 == true) OnProcess1 = false;
        else OnProcess2 = false;
    }
    public synchronized int getNextItem() {
        // add a small delay to simulate the time taken to get the next operation.
        lock.lock();
        try {
            while (operations.isEmpty() && (endofoperation == false || OnProcess1 == true || OnProcess2 == true)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("OnCircle    --- --- --- --- --- --- ---");
            }
            if(operations.isEmpty()) return -9999;
            System.out.println(Thread.currentThread().getName() + operations);
            return operations.remove(0);
        } finally {
            lock.unlock();
        }
    }
}
