package com.jetbrains;
import java.util.Scanner;
/**
 Charles Kolozsvary
 Period 2 Algorithms
 Java Version 4.2.1
 Java SDK: 12.01.0
 Description:
 Create a system to simulate vehicles at an intersection. Assume that there is one lane going in each of four directions, with stoplights facing each direction. Vary the arrival average of vehicles in each direction and the frequency of the light changes to view the “behavior” of the intersection.
 http://algorithms.mrseliasclasses.org/queue-adt/
 */
public class Intersection_CK
{
    public static void main(String[] args)
    {
        TrafficLight light1 = new TrafficLight();
        TrafficLight light2 = new TrafficLight();
        Lane rightLane1 = new Lane("first right lane", light1);
        Lane rightLane2 = new Lane("second right lane", light2);
        Lane leftLane1 = new Lane("first left lane", light1);
        Lane leftLane2 = new Lane("second left lane", light2);
        Lane[] lanes = {rightLane1,rightLane2,leftLane1,leftLane2};
        Scanner scan = new Scanner(System.in);
        boolean SimulationRunning = true;
        //print message, tell user how to start or end
        System.out.println("The following simulation will model cars interacting at an intersection\nThere are two roads (each with two lanes) that intersect at a pair of traffic lights (one light for each road)\nThe Roads intersect to form perpendicular lines; one vertical road (the first road/lanes), one horizontal road (the second road/lanes)\nEnter the number of minutes you would like to go by in this simulation(some integer between 0-1000000000)");
        int iterations = Integer.parseInt(scan.nextLine());
        randomlySetInitialLights(light1, light2);
        for(int i = 0; i<iterations; i++) //run simulations for 50 iterations (each iteration is supposed to replicate 10 minutes in real time)
        {
            //initial light status
            System.out.println("Event report at minute: "+i);
            System.out.println("Color of light 1:"+light1.getColor());
            System.out.println("Color of light 2:"+light2.getColor());
            //see how cars join
            doesCarJoin(rightLane1, rightLane1.getLight());
            doesCarJoin(leftLane1, leftLane1.getLight());
            doesCarJoin(rightLane2, rightLane2.getLight());
            doesCarJoin(leftLane2, leftLane2.getLight());
            //see if cars move and how much, or add cars
            for(Lane lane: lanes)
            {
                switch(lane.getLight().getColor())
                {
                    case "green":
                        doesCarJoin(lane, lane.getLight());
                        lane.moveOn(); //2 cars max get to move through in a minute if green
                        lane.moveOn();
                        lane.moveOn();
                        break;
                    case "red": //if the light is red the lane gets another chance to have a car join
                        doesCarJoin(lane, lane.getLight());
                        doesCarJoin(lane, lane.getLight());
                        break;
                }
            }
            //change lights
            changeLights(light1, light2);

            //status after light change and the movement
            System.out.println("Status after minute "+i+" has passed");
            System.out.println("Lights After Switching:");
            System.out.println("Color of light 1:"+light1.getColor());
            System.out.println("Color of light 2:"+light2.getColor());
            for(Lane lane: lanes)
            {
                System.out.println("Number of cars in "+lane.getName()+": "+lane.getLineup().size());
            }
            System.out.println("\n\n");
        }
    }
    public static void randomlySetInitialLights(TrafficLight light1, TrafficLight light2)
    {
        if(Math.random()*2 == 0) //does the first or second light start red or green, well its random (ish)
        {
            light1.setColor("green");
            light2.setColor("red");
        }
        else
        {
            light1.setColor("red");
            light2.setColor("green");
        }
    }
    public static void move(Queue<Car> lane)
    {
        lane.dequeue();
    }
    public static void addCar(Queue<Car> lane, TrafficLight light)
    {
        lane.enqueue(new Car(light));
    }
    private static void changeLights(TrafficLight light1, TrafficLight light2)
    {
        switch(light1.getColor())
        {
            case "green":
                light1.setColor("red");
                light2.setColor("green");
                break;
            case "red":
                light1.setColor("green");
                light2.setColor("red");
                break;
        }
    }
    public static void doesCarJoin(Lane lane, TrafficLight light)
    {
        if(0 == (int) (Math.random() * 2)) //50% chance that a car joins every iteration returns 0-1 so there is 1/2 chance it is zero
        {
            addCar(lane.getLineup(),light); //add a car to the lane with the same traffic light object
            System.out.println("A car joined the "+lane.getName());
        }
        //experimental different chances with different lights
        /*switch(lane.peek().getLightColor())
        {
            case "green":
                if((int)(Math.random()*5)==4) // 1 out of 4 chance if green
                {
                    lane.enqueue(new Car(lane.peek().getLight()));
                }
            case "red":
                if((int)(Math.random()*2)==0) //50% chance if red
                {
                    lane.enqueue(new Car(lane.peek().getLight()));
                }

        }*/

    }
}
class TrafficLight
{
    private String color;

