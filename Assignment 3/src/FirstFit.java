import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class FirstFit
{
    static int numOfRemoves=0;
    static boolean isCompaction = false;
    static void firstFit(List<Process> processes,  List<Partitions> partitions)
    {


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
            for (int j = 0; j < tempPartitions.size(); j++)
            {
                if (!processes.get(i).isAllocated &&tempPartitions.get(j).process == null && tempPartitions.get(j).size >= processes.get(i).size)
                {
                    tempPartitions.get(j).process = processes.get(i);
                    processes.get(i).isAllocated = true;
                    tempPartitions.get(j).size -= processes.get(i).size;
                    if (tempPartitions.get(j).size > 0)
                    {
                        tempPartitions.add(j+1,new Partitions("Partition "+(tempPartitions.size()+numOfRemoves),tempPartitions.get(j).size));
                    }
                    break;
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
                firstFit(processes, tempPartitions);
            }

        }
    }
}