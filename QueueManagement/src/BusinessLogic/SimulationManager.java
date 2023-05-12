package BusinessLogic;

import Model.Server;
import Model.Task;
import View.SimulationFrame;


import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.abs;

public class SimulationManager implements Runnable{
    //data read from UI
    public int timeLimit = 18; //maximum processing time - read from UI
    public int maxProcessingTime = 9;
    public int minProcessingTime = 3;
    public int maxArrivalTime = 16;
    public int minArrivalTime = 2;
    public int noOfServers = 3;
    public int noOfClients = 5;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private double avServiceTime=0;
    //entity responsible with queue management and client distribution
    private Scheduler scheduler;
    //frame for displaying simulation
    private SimulationFrame frame;
    //pool of tasks (client shopping in the store)
    private List<Task> generatedTasks;
    public int queueNo;
    int avWaitingTime[] = {0};
    int clientsWaitingTime[] = {0};
    int totalWaiting = 0;
    //int avServiceTime[] = {0};
    int totalService = 0;
    int peakHourTime = 0;
    int peakHourTask = 0;

    public SimulationManager(){
    //this.serverNo = serverNo;

        scheduler = new Scheduler(noOfServers);
        scheduler.changeStrategy(SelectionPolicy.SHORTEST_TIME);

        //frame = new SimulationFrame(noOfServers);
        for (int i = 0; i < noOfServers; i++) {
            Thread serverThread = new Thread(scheduler.getServers().get(i));
            serverThread.start();
        }
        generateNRandomTasks();
    }



    private void generateNRandomTasks(){

        generatedTasks = new LinkedList<>();
        Random rand = new Random();

        for(int i = 0; i < noOfClients; i++){
            int processingTime = minProcessingTime + rand.nextInt(maxProcessingTime - minProcessingTime + 1);
            int arrTime = minArrivalTime+ rand.nextInt(maxArrivalTime - minArrivalTime + 1);
            //int j=i+1;
            Task t = new Task(i,arrTime, processingTime);
            generatedTasks.add(t);
        }
        generatedTasks.sort(Comparator.comparingInt(Task::getArrivalTime));
    }


//    private void averageServiceTime(){
//        float avTime = 0;
//        int usedServ = serversNo.get();
//        List<Server> servers = scheduler.getServers();
//        for(Server s : servers){
//            if(s.tasks != 0)
//        }
//    }

