import BusinessLogic.SimulationManager;
import View.SimulationFrame;

public class Main {
    public static void main(String[] args) {
        SimulationFrame simulationFrame = new SimulationFrame();
        SimulationManager simulationManager = new SimulationManager();
        simulationManager.run();
        simulationFrame.SimulationFrame();
    }
}
