package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;
import java.util.LinkedList;
import java.util.List;

public class Response {
    private final List<Gene> reply = new LinkedList<>();
    private int numericalValue;
    private final int white;
    private final int black;

    public Response(int white, int black){

        this.white = white;
        this.black = black;

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

    public List<Gene> getReply(){return this.reply;}

    public int getWhite() { return white; }

    public int getBlack() { return black; }

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
            for(int i = 0; i < Window.NUM_GENES; i++) {
                out.append(reply.get(i).toString());
            }
            out.append("\t->\t").append(numericalValue);
            return out.toString();
        }
    }
}
