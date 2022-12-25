package MyHomework_JavaJD2.lesson2Homework.Zadacha;

import java.util.ArrayList;
import java.util.List;


public class PartsList_Zadacha {


    public static class Head extends PartOfRobot_Zadacha {
    }

    public static class Body extends PartOfRobot_Zadacha {
    }

    public static class LeftHand extends PartOfRobot_Zadacha {
    }

    public static class RightHand extends PartOfRobot_Zadacha {
    }

    public static class LeftLeg extends PartOfRobot_Zadacha {
    }

    public static class RightLeg extends PartOfRobot_Zadacha {
    }

    public static class CPU extends PartOfRobot_Zadacha {
    }

    public static class RAM extends PartOfRobot_Zadacha {
    }

    public static class HDD extends PartOfRobot_Zadacha {
    }


    private List<PartOfRobot_Zadacha> listOfParts = new ArrayList<>();


    public PartsList_Zadacha() {
        this.listOfParts.add(new Head());
        this.listOfParts.add(new Body());
        this.listOfParts.add(new LeftHand());
        this.listOfParts.add(new RightHand());
        this.listOfParts.add(new LeftLeg());
        this.listOfParts.add(new RightLeg());
        this.listOfParts.add(new CPU());
        this.listOfParts.add(new RAM());
        this.listOfParts.add(new HDD());
    }


    public List<PartOfRobot_Zadacha> getListOfParts() {
        return listOfParts;
    }
}
