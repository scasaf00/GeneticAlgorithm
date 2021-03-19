package mastermind;

import java.util.Arrays;

public class Response {
    private final Gene[] reply = {new Gene(Colors.EMPTY), new Gene(Colors.EMPTY), new Gene(Colors.EMPTY), new Gene(Colors.EMPTY)};
    private int numericalValue;

    public Response(int white, int black){

        switch (black){
            case 0:
                switch (white){
                    case 1:
                        reply[0] = new Gene(Colors.WHITE);
                        break;
                    case 2:
                        reply[0] = new Gene(Colors.WHITE);
                        reply[1] = new Gene(Colors.WHITE);
                        break;
                    case 3:
                        reply[0] = new Gene(Colors.WHITE);
                        reply[1] = new Gene(Colors.WHITE);
                        reply[2] = new Gene(Colors.WHITE);
                        break;
                    case 4:
                        reply[0] = new Gene(Colors.WHITE);
                        reply[1] = new Gene(Colors.WHITE);
                        reply[2] = new Gene(Colors.WHITE);
                        reply[3] = new Gene(Colors.WHITE);
                        break;
                }
                break;
            case 1:
                switch (white){
                    case 0:
                        reply[0] = new Gene(Colors.BLACK);
                        break;
                    case 1:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.WHITE);
                        break;
                    case 2:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.WHITE);
                        reply[2] = new Gene(Colors.WHITE);
                        break;
                    case 3:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.WHITE);
                        reply[2] = new Gene(Colors.WHITE);
                        reply[3] = new Gene(Colors.WHITE);
                        break;
                }
                break;
            case 2:
                switch (white){
                    case 0:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.BLACK);
                        break;
                    case 1:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.BLACK);
                        reply[2] = new Gene(Colors.WHITE);
                        break;
                    case 2:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.BLACK);
                        reply[2] = new Gene(Colors.WHITE);
                        reply[3] = new Gene(Colors.WHITE);
                        break;
                }
                break;
            case 3:
                switch (white){
                    case 0:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.BLACK);
                        reply[2] = new Gene(Colors.BLACK);
                        break;
                    case 1:
                        reply[0] = new Gene(Colors.BLACK);
                        reply[1] = new Gene(Colors.BLACK);
                        reply[2] = new Gene(Colors.BLACK);
                        reply[3] = new Gene(Colors.WHITE);
                        break;
                }
                break;
            case 4:
                reply[0] = new Gene(Colors.BLACK);
                reply[1] = new Gene(Colors.BLACK);
                reply[2] = new Gene(Colors.BLACK);
                reply[3] = new Gene(Colors.BLACK);
                break;
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
