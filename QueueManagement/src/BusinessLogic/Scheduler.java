package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Scheduler {
    private List<Server> servers = new LinkedList<>();
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    

//    public Scheduler(int maxNoSevers){
//        for(int i=1; i <= maxNoSevers; i++){
//            Server s = new Server(i);
//            servers.add(s);
//            Thread th = new Thread(s);
//            th.start();
//        }
//    }

    public Scheduler(int maxNoServers){
        //for maxNoServers
        //-create server object
        //-create thread with the object

        this.maxNoServers = maxNoServers;
        for(int i = 1; i <= maxNoServers; i++){
            Server server = new Server(i);
            servers.add(server);
            Thread thread = new Thread(server);
            thread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        switch(policy) {
            case SHORTEST_QUEUE:
                strategy = new ConcreteStrategyQueue();
                break;
            case SHORTEST_TIME:
                strategy = new ConcreteStrategyTime();
                break;
            default:
                // handle unknown policy
                break;
        }

    }

    void printQueues(){
        for (Server s:servers) {
            if(s.getWorkingTask()!=null) {
                //UserInterface.appendTextField("\nQueue " + s.getid() + " ");
                System.out.print("\nQueue " + s.id() + " ");
                //System.out.print(s.getWaitingPeriod().get());
                s.getWorkingTask().printT();
                for (Task t: s.getTasks()) {
                    t.printT();
                }
            }
            else {
                //UserInterface.appendTextField("\nQueue " + s.id() + " closed");
                System.out.print("\nQueue " + s.id() + " closed");
            }
        }
    }
    public void dispatchTask(Task t){
        strategy.addTask(servers, t);
    }

    public List<Server> getServers(){
            return servers;
    }

}
