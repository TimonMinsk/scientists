package MyHomework_JavaJD2.lesson2Homework.Zadacha;

/*Задача
Двое безумных учёных устроили соревнование, кто из них соберёт больше роботов за 100 ночей.
Для этого каждую ночь каждый из них отправляет своего прислужника на свалку фабрики
 роботов за деталями. Чтобы собрать одного робота им нужно:
Голова, Тело, Левая рука, Правая рука, Левая нога, Правая нога, CPU, RAM, HDD
В первую ночь на свалке находится 20 случайных деталей. Каждую ночь фабрика выбрасывает на
 свалку от 1 до 4 случайных деталей.
В то же самое время прислужники обоих учёных отправляются на свалку, и каждый собирает от 1
 до 4 случайных деталей. Если на свалке деталей нет – прислужник уходит ни с чем.
Затем они возвращаются домой и отдают детали хозяевам.
Учёные пытаются собрать как можно больше роботов из деталей, которые они получили.
Написать программу, симулирующую этот процесс. Для симуляции принять, что каждая ночь
 наступает через 100мс.

Фабрика и два прислужника действуют в одно и то же время.
После 100 ночей (около 10 секунд) определить победителя соревнования.*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Runner {

    //все заданные условиями задачи параметры вносятся в одном месте:
    public static final int FIRST_NIGHT_FACTORY_TRASH_COUNT = 20;
    public static final int EVERY_NIGHT_MAX_FACTORY_TRASH_COUNT = 4;
    public static final int EVERY_NIGHT_MIN_FACTORY_TRASH_COUNT = 1;
    public static final int EVERY_NIGHT_MIN_SERVANT_PART_COUNT = 1;
    public static final int EVERY_NIGHT_MAX_SERVANT_PART_COUNT = 4;
    public static final int TIME_OF_NIGHT_MSEC = 100;
    public static final int COUNT_OF_ALL_SCIENTISTS = 4;
    public static final int COUNT_OF_ALL_NIGHTS = 100;


    public static void main(String[] args) throws InterruptedException {

        PartsList partsList = new PartsList();//объект с набором выпускаемых(и необходимых для сборки роботов) компонентов
        Factory factory = new Factory(partsList);//общая фабрика компонентов
        List<Scientist> academyOfScience = new ArrayList<>();//академия сумасшедщих учёных


        for (int i = 0; i < COUNT_OF_ALL_SCIENTISTS; i++) {
            Scientist scientist = new Scientist(factory);//i-ый ученый
            academyOfScience.add(scientist);//заполнение академии учёными
        }

        //запуск всех потоков
        factory.start();
        for (Thread scientist : academyOfScience) {
            scientist.start();
        }

        //ожидание завершения гонки
        factory.join();
        for (Thread scientist : academyOfScience) {
            scientist.join();
        }

//подсчёт результатов
        checkTheWinner(academyOfScience);
        System.out.println(Thread.currentThread().getName() + "  finished.");
    }

    private static void checkTheWinner(List<Scientist> academyOfScience) {

        int winnerCount = academyOfScience.stream()
                .map(scientist -> scientist.getCountOfRobots())
                .max(Integer::compareTo)
                .get();

        if (winnerCount == 0) {
            System.out.println("Nobody had build any robots. ");
        } else {
            List<Scientist> winnersList = academyOfScience.stream()
                    .filter(scientist -> scientist.getCountOfRobots() == winnerCount)
                    .collect(Collectors.toList());
            if (winnersList.size() > 1) {
                System.out.println("The same max number of robots were build by "+winnersList.size()+" scientists: ");
                for (Scientist scientist : winnersList) {
                    System.out.println("The scientist number "+scientist.getNumberOfScientist()+";");
                }
            }else {
                System.out.println("The winner is scientist number "+winnersList.get(0).getNumberOfScientist()+";");
                System.out.println("Hi had build "+winnerCount+" robots.");
            }
        }
    }
}
