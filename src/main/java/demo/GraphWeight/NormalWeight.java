package demo.GraphWeight;

public class NormalWeight implements GraphWeight {

    private String name;
    private double value;

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setValue(String value){
        this.value = Double.valueOf(value);
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name+" "+value+" ";
    }
}
