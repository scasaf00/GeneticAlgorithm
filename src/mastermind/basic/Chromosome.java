package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.replaceAll;

/**
 * @author Sergio Casado Fernandez
 * @version 1.0v
 */

public class Chromosome {
    /**
     * List with the genes of the chromosome
     */
    private final List<Gene> genes;
    /**
     * Special type of Gene to indicate value of the chromosome (Visual value: number white and black)
     */
    private Response response;
    /**
     * Value of the chromosome (Integer value)
     */
    private int value;

    /**
     * Constructor for the initial guess code and population
     */
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

    /**
     * Constructor based on a list of genes
     * @param genes - List with the genes of the chromosome
     */
    public Chromosome(List<Gene> genes){
        List<Gene> newGenes = new ArrayList<>();
        for (Gene gene : genes) {
            newGenes.add(new Gene(gene.getColor()));
        }
        this.genes = newGenes;
    }

    /**
     * Constructor for development options (Don't go in the release)
     * @param c1 - First color
     * @param c2 - Second color
     * @param c3 - Third color
     * @param c4 - Fourth color
     */
    public Chromosome(Colors c1, Colors c2, Colors c3, Colors c4){
        List<Gene> l = new ArrayList<>();
        l.add(new Gene(c1));
        l.add(new Gene(c2));
        l.add(new Gene(c3));
        l.add(new Gene(c4));
        this.genes = l;
    }

    /**
     * Method to replace gene in position 'pos' with 'gene'
     * @param pos - Position to replace
     * @param gene - Gene to replace with
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void replace(int pos, Gene gene){
        replaceAll(genes, genes.get(pos), gene);
    }

    /**
     * Setter of the numerical value
     */
    public void setValue() {
        this.value = this.response.getNumericalValue();
    }

    /**
     * Getter of the numerical value
     * @return value
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter of the visual value
     * @param white - Number of white genes reply
     * @param black - Number of black genes reply
     */
    public void setResponse(int white, int black){
        this.response = new Response(white, black);
    }

    /**
     * Getter of the gene list
     * @return genes
     */
    public List<Gene> getGenes() {
        return genes;
    }

    /**
     * Setter of the color in position 'pos' with 'color'
     * @param pos - Position to set in
     * @param color - Color to set
     */
    public void setGenes(int pos, Colors color) {
        this.genes.remove(pos);
        this.genes.add(pos, new Gene(color));
    }

    /**
     * Getter of the visual value
     * @return response
     */
    public Response getResponse(){ return this.response; }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Gene gene : genes) { out.append(gene.toString()); }
        if(response != null) out.append("\t").append(response);
        return out.toString();
    }
}