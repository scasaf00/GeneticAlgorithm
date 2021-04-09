package mastermind.utils;

import mastermind.Window;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class Graph extends ImageIcon {

    public Graph(Dimension d, List<Integer> fitness, List<Integer> bestChromosomeFitness, List<Integer> averageFitness){
        XYDataset dataset = xyDataset(fitness, bestChromosomeFitness, averageFitness);

        JFreeChart jFreeChart = ChartFactory.createXYLineChart(
                "Fitness in generations", "Generations", "Fitness",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        XYPlot xyPlot = (XYPlot) jFreeChart.getPlot();
        xyPlot.setBackgroundPaint(Color.WHITE);
        xyPlot.setDomainGridlinePaint( Color.BLACK );
        xyPlot.setRangeGridlinePaint( Color.BLACK );

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyPlot.getRenderer();
        xylineandshaperenderer.setBaseShapesVisible(true);

        XYItemLabelGenerator xy = new StandardXYItemLabelGenerator();
        xylineandshaperenderer.setBaseItemLabelGenerator( xy );
        xylineandshaperenderer.setBaseItemLabelsVisible(true);
        xylineandshaperenderer.setBaseLinesVisible(true);
        xylineandshaperenderer.setBaseItemLabelsVisible(true);

        BufferedImage bufferedImage  = jFreeChart.createBufferedImage( d.width, d.height);
        this.setImage(bufferedImage);
        
    }

    private XYDataset xyDataset(List<Integer> fitness, List<Integer> bestChromosomeFitness, List<Integer> averageFitness) {
        XYSeries generations = new XYSeries("Total Fitness");
        XYSeries chromosomes = new XYSeries("Best Chromosome Fitness");
        XYSeries generationsAverage = new XYSeries("Average Fitness");

        addAxis(fitness, generations);
        addAxis(bestChromosomeFitness, chromosomes);
        addAxis(averageFitness, generationsAverage);

        XYSeriesCollection xySeriesCollection =  new XYSeriesCollection();
        xySeriesCollection.addSeries(generations);
        xySeriesCollection.addSeries(chromosomes);
        xySeriesCollection.addSeries(generationsAverage);

        return xySeriesCollection;
    }

    private void addAxis(List<Integer> axis, XYSeries serie) {
        Iterator<Integer> it = axis.iterator();
        int total = axis.size();
        int i = 0;
        while (it.hasNext()){
            int in = it.next();
            if(i%Window.GRAPH_INTERVAL==0) serie.add(i, in);
            if(i+1 == total) serie.add(i, in);
            i++;
        }
    }

    public static class ThreadGrpah extends Thread{
        public ThreadGrpah(){
            super();
        }
        public void run(){
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(mastermind.Window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> {
                new Window().setVisible(true);
            });
        }
    }
}
