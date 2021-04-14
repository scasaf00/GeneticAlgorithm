package mastermind;

import mastermind.basic.Chromosome;
import mastermind.basic.Population;
import mastermind.utils.Colors;
import mastermind.utils.Graph;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Sergio Casado Fernandez
 * @version 1.0v
 */

public class Window extends JFrame {
    /**
     * Enum with the mutate options
     */
    public enum Mutations{
        PER_CHROMOSOME,
        PER_GEN
    }

    /**
     * Properties object
     */
    public static Properties prop = new Properties();
    /**
     * Input to load the properties
     */
    private static InputStream input;

    static {
        try {
            input = new FileInputStream("config.properties");
        } catch (FileNotFoundException ignored) {}
    }

    static {
        try {
            prop.load(input);
        } catch (IOException ignored) {}
    }

    /**
     * GRAPH
     *
     * List of the total amount of fitness in all generations
     */
    private static final List<Integer> fitness = new ArrayList<>();
    /**
     * List of the best chromosome's fitness in all generations
     */
    private static final List<Integer> bestChromosomeFitness = new ArrayList<>();
    /**
     * List of the average fitness in all generations
     */
    private static final List<Integer> averageFitness = new ArrayList<>();
    /**
     * Labels for the graph
     */
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JPanel jPanel1;

    /**
     * Development option choose (Don't go in release)
     */
    public static final boolean DEV_OPS = false;

    /**
     * MUTATION
     *
     * Mutation option loaded from properties
     */
    public static Mutations mutation;

    static {
        if(prop.getProperty("mutate").equals("gene"))
            mutation = Mutations.PER_GEN;
        else
            mutation = Mutations.PER_CHROMOSOME;
    }

    /**
     * PROBABILITY
     *
     * Probability for the mutation option: PER_CHROMOSOME loaded from properties
     */
    public static final int probabilityMutationPerChromosome = Integer.parseInt(prop.getProperty("probability_mutation_per_chromosome"));
    /**
     * Probability for the mutation option: PER_GEN loaded from properties
     */
    public static final int probabilityMutationPerGene = Integer.parseInt(prop.getProperty("probability_mutation_per_gene"));

    /**
     * SIZE
     *
     * Size of chromosomes list loaded from properties
     */
    public static final int NUM_CHROMOSOMES = Integer.parseInt(prop.getProperty("num_chromosomes"));
    /**
     * Size of chromosome's genes loaded from properties
     */
    public static int NUM_GENES = Integer.parseInt(prop.getProperty("num_genes"));

    /**
     * SHOW
     *
     * Graph view option loaded from properties
     */
    private static final boolean SHOW_GRAPH = Boolean.parseBoolean(prop.getProperty("show_graph"));
    /**
     * Graph interval option loaded from properties
     */
    public static final int GRAPH_INTERVAL = Integer.parseInt(prop.getProperty("graph_interval"));
    /**
     * Generate view option loaded from properties
     */
    public static final boolean SHOW_GENERATE =  Boolean.parseBoolean(prop.getProperty("show_generate"));
    /**
     * Evaluate view option loaded from properties
     */
    public static final boolean SHOW_EVALUATE =  Boolean.parseBoolean(prop.getProperty("show_evaluate"));
    /**
     * Select view option loaded from properties
     */
    public static final boolean SHOW_SELECTED =  Boolean.parseBoolean(prop.getProperty("show_selected"));
    /**
     * Crossover view option loaded from properties
     */
    public static final boolean SHOW_CROSSOVER =  Boolean.parseBoolean(prop.getProperty("show_crossover"));
    /**
     * Mutation view option loaded from properties
     */
    public static final boolean SHOW_MUTATE =  Boolean.parseBoolean(prop.getProperty("show_mutate"));

    /**
     * Constructor
     */
    public Window() {
        // Graph view
        initComponents();
        setLocationRelativeTo(null);
        this.setTitle("Relation between Generations and Fitness");

        jLabel1.setIcon(new Graph(jPanel1.getSize(), fitness, bestChromosomeFitness, averageFitness));
        jLabel1.setText("");
    }

    /**
     * Initialize the components for the graph
     */
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
        System.out.println(codeToGuess);

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
        while (!population.stopCondition() && i < (Integer.parseInt(prop.getProperty("num_genes"))*500)) {
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

        if(i == (Integer.parseInt(prop.getProperty("num_genes"))*500))
            System.out.println("Bad luck, code has not been got it right");

        System.out.println("Generation: " + i);

        bestChromosome = population.getBestChromosome();
        System.out.println("Best Chromosome:\n"+bestChromosome);

        System.out.println("Code: ");
        System.out.println(codeToGuess);

        if(SHOW_GRAPH) {
            new Graph.ThreadGraph().start();
        }
    }
}
