package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.ArrayList;
import java.util.List;

public class Response {
    /**
     * List of genes of the reply (Chromosome)
     */
    private final List<Gene> reply = new ArrayList<>();
    /**
     * Numerical value
     */
    private int numericalValue;
    /**
     * Number of white genes
     */
    private final int white;
    /**
     * Number of black genes
     */
    private final int black;

    /**
     * Constructor
     * @param white - Number of white genes
     * @param black - Number of black genes
     */
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

    /**
     * Calculation of the numerical value for the evaluation of the chromosome
     * @param white - Number of white genes
     * @param black - Number of black genes
     */
    private void setChromosomeValue(int white, int black){
        int result;
        int sum = 0;
        for(int i = 1; i < (white+black); i++){
            sum += i;
        }
        result = (2*black + white) + sum;
        this.numericalValue = result;
    }

    /**
     * Getter of the numerical value
     * @return numericalValue
     */
    public int getNumericalValue() {
        return numericalValue;
    }

    /**
     * Getter of the chromosome reply
     * @return reply
     */
    public List<Gene> getReply(){return this.reply;}

    /**
     * Getter of the number of white genes
     * @return white
     */
    public int getWhite() { return white; }

    /**
     * Getter of the number of black genes
     * @return black
     */
    public int getBlack() { return black; }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < Window.NUM_GENES; i++) {
            out.append(reply.get(i).toString());
        }
        out.append("\t->\t").append(numericalValue);
        return out.toString();
    }
}
