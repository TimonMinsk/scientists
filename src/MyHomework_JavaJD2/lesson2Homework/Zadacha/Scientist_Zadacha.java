package MyHomework_JavaJD2.lesson2Homework.Zadacha;

import java.util.Comparator;

import static MyHomework_JavaJD2.lesson2Homework.Zadacha.ZadachaRunner.COUNT_OF_ALL_NIGHTS;

public class Scientist_Zadacha extends Thread {
    private Factory_Zadacha factory;
    private PartsList_Zadacha listWithComponents = new PartsList_Zadacha();
    private Servant_Zadacha servant = new Servant_Zadacha();
    private int countOfRobots = 0;


    @Override
    public void run() {
        for (int i = 0; i < COUNT_OF_ALL_NIGHTS; i++) {
            if (i == factory.theCurrentNight) {
                servant.takeAnyParts(factory);//слуга сбегал на свалку и что-то принёс
                System.out.println(i + " night, servant bring " + servant.getCountOfNightParts() + " " + Thread.currentThread().getName());
                servant.giveAllParts(listWithComponents);//отдал все принесённые детали учёному
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (i > factory.theCurrentNight) {
                do {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (i > factory.theCurrentNight);
                servant.takeAnyParts(factory);//слуга сбегал на свалку и что-то принёс
                System.out.println(i + " night, servant bring " + servant.getCountOfNightParts() + " " + Thread.currentThread().getName());
                servant.giveAllParts(listWithComponents);//отдал все принесённые детали учёному
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (i < factory.theCurrentNight) {
                throw new RuntimeException(Thread.currentThread().getName()
                        + " Servant prospal " + (factory.theCurrentNight - i) + " nochei!");
            }
        }

        makeRobots();
        System.out.println(Thread.currentThread().getName() + " " + countOfRobots + " robotos");
        printListWithComponents();
    }

    public Scientist_Zadacha(Factory_Zadacha factory) {
        this.factory = factory;
    }


    public void makeRobots() {
        int minValueOfPartCount = listWithComponents.getListOfParts()
                .stream()
                .map(partOfRobot -> partOfRobot.getCount())
                .min(Comparator.naturalOrder())
                .get();

        if (minValueOfPartCount != 0) {
            for (PartOfRobot_Zadacha part : listWithComponents.getListOfParts()) {
                part.decrCount(minValueOfPartCount);
            }
            countOfRobots += minValueOfPartCount;
            System.out.println(minValueOfPartCount + " robot(s) had build in thread: " + Thread.currentThread().getName());
        } else {
            System.out.println("It is impossible to build any robots! " + Thread.currentThread().getName());
        }
    }


    public void printListWithComponents() {
        synchronized (Scientist_Zadacha.class) {
            System.out.println();
            System.out.println(Thread.currentThread().getName() + " List with components:");
            for (PartOfRobot_Zadacha part : listWithComponents.getListOfParts()) {
                String tmp = part.getClass().getCanonicalName();
                String name = tmp.replace(part.getClass().getPackage().getName() + ".PartsList_Zadacha.", " ");
                System.out.println(name + " - " + part.getCount());
            }
        }
    }


    public int getCountOfRobots() {
        return countOfRobots;
    }
}
