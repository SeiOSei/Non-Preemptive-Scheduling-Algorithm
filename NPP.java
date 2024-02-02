import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    char processId;
    int arrivalTime;
    int burstTime;
    int priority;

    public Process(char processId, int arrivalTime, int burstTime, int priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
public class NPP {

    private static Scanner scanner = new Scanner(System.in);
    private static int[] waitingTime;
    private static int[] turnaroundTime;
    private static int[] completionTime;

    public static void main(String[] args) {
        // Take user input for the number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        // Take user input for each process
        Process[] processes = new Process[n];
        for (char i = 'A'; i < 'A' + n; i++) {
            System.out.print("Process " + i + " (AT BT Priority): ");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();
            processes[i - 'A'] = new Process(i, arrivalTime, burstTime, priority);
        }
        // Sort processes based on arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        // Initialize arrays for turnaroundTime, waitingTime, and completionTime
        waitingTime = new int[n];
        turnaroundTime = new int[n];
        completionTime = new int[n];
        // Execute NPP scheduling algorithm
        executeNPP(processes);
        // Display the result
        displayResult(processes);
    }
    static void executeNPP(Process[] processes) {
        int n = processes.length;
        completionTime[0] = processes[0].burstTime + processes[0].arrivalTime;
        turnaroundTime[0] = completionTime[0] - processes[0].arrivalTime;
        waitingTime[0] = turnaroundTime[0] - processes[0].burstTime;
        for (int i = 1; i < n; i++) {
            int index = i;
            for (int j = i + 1; j < n; j++) {
                if (processes[j].arrivalTime <= completionTime[i - 1] && processes[j].priority < processes[index].priority) {
                    index = j;
                }
            }
            // Swap processes[i] and processes[index]
            Process temp = processes[index];
            processes[index] = processes[i];
            processes[i] = temp;

            completionTime[i] = completionTime[i - 1] + processes[i].burstTime;
            turnaroundTime[i] = completionTime[i] - processes[i].arrivalTime;
            waitingTime[i] = turnaroundTime[i] - processes[i].burstTime;
        }
    }
    static void displayResult(Process[] processes) {
        System.out.println("Process\tAT\tBT\tTAT\tWT\tCT");
        for (int i = 0; i < processes.length; i++) {
            System.out.println(processes[i].processId + "\t" + processes[i].arrivalTime + "\t" + processes[i].burstTime + "\t"
                    + turnaroundTime[i] + "\t" + waitingTime[i] + "\t" + completionTime[i]);
        }
    }
}