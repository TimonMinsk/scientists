package MyHomework_JavaJD2.lesson2Homework.scientists;

public abstract class PartOfRobot {//на основе этого класса можно создать любое количество разных составных робочастей
                                    // в классе PartList в виде внутренних статических классов именно так и сделано

    //единственное поле - счётчик частей данного типа
    private int count = 0;


    public int getCount() {
        return count;
    }

    public void incrCount(int count) {
        this.count += count;
    }

    public void decrCount(int count) {
        this.count -= count;
    }
}
