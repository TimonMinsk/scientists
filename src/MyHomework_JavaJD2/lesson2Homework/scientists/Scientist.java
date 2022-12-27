package MyHomework_JavaJD2.lesson2Homework.scientists;

import java.util.Comparator;
import static MyHomework_JavaJD2.lesson2Homework.scientists.Runner.*;


public class Scientist extends Thread {
    private Factory factory;//Поле, которое заполняется при вызове конструктора. Объект должен быть общим для всех учёных
    private PartsList listWithComponents = new PartsList();//при создании нового экземпляра учёного создаётся новый список всех
    //доступных робочастей(счётчики количества внутри всех объектов списка - дефолтные т.е. 0)
    private Servant servant = new Servant();//новый экземпляр объекта Servant для каждого учёного
    private int countOfRobots = 0;//количество собранных каждым учёным роботов
    private static int countOfScientist = 0;//количество учёных уже вступивших в академию
    private int numberOfScientist = 0;//номер учёного в академии


    @Override
    public void run() {
        for (int i = 0; i < COUNT_OF_ALL_NIGHTS; i++) {//счётчик ночей для коректировки походов слуги на свалку
            if (i == factory.theCurrentNight) {
                pushServantToFactory(i);//отправил слугу на свалку
                try {
                    Thread.sleep(TIME_OF_NIGHT_MSEC);//ждёт следующей ночи
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (i > factory.theCurrentNight) {//если вдруг часы учёного начали спешить, а ночь ещшё не наступила
                do {
                    try {
                        Thread.sleep(TIME_OF_NIGHT_MSEC / 10);//стоит ещё немного поспать
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (i != factory.theCurrentNight);//поспать до тех пор пока не наступит ночь
                pushServantToFactory(i);//отправил слугу на свалку
                try {
                    Thread.sleep(4 * TIME_OF_NIGHT_MSEC / 5);//ждать следующей ночи
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (i < factory.theCurrentNight) {//если всё таки слуга проспал
                System.out.println(Thread.currentThread().getName()
                        + " Servant prospal " + (factory.theCurrentNight - i) + " noch(ei)!");
                pushServantToFactory(i);//отправил слугу на свалку вдогонку
                i++;//необходимо подогнать отставшие часы
                pushServantToFactory(i);//отправил слугу на свалку второй раз(хотя это не совсем по условию...)
            }
        }
        synchronized (factory) {//блок синхронизирован для красивого вывода
            System.out.println();
            makeRobots();//строятся роботы
            printListWithComponents();//выводится перечень оставшихся после строительства робочастей
            System.out.println(Thread.currentThread().getName() + "  finished.");
            System.out.println();
        }
    }


    //метод отправляет слугу на свалку и принимает принесённые им робочасти
    private void pushServantToFactory(int i) {
        servant.takeAnyParts(factory);//слуга сбегал на свалку и что-то принёс
        System.out.println(i + " night, servant bring " + servant.getCountOfNightParts() + " " + Thread.currentThread().getName());
        servant.giveAllParts(listWithComponents);//отдал все принесённые детали учёному
    }


    public void makeRobots() {
        //определение того колличества составных робочастей, которых меньше всего
        // у учёного(оно же является числом роботов, которых можно построить)
        int minValueOfPartCount = listWithComponents.getListOfParts()
                .stream()
                .map(partOfRobot -> partOfRobot.getCount())
                .min(Comparator.naturalOrder())
                .get();

        //если расчитанное число не равно 0, то собираются роботы
        if (minValueOfPartCount != 0) {
            for (PartOfRobot part : listWithComponents.getListOfParts()) {
                part.decrCount(minValueOfPartCount); //Израсходованные робокомпоненты вычитаются из перечня
            }
            countOfRobots += minValueOfPartCount;
            System.out.println(minValueOfPartCount + " robot(s) had build by " + numberOfScientist
                    + " scintist in thread: " + Thread.currentThread().getName());
        } else {//если в перечне есть хотябы один ноль, то построить робота невозможно
            System.out.println("It is impossible to build any robots for scientist number "
                    + numberOfScientist + "! " + Thread.currentThread().getName());
        }
    }


    //метод просто для информации выводит перечень оставшихся на складе у учёного частей после сборки роботов
    public void printListWithComponents() {
        System.out.println(Thread.currentThread().getName() + " List with components:");
        for (PartOfRobot part : listWithComponents.getListOfParts()) {
            String tmp = part.getClass().getCanonicalName();
            String name = tmp.replace(part.getClass().getPackage().getName() + ".PartsList.", " ");
            System.out.println(name + " - " + part.getCount());
        }
    }


    public int getCountOfRobots() {
        return countOfRobots;
    }

    public int getNumberOfScientist() {
        return numberOfScientist;
    }

    //в конструктор передаётся только экземпляр общей для учёных фабрики, остальные поля заполняются автоматически
    public Scientist(Factory factory) {
        this.factory = factory;
        this.numberOfScientist = countOfScientist + 1;
        countOfScientist++;
    }
}