    TrafficLight()
    {
        this.color = ""; //String which denotes which color the light is
    }

    void setColor(String color) {
        this.color = color;
    }
    String getColor()
    {
        return color;
    }
}

class Lane
{
    private Queue<Car> cars;
    private String name;
    private TrafficLight light;
    Lane(String name, TrafficLight light)
    {
        this.name = name;
        this.light = light;
        this.cars = new Queue<Car>();
    }

    String getName() {
        return name;
    }

    Queue<Car> getLineup() {
        return cars;
    }

    TrafficLight getLight() {
        return light;
    }
    void moveOn()
    {
        if(!cars.isEmpty())
        {
            cars.dequeue();
            System.out.println("a car passed through the "+name);
        }
    }
}
class Car
{
    private TrafficLight light;
    Car(TrafficLight light)
    {
        this.light = light;
    }

    String getLightColor()
    {
        return light.getColor();
    }

    TrafficLight getLight()
    {
        return light;
    }

}
/*
OUTPUT:
/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=53753:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath "/Users/charleskolozsvary/Code/Algorithms Quarter 2/out/production/Algorithms Quarter 2" com.jetbrains.Intersection_CK
The following simulation will model cars interacting at an intersection
There are two roads (each with two lanes) that intersect at a pair of traffic lights (one light for each road)
The Roads intersect to form perpendicular lines; one vertical road (the first road/lanes), one horizontal road (the second road/lanes)
Enter the number of minutes you would like to go by in this simulation(some integer between 0-1000000000)
3
Event report at minute: 0
Color of light 1:red
Color of light 2:green
A car joined the first right lane
A car joined the first left lane
A car joined the second right lane
A car joined the first right lane
A car joined the second right lane
a car passed through the second right lane
a car passed through the second right lane
Status after minute 0 has passed
Lights After Switching:
Color of light 1:green
Color of light 2:red
Number of cars in first right lane: 2
Number of cars in second right lane: 0
Number of cars in first left lane: 1
Number of cars in second left lane: 0



Event report at minute: 1
Color of light 1:green
Color of light 2:red
A car joined the second right lane
A car joined the first right lane
a car passed through the first right lane
a car passed through the first right lane
a car passed through the first right lane
A car joined the second right lane
A car joined the second right lane
a car passed through the first left lane
A car joined the second left lane
Status after minute 1 has passed
Lights After Switching:
Color of light 1:red
Color of light 2:green
Number of cars in first right lane: 0
Number of cars in second right lane: 3
Number of cars in first left lane: 0
Number of cars in second left lane: 1



Event report at minute: 2
Color of light 1:red
Color of light 2:green
A car joined the first right lane
A car joined the second right lane
A car joined the first right lane
A car joined the first right lane
a car passed through the second right lane
a car passed through the second right lane
a car passed through the second right lane
A car joined the first left lane
A car joined the second left lane
a car passed through the second left lane
a car passed through the second left lane
Status after minute 2 has passed
Lights After Switching:
Color of light 1:green
Color of light 2:red
Number of cars in first right lane: 3
Number of cars in second right lane: 1
Number of cars in first left lane: 1
Number of cars in second left lane: 0




Process finished with exit code 0


 */