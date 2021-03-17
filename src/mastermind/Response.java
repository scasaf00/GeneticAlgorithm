package mastermind;

public class Response {
    private final Gene[] reply = {new Gene(Colors.EMPTY), new Gene(Colors.EMPTY), new Gene(Colors.EMPTY), new Gene(Colors.EMPTY)};
    private int numericalValue;

    public Response(int white, int black){
        for(int i = 0; i < black; i++){
            reply[i] = new Gene(Colors.BLACK);
        }
        for(int i = black; i < white+black; i++){
            reply[i] = new Gene(Colors.WHITE);
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

    public Gene[] getReply(){
        return reply;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            out.append(reply[i].toString());
        }
        out.append("\t->\t").append(numericalValue);
        return out.toString();
    }
}