    @Override
    public void run() {

        int currentTime = 0;
        avWaitingTime = new int[noOfServers];
        clientsWaitingTime = new int[noOfServers];
       // peakHour = new int[timeLimit + 1];

        FileWriter writeFile;
        //FileWriter writeFile1;
        //FileWriter writeFile2;
        //FileWriter writeFile3;

        try {
            writeFile = new FileWriter("test.txt");
            //writeFile1 = new FileWriter("test1.txt");
            //writeFile2 = new FileWriter("test2.txt");
            //writeFile3 = new FileWriter("test3.txt");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (currentTime <= timeLimit) {


            try {
                writeFile.write("Time: " + currentTime + "\n");
                writeFile.write("Waiting clients: ");

                for (Task task : generatedTasks) {
                    writeFile.write(task.printT());
                    //System.out.print("(" + task.getId() + "," + task.getArrivalTime() + "," + task.getServiceTime() + "),");
                }

                writeFile.flush();
                writeFile.write("\n");

                List<Server> serverList = scheduler.getServers();
                int i = 0;

                for(Server s: serverList) {
                    i++;
                    writeFile.write("Queue" + i+": ");
                    //Task[] tasks = s.getTasks();
                    if(s.tasks.isEmpty()){
                        writeFile.write("closed");

                    } else {
                        Task[] tasks = s.getTasks();
                        for (Task t : tasks) {
                            writeFile.write("(" + t.getId() + " " + t.getArrivalTime() + " " + t.getServiceTime() + ")");
                        }
                    }
                    writeFile.write("\n");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            for(Iterator<Task> i=generatedTasks.iterator(); i.hasNext();)
            {
                Task task=i.next();
                if(task.getArrivalTime()==currentTime){
                    avServiceTime=avServiceTime+task.getServiceTime();
                    scheduler.dispatchTask(task);
                    i.remove();
                    generatedTasks.remove(task);
                }
            }

            for(Server s: scheduler.getServers())
            {
                if(s!=null)
                    s.setWaitingPeriod();
            }


            System.out.print("Time: " + currentTime + "\n");
            System.out.print("Waiting clients: ");

            for (Task task : generatedTasks) {
                System.out.print("(" + task.getId() + "," + task.getArrivalTime() + "," + task.getServiceTime() + ") ");
                
            }
            System.out.println("\n");
            int i=0;
            List<Server> serverList = scheduler.getServers();
            for(Server s: serverList) {
                i++;
                System.out.println("Queue:" + i + ": ");
                //Task[] tasks = s.getTasks();
                if(s.tasks.isEmpty()){
                    System.out.println("closed");

                } else {
                    Task[] tasks = s.getTasks();
                    for (Task t : tasks) {
                        //peakHour[currentTime + 1]++;
                        System.out.println("(" + t.getId() + " " + t.getArrivalTime() + " " + t.getServiceTime() + ")");
                    }
                }
            }
            int j=0;
            for(Server s: scheduler.getServers()) {
                for (Task t : s.getTasks()){
                    if(t.getArrivalTime()+t.getServiceTime()==currentTime)
                    {
                        avWaitingTime[j]=avWaitingTime[j]+(currentTime-t.getArrivalTime());
                        clientsWaitingTime[j]= clientsWaitingTime[j]+1;
                        s.remove();
                    }
                }
                j++;
            }

            System.out.println("\n");



//            int j = 0;
//            for(Server s: scheduler.getServers()){
//                if(!s.tasks.isEmpty()){
//                    Task t = s.getTask();
//                    //int sWP = s.getWaitingPeriod();
//                    if((s.getPrevTime() + t.getServiceTime()) == (currentTime + 1)){
////                        avWaitingTime[j] = avServiceTime[j] + (currentTime + 1 - t.getArrivalTime());
////                        avServiceTime[j]++;
//                        if(s.getTasks().length > 1){
//                            s.setPrevTime(currentTime +1);
//                        } else if (!generatedTasks.isEmpty()) {
//                            s.setPrevTime(generatedTasks.get(0).getArrivalTime());
//                        }
//                        s.remove();
//                    }
//                }
//                j++;
//            }

                //aici ar trebui sa updatez frame u


                currentTime++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        for(int i = 0; i < noOfServers; i++){
            if(avWaitingTime[i] == 0){
                clientsWaitingTime[i] = 1;
            }
            totalWaiting = totalWaiting + avWaitingTime[i] / clientsWaitingTime[i];
        }
        totalWaiting = totalWaiting / noOfServers;
        System.out.println("The average waiting time is: " + totalWaiting);

        int sUsed = noOfServers;
        for(Server s : scheduler.getServers()){
            if(noOfClients != 0){
                totalService = s.totalS/noOfClients;
            }else sUsed--;
        }
        if(sUsed != 0){
            totalService = abs(totalService) / sUsed;
        }
        avServiceTime=avServiceTime/noOfClients;
        System.out.println("The average service time is: " + avServiceTime);

        int clients = 0;
        for(Server s : scheduler.getServers()){
            for(int i = 0; i < noOfServers; i++){
                clients = clients + noOfClients;
                if(peakHourTask < noOfClients){
                    peakHourTask = noOfClients;
                    peakHourTime = currentTime;
                }
            }
        }
        System.out.println("Peak hour: " + peakHourTime);

        try{
            writeFile.write("The average waiting time is: " + totalWaiting);
            writeFile.write("\n");
            writeFile.write("The average service time is: " + avServiceTime);
            writeFile.write("\n");
            writeFile.write("Peak hour: " + peakHourTime);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try {
            writeFile.close();
            //writeFile1.close();
            //writeFile2.close();
            //writeFile3.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
        public static void main(String[] args){
            SimulationManager gen = new SimulationManager();
            //SimulationFrame simulationFrame = new SimulationFrame();
            //simulationFrame.SimulationFrame();
            Thread t = new Thread(gen);
            t.start();
        }

}
