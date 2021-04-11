package mastermind.basic;

import mastermind.utils.Colors;

public class Gene {

    private Colors color;

    private boolean evaluated = false;

    public Gene(Colors color){
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color){
        this.color = color;
    }

    public boolean getEvaluated(){return this.evaluated;}

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
