package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.replaceAll;

public class Chromosome {

    private final List<Gene> genes;
    private Response response;
    private int value;

    // Constructor for the initial guess code and population
    public Chromosome(){
        List<Gene> l = new ArrayList<>();
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
        List<Gene> newGenes = new ArrayList<>();
        for (Gene gene : genes) {
            newGenes.add(new Gene(gene.getColor()));
        }
        this.genes = newGenes;
    }

    public Chromosome(Colors c1, Colors c2, Colors c3, Colors c4){
        List<Gene> l = new ArrayList<>();
        l.add(new Gene(c1));
        l.add(new Gene(c2));
        l.add(new Gene(c3));
        l.add(new Gene(c4));
        this.genes = l;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void replace(int pos, Gene gene){
        replaceAll(genes, genes.get(pos), gene);
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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Gene gene : genes) { out.append(gene.toString()); }
        if(response != null) out.append("\t").append(response.toString());
        return out.toString();
    }
}