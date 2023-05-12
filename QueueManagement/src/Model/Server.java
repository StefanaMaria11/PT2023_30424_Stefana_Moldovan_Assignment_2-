package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Server implements Runnable{

   // public BlockingQueue<Task> task = new ArrayBlockingQueue<>(100);
    int id;
    public BlockingQueue<Task> tasks = new ArrayBlockingQueue<>(100);
    private AtomicInteger waitingPeriod;
    private int prevTime = 0;
    public int totalS = 0;
    Task workingTask;
    private boolean var=true;

    public Server(int i){
        //initialize queue and waitingPeriod
        this.id = i;
        //this.tasks = tasks;
        this.waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask){
            this.tasks.add(newTask);
            waitingPeriod.addAndGet(newTask.getServiceTime());
    }

    @Override
    public void run() {
        while (var) {
            try {
                if (tasks.isEmpty()) {
                    Thread.sleep(100);
                    continue;
                }
                Task client =tasks.element();
                Thread.sleep(client.getServiceTime());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public Task[] getTasks() {
        Task[] t = new Task[tasks.size()];
        tasks.toArray(t);
        return t;
    }

    public int id(){
        return id;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public int getPrevTime() {
        return prevTime;
    }

    public void setPrevTime(int prevTime){
        this.prevTime = prevTime;
    }
    public void setWaitingPeriod() {
        this.waitingPeriod.set(waitingPeriod.get()-1);
    }
    public Task getWorkingTask(){return workingTask;}
    public Task getTask(){return tasks.element();}
    public void remove(){ tasks.remove(); }
}
