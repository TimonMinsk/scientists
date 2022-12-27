package MyHomework_JavaJD2.lesson2Homework.Zadacha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static MyHomework_JavaJD2.lesson2Homework.Zadacha.Runner.EVERY_NIGHT_MAX_SERVANT_PART_COUNT;
import static MyHomework_JavaJD2.lesson2Homework.Zadacha.Runner.EVERY_NIGHT_MIN_SERVANT_PART_COUNT;

public class Servant {

    private List<PartOfRobot> servantComponentsList = new ArrayList<>();//пустой "мешок" для переноски собраных частей
                                                                        //со свалки своему хозяину
    private Random random = new Random();


//метод берёт несколько частей со свалки
    public void takeAnyParts(Factory factory) {
        int servantCountOfComponentsPerNight = random//расчёт числа робочастей, которые слуга ХОЧЕТ сегодня принести
                .nextInt(EVERY_NIGHT_MAX_SERVANT_PART_COUNT + 1
                        - EVERY_NIGHT_MIN_SERVANT_PART_COUNT) + EVERY_NIGHT_MIN_SERVANT_PART_COUNT;

        PartOfRobot part;
        for (int i = 0; i < servantCountOfComponentsPerNight; i++) {//метод несколько раз обращается к свалке фабрики(единсвенному синхронизированному методу)
            part = factory.getAnyPart();
            if (part != null) {
                servantComponentsList.add(part);//каждый раз получая по одной робочасти(или null если свалка пуста)
            }
        }
    }


    //метод отдаёт все принесённые сегодня слугой робочасти на склад учёному
    public void giveAllParts(PartsList listWithComponents) {
        for (PartOfRobot servantPart : servantComponentsList) {
            Class servPrt = servantPart.getClass();//определение типа составной робочастив "мешке" у слуги
            for (PartOfRobot sientistPart : listWithComponents.getListOfParts()) {
                Class sienPrt = sientistPart.getClass();
                if (servPrt.getName().equals(sienPrt.getName())) {//определение "стелажа" на складе учёного, куда нужно положит деталь
                    sientistPart.incrCount(1);//передача осуществляется путём увеличения внутреннего счётчика в экземпляре объекта
                    break;
                }
            }
        }
        servantComponentsList.clear();//очистка "мешка" слуги после предачи всего содержимого на склад
    }


    public int getCountOfNightParts(){
        return servantComponentsList.size();
    }
}
