package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.*;

public class Population {

    private List<Chromosome> chromosomes = new LinkedList<>();
    private Chromosome bestChromosome;
    private int generation = 0;
    public int totalFitness = 0;

    public Population(){
    }

    public void generate(){
        for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
            chromosomes.add(new Chromosome());
        }
        if(Window.SHOW_GENERATE){
            new ThreadShowGenerate().run(this);
        }
    }

    public void evaluate(List<Gene> genesCode){
        Iterator<Chromosome> it = this.chromosomes.iterator();
        int white = 0, black = 0;
        while (it.hasNext()){
            Chromosome c = it.next();
            List<Gene> genesChromosome = c.getGenes();

            for(int i = 0; i < Window.NUM_GENES; i++){
                if (genesChromosome.get(i).getColor() == genesCode.get(i).getColor()){
                    black++;
                    genesCode.get(i).eveluated = true;
                    genesChromosome.get(i).eveluated = true;
                }
            }
            for(int i = 0; i < Window.NUM_GENES; i++) {
                for (int j = 0; j < Window.NUM_GENES; j++) {
                    if (genesCode.get(i).getColor() == genesChromosome.get(j).getColor() && !genesCode.get(i).eveluated && !genesChromosome.get(j).eveluated) {
                        white++;
                        genesCode.get(i).eveluated = true;
                        genesChromosome.get(j).eveluated = true;
                    }
                }
                genesCode.get(i).eveluated = true;
            }
            c.setResponse(white, black);
            c.setValue();
            totalFitness += c.getValue();
            if(this.getBestChromosome() == null || c.getValue() > this.getBestChromosome().getValue()){
                Chromosome chromosome = new Chromosome(c.getGenes());
                chromosome.setResponse(c.getResponse().getWhite(), c.getResponse().getBlack());
                chromosome.setValue();
                this.setBestChromosome(chromosome);
            }
            white = 0;
            black = 0;
        }
        if(Window.SHOW_EVALUATE){
           new ThreadShowEvaluate().run(this);
        }
    }

    public boolean stopCondition(){
        List<Gene> win = new LinkedList<>();
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

    public void selection(){
        generation++;
        List<Chromosome> newChromosomes = new LinkedList<>();
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
            new ThreadShowSelect().run(this);
        }
    }

    public void crossover(){
        StringBuilder out = new StringBuilder();
        List<Chromosome> oldChromosomes = this.chromosomes;
        List<Chromosome> newChromosomes = new LinkedList<>();
        for (int i = 0; i < Window.NUM_CHROMOSOMES; i += 2) {
            if ((i + 1) == oldChromosomes.size())
                break;
            int breakPoint = (int) (Math.random()*(Window.NUM_GENES-1)+1);
            out.append("Chromosomes ").append(i).append(" y ").append(i+1).append(" before crossover:\t").append(oldChromosomes.get(i).toString()).append(" , ").append(oldChromosomes.get(i + 1).toString()).append("\n");

            Chromosome chromosome1 = new Chromosome(oldChromosomes.get(i+1).getGenes());
            Chromosome chromosome2 = new Chromosome(oldChromosomes.get(i).getGenes());
            for (int j = breakPoint; j < Window.NUM_GENES; j++) {
                Colors color = oldChromosomes.get(i + 1).getGenes().get(j).getColor();
                chromosome1.setGenes(j, oldChromosomes.get(i).getGenes().get(j).getColor());
                chromosome2.setGenes(j, color);
            }
            newChromosomes.add(i, chromosome1);
            newChromosomes.add(i+1, chromosome2);

            out.append("Chromosomes ").append(i).append(" y ").append(i+1).append(" after crossover:\t").append(chromosome1).append(" , ").append(chromosome2).append("\n");
            out.append("-------------------------------------------------------------------\n");
        }
        this.chromosomes.clear();
        this.chromosomes.addAll(newChromosomes);
        if(Window.SHOW_CROSSOVER)
           new ThreadShowCrossoverMutate().run(out);
    }

    public void mutate(){
        StringBuilder out = new StringBuilder();
        List<Chromosome> oldChromosomes = this.chromosomes;
        List<Chromosome> newChromosomes = new LinkedList<>();
        if(Window.mutation == Window.Mutations.PER_CHROMOSOME){
            for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
                if((int) (Math.random() * 99) < Window.probabilityMutationPerChromosome) {
                    int mutatePosition = (int) (Math.random() * (Window.NUM_GENES - 1));
                    out.append("Chromosome ").append(i+1).append(" before Gene ").append(mutatePosition+1).append(" mutated:\t").append(oldChromosomes.get(i).toString()).append("\n");
                    Chromosome chromosome = new Chromosome(oldChromosomes.get(i).getGenes());
                    chromosome.setGenes(mutatePosition, getRandomColor(chromosome.getGenes().get(mutatePosition).getColor()));
                    newChromosomes.add(i, chromosome);
                    out.append("Chromosome ").append(i+1).append(" after Gene ").append(mutatePosition+1).append(" mutated:\t").append(chromosome).append("\n");
                    out.append("------------------------------------------------------\n");
                } else {
                    newChromosomes.add(i, new Chromosome(oldChromosomes.get(i).getGenes()));
                }
            }
            System.out.println(oldChromosomes);
            System.out.println(newChromosomes);
        }else{
            for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
                for(int j = 0; j < Window.NUM_GENES; j++){
                    if(Math.random()*99 < Window.probabilityMutationPerGene) {
                        out.append("Chromosome ").append(i+1).append(" before Gene ").append(j+1).append(" mutated:\t").append(this.chromosomes.get(i).toString()).append("\n");
                        this.chromosomes.get(i).setGenes(j, getRandomColor(this.chromosomes.get(i).getGenes().get(j).getColor()));
                        out.append("Chromosome ").append(i+1).append(" before Gene ").append(j+1).append(" mutated:\t").append(this.chromosomes.get(i).toString()).append("\n");
                        out.append("------------------------------------------------------\n");
                    }
                }
            }
        }
        this.chromosomes.clear();
        this.chromosomes.addAll(newChromosomes);
        if(Window.SHOW_MUTATE)
           new ThreadShowCrossoverMutate().run(out);
    }

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

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public void setBestChromosome(Chromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

    public int getTotalFitness(){
        return this.totalFitness;
    }

    public int getAverageFitness(){
        return this.totalFitness/Window.NUM_CHROMOSOMES;
    }

    @Override
    public String toString() {
        return new ThreadToString().call();
    }

    private class ThreadToString extends Thread{
        public ThreadToString(){ super(); }
        public String call(){
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

    private static class ThreadShowGenerate extends Thread{
        public ThreadShowGenerate(){ super ();}
        public void run(Object o){
            System.out.println("\nInitial Population:");
            System.out.println(o.toString());
        }
    }

    private static class ThreadShowEvaluate extends Thread{
        public ThreadShowEvaluate(){ super ();}
        public void run(Object o){
            System.out.println("\nEvaluation with color code:");
            System.out.println(" Chromosome\t\t   Reply\t\t Value");
            System.out.println(o.toString());
        }
    }

    private class ThreadShowSelect extends Thread{
        public ThreadShowSelect(){ super ();}
        public void run(Object o){
            System.out.println("Generation: "+generation);
            System.out.println(" Seleccion");
            System.out.println(o.toString());
        }
    }

    private static class ThreadShowCrossoverMutate extends Thread{
        public ThreadShowCrossoverMutate(){ super ();}
        public void run(Object o){
            System.out.println(o.toString());
        }
    }

}

