package MyHomework_JavaJD2.lesson2Homework.scientists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dump {

    private volatile int currentNight = 0;
    private List<Parts> trashList = new ArrayList<>();


    public synchronized int getAnyPartIndex(int curNgt, Random rnd) {
        if (this.currentNight == curNgt) {

            // normal return values(>0):
            if (trashList.size() > 0) {
                Parts part = trashList.remove(rnd.nextInt(trashList.size()));
                return part.ordinal();
            } else {
                return InputData.RETURN_VALUE_DUMP_IS_EMPTY;//свалка пуста
            }

            //not normal return values(<0):
        } else if (this.currentNight < curNgt) {
            return InputData.RETURN_VALUE_IT_IS_STILL_TOO_EARLY;//слуга пришёл слишком рано(не наступила ночь)
        } else {//this.currentNight > curNgt слуга проспал ночь!
            return InputData.RETURN_VALUE_IT_IS_TOO_LATE;
        }
    }


    public void setCurrentNight(int currentNight) {
        this.currentNight = currentNight;
        System.out.println(currentNight + " night. The Dump has " + trashList.size() + " parts. " + Thread.currentThread().getName());
    }


    public synchronized void partToTrash(Parts part) {
        trashList.add(part);
    }
}
