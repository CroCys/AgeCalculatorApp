package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MainWindowController {
	@FXML
	private MenuItem checkFromDBMenuItem;

	@FXML
	private MenuItem saveToDBMenuItem;

	@FXML
	protected DatePicker datePicker1;

	@FXML
	protected DatePicker datePicker2;

	@FXML
	private Button setTodayButton1;

	@FXML
	private Button setTodayButton2;

	@FXML
	private Button clearButton1;

	@FXML
	private Button clearButton2;

	@FXML
	private Button calculateButton;

	@FXML
	protected TextField resultField;

	@FXML
	public void initialize() {
		setTodayButton1.setOnAction(event -> datePicker1.setValue(LocalDate.now()));
		setTodayButton2.setOnAction(event -> datePicker2.setValue(LocalDate.now()));

		clearButton1.setOnAction(event -> datePicker1.setValue(null));
		clearButton2.setOnAction(event -> datePicker2.setValue(null));

		calculateButton.setOnAction(event -> calculateDifference());

		checkFromDBMenuItem.setOnAction(event -> loadFromDB());
		saveToDBMenuItem.setOnAction(event -> saveToDB());
	}

	private void calculateDifference() {
		LocalDate date1 = datePicker1.getValue();
		LocalDate date2 = datePicker2.getValue();

		if (date1 != null && date2 != null) {
			// Расчет разницы между датами и вывод результатов
			long years = ChronoUnit.YEARS.between(date1, date2);
			long months = ChronoUnit.MONTHS.between(date1, date2);
			long weeks = ChronoUnit.WEEKS.between(date1, date2);
			long days = ChronoUnit.DAYS.between(date1, date2);

			String result = "Difference:\n" +
					" Years: " + years + "\n" +
					" Months: " + months + "\n" +
					" Weeks: " + weeks + "\n" +
					" Days: " + days;

			resultField.setText(result);
		} else {
			resultField.setText("Please select both dates.");
		}
	}

	protected void loadFromDB() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoadPopupView.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage loadPopupStage = new Stage();
			loadPopupStage.initModality(Modality.APPLICATION_MODAL);
			loadPopupStage.centerOnScreen();
			loadPopupStage.setWidth(850);
			loadPopupStage.setHeight(400);
			loadPopupStage.setMinWidth(850);
			loadPopupStage.setMinHeight(400);
			loadPopupStage.setTitle("Database view");
			loadPopupStage.setScene(scene);
			loadPopupStage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void saveToDB() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SavePopupView.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			SavePopupController controller = fxmlLoader.getController();
			controller.setMainController(this);
			Stage savePopupStage = new Stage();
			savePopupStage.initModality(Modality.APPLICATION_MODAL);
			savePopupStage.centerOnScreen();
			savePopupStage.setWidth(400);
			savePopupStage.setHeight(300);
			savePopupStage.setMinWidth(400);
			savePopupStage.setMinHeight(300);
			savePopupStage.setTitle("Save to database");
			savePopupStage.setScene(scene);
			savePopupStage.show();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
