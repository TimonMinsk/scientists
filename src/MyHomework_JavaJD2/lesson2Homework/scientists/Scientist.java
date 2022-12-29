package MyHomework_JavaJD2.lesson2Homework.scientists;

import java.util.Arrays;
import java.util.Random;

public class Scientist extends Thread {

    private boolean isClockOK = true;//переменная позволяет пропустить время сна потока, если вдруг получится, что данный поток отстал
    private Dump dump;
    private Random random;
    private int[] storageOfParts = new int[Parts.values().length];
    private int countOfRobots = 0;//количество собранных каждым учёным роботов
    private int scientistID = 0;//номер учёного в академии
    private static int countOfScientist = 0;//количество учёных уже вступивших в академию


    public Scientist(Dump dump, Random random) {
        this.dump = dump;
        this.random = random;
        this.scientistID = ++countOfScientist;
    }


    @Override
    public void run() {
        System.out.println("Scientist " + scientistID + ". " + Thread.currentThread().getName() + " start.");
        Arrays.fill(storageOfParts, 0);//на складе пусто(количество деталей каждого элемента равно 0)

        //ночи учёных начинаются с индекса 1, под индексом 0 создаётся начальное состояние свалки
        for (int currentNight = 1; currentNight < InputData.COUNT_OF_ALL_NIGHTS + 1; currentNight++) {
            int countOfTonightParts = getCountOfTonightParts();
            everyNightProcess(currentNight, countOfTonightParts);
        }
        buildRobots();
        printResult();
    }


    private void buildRobots() {
        int minCount = Arrays.stream(storageOfParts)
                .min()
                .getAsInt();
        countOfRobots = minCount;
        for (int i = 0; i < storageOfParts.length; i++) {
            int count = storageOfParts[i];
            count -= minCount;
            storageOfParts[i] = count;
        }
    }


    private void printResult() {
        synchronized (dump) {
            System.out.println();
            System.out.println(Thread.currentThread().getName()+". List of remaining parts of scientist " + scientistID + ": ");
            for (int i = 0; i < storageOfParts.length; i++) {
                System.out.println(Parts.values()[i] + ": " + storageOfParts[i]);
            }
            System.out.println("Scientist number " + scientistID+" has build "+countOfRobots+" robots");
            System.out.println(Thread.currentThread().getName() + " finished.");
            System.out.println();
        }
    }


    private void everyNightProcess(int currentNight, int countOfTonightParts) {
        int takenParts = 0;
        for (int j = 0; j < countOfTonightParts; j++) {
            int index = takeAnyPart(currentNight, j);
            if (index == InputData.RETURN_VALUE_DUMP_IS_EMPTY) {
                break;//если свалка пуста, сегодня не нужно больше пытаться взять детали
            }
            int count = storageOfParts[index];
            try {
                storageOfParts[index] = ++count;
                takenParts++;
            } catch (IndexOutOfBoundsException e) {
                throw new RuntimeException("incorrect return value of Dump dump.getAnyPartIndex(). value=" + index);
            }
        }
        System.out.println(currentNight + " night. Servant want to take "+countOfTonightParts+" parts. Has brought " + takenParts + " parts to scientist " + scientistID + ". " + Thread.currentThread().getName());
        if (isClockOK) {
            try {
                Thread.sleep(InputData.TIME_OF_NIGHT_MSEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isClockOK=true;
    }


    private int takeAnyPart(int currentNight, int j) {
        int partIndex = dump.getAnyPartIndex(currentNight, random);
        if (partIndex >= 0) {
            return partIndex;
        } else {//обработка исключительных ситуаций:
            do {
                switch (partIndex) {
                    case InputData.RETURN_VALUE_IT_IS_STILL_TOO_EARLY://ночь на свалке ещё не наступила(часы учёного спешат и его слуга пришёл слишком рано)
                        try {
                            System.out.println(Thread.currentThread().getName()+" HAVE_TO_SLEEP_NEMNOGO");
                            Thread.sleep((long) (InputData.TIME_OF_NIGHT_MSEC * InputData.PART_OF_TIME_OF_NIGHT_TO_SLEEP_NEMNOGO));//нужно ещё немного поспать
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        partIndex = dump.getAnyPartIndex(currentNight, random);
                        break;
                    case InputData.RETURN_VALUE_IT_IS_TOO_LATE://ученый проспал ночь(часы учёного отстают)
                        isClockOK=false;//подогнали учёному часы
                        partIndex = dump.getAnyPartIndex(currentNight + 1, random);//Отправляем слугу вдогонку
                        System.out.println("Servant prospall i otstal ot zjizni!"+ Thread.currentThread().getName());
                        break;
                    default:
                        throw new RuntimeException("incorrect return value of Dump dump.getAnyPartIndex(). value=" + partIndex);
                }
            } while (partIndex < 0);
            return partIndex;
        }
    }


    private int getCountOfTonightParts() {//определение желаемого количества робочастей на сегодня
        return random
                .nextInt(InputData.EVERY_NIGHT_MAX_SERVANT_PART_COUNT
                        - InputData.EVERY_NIGHT_MIN_SERVANT_PART_COUNT + 1)
                + InputData.EVERY_NIGHT_MIN_SERVANT_PART_COUNT;
    }


    public int getCountOfRobots() {
        return countOfRobots;
    }


    public int getScientistID() {
        return scientistID;
    }
}
