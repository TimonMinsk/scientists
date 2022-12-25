package MyHomework_JavaJD2.lesson2Homework.Zadacha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static MyHomework_JavaJD2.lesson2Homework.Zadacha.ZadachaRunner.EVERY_NIGHT_MAX_SERVANT_PART_COUNT;
import static MyHomework_JavaJD2.lesson2Homework.Zadacha.ZadachaRunner.EVERY_NIGHT_MIN_SERVANT_PART_COUNT;

public class Servant_Zadacha {

    private List<PartOfRobot_Zadacha> servantComponentsList = new ArrayList<>();
    private Random random = new Random();


    public void takeAnyParts(Factory_Zadacha factory) {
        int servantCountOfComponentsPerNight = random
                .nextInt(EVERY_NIGHT_MAX_SERVANT_PART_COUNT + 1
                        - EVERY_NIGHT_MIN_SERVANT_PART_COUNT) + EVERY_NIGHT_MIN_SERVANT_PART_COUNT;
        PartOfRobot_Zadacha part;
        for (int i = 0; i < servantCountOfComponentsPerNight; i++) {
            part = factory.getAnyPart();
            if (part != null) {
                servantComponentsList.add(part);
            }
        }
    }

    public int getCountOfNightParts(){

        return servantComponentsList.size();
    }


    public void giveAllParts(PartsList_Zadacha listWithComponents) {
        for (PartOfRobot_Zadacha servantPart : servantComponentsList) {
            Class servPrt = servantPart.getClass();
            for (PartOfRobot_Zadacha sientistPart : listWithComponents.getListOfParts()) {
                Class sienPrt = sientistPart.getClass();
                if (servPrt.getName().equals(sienPrt.getName())) {
                    sientistPart.incrCount(1);
                    break;
                }
            }
        }
        servantComponentsList.clear();
    }
}
