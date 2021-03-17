package mastermind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chromosome {

    private List<Gene> genes;
    private Response response;
    private int value;

    // Constructor for the initial guess code
    public Chromosome(){
        //TODO
        ArrayList<Gene> l = new ArrayList<>();
        for(int i = 0 ; i < 4; i++){
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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Iterator<Gene> it = genes.iterator();
        while(it.hasNext()) {out.append(it.next().toString());}
        if(this.response != null) out.append("\t").append(response.toString());
        return out.toString();
    }
}
