package MyHomework_JavaJD2.lesson2Homework.scientists;


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
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        Dump dump = new Dump();//общая свалка
        Factory factory = new Factory(dump, random);//общая фабрика компонентов
        List<Scientist> academyOfScience = new ArrayList<>();//академия сумасшедщих учёных


        for (int i = 0; i < InputData.COUNT_OF_ALL_SCIENTISTS; i++) {
            Scientist scientist = new Scientist(dump, random);//создаётся i-ый ученый
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

        int winnerCountOfRobots = academyOfScience.stream()
                .map(scientist -> scientist.getCountOfRobots())
                .max(Integer::compareTo)
                .get();

        if (winnerCountOfRobots == 0) {
            System.out.println("Nobody had build any robots. ");
        } else {
            List<Scientist> winnersList = academyOfScience.stream()
                    .filter(scientist -> scientist.getCountOfRobots() == winnerCountOfRobots)
                    .collect(Collectors.toList());
            if (winnersList.size() > 1) {
                System.out.println("The same max number of robots were build by " + winnersList.size() + " scientists: ");
                for (Scientist scientist : winnersList) {
                    System.out.println("The scientist number " + scientist.getScientistID() + ";");
                }
            } else {
                System.out.println("The winner is scientist number " + winnersList.get(0).getScientistID() + ";");
                System.out.println("Hi had build " + winnerCountOfRobots + " robots.");
            }
        }
    }
}
