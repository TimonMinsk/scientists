package MyHomework_JavaJD2.lesson2Homework.scientists;


/*Задача
$ДВОЕ$ безумных учёных устроили соревнование, кто из них соберёт больше роботов за $100$ ночей.
Для этого $каждую$ ночь каждый из них отправляет своего прислужника на свалку фабрики
 роботов за деталями. Чтобы собрать $одного$ робота им нужно:
$Голова, Тело, Левая рука, Правая рука, Левая нога, Правая нога, CPU, RAM, HDD$
В $первую$ ночь на свалке находится $20$ случайных деталей. Каждую ночь фабрика выбрасывает на
 свалку от $1 до 4$ случайных деталей.
В то же самое время прислужники обоих учёных отправляются на свалку, и каждый собирает от $1
 до 4$ случайных деталей. Если на свалке деталей нет – прислужник уходит ни с чем.
Затем они возвращаются домой и отдают детали хозяевам.
Учёные пытаются собрать как можно больше роботов из деталей, которые они получили.
Написать программу, симулирующую этот процесс. Для симуляции принять, что каждая ночь
 наступает через $100мс$.

Фабрика и два прислужника действуют в одно и то же время.
После 100 ночей (около 10 секунд) определить победителя соревнования.*/


public interface InputData {
    public static final int FIRST_NIGHT_FACTORY_TRASH_COUNT = 20;
    public static final int EVERY_NIGHT_MIN_FACTORY_TRASH_COUNT = 1;
    public static final int EVERY_NIGHT_MAX_FACTORY_TRASH_COUNT = 4;
    public static final int EVERY_NIGHT_MIN_SERVANT_PART_COUNT = 1;
    public static final int EVERY_NIGHT_MAX_SERVANT_PART_COUNT = 4;
    public static final long TIME_OF_NIGHT_MSEC = 100;
    public static final int COUNT_OF_ALL_SCIENTISTS = 2;
    public static final int COUNT_OF_ALL_NIGHTS = 100;

    //The Dump not normal return values:
    public static final int RETURN_VALUE_DUMP_IS_EMPTY = Integer.MAX_VALUE;//обязательно >=0
    public static final int RETURN_VALUE_IT_IS_STILL_TOO_EARLY = -1;//обязательно <0
    public static final int RETURN_VALUE_IT_IS_TOO_LATE = -2;//обязательно <0

    public static final float PART_OF_TIME_OF_NIGHT_TO_SLEEP_NEMNOGO = 0.1f;
}
