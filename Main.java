//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {



        System.out.println("Hello and welcome!");

        System.out.println("Initializing banking system..");

        int totalNumberOfSimulaion = 10;
        OperationsQueue operationsQueue = new OperationsQueue();
        Bank bank = new Bank("123", operationsQueue);

        System.out.println("Initializing simulation....");
        Thread simulationThread = new Thread(() -> {
            operationsQueue.addSimulation(totalNumberOfSimulaion);
        });
        simulationThread.start();


        System.out.println("Initializing deposit system....");
        Thread depositThread = new Thread(() -> {
            bank.deposit();
        });
        depositThread.start();
        System.out.println("completed");

        System.out.printf("Initializing withdraw system....");
        Thread withdrawThread = new Thread(() -> {
            bank.withdraw();
        });
        withdrawThread.start();
        
        simulationThread.join();
        depositThread.join();
        withdrawThread.join();
        System.out.println("completed");


        System.out.println("completed");

    }
}