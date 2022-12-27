package MyHomework_JavaJD2.lesson2Homework.Zadacha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static MyHomework_JavaJD2.lesson2Homework.Zadacha.Runner.*;

public class Factory extends Thread {

    private PartsList factoryComponentsList;//список всех составных частей выпускаемых фабрикой(заполняется при создании класса).
    // Счётчики количества частей во всех элементах дефолтные(0)
    //Данный объект никак не редактируется в данном классе, а служит лишь источником составных частей


    private List<PartOfRobot> trashList = new ArrayList<>();//список частей, которые сейчас находятся на свалке(может содержать одинаковые объекты)
    private int countOfKindOfComponents;//переменная полностью заменяет factoryComponentsList.getListOfParts().size(), поле заполняется конструктором
    public volatile int theCurrentNight = 0;//счётчик ночей на фабрике
    private Random random = new Random();


    //Фабрика создаётся на основе перечня выпускаемых её частей
    //при создании также заполняется перечень выброшенных частей по состоянию на первую ночь в соответствии с условием
    public Factory(PartsList factoryComponentsList) {
        this.factoryComponentsList = factoryComponentsList;
        countOfKindOfComponents = factoryComponentsList.getListOfParts().size();
        for (int i = 0; i < FIRST_NIGHT_FACTORY_TRASH_COUNT; i++) {
            PartOfRobot part = factoryComponentsList.getListOfParts().get(random.nextInt(countOfKindOfComponents));
            trashList.add(part);
        }
    }


    @Override
    public void run() {//метод выполняется при запуске потока
        for (theCurrentNight = 0; theCurrentNight < COUNT_OF_ALL_NIGHTS; theCurrentNight++) {
            System.out.println(theCurrentNight + " night, factory has  " + trashList.size() + " parts");
            try {
                Thread.sleep(TIME_OF_NIGHT_MSEC);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(theCurrentNight!=COUNT_OF_ALL_NIGHTS-1) {
                nextNight();//пополнение свалки к следующему приходу слуг
            }
        }
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " factory finished.");
        }
    }


    //Метод заполняет свалку случайным количеством случайных робочастей(из списка производимых фабрикой)
// к следующему приходу слуг в соответствии с заданными условиями
    public void nextNight() {
        //расчёт количества частей, которые будут сегодня выброшены
        int todayCount = random.nextInt(EVERY_NIGHT_MAX_FACTORY_TRASH_COUNT + 1 - EVERY_NIGHT_MIN_FACTORY_TRASH_COUNT) + EVERY_NIGHT_MIN_FACTORY_TRASH_COUNT;
        //выбор тех видов робочастей, которые попадут сегодня на свалку
        for (int i = 0; i < todayCount; i++) {
            PartOfRobot part = factoryComponentsList.getListOfParts().get(random.nextInt(countOfKindOfComponents));
            trashList.add(part);//забрасывание части на свалку
        }
        System.out.println("factory add to trash " + todayCount + " component(s)");
    }

    //единственный синхронизированный метод в программе,
// который выдаёт одну случайную робочасть из тех, что находятся сейчас на свалке
    public synchronized PartOfRobot getAnyPart() {
        if (trashList.size() == 0) {//выдаёт только когда свалка не пуста
            return null;
        }
        int index = random.nextInt(trashList.size());
        PartOfRobot anyPart = trashList.remove(index);//при выдаче эта часть удаляется из списка
        return anyPart;
    }
}
