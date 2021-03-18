/**
 * @Author Sergio Casado Fernandez
 * @Version 1.0
 */
package mastermind;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int NUM_CHROMOSOMES = 3;

    public static final boolean SHOW_GENERATE = false;
    public static final boolean SHOW_EVALUATE = true;
    public static final boolean SHOW_SELECTED = true;
    public static final boolean SHOW_CROSSOVER = true;
    public static final boolean SHOW_MUTATE = true;

    //###############################################
    private static final int NUM_VUELTAS = 2;
    //###############################################

    public static void main(String[] args) {

        Chromosome bestChromosome;
        Chromosome codeToGuess= new Chromosome();
        Population population = new Population();

        System.out.println("\tWELCOME TO MASTERMIND GAME\n");
        System.out.println("A code has been generated randomly:");
        System.out.println(codeToGuess.toString());

        /*
         *  Start of the genetic algorithm
         */

        int i = 0;

        //Randomly generate initial population
        population.generate();
        //Evaluate chromosomes
        population.evaluate(codeToGuess.getGenes());
        //Loop to improve population
        while(/*!population.stopCondition() ||*/ i < NUM_VUELTAS){
            System.out.println("Generation : "+ (i+1));
            //Selection of the best chromosomes
            population.selection();
            //Crossover
            population.crossover();
            //Mutate
            population.mutate();
            //Evaluate chromosomes
            population.evaluate(codeToGuess.getGenes());
            i++;
        }
        bestChromosome = population.getBestChromosome();

        /*
         *  End of the genetic algorithm
         */

    }
}
