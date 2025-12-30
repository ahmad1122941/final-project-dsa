public class MainApp {
    public static void main(String[] args) {

        System.out.println("SMART TRANSPORT MANAGEMENT SYSTEM\n");

        CityGraphUI.run();
        VehicleUI.run();
        TrafficUI.run();
        SchedulerUI.run();
        ShortestPath.run();
        RouteUI.run();
        FareUI.run();
        BookingUI.run();
        ReportUI.run();

        System.out.println("\nSYSTEM EXECUTION COMPLETE");
    }
}
