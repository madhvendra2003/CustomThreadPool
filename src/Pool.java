import java.util.ArrayList;

public class Pool {
    int min ;
    int minThread;
    MyBlockingQueue bq;
    ArrayList<Worker> threadPool;

    public Pool(int min, int minThread){
        this.min = min;
        bq = new MyBlockingQueue(min);
        this.minThread = minThread;
        threadPool = new ArrayList<Worker>();

        for(int i =0 ;i< minThread ; i++){
            Worker w = new Worker(new ThreadRunnable(bq));
            threadPool.add(w);
            w.createAndStart();
        }
    }

    public void AbortTask(){

        for(int i = 0 ; i <minThread ; i++){
             threadPool.get(i).interrupt();
        }

    }


    public synchronized void AddTask(Task task){

        if (bq.bq.remainingCapacity() != 0){
          bq.bq.add(task);
        }else{
            throw new RuntimeException("the Queue is full \n Please wait for the queue to get empty");

        }
    }




}

