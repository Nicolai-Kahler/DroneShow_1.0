public class Main {

    //This program is inspired by Ebbe Vang, lektor at RUC, and his existing program TelloDroneJavaConnect.

    public static void main(String[] args) throws InterruptedException {
        //Create drone swarm
        Swarm swarm = new Swarm();

        //Static assignment of drone IP
        swarm.addDrone(new TelloDrone(("192.168.1.9")));
        swarm.addDrone(new TelloDrone(("192.168.1.8")));
        swarm.addDrone(new TelloDrone(("192.168.1.7")));
        swarm.addDrone(new TelloDrone(("192.168.1.6")));

        // send commands to all drones in drone swarm.
        swarm.connectAll();
        Thread.sleep(5000);
        //swarm.waitForAllDronesToBeOK(swarm);
        swarm.takeOff();
        Thread.sleep(5000);
        //swarm.waitForAllDronesToBeOK(swarm);
        swarm.moveAll(Direction.FORWARD, 100);
        Thread.sleep(5000);
        swarm.moveAll(Direction.BACK, 50);
        Thread.sleep(5000);
        //swarm.waitForAllDronesToBeOK(swarm);
        swarm.land();

        // never ending
        while (true)
        {
        }
    }
}

