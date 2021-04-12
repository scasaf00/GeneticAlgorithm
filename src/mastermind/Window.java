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

public class Window extends JFrame {

    public enum Mutations{
        PER_CHROMOSOME,
        PER_GEN
    }

    public static Properties prop = new Properties();
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

    //Graph
    private static final List<Integer> fitness = new ArrayList<>();
    private static final List<Integer> bestChromosomeFitness = new ArrayList<>();
    private static final List<Integer> averageFitness = new ArrayList<>();
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JPanel jPanel1;

    public static final boolean DEV_OPS = false;

    //Mutation Option
    public static Mutations mutation;

    static {
        if(prop.getProperty("mutate").equals("gene"))
            mutation = Mutations.PER_GEN;
        else
            mutation = Mutations.PER_CHROMOSOME;
    }

    // Probability options
    public static final int probabilityMutationPerChromosome = Integer.parseInt(prop.getProperty("probability_mutation_per_chromosome"));
    public static final int probabilityMutationPerGene = Integer.parseInt(prop.getProperty("probability_mutation_per_gene"));

    // Number of the chromosomes and genes
    public static final int NUM_CHROMOSOMES = Integer.parseInt(prop.getProperty("num_chromosomes"));
    public static int NUM_GENES = Integer.parseInt(prop.getProperty("num_genes"));

    // Visibility options
    private static final boolean SHOW_GRAPH = Boolean.parseBoolean(prop.getProperty("show_graph"));
    public static final int GRAPH_INTERVAL = Integer.parseInt(prop.getProperty("graph_interval"));
    public static final boolean SHOW_GENERATE =  Boolean.parseBoolean(prop.getProperty("show_generate"));
    public static final boolean SHOW_EVALUATE =  Boolean.parseBoolean(prop.getProperty("show_evaluate"));
    public static final boolean SHOW_SELECTED =  Boolean.parseBoolean(prop.getProperty("show_selected"));
    public static final boolean SHOW_CROSSOVER =  Boolean.parseBoolean(prop.getProperty("show_crossover"));
    public static final boolean SHOW_MUTATE =  Boolean.parseBoolean(prop.getProperty("show_mutate"));

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
        while (!population.stopCondition()) {
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
