package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t)  {
        Server shortestQueue = servers.get(0);
        for (Server s : servers) {
            if (s.getTasks().length < shortestQueue.getTasks().length) {
                shortestQueue = s;
            }
        }
        shortestQueue.addTask(t);
    }
}
