package com.example.studentproblembook;

import com.example.studentproblembook.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;


public class TaskPlannerApp extends Application {

    private TaskPlanner taskPlanner;
    private ListView<Task> taskListView;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        User user = new User("YourUsername", "YourPassword");
        taskPlanner = new TaskPlanner(user);

        primaryStage.setTitle("Task Planner App");

        Button addTaskButton = new Button("Добавить задачу");
        addTaskButton.setOnAction(e -> showAddTaskDialog(primaryStage));

        Button removeTaskButton = new Button("Удалить задачу");
        removeTaskButton.setOnAction(e -> showRemoveTaskDialog());

        Button completeTaskButton = new Button("Отметить задачу как законченную");
        completeTaskButton.setOnAction(e -> showCompleteTaskDialog());

        Button showTasksButton = new Button("Показать список задач");
        showTasksButton.setOnAction(e -> showTaskList());

        // Создаем ListView для отображения задач
        taskListView = new ListView<>();
        taskListView.setItems(FXCollections.observableList(taskPlanner.get_tasks()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(addTaskButton, removeTaskButton, completeTaskButton, showTasksButton, taskListView);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        taskListView.refresh();
    }

    private void showAddTaskDialog(Stage primaryStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Add Task");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label descriptionLabel = new Label("Description:");
        TextField descriptionTextField = new TextField();

        Label deadlineLabel = new Label("Deadline:");
        DatePicker deadlineDatePicker = new DatePicker();

        Label priorityLabel = new Label("Priority:");
        ComboBox<Priority> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll(Priority.LOW, Priority.MEDIUM, Priority.HIGH);
        priorityComboBox.getSelectionModel().selectFirst();

        Button addButton = new Button("Add Task");
        addButton.setOnAction(e -> {
            String description = descriptionTextField.getText();
            LocalDate deadlineDate = deadlineDatePicker.getValue();
            Priority priority = priorityComboBox.getValue();

            if (!description.isEmpty() && deadlineDate != null && priority != null) {
                System.out.println("Description: " + description);
                System.out.println("Deadline: " + deadlineDate);
                System.out.println("Priority: " + priority);

                Task newTask = new Task(description, java.sql.Date.valueOf(deadlineDate), priority);


                // Обновляем задачи в ListView
                taskListView.getItems().addAll(newTask);

                dialogStage.close();
            } else {
                System.out.println("Ошибка: Некорректные данные для создания задачи");
            }
        });

        vbox.getChildren().addAll(descriptionLabel, descriptionTextField, deadlineLabel,
                deadlineDatePicker, priorityLabel, priorityComboBox, addButton);

        Scene dialogScene = new Scene(vbox, 300, 250);
        dialogStage.setScene(dialogScene);

        // Используем show() вместо showAndWait()
        dialogStage.showAndWait();
    }

    private Task getSelectedTask() {
        return taskListView.getSelectionModel().getSelectedItem();
    }


    private void showRemoveTaskDialog() {
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            taskListView.getItems().remove(selectedTask);
        } else {
            // Выведите сообщение об ошибке, если задача не выбрана
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Выберите задачу для удаления.");
            alert.showAndWait();
        }
    }



    private void showCompleteTaskDialog() {
        // Реализуйте диалог для отметки задачи как завершенной
    }
    private void showTaskList() {
        System.out.println("Список задач:");
        for (Task task : taskPlanner.get_tasks()) {
            System.out.println(task);
        }
        System.out.println("-----");
    }

}
