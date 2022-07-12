package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import models.Student;

public class StudentFormController {
    public JFXTextField txtStudentId;
    public JFXTextField txtEmial;
    public JFXTextField txtStudentName;
    public JFXTextField txtContact;
    public JFXTextField txtNic;
    public JFXTextField txtAddress;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<Student> tblStudent;


    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }
}
