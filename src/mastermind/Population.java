package mastermind;

import java.util.*;

public class Population {

    private List<Chromosome> chromosomes = new ArrayList<>();
    private Chromosome bestChromosome;

    public Population(){
    }

    public void generate(){
        for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
            chromosomes.add(new Chromosome());
        }
        if(Window.SHOW_GENERATE){
            System.out.println("\nInitial Population:");
            System.out.println(this.toString());
        }
    }

    public void evaluate(List<Gene> genesCode){
        Iterator<Chromosome> it = chromosomes.iterator();
        int white = 0, black = 0;
        while (it.hasNext()){
            Chromosome c = it.next();
            List<Gene> genesChromosome = c.getGenes();
            for(int i = 0; i < 4; i++) {
                for (int j = 0; j < 4 - i; j++) {
                    if (genesCode.get(i).getColor() == genesChromosome.get(j).getColor()) {
                        white++;
                        break;
                    }
                }
                if (genesCode.get(i).getColor() == genesChromosome.get(i).getColor()){
                    black++;
                    break;
                }
            }
            c.setResponse(white, black);
            c.setValue();
            if(this.getBestChromosome() == null || c.getValue() > this.getBestChromosome().getValue())
                this.setBestChromosome(c);
            white = 0;
            black = 0;
        }
        if(Window.SHOW_EVALUATE){
            System.out.println("\nEvaluation with color code:");
            System.out.println(" Chromosome\t\t   Reply\t\t Value");
            System.out.println(this.toString());
        }
    }

    public boolean stopCondition(){
        for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
            if(chromosomes.get(i).getValue() == 14) return true;
        }
        return false;
    }

    public void selection(){
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
                    newChromosomes.add(new Chromosome(this.chromosomes.get(j).getGenes()));
                    break;
                }
            }
        }
        this.chromosomes = newChromosomes;
        if(Window.SHOW_SELECTED) {
            System.out.println(" Seleccion");
            System.out.println(this.toString());
        }
    }

    public void crossover(){
        for(int i = 0; i < Window.NUM_CHROMOSOMES; i += 2){
            if((i+1) == Window.NUM_CHROMOSOMES) break;
            for(int j = 2; j < 4; j++){
                Colors color = this.chromosomes.get(i+1).getGenes().get(j).getColor();
                this.chromosomes.get(i+1).setGenes(j, color);
            }
        }
        if(Window.SHOW_CROSSOVER) {
            System.out.println("Crossover:");
            System.out.println(this.toString());
        }
    }

    public void mutate(){
        //TODO
    }

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public void setBestChromosome(Chromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

    public int getTotalFitness(){
        Iterator<Chromosome> it = chromosomes.iterator();
        int out = 0;
        while(it.hasNext()){
            out += it.next().getValue();
        }

        return out;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Chromosome chromosome : chromosomes) {
            out.append(chromosome.toString()).append("\n");
        }
        out.append("\n\t\tBest Chromosome:\n");
        out.append(bestChromosome.toString()).append("\n");
        return out.toString();
    }
}
