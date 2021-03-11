package scasaf00.ce.basic;

import scasaf00.ce.exceptions.GeneticAlgorithmException;
import scasaf00.ce.utils.Utils;

public class Gene {

    private int value;

    private final int min;

    private final int max;

    public Gene(int value, int min, int max) {
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public int getValue() {
        return this.value;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public void changeValue(int newValue) {
        if (newValue < this.min || newValue > this.max)
            throw new GeneticAlgorithmException("Se esta intentando cambiar el valor del gen a " + newValue + "(Debe estar en [" + this.min + ", " + this.max + "])");
        this.value = newValue;
    }

    public void mutate() {
        this.value = Utils.generateRandom(this.min, this.max);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}