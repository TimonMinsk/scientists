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

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

public class ZadachaRunner  {

    public static final int FIRST_NIGHT_FACTORY_PART_COUNT = 20;
    public static final int EVERY_NIGHT_MAX_FACTORY_PART_COUNT = 4;
    public static final int EVERY_NIGHT_MIN_FACTORY_PART_COUNT = 1;
    public static final int EVERY_NIGHT_MIN_SERVANT_PART_COUNT = 1;
    public static final int EVERY_NIGHT_MAX_SERVANT_PART_COUNT = 4;
    public static final int COUNT_OF_ALL_NIGHTS = 100;

    public static void main(String[] args) throws InterruptedException {
        PartsList_Zadacha partsList = new PartsList_Zadacha();//объект с набором выпускаемых(и необходимых для сборки роботов) компонентов
        Factory_Zadacha factory = new Factory_Zadacha(partsList);//общая фабрика компонентов
        Scientist_Zadacha scientist1st = new Scientist_Zadacha(factory);//первый ученый
        Scientist_Zadacha scientist2st = new Scientist_Zadacha(factory);//второй ученый

        Thread thread1 = factory;
        Thread thread2 = scientist1st;
        Thread thread3 = scientist2st;

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        checkTheWinner(scientist1st, scientist2st);
        System.out.println(Thread.currentThread().getName() + "  finished.");
    }

    private static void checkTheWinner(Scientist_Zadacha scientist1st, Scientist_Zadacha scientist2st) {
        if (scientist1st.getCountOfRobots() == 0&&scientist2st.getCountOfRobots()==0) {
            System.out.println("Nobody had build any robots. ");
        }else if(scientist1st.getCountOfRobots() == scientist2st.getCountOfRobots()){
            System.out.println("The same number of robots were build ");
        }else {
            System.out.println(scientist1st.getCountOfRobots() > scientist2st.getCountOfRobots() ? "1st scientist is winner." : "2nd scientist is winner.");
        }
    }
}
