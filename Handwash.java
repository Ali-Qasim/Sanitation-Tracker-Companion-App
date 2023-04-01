public class Handwash implements Comparable<Handwash> {

    String time;
    String id;
    double duration;

    public Handwash(String time, String id, double duration) {
        this.time = time;
        this.id = id;
        this.duration = duration;
    }

    @Override
    public int compareTo(Handwash o) {
        return Double.compare(this.duration, o.duration);
    }

    public String toString() {

        return this.time + "\t" + this.id + "\t" + this.duration;
    }
}
