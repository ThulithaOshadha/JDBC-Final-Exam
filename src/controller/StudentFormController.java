package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Student;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void initialize() throws SQLException {
        btnDelete.setDisable(true);
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("student_name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("nic"));

        loadTable();

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((Student) newValue);
            }
        });
    }

    private void setData(Student student) {
        btnSave.setText("Update");
        btnDelete.setDisable(false);
        txtStudentId.setText(student.getStudent_id());
        txtStudentName.setText(student.getStudent_name());
        txtEmial.setText(student.getEmail());
        txtContact.setText(student.getContact());
        txtAddress.setText(student.getAddress());
        txtNic.setText(student.getNic());
    }

    private void loadTable() throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute("SELECT * FROM student");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

        while (true) {
            if (!resultSet.next()) break;

            studentObservableList.add(
                    new Student(
                            resultSet.getString("student_id"),
                            resultSet.getString("student_name"),
                            resultSet.getString("email"),
                            resultSet.getString("contact"),
                            resultSet.getString("address"),
                            resultSet.getString("nic")

                    )
            );
            tblStudent.setItems(studentObservableList);
        }

    }

    public void saveOnAction(ActionEvent actionEvent) {
        Student s = new Student(
                txtStudentId.getText(),
                txtStudentName.getText(),
                txtEmial.getText(),
                txtContact.getText(),
                txtAddress.getText(),
                txtNic.getText()
        );

        try {
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO student VALUES(?,?,?,?,?,?)", s.getStudent_id(), s.getStudent_name(),
                        s.getEmail(),s.getContact(),s.getAddress(),s.getNic())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadTable();
                    txtClear();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }

            }else {
                if (CrudUtil.execute("UPDATE student SET student_name=?, email=?, contact=?," +
                                "address=? ,nic=? WHERE student_id=?",s.getStudent_name(),
                        s.getEmail(),s.getContact(),s.getAddress(),s.getNic(),s.getStudent_id())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadTable();
                    btnSave.setText("Update");
                    txtClear();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    private void txtClear() {
        txtStudentId.clear();
        txtAddress.clear();
        txtEmial.clear();
        txtContact.clear();
        txtNic.clear();
        txtStudentName.clear();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            CrudUtil.execute("DELETE FROM student WHERE student_id=?",txtStudentId.getText());
            new Alert(Alert.AlertType.CONFIRMATION,"DELETED !").show();
            loadTable();
            btnSave.setText("Save");
            txtClear();
        }catch (SQLException | ClassNotFoundException a){
            a.printStackTrace();
        }
    }
}
