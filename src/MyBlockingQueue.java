import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyBlockingQueue {

    public static BlockingQueue<Task> bq;
    public MyBlockingQueue(int min) {
        bq = new ArrayBlockingQueue<Task>(min);
    }

}
