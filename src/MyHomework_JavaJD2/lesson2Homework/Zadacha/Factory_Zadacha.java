package MyHomework_JavaJD2.lesson2Homework.Zadacha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static MyHomework_JavaJD2.lesson2Homework.Zadacha.ZadachaRunner.*;

public class Factory_Zadacha extends Thread {

    private PartsList_Zadacha factoryComponentsList;
    private List<PartOfRobot_Zadacha> trashList = new ArrayList<>();
    private Random random = new Random();
    private int countOfKindOfComponents;
    public volatile int theCurrentNight = 0;


    @Override
    public void run() {
        for (theCurrentNight = 0; theCurrentNight < COUNT_OF_ALL_NIGHTS; theCurrentNight++) {
            System.out.println(theCurrentNight + " night, factory has  " + trashList.size() + " parts");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            nextNight();
        }
        System.out.println(Thread.currentThread().getName() + " factory finished.");
    }

    public Factory_Zadacha(PartsList_Zadacha factoryComponentsList) {
        this.factoryComponentsList = factoryComponentsList;
        countOfKindOfComponents = factoryComponentsList.getListOfParts().size();
        for (int i = 0; i < FIRST_NIGHT_FACTORY_PART_COUNT; i++) {
            PartOfRobot_Zadacha part = factoryComponentsList.getListOfParts().get(random.nextInt(countOfKindOfComponents));
            trashList.add(part);
        }
    }


    public synchronized void nextNight() {
        int todayCount = random.nextInt(EVERY_NIGHT_MAX_FACTORY_PART_COUNT + 1 - EVERY_NIGHT_MIN_FACTORY_PART_COUNT) + EVERY_NIGHT_MIN_FACTORY_PART_COUNT;
        for (int i = 0; i < todayCount; i++) {
            PartOfRobot_Zadacha part = factoryComponentsList.getListOfParts().get(random.nextInt(countOfKindOfComponents));
            trashList.add(part);
        }
        System.out.println("factory add to trash "+todayCount+" component(s)");
    }


    public synchronized PartOfRobot_Zadacha getAnyPart() {
        if (trashList.size() == 0) {
            return null;
        }
        int index = random.nextInt(trashList.size());
        PartOfRobot_Zadacha anyPart = trashList.remove(index);
        return anyPart;
    }
}
