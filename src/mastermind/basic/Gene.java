package mastermind.basic;

import mastermind.utils.Colors;

public class Gene {

    /**
     * Gene associated color
     */
    private Colors color;

    /**
     * Flag to indicate the evaluated state of the gene
     */
    private boolean evaluated = false;

    /**
     * Class constructor
     * @param color - Gene associated color
     */
    public Gene(Colors color){
        this.color = color;
    }

    /**
     * Getter for the color
     * @return color
     */
    public Colors getColor() {
        return color;
    }

    /**
     * Setter for the color
     * @param color - Gene associated color
     */
    public void setColor(Colors color){
        this.color = color;
    }

    /**
     * Getter for the (denied) evaluated flag
     * @return evaluated
     */
    public boolean getEvaluated(){return !this.evaluated;}

    /**
     * Setter for the evaluated flag
     * @param evaluated - Boolean flag to indicate evaluation
     */
    public void setEvaluated(boolean evaluated){ this.evaluated = evaluated;}

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        if (color == Colors.EMPTY) {
            out.append("|").append(" ").append("|");
        } else {
            out.append("|").append(color.getValue()).append("0").append(Colors.EMPTY.getValue()).append("|");
        }
        return out.toString();
    }
}
