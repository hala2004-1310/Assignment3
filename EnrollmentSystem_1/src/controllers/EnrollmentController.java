package controllers;

import dao.EnrollmentDAO;
import models.Enrollment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.time.LocalDate;   
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class EnrollmentController {

   
    @FXML TextField txtStudentId;
    @FXML TextField txtCourseId;
    @FXML DatePicker datePicker; 

    @FXML TableView<Enrollment>            tableView;
    @FXML TableColumn<Enrollment, Integer> colId;
    @FXML TableColumn<Enrollment, String>  colStudentId;
    @FXML TableColumn<Enrollment, String>  colCourseId;
    @FXML TableColumn<Enrollment, String>  colDate;

    @FXML Label labelMsg;

    
    EnrollmentDAO dao = new EnrollmentDAO();

    
    int selectedId = -1;

  
    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("enrollId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("enrollDate"));

        
        showData();

      
        tableView.setOnMouseClicked(event -> {
            Enrollment selected = tableView.getSelectionModel().getSelectedItem();

            if (selected != null) {
                selectedId = selected.getEnrollId();
                txtStudentId.setText(selected.getStudentId());
                txtCourseId.setText(selected.getCourseId());

                
                datePicker.setValue(LocalDate.parse(selected.getEnrollDate()));
            }
        });
    }

    
    public String getDate() {
        
        if (datePicker.getValue() == null) {
            return ""; 
        }

     
        return datePicker.getValue().toString();
    }

    
    public void showData() {

      
        ArrayList<Enrollment> list = dao.getAllEnrollments();

        
        ObservableList<Enrollment> obList = FXCollections.observableArrayList(list);

        
        tableView.setItems(obList);
    }

    
    @FXML
    public void clickAdd() {

        String sId  = txtStudentId.getText().trim();
        String cId  = txtCourseId.getText().trim();
        String date = getDate(); 

        
        if (sId.isEmpty() || cId.isEmpty() || date.isEmpty()) {
            showMsg("Please fill all fields and pick a date!", "red");
            return;
        }

        
        boolean ok = dao.addEnrollment(sId, cId, date);

        if (ok) {
            showMsg("Enrollment added successfully!", "green");
            clearFields();
            showData(); // Refresh the table
        } else {
            showMsg("Duplicate! This student is already enrolled in this course.", "red");
        }
    }

  
    @FXML
    public void clickUpdate() {

       
        if (selectedId == -1) {
            showMsg("Please select a row from the table first!", "red");
            return;
        }

        String sId  = txtStudentId.getText().trim();
        String cId  = txtCourseId.getText().trim();
        String date = getDate();

        if (sId.isEmpty() || cId.isEmpty() || date.isEmpty()) {
            showMsg("Please fill all fields and pick a date!", "red");
            return;
        }

        boolean ok = dao.updateEnrollment(selectedId, sId, cId, date);

        if (ok) {
            showMsg("Enrollment updated successfully!", "green");
            clearFields();
            showData();
            selectedId = -1;
        } else {
            showMsg("Update failed. Please try again.", "red");
        }
    }

    
    @FXML
    public void clickDelete() {

        if (selectedId == -1) {
            showMsg("Please select a row from the table first!", "red");
            return;
        }

        boolean ok = dao.deleteEnrollment(selectedId);

        if (ok) {
            showMsg("Enrollment deleted successfully!", "green");
            clearFields();
            showData();
            selectedId = -1;
        } else {
            showMsg("Delete failed. Please try again.", "red");
        }
    }

    
    @FXML
    public void clickClear() {
        clearFields();
        selectedId = -1;
        showMsg("", "black");
    }

    
    public void clearFields() {
        txtStudentId.setText("");
        txtCourseId.setText("");
        datePicker.setValue(null);  
    }

    
    public void showMsg(String msg, String color) {
        labelMsg.setText(msg);
        labelMsg.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 13px;");
    }
}