/**
 * @Author Sergio Casado Fernandez
 * @Version 1.0
 */
package mastermind;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {

    public static final int NUM_CHROMOSOMES = 20;

    public static final boolean SHOW_GENERATE = false;
    public static final boolean SHOW_EVALUATE = true;
    public static final boolean SHOW_SELECTED = true;
    public static final boolean SHOW_CROSSOVER = true;
    public static final boolean SHOW_MUTATE = true;

    //Graph
    private static final boolean SHOW_GRAPH = true;

    public static List<Integer> fitness = new ArrayList<>();
    public static List<Integer> bestChromosomeFitness = new ArrayList<>();
    private static javax.swing.JLabel jLabel1;
    private static javax.swing.JPanel jPanel1;

    //###############################################
    private static final int NUM_VUELTAS = 4;
    //###############################################

    public Window() {
        // Graph view
        initComponents();
        setLocationRelativeTo(null);
        this.setTitle("Relation between Generations and Fitness");

        jLabel1.setIcon(new Graph(jPanel1.getSize(), fitness, bestChromosomeFitness));
        jLabel1.setText("");
    }

    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(600 , 500));
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
        Chromosome codeToGuess = new Chromosome();
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
        //Loop to improve population
        while (/*!population.stopCondition() ||*/ i < NUM_VUELTAS) {
            System.out.println("Generation : " + (i + 1));
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
            i++;
        }
        bestChromosome = population.getBestChromosome();
        System.out.println("Best Chromosome: "+bestChromosome);

        /*
         *  End of the genetic algorithm
         */
        if(SHOW_GRAPH) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> new Window().setVisible(true));
        }
    }
}
