package Model;

import BusinessLogic.Scheduler;

public class Task {
    private int arrivalTime;
    private int serviceTime;
    private int id;

    public Task(int id,int arrivalTime, int serviceTime){
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getProcessingTime(int i){
        return serviceTime + arrivalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String printT(){
        String s="("+ getId() + "," + getArrivalTime() + "," + getServiceTime()+");";
        return s;
    }

    public void decrementServiceTime() {
        serviceTime--;
    }
}
