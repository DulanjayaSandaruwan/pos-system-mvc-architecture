package controller;

import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-22
 **/
public class MonthlySystemReportsFormController {
    public LineChart<String, Number> lneChartIncomeReports;


    public void btnShowResultsOnAction(ActionEvent actionEvent) {
        lneChartIncomeReports.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number> ();
        series.getData().add(new XYChart.Data<String, Number>("Jan",60000));
        series.getData().add(new XYChart.Data<String, Number>("Feb",40000));
        series.getData().add(new XYChart.Data<String, Number>("Mar",80000));
        series.getData().add(new XYChart.Data<String, Number>("Apr",70000));
        series.getData().add(new XYChart.Data<String, Number>("May",90000));
        series.getData().add(new XYChart.Data<String, Number>("June",100000));
        series.getData().add(new XYChart.Data<String, Number>("July",65000));

        series.setName("Month's Income");
        lneChartIncomeReports.getData().add(series);
    }

    public void btnBackOnAction(ActionEvent actionEvent) {

    }
}
