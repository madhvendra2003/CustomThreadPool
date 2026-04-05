public class ThreadRunnable implements Runnable{

    MyBlockingQueue bq;

    public ThreadRunnable(MyBlockingQueue bq){
        this.bq = bq;
    }

    @Override
    public void run() {
        while(true){
            try {


                System.out.println("--> " + Thread.currentThread().getName() + "is a worker thread doing a job");
                bq.bq.take().run(new String(Thread.currentThread().getName()));


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
