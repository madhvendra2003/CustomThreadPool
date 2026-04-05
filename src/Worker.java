public class Worker{

    Thread t1 ;
    ThreadRunnable r1 ;

    public Worker(ThreadRunnable r1){
        this.r1 = r1;

    }

    public void createAndStart(){
       this.t1 = new Thread(r1);
       this.t1.start();
    }

    public void interrupt(){
        this.t1.interrupt();
    }




}
