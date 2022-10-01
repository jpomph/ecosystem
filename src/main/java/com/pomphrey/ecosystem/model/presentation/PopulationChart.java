package com.pomphrey.ecosystem.model.presentation;

import com.pomphrey.ecosystem.model.worldstate.Summary;
import lombok.Getter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PopulationChart {

    DefaultCategoryDataset defaultChart;

    XYSeriesCollection dataset;

    List<XYSeries> seriesList;

    public PopulationChart(){
        dataset = new XYSeriesCollection();
        seriesList = new ArrayList<>();
    }

    public void addSummaryData(List<Summary> summaryList){
        for (Summary summary: summaryList){
            boolean seriesFound = false;
            for (XYSeries xySeries: seriesList){
                String seriesKey = xySeries.getKey().toString();
                if(seriesKey.equalsIgnoreCase(summary.getSummaryKey().getSpecies())){
                    xySeries.add(summary.getSummaryKey().getYear(), summary.getIndividualCount());
                    seriesFound = true;
                }
            }
            if(!seriesFound){
                XYSeries series = new XYSeries(summary.getSummaryKey().getSpecies());
                series.add(summary.getSummaryKey().getYear(), summary.getIndividualCount());
                seriesList.add(series);
            }
        }
        for (XYSeries xySeries: seriesList){
            dataset.addSeries(xySeries);
        }
    }

    public JFreeChart drawChart(){
        JFreeChart chart = ChartFactory.createScatterPlot("Population size over time", "Year", "Count", dataset);
        //format x-axis
        final NumberAxis xAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
        final DecimalFormat format = new DecimalFormat("####");
        xAxis.setNumberFormatOverride(format);
        xAxis.setTickUnit(new NumberTickUnit(1));

        return chart;
    }

    public JFreeChart generateChart(){
        return ChartFactory.createLineChart("Population Count Over Time", "Year", "Count", defaultChart, PlotOrientation.VERTICAL, true, true, false);
    }

}
