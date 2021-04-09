package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.LinkedList;
import java.util.List;

public class Chromosome {

    private final List<Gene> genes;
    private Response response;
    private int value;
    private boolean elite = false;

    // Constructor for the initial guess code
    public Chromosome(){
        List<Gene> l = new LinkedList<>();
        for(int i = 0; i < Window.NUM_GENES; i++){
            switch ((int) (Math.random()*7+1)){
                case 1:
                    l.add(new Gene(Colors.RED));
                    break;
                case 2:
                    l.add(new Gene(Colors.YELLOW));
                    break;
                case 3:
                    l.add(new Gene(Colors.BLUE));
                    break;
                case 4:
                    l.add(new Gene(Colors.GREEN));
                    break;
                case 5:
                    l.add(new Gene(Colors.PURPLE));
                    break;
                case 6:
                    l.add(new Gene(Colors.BLACK));
                    break;
                case 7:
                    l.add(new Gene(Colors.WHITE));
                    break;
            }
        }
        this.genes = l;
    }

    public Chromosome(List<Gene> genes){
        this.genes = genes;
    }

    public Chromosome(Colors c1, Colors c2, Colors c3, Colors c4){
        List<Gene> l = new LinkedList<>();
        l.add(new Gene(c1));
        l.add(new Gene(c2));
        l.add(new Gene(c3));
        l.add(new Gene(c4));
        this.genes = l;
    }

    public void setValue() {
        this.value = this.response.getNumericalValue();
    }

    public int getValue() {
        return value;
    }

    public void setResponse(int white, int black){
        this.response = new Response(white, black);
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(int pos, Colors color) {
        this.genes.remove(pos);
        this.genes.add(pos, new Gene(color));
    }

    public Response getResponse(){ return this.response; }

    public Boolean getElite(){return this.elite;}

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
            for (Gene gene : genes) { out.append(gene.toString()); }
            if(response != null) out.append("\t").append(response.toString());
            return out.toString();
        }
    }
}