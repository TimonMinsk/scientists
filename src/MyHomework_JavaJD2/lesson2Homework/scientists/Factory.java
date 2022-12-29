package MyHomework_JavaJD2.lesson2Homework.scientists;

import java.util.Random;

public class Factory extends Thread {

    private Dump dump;
    private Random random;


    public Factory(Dump dump, Random random) {
        this.dump = dump;
        this.random = random;
    }


    @Override
    public void run() {
        System.out.println("Factory. " + Thread.currentThread().getName() + " start.");
        for (int currentNight = 0; currentNight < InputData.COUNT_OF_ALL_NIGHTS; ) {//ночи фабрики начинаются с индекса 0 (для задания начального состояния свалки)

            //создаётся начальное состояние свалки
            if (currentNight == 0) {   //создаётся начальное состояние свалки
                for (int j = 0; j < InputData.FIRST_NIGHT_FACTORY_TRASH_COUNT; j++) {
                    generateAnyTrash();
                }
                System.out.println("The 1st day has come. \nFactory generate " + InputData.FIRST_NIGHT_FACTORY_TRASH_COUNT + " parts. " + Thread.currentThread().getName());
            }

            //повседневная работа фабрики:
            else {
                int countOfTonightParts = random.nextInt
                        (InputData.EVERY_NIGHT_MAX_FACTORY_TRASH_COUNT
                                - InputData.EVERY_NIGHT_MIN_FACTORY_TRASH_COUNT + 1)
                        + InputData.EVERY_NIGHT_MIN_FACTORY_TRASH_COUNT;
                for (int j = 0; j < countOfTonightParts; j++) {
                    generateAnyTrash();
                }
                System.out.println("The " + (currentNight + 1) + " day has come. \nFactory generate " + countOfTonightParts + " parts. " + Thread.currentThread().getName());
            }
            synchronized (dump) {
                dump.setCurrentNight(++currentNight);
            }
            try {
                Thread.sleep(InputData.TIME_OF_NIGHT_MSEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Factory. " + Thread.currentThread().getName() + " finished.");
    }


    private void generateAnyTrash() {
        int index = random.nextInt(Parts.values().length);
        dump.partToTrash(Parts.values()[index]);
    }
}
