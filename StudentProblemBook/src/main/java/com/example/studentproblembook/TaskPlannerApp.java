package com.example.studentproblembook;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Optional;

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



        // Создаем ListView для отображения задач
        taskListView = new ListView<>();
        taskListView.setItems(FXCollections.observableList(taskPlanner.get_tasks()));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(addTaskButton, removeTaskButton, completeTaskButton, taskListView);

        Scene scene = new Scene(layout, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        taskListView.refresh();
    }

    private void showAddTaskDialog(Stage primaryStage) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Добавить задачу");

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label descriptionLabel = new Label("Описание:");
        TextField descriptionTextField = new TextField();

        Label deadlineLabel = new Label("Дедлайн:");
        DatePicker deadlineDatePicker = new DatePicker();

        Label priorityLabel = new Label("Приоритет:");
        ComboBox<Priority> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll(Priority.LOW, Priority.MEDIUM, Priority.HIGH);
        priorityComboBox.getSelectionModel().selectFirst();

        Button addButton = new Button("Добавить задачу");
        addButton.setOnAction(e -> {
            try {
                String description = descriptionTextField.getText().trim();
                LocalDate deadlineDate = deadlineDatePicker.getValue();
                Priority priority = priorityComboBox.getValue();

                // Регулярное выражение для проверки, что описание состоит только из букв, цифр и русских букв
                String descriptionRegex = "^[a-zA-Zа-яА-Я0-9]+$";

                if (description.matches(descriptionRegex) && deadlineDate != null && priority != null) {
                    System.out.println("Описание: " + description);
                    System.out.println("Дедлайн: " + deadlineDate);
                    System.out.println("Приоритет: " + priority);

                    Task newTask = new Task(description, java.sql.Date.valueOf(deadlineDate), priority);



                    // Обновляем задачи в ListView
                    taskListView.getItems().addAll(newTask);

                    dialogStage.close();
                } else {
                    // Вывод сообщения об ошибке в случае некорректных данных
                    showErrorDialog("Ошибка: Некорректные данные для создания задачи");
                }
            } catch (Exception ex) {
                // Вывод стека вызова в случае возникновения исключения
                ex.printStackTrace();
                showErrorDialog("Произошла ошибка при создании задачи");
            }
        });


        vbox.getChildren().addAll(descriptionLabel, descriptionTextField, deadlineLabel,
                deadlineDatePicker, priorityLabel, priorityComboBox, addButton);

        Scene dialogScene = new Scene(vbox, 300, 250);
        dialogStage.setScene(dialogScene);

        // Используем show() вместо showAndWait()
        dialogStage.showAndWait();
    }

    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
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
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            // Создаем диалоговое окно для подтверждения завершения задачи
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Complete Task");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите отметить задачу как завершенную?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Изменяем статус задачи на завершенный
                selectedTask.set_status(Status.COMPLETED);

                // Обновляем интерфейс
                taskListView.refresh();
            }
        } else {
            // Выведите сообщение об ошибке, если задача не выбрана
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Выберите задачу для завершения.");
            alert.showAndWait();
        }
    }



}
