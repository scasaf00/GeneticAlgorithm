package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.*;

import static java.util.Collections.replaceAll;

public class Population {
    /**
     * List of the population's chromosomes
     */
    private List<Chromosome> chromosomes = new ArrayList<>();
    /**
     * Chromosome with the highest value
     */
    private Chromosome bestChromosome;
    /**
     * Number of the actual generation
     */
    private int generation = 0;
    /**
     * Number of the total amount of fitness in the actual generation
     */
    public int totalFitness = 0;

    /**
     * Empty Constructor
     */
    public Population(){
    }

    /**
     * Generation of the initial population (randomly)
     */
    public void generate(){
        for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
            chromosomes.add(new Chromosome());
        }
        if(Window.SHOW_GENERATE){
            System.out.println("\nInitial Population:");
            System.out.println(this);
        }
    }

    /**
     * Evaluate every chromosome (compare to the 'genesCode')
     * @param genesCode - Genes that make up the secret code
     */
    public void evaluate(List<Gene> genesCode){
        for (Chromosome value : this.chromosomes) {
            int white = 0, black = 0;
            List<Gene> genesChromosome = value.getGenes();

            for(int i = 0; i < Window.NUM_GENES; i++)
                genesCode.get(i).setEvaluated(false);

            for (int i = 0; i < Window.NUM_GENES; i++) {
                for (int j = 0; j < Window.NUM_GENES; j++) {
                    if ((genesCode.get(j).getColor() == genesChromosome.get(i).getColor()) && (i == j) && genesCode.get(j).getEvaluated() && genesChromosome.get(i).getEvaluated()) {
                        black++;
                        genesCode.get(j).setEvaluated(true);
                        genesChromosome.get(i).setEvaluated(true);
                    }
                }
            }
            for (int i = 0; i < Window.NUM_GENES; i++) {
                for (int j = 0; j < Window.NUM_GENES; j++) {
                    if ((genesCode.get(j).getColor() == genesChromosome.get(i).getColor()) && (i != j) && genesCode.get(j).getEvaluated() && genesChromosome.get(i).getEvaluated()) {
                        white++;
                        genesCode.get(j).setEvaluated(true);
                        genesChromosome.get(i).setEvaluated(true);
                    }
                }
            }

            value.setResponse(white, black);
            value.setValue();
            totalFitness += value.getValue();
            if (this.getBestChromosome() == null || value.getValue() > this.getBestChromosome().getValue()) {
                Chromosome chromosome = new Chromosome(value.getGenes());
                chromosome.setResponse(value.getResponse().getWhite(), value.getResponse().getBlack());
                chromosome.setValue();
                this.setBestChromosome(chromosome);
            }
        }
        if(Window.SHOW_EVALUATE){
            System.out.println("\nEvaluation with color code:");
            System.out.println(" Chromosome\t\t   Reply\t\t Value");
            System.out.println(this.toString());
        }
    }

    /**
     * Stop condition if secret code is hit
     * @return boolean
     */
    public boolean stopCondition(){
        List<Gene> win = new ArrayList<>();
        for(int i = 0; i < Window.NUM_GENES; i++){
            win.add(new Gene(Colors.BLACK));
        }
        Iterator<Gene> it = this.bestChromosome.getResponse().getReply().iterator();
        Iterator<Gene> it2 = win.iterator();
        while(it.hasNext()){
            if(it.next().getColor() != it2.next().getColor()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Weight roulette selection of the best chromosomes
     */
    public void selection(){
        generation++;
        List<Chromosome> newChromosomes = new ArrayList<>();
        int totalFitness = 0;
        for (Chromosome chromosome : this.chromosomes) {
            totalFitness += chromosome.getValue();
        }
        for(int i = 0; i < Window.NUM_CHROMOSOMES; i++) {
            int rnd = (int) (Math.random() * totalFitness);
            int partialSum = 0;
            for (int j = Window.NUM_CHROMOSOMES - 1; j >= 0; j--) {
                partialSum += this.chromosomes.get(j).getValue();
                if (partialSum >= rnd) {
                    newChromosomes.add(i, new Chromosome(this.chromosomes.get(j).getGenes()));
                    break;
                }
            }
        }
        this.chromosomes = newChromosomes;
        if(Window.SHOW_SELECTED) {
            System.out.println("Generation: "+generation);
            System.out.println(" Seleccion");
            System.out.println(this.toString());
        }
    }

    /**
     * Crossover between two consecutive chromosomes
     */
    public void crossover(){

        StringBuilder out = new StringBuilder();
        Chromosome back;

        for(int i = 0; i < Window.NUM_CHROMOSOMES; i += 2){
            Chromosome c1 = this.chromosomes.get(i);
            Chromosome c2 = this.chromosomes.get(i+1);
            back = new Chromosome(this.chromosomes.get(i+1).getGenes());

            int bP = (int) (Math.random() * (Window.NUM_GENES-1)+1);
            out.append("Chromosomes\t").append(i).append("\t&\t").append(i + 1).append("\tbefore crossover at point\t").append(bP).append(" :\t").append(c1.toString()).append("\t,\t").append(c2.toString()).append("\n");

            cross(c1, c2, i, back, bP);

            out.append("Chromosomes\t").append(i).append("\t&\t").append(i + 1).append("\tafter crossover at point\t").append(bP).append(" :\t").append(c1.toString()).append("\t,\t").append(c2.toString()).append("\n");
            out.append("----------------------------------------------------------------------------------\n");
        }

        if(Window.SHOW_CROSSOVER)
            System.out.println(out);
    }

    /**
     * Auxiliary method to crossover
     * Cross the chromosomes 'c1' and 'c2' by position 'bP'
     * @param c1 - Chromosome to cross
     * @param c2 - Chromosome to cross
     * @param pos - Position of the chromosome 'c1'
     * @param back - Copy of the chromosome 'c2'
     * @param bP - Break point
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void cross(Chromosome c1, Chromosome c2, int pos, Chromosome back, int bP){
        for(int i = bP; i < Window.NUM_GENES; i++) {
            c2.replace(i, c1.getGenes().get(i));
            c1.replace(i, back.getGenes().get(i));
        }
        replaceAll(this.chromosomes, this.chromosomes.get(pos), c1);
        replaceAll(this.chromosomes, this.chromosomes.get(pos+1), c2);
    }

    /**
     * Mutate by chromosome or by gen
     */
    public void mutate(){
        StringBuilder out = new StringBuilder();
        List<Chromosome> oldChromosomes = this.chromosomes;
        List<Chromosome> newChromosomes = new ArrayList<>();
        /* Mutation by chromosome */
        if(Window.mutation == Window.Mutations.PER_CHROMOSOME){
            /* Mutation by chromosome */
            for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
                if((int) (Math.random() * 99) < Window.probabilityMutationPerChromosome) {
                    int mutatePosition = (int) (Math.random() * (Window.NUM_GENES - 1));
                    out.append("Chromosome\t").append(i+1).append("\tbefore\tGene\t").append(mutatePosition+1).append("\tmutated:\t").append(oldChromosomes.get(i).toString()).append("\n");
                    Chromosome chromosome = new Chromosome(oldChromosomes.get(i).getGenes());
                    chromosome.setGenes(mutatePosition, getRandomColor(chromosome.getGenes().get(mutatePosition).getColor()));
                    newChromosomes.add(i, chromosome);
                    out.append("Chromosome\t").append(i+1).append("\tafter\tGene\t").append(mutatePosition+1).append("\tmutated:\t").append(chromosome).append("\n");
                    out.append("----------------------------------------------------------------------------------\n");
                } else {
                    newChromosomes.add(i, new Chromosome(oldChromosomes.get(i).getGenes()));
                }
            }
        }else{
            /* Mutation by gene */
            for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
                for(int j = 0; j < Window.NUM_GENES; j++){
                    if(Math.random()*99 < Window.probabilityMutationPerGene) {
                        out.append("Chromosome\t").append(i+1).append("\tbefore\tGene\t").append(j+1).append("\tmutated:\t").append(this.chromosomes.get(i).toString()).append("\n");
                        this.chromosomes.get(i).setGenes(j, getRandomColor(this.chromosomes.get(i).getGenes().get(j).getColor()));
                        out.append("Chromosome\t").append(i+1).append("\tbefore\tGene\t").append(j+1).append("\tmutated:\t").append(this.chromosomes.get(i).toString()).append("\n");
                        out.append("------------------------------------------------------------------------------\n");
                    }
                }
            }
        }
        this.chromosomes.clear();
        this.chromosomes.addAll(newChromosomes);
        if(Window.SHOW_MUTATE)
            System.out.println(out);
    }

    /**
     * Getter to get a random color other than 'color'
     * @param color - Gene associated color
     * @return color
     */
    private Colors getRandomColor(Colors color){
        Colors out = color;
        while(out == color) {
            switch ((int) (Math.random() * 7) + 1) {
                case 1:
                    out = Colors.RED;
                    break;
                case 2:
                    out = Colors.YELLOW;
                    break;
                case 3:
                    out = Colors.BLUE;
                    break;
                case 4:
                    out = Colors.GREEN;
                    break;
                case 5:
                    out = Colors.PURPLE;
                    break;
                case 6:
                    out = Colors.BLACK;
                    break;
                case 7:
                    out = Colors.WHITE;
            }
        }
        return out;
    }

    /**
     * Getter of the chromosome with the highest value
     * @return bestChromosome
     */
    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    /**
     * Setter of the chromosome with the highest value
     * @param bestChromosome - Chromosome with the highest value
     */
    public void setBestChromosome(Chromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

    /**
     * Getter of the total amount of fitness
     * @return totalFitness
     */
    public int getTotalFitness(){
        return this.totalFitness;
    }

    /**
     * Getter of the average fitness
     * @return int
     */
    public int getAverageFitness(){
        return this.totalFitness/Window.NUM_CHROMOSOMES;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Chromosome chromosome : chromosomes) {
            out.append(chromosome.toString()).append("\n");
        }
        if(bestChromosome!=null) {
            out.append("\n\t\tBest Chromosome:\n");
            out.append(bestChromosome.toString()).append("\n");
        }
        return out.toString();
    }
}

