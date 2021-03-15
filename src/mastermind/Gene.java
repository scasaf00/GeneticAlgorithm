package mastermind;

public class Gene {

    private final Colors color;

    /*
     *  Colors for terminal
     */
    String red="\033[31m";
    String yellow="\033[33m";
    String blue="\033[34m";
    String green="\033[32m";
    String purple="\033[35m";
    String black="\033[30m";
    String white="\033[37m";
    String reset="\u001B[0m";

    public Gene(Colors color){
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        switch (color){
            case RED: out.append("|").append(red).append("0").append(reset).append("|"); break;
            case YELLOW: out.append("|").append(yellow).append("0").append(reset).append("|"); break;
            case BLUE: out.append("|").append(blue).append("0").append(reset).append("|"); break;
            case GREEN: out.append("|").append(green).append("0").append(reset).append("|"); break;
            case PURPLE: out.append("|").append(purple).append("0").append(reset).append("|"); break;
            case BLACK: out.append("|").append(black).append("0").append(reset).append("|"); break;
            case WHITE: out.append("|").append(white).append("0").append(reset).append("|"); break;
        }
        return out.toString();
    }
}
