package mastermind;

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
import java.util.Iterator;
import java.util.List;

public class Graph extends ImageIcon {

    public Graph(Dimension d, List<Integer> fitness, List<Integer> bestChromosomeFitness){
        XYDataset dataset = xyDataset(fitness, bestChromosomeFitness);

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

    private XYDataset xyDataset(List<Integer> fitness, List<Integer> bestChromosomeFitness) {
        XYSeries generations = new XYSeries("Total Fitness");
        XYSeries chromosomes = new XYSeries("Best Chromosome Fitness");

        Iterator<Integer> it = fitness.iterator();
        int i = 0;
        while(it.hasNext()){
            int in = it.next();
            generations.add(i, in);
            i++;
        }

        Iterator<Integer> it2 = bestChromosomeFitness.iterator();
        int j = 0;
        while (it2.hasNext()){
            int in = it2.next();
            chromosomes.add(j, in);
            j++;
        }

        XYSeriesCollection xySeriesCollection =  new XYSeriesCollection();
        xySeriesCollection.addSeries(generations);
        xySeriesCollection.addSeries(chromosomes);

        return xySeriesCollection;
    }
}
