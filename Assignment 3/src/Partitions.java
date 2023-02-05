public class Partitions {
    public  String name;
    public  int size;
    public Process process;
    public int originalSize;

    public Partitions(String name, int size) {
        this.name = name;
        this.size = size;
        this.originalSize = size;
    }

}