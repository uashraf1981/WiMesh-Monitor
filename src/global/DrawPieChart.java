/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package global;

import static gui.WiMeshMainForm.freeMemory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import meshmonitoringunit.RouterInfo;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ICTWimesh
 */
public class DrawPieChart {
    public static void DrawCharts(String Free,String Used,int index){
        DefaultPieDataset DefaultDs = new DefaultPieDataset();
        DefaultDs.setValue("Free ",new Integer(Free));
        DefaultDs.setValue("Used ", new Integer(Used)); 
        JFreeChart piechart_ = ChartFactory.createPieChart3D(RouterInfo.routerList.get(index).IP, DefaultDs, true, true,true);
        //PiePlot3D Pie = (PiePlot3D)piechart.getPlot();
        ChartPanel cp = new ChartPanel(piechart_);
        //JPanel panel1 = new JPanel();
        freeMemory[index].setLayout(new java.awt.BorderLayout());
        freeMemory[index].removeAll();
        freeMemory[index].add(cp,BorderLayout.CENTER);
        freeMemory[index].validate();
        freeMemory[index].setPreferredSize(new Dimension(260, 250));
        freeMemory[index].setBackground(Color.GREEN);
    }
}
