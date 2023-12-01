import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Chart  extends JFrame {
    public Chart(String WindowTitle , List<String> comand1, List<Double> avgHeight) {
        super(WindowTitle);
        JFreeChart Chart = ChartFactory.createBarChart(
                "Средний возраст команд", // Название графика
                "Команда", // Название Оси Х
                "Средний возраст, лет", // Название Оси У
                createDataset(comand1, avgHeight), // Создание набора данных
                PlotOrientation.VERTICAL, // Отоброжение графика по вертикали
                false, true, false);
        CategoryPlot plot = (CategoryPlot) Chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE); // Изменил цвет столбцов


        ChartPanel chartPanel = new ChartPanel(Chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.black); // Изменил фон
        //chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        setContentPane(chartPanel);
    }

    //Метод, который создает данные для графика
    private CategoryDataset createDataset(List<String> comand, List<Double> avg) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(avg.get(0),"средний возраст", comand.get(0));
        dataset.addValue(avg.get(1),"средний возраст", comand.get(1));
        dataset.addValue(avg.get(2),"средний возраст", comand.get(2));
        dataset.addValue(avg.get(3),"средний возраст", comand.get(3));
        dataset.addValue(avg.get(4),"средний возраст", comand.get(4));
        dataset.addValue(avg.get(5),"средний возраст", comand.get(5));
        dataset.addValue(avg.get(6),"средний возраст", comand.get(6));
        dataset.addValue(avg.get(7),"средний возраст", comand.get(7));
        dataset.addValue(avg.get(8),"средний возраст", comand.get(8));
        dataset.addValue(avg.get(9),"средний возраст", comand.get(9));
        return dataset;
    }

}
