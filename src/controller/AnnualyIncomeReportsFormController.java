package controller;

import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-08-22
 **/
public class AnnualyIncomeReportsFormController {
    public LineChart<String, Number> lneChartIncomeReports;

    public void btnShowResultsOnAction(ActionEvent actionEvent) {
        lneChartIncomeReports.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number> ();
        series.getData().add(new XYChart.Data<String, Number>("2014",1000000));
        series.getData().add(new XYChart.Data<String, Number>("2015",4000000));
        series.getData().add(new XYChart.Data<String, Number>("2016",8000000));
        series.getData().add(new XYChart.Data<String, Number>("2017",7000000));
        series.getData().add(new XYChart.Data<String, Number>("2018",9000000));
        series.getData().add(new XYChart.Data<String, Number>("2019",2500000));
        series.getData().add(new XYChart.Data<String, Number>("2020",9600000));

        series.setName("Year's Income");
        lneChartIncomeReports.getData().add(series);
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
    }

}
