package mastermind;

import mastermind.basic.Chromosome;
import mastermind.basic.Population;
import mastermind.utils.Colors;
import mastermind.utils.Graph;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class Window extends JFrame {

    public enum Mutations{
        PER_CHROMOSOME,
        PER_GEN
    }

    //Graph
    private static final List<Integer> fitness = new LinkedList<>();
    private static final List<Integer> bestChromosomeFitness = new LinkedList<>();
    private static final List<Integer> averageFitness = new LinkedList<>();
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JPanel jPanel1;

    public static final boolean DEV_OPS = true;

    //##################################################################
    //Mutation Option
    public static final Mutations mutation = Mutations.PER_CHROMOSOME;

    // Probability options
    public static final int probabilityMutationPerChromosome = 10;
    public static final int probabilityMutationPerGene = 5;

    // Number of the chromosomes and genes
    public static final int NUM_CHROMOSOMES = 4;
    public static int NUM_GENES = 4;

    // Visibility options
    private static final boolean SHOW_GRAPH = true;
    public static final int GRAPH_INTERVAL = 100;
    public static final boolean SHOW_GENERATE = true;
    public static final boolean SHOW_EVALUATE = true;
    public static final boolean SHOW_SELECTED = true;
    public static final boolean SHOW_CROSSOVER = true;
    public static final boolean SHOW_MUTATE = true;
    //##################################################################

    public Window() {
        // Graph view
        initComponents();
        setLocationRelativeTo(null);
        this.setTitle("Relation between Generations and Fitness");

        jLabel1.setIcon(new Graph(jPanel1.getSize(), fitness, bestChromosomeFitness, averageFitness));
        jLabel1.setText("");
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(900 , 800));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }

    public static void main(String[] args) {

        Chromosome bestChromosome;
        Chromosome codeToGuess;

        if(DEV_OPS) {
            codeToGuess = new Chromosome(Colors.RED, Colors.RED, Colors.YELLOW, Colors.RED);
            NUM_GENES = 4;
        }
        else
            codeToGuess = new Chromosome();


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
        // Only for the graph view
        Window.fitness.add(population.getTotalFitness());
        Window.bestChromosomeFitness.add(population.getBestChromosome().getValue());
        Window.averageFitness.add(population.getAverageFitness());
        //Loop to improve population
        while (/*!population.stopCondition()*/ i<100) {
            population.totalFitness = 0;
            //Selection of the best chromosomes
            population.selection();
            //Crossover
            population.crossover();
            //Mutate
            population.mutate();
            //Evaluate chromosomes
            population.evaluate(codeToGuess.getGenes());
            // Only for the graph view
            Window.fitness.add(population.getTotalFitness());
            Window.bestChromosomeFitness.add(population.getBestChromosome().getValue());
            Window.averageFitness.add(population.getAverageFitness());
            i++;
            population.totalFitness = 0;
        }
        /*
         *  End of the genetic algorithm
         */

        System.out.println("Generation: " + i);

        bestChromosome = population.getBestChromosome();
        System.out.println("Best Chromosome:\n"+bestChromosome);

        System.out.println("Code: ");
        System.out.println(codeToGuess.toString());

        if(SHOW_GRAPH) {
            new Graph.ThreadGrpah().start();
        }
    }
}
