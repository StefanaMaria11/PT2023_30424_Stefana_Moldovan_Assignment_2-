package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task t) {
        Server shortestQueue = null; // assume first server has shortest queue
        AtomicInteger min = new AtomicInteger(999);
        for (Server s : servers) {
            AtomicInteger k = s.getWaitingPeriod();
            if (k.get() < min.get()) {
                shortestQueue=s;
                min=k;

            }
        }
        if(shortestQueue!=null)
        shortestQueue.addTask(t);
    }
}
