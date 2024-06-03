package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class LoadPopupController {
	@FXML
	private ListView<String> listView;

	@FXML
	private Button closeButton;

	@FXML
	public void initialize() {
		List<String> data = DataBaseLoader.loadDataFromDatabase();
		listView.getItems().addAll(data);
	}

	@FXML
	protected void handleCloseButtonAction() {
		DataBaseLoader.data.clear();
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	protected void handleDeleteButtonAction() {
		String selectedItem = listView.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			// Извлечь ID из выбранного элемента
			int id = extractIdFromSelectedItem(selectedItem);

			boolean success = DataBaseLoader.deleteRecordFromDatabase(id);
			if (success) {
				listView.getItems().remove(selectedItem);

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Record Deleted");
				alert.setHeaderText("Record deleted successfully");
				alert.setContentText("Record with ID " + id + " was deleted.");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Deletion Failed");
				alert.setHeaderText("Failed to delete record");
				alert.setContentText("An error occurred while trying to delete the record.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No Selection");
			alert.setHeaderText("No item selected");
			alert.setContentText("Please select an item from the list.");
			alert.showAndWait();
		}
	}

	private int extractIdFromSelectedItem(String selectedItem) {
		// Предполагаем, что ID является первым элементом в строке, после "id "
		String[] parts = selectedItem.split(" ");
		try {
			return Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			e.getMessage();
			return -1; // или выбросить исключение, если ID не может быть извлечен
		}
	}
}
