package MyHomework_JavaJD2.lesson2Homework.Zadacha;

import java.util.ArrayList;
import java.util.List;


public class PartsList {

//В 10 - 18 строках создаются все необходимые составные робочасти(в данном случае их 9 разновидностей)
    public static class Head extends PartOfRobot {    }
    public static class Body extends PartOfRobot {    }
    public static class LeftHand extends PartOfRobot {    }
    public static class RightHand extends PartOfRobot {    }
    public static class LeftLeg extends PartOfRobot {    }
    public static class RightLeg extends PartOfRobot {    }
    public static class CPU extends PartOfRobot {    }
    public static class RAM extends PartOfRobot {    }
    public static class HDD extends PartOfRobot {    }


//Единственное поле этого класса заполняется при вызове конструктора всеми объявленными выше составными частями
    private List<PartOfRobot> listOfParts = new ArrayList<>();


//Единственный(помимо конструктора) метод класса, который возвращает список всех возможных комплектующих для сборки
    public List<PartOfRobot> getListOfParts() {
        return listOfParts;
    }


    //Для корректной работы в конструкторе в listOfParts должны быть добавлены все объявленные составные части
    public PartsList() {
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
}
