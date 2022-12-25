package MyHomework_JavaJD2.lesson2Homework.Zadacha;

public abstract class PartOfRobot_Zadacha {
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
