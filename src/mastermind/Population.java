package mastermind;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Population {

    private List<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private Chromosome bestChromosome;

    public Population(){
        //TODO
    }

    public void generate(){
        for(int i = 0; i < Main.NUM_CHROMOSOMES; i++){
            chromosomes.add(new Chromosome());
        }
        if(Main.SHOW_GENERATE){
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
            for(int i = 0; i < 4; i++){
                for(int j = 0; j < 4-i; j++){
                    if(genesChromosome.get(i).getColor() == genesCode.get(j).getColor())
                        white++;
                }
                if(genesChromosome.get(i).getColor() == genesCode.get(i).getColor())
                    black++;
            }
            c.setResponse(white, black);
            white = 0;
            black = 0;
        }
        if(Main.SHOW_EVALUATE){
            System.out.println("\nEvaluation with color code:");
            System.out.println(this.toString());
        }
    }

    public boolean stopCondition(){
        //TODO
        return true;
    }

    public void selection(){
        //TODO
    }

    public void crossover(){
        //TODO
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

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        Iterator<Chromosome> it = chromosomes.iterator();
        while (it.hasNext()){
            out.append(it.next().toString()).append("\n");
        }
        return out.toString();
    }
}
