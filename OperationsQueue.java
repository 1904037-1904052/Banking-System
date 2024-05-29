import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OperationsQueue {
    private final List<Integer> operations = new ArrayList<>();
    private final Lock lock = new ReentrantLock(true);

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
        operations.add(-9999);
    }
    public void add(int amount) {
        lock.lock();
        try {
            operations.add(amount);
        } finally {
            lock.unlock();
        }
    }

    public synchronized int getNextItem() {
        // add a small delay to simulate the time taken to get the next operation.
        lock.lock();
        try {
            while (operations.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + operations);
            return operations.remove(0);
        } finally {
            lock.unlock();
        }
    }
}
