package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SavePopupController {
	private MainWindowController mainWindowController;

	@FXML
	private TextField firstNameField;

	@FXML
	private TextField lastNameField;

	public void setMainController(MainWindowController mainController) {
		this.mainWindowController = mainController;
	}

	@FXML
	protected void handleSaveButtonAction() {
		// Получаем данные из текстовых полей
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String firstDate = String.valueOf(mainWindowController.datePicker1.getValue());
		String lastDate = String.valueOf(mainWindowController.datePicker2.getValue());
		String result = mainWindowController.resultField.getText();

		// Здесь можно добавить логику для сохранения данных
		DataBaseLoader.saveDataToDatabase(firstName, lastName, firstDate, lastDate, result);
		System.out.println("Saving: " + firstName + " " + lastName);

		// Закрываем окно после сохранения
		Stage stage = (Stage) firstNameField.getScene().getWindow();
		stage.close();
	}

	@FXML
	protected void handleCloseButtonAction() {
		// Закрываем окно
		Stage stage = (Stage) firstNameField.getScene().getWindow();
		stage.close();
	}
}
