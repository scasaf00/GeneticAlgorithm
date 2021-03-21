package mastermind.basic;

import mastermind.Window;
import mastermind.utils.Colors;

import java.util.*;

public class Population {

    private List<Chromosome> chromosomes = new ArrayList<>();
    private Chromosome bestChromosome;
    private int generation = 0;
    private int totalFitness = 0;

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
        Iterator<Chromosome> it = chromosomes.iterator();
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
            if(this.getBestChromosome() == null || c.getValue() > this.getBestChromosome().getValue())
                this.setBestChromosome(c);
            white = 0;
            black = 0;
        }
        if(Window.SHOW_EVALUATE){
           new ThreadShowEvaluate().run(this);
        }
    }

    public boolean stopCondition(){
        return bestChromosome.getValue() == 14;
    }

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
                    newChromosomes.add(new Chromosome(this.chromosomes.get(j).getGenes()));
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
        for (int i = 0; i < Window.NUM_CHROMOSOMES; i += 2) {
            if ((i + 1) == this.chromosomes.size())
                break;
            int breakPoint = (int) (Math.random()*(Window.NUM_GENES-2)+1);

            out.append("Chromosome before crossover:\t").append(this.chromosomes.get(i).toString()).append(" , ").append(this.chromosomes.get(i + 1).toString()).append("\n");

            for (int j = breakPoint; j < Window.NUM_GENES; j++) {
                Colors color = this.chromosomes.get(i + 1).getGenes().get(j).getColor();
                this.chromosomes.get(i + 1).setGenes(j, this.chromosomes.get(i).getGenes().get(j).getColor());
                this.chromosomes.get(i).setGenes(j, color);
            }

            out.append("Chromosome after crossover:\t\t").append(this.chromosomes.get(i).toString()).append(" , ").append(this.chromosomes.get(i + 1).toString()).append("\n");
        }
        if(Window.SHOW_CROSSOVER)
           new ThreadShowCrossoverMutate().run(out);
    }

    public void mutate(){
        StringBuilder out = new StringBuilder();
        if(Window.mutation == Window.Mutations.PER_CHROMOSOME){
            for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
                if((int) (Math.random() * 99) < Window.probabilityMutationPerChromosome){
                    int mutatePosition = (int) (Math.random() * (Window.NUM_GENES - 1));
                    out.append("Before mutation:\t").append(this.chromosomes.get(i).toString()).append("\n");
                    this.chromosomes.get(i).setGenes(mutatePosition, getRandomColor(this.chromosomes.get(i).getGenes().get(mutatePosition).getColor()));
                    out.append("After mutation:\t\t").append(this.chromosomes.get(i).toString()).append("\n");
                }else{
                    out.append("Chromosome ").append(i + 1).append(" didn't mute").append("\n");
                }
            }
        }else{
            for(int i = 0; i < Window.NUM_CHROMOSOMES; i++){
                for(int j = 0; j < Window.NUM_GENES; j++){
                    if(Math.random()*99 < Window.probabilityMutationPerGene){
                        out.append("Before mutation:\t").append(this.chromosomes.get(i).toString()).append("\n");
                        this.chromosomes.get(i).setGenes(j, getRandomColor(this.chromosomes.get(i).getGenes().get(j).getColor()));
                        out.append("After mutation:\t\t").append(this.chromosomes.get(i).toString()).append("\n");
                    }else{
                        out.append("Gene ").append(j + 1).append(" from chromosome ").append(i + 1).append(" didn't mute").append("\n");
                    }
                }
            }
        }
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

