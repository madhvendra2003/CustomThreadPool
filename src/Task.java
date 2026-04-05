import java.util.concurrent.Callable;

public class Task  {


    private Runnable runnable ;

    public Task(Runnable temp){
        this.runnable = temp;
    }

    public void run(String temp){
        System.out.println("this is the tread taking this task " + temp);
        runnable.run();
    }



}
