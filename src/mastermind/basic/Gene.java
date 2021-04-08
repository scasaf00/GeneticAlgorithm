package mastermind.basic;

import mastermind.utils.Colors;

public class Gene {

    private Colors color;

    public boolean eveluated = false;

    public Gene(Colors color){
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color){
        this.color = color;
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
            if (color == Colors.EMPTY) {
                out.append("|").append(" ").append("|");
            } else {
                out.append("|").append(color.getValue()).append("0").append(Colors.EMPTY.getValue()).append("|");
            }
            return out.toString();
        }
    }
}
