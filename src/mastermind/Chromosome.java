package mastermind;

import java.util.ArrayList;
import java.util.List;

public class Chromosome {

    private final List<Gene> genes;
    private Response response;
    private int value;

    // Constructor for the initial guess code
    public Chromosome(){
        ArrayList<Gene> l = new ArrayList<>();
        for(int i = 0 ; i < Window.NUM_GENES; i++){
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

    public void setValue() {
       this.value = this.response.getNumericalValue();
    }

    public int getValue() {
        return value;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(int white, int black){
        this.response = new Response(white, black);
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(int pos, Colors color) {
        this.genes.get(pos).setColor(color);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Gene gene : genes) { out.append(gene.toString()); }
        if(this.response != null) out.append("\t").append(response.toString());
        return out.toString();
    }
}
