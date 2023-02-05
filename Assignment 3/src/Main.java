import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static int NumOfPartitions = 0;
    private static int NumOfProcesses = 0;
    private static List<Process> processes = new LinkedList<Process>();
    private static List<Partitions> partitions = new LinkedList<Partitions>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter number of Partitions: ");
        NumOfPartitions = scan.nextInt();
        System.out.println("Enter partitions names: ");
        for (int i = 0; i < NumOfPartitions; i++) {
            partitions.add(new Partitions(scan.next(), scan.nextInt()));
        }
        System.out.println("Enter number of Processes: ");
        NumOfProcesses = scan.nextInt();
        System.out.println("Enter processes names: ");
        for (int i = 0; i < NumOfProcesses; i++) {
            processes.add(new Process(scan.next(), scan.nextInt()));
        }
        while (true){
            System.out.println("1. First Fit");
            System.out.println("2. Best Fit");
            System.out.println("3. Worst Fit");
            System.out.println("4. Exit");
            int UserChoice = scan.nextInt();
            switch (UserChoice){
                case 1:
                    FirstFit.isCompaction = false;
                    FirstFit.firstFit(processes,  partitions);
                    break;
                case 2:
                    BestFit.isCompaction = false;
                    BestFit.bestFit(processes, partitions);
                    break;
                case 3:
                    WorstFit.isCompaction = false;
                    WorstFit.worstFit(processes, partitions);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}