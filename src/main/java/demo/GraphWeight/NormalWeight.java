package demo.GraphWeight;

import java.io.Serializable;

public class NormalWeight implements GraphWeight, Serializable {

    private String name;
    private double value;

    public NormalWeight() {
    }

    public NormalWeight(String name, double value) {
        this.name = name;
        this.value = value;
    }

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
