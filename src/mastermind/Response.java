package mastermind;

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
        int result = 0;
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

    public List<Gene> getReply(){
        return reply;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            out.append(reply.get(i).toString());
        }
        out.append("\t->\t").append(numericalValue);
        return out.toString();
    }
}
