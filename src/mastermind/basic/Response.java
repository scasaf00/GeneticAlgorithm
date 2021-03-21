package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private final List<Gene> reply = new ArrayList<>();
    private int numericalValue;

    public Response(int white, int black){

        for(int i = 0; i < Window.NUM_GENES; i++){
            reply.add(new Gene(Colors.EMPTY));
        }

        for(int i = 0; i < white; i++){
            reply.get(i).setColor(Colors.WHITE);
        }
        for(int i = white; i < (black+white); i++){
            reply.get(i).setColor(Colors.BLACK);
        }

        setChromosomeValue(white, black);
    }

    // Calculation of the numerical value for the evaluation of the chromosome
    private void setChromosomeValue(int white, int black){
        int result;
        int sum = 0;
        for(int i = 1; i < (white+black); i++){
            sum += i;
        }
        result = (2*black + white) + sum;
        this.numericalValue = result;
    }

    public int getNumericalValue() {
        return numericalValue;
    }

    @Override
    public String toString() {
        return new ThreadToString().call();
    }

    private class ThreadToString extends Thread{
        public ThreadToString(){
            super();
        }
        public String call(){
            StringBuilder out = new StringBuilder();
            for(int i = 0; i < 4; i++) {
                out.append(reply.get(i).toString());
            }
            out.append("\t->\t").append(numericalValue);
            return out.toString();
        }
    }
}
