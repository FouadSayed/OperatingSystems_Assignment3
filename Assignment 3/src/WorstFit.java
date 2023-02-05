import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WorstFit {
    static int numOfRemoves=0;
    static boolean isCompaction = false;
    public static void worstFit(List<Process> processes, List<Partitions> partitions) {

        if (!isCompaction) {
            for (Process process : processes) {
                process.isAllocated = false;
            }
            for(Partitions partition : partitions){
                partition.process = null;
                partition.size = partition.originalSize;
            }
        }

        List<Partitions> tempPartitions =new LinkedList<Partitions>();
        tempPartitions.addAll(partitions);


        for (int i = 0; i < processes.size(); i++)
        {
            int max = 0;
            int index = 0;
            boolean check = false;
            for (int j = 0; j < tempPartitions.size(); j++)
            {
                if (!processes.get(i).isAllocated &&tempPartitions.get(j).process == null && tempPartitions.get(j).size >= processes.get(i).size)
                {
                    if (tempPartitions.get(j).size > max) {
                        max = tempPartitions.get(j).size;
                        index = j;
                        check = true;
                    }
                }
            }
            if(check){
                tempPartitions.get(index).process = processes.get(i);
                processes.get(i).isAllocated = true;
                tempPartitions.get(index).size -= processes.get(i).size;
                if (tempPartitions.get(index).size > 0)
                {
                    tempPartitions.add(index+1,new Partitions("Partition "+(tempPartitions.size()+numOfRemoves),tempPartitions.get(index).size));
                }
            }
        }
        for (int i = 0; i < tempPartitions.size(); i++)
        {
            if (tempPartitions.get(i).process == null) {
                System.out.println(tempPartitions.get(i).name+" (" +tempPartitions.get(i).size+" KB) =>" + "External Fragment");

            }
            else{
                System.out.println( tempPartitions.get(i).name+" (" +tempPartitions.get(i).process.size+" KB) =>" + tempPartitions.get(i).process.name);

            }
        }
        System.out.println();
        for (int i = 0; i < processes.size(); i++)
        {
            if (!processes.get(i).isAllocated) {
                System.out.println(processes.get(i).name+" can not be allocated");
            }
        }
        if(!isCompaction) {

            System.out.println("Do you want to do compaction?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if (choice == 1) {
                isCompaction = true;
            }
            if (isCompaction) {
                Partitions temp = new Partitions("Partition " + tempPartitions.size() , 0);
                for (int i = 0; i < tempPartitions.size(); i++) {
                    if (tempPartitions.get(i).process == null) {
                        temp.size += tempPartitions.get(i).size;
                        tempPartitions.remove(i);
                        i--;
                        numOfRemoves++;

                    }
                }
                tempPartitions.add(temp);
                worstFit(processes, tempPartitions);
            }

        }

    }
}
