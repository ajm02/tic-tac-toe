package tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameHandler extends Application {

    /**
     * Main class. Handles everything that is displayed to the user and user interaction
     * with the game, such as building and displaying the scenes.
     */

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Impossible Tic Tac Toe");
        primaryStage.setResizable(false);
        stage = primaryStage;
        displayScene(buildMenuScene()); // Build and display menu scene upon startup
    }

    /**
     * Build the main menu
     * @return main menu scene
     */
    private Scene buildMenuScene() {
        StackPane stackPane = new StackPane();
        stackPane.setPrefHeight(480);
        stackPane.setPrefWidth(640);

        Scene scene = new Scene(stackPane, 640, 480);
        scene.getStylesheets().add(getClass().getResource("tictactoe.css").toExternalForm());

        ImageView background = new ImageView(new Image(getClass().getResource("background.jpg").toExternalForm()));
        background.setFitHeight(stackPane.getPrefHeight());
        background.setFitWidth(stackPane.getPrefWidth());

        VBox vBox = new VBox();
        Text title = new Text("Impossible Tic Tac Toe");
        title.getStyleClass().add("title");
        Text text = new Text("See if you can beat the AI at at Tic Tac Toe (you can't, it's impossible!)");
        text.getStyleClass().add("text");
        Button start = new Button("Start");
        start.getStyleClass().add("button");
        start.setPrefWidth(100);
        start.setOnAction((event -> startGame())); // Set the start button to start the game

        // Add nodes to a vbox so they can all be displayed
        vBox.getChildren().addAll(title, start, text);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        VBox.setMargin(start, new Insets(0, 0, 100, 0));

        stackPane.getChildren().add(background);
        stackPane.getChildren().add(vBox);

        return scene;
    }

    /**
     * Begin a new game
     */
    public void startGame() {
        Game game = new Game(); // Make a new game
        Scene scene = buildGameScene(game); // Build the game scene

        // If esc key is pressed, then return to the menu scene
        scene.setOnKeyPressed((key) -> {
            if (key.getCode() == KeyCode.ESCAPE) {
               displayScene(buildMenuScene());
            }
        });

        displayScene(scene);

        // If the AI is the starting player, have the make a move
        if (game.getCurrentPlayer() == 1) {
            game.makeAIMove();
        }
    }

    /**
     * Builds the game scene
     * @param game the game taking place in this scene
     * @return game scene
     */
    private Scene buildGameScene(Game game) {
        StackPane stackPane = new StackPane();
        stackPane.setPrefHeight(480);
        stackPane.setPrefWidth(640);

        Scene scene = new Scene(stackPane, 640, 480);
        scene.getStylesheets().add(getClass().getResource("tictactoe.css").toExternalForm());

        ImageView background = new ImageView(new Image(getClass().getResource("background.jpg").toExternalForm()));
        background.setFitHeight(stackPane.getPrefHeight());
        background.setFitWidth(stackPane.getPrefWidth());

        VBox vBox = new VBox();

        Text text = new Text();
        text.getStyleClass().add("title");
        text.textProperty().bindBidirectional(game.getDisplayText()); // Bind the text to be displayed at the top to text display of the game

        // Create a gridpane to hold 9 squares
        GridPane grid = new GridPane();
        int column = -1;
        for (int i = 0; i < 9; i++) {

            if (i % 3 == 0) {
                column++;
            }

            Square square = new Square(100, 100, column, i % 3);
            game.addSquare(square); // Add this square to the squares in this game

            // If square is clicked on, then play a move
            square.setOnMouseClicked((event) -> {
                game.movePlayed(square.getColumn(), square.getRow());
            });

            grid.add(square, column, i % 3); // Add this square to the gridpane
        }

        Text goBack = new Text("Press esc to return to the main menu");
        goBack.getStyleClass().add("text");

        grid.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(text, grid, goBack);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);

        stackPane.getChildren().add(background);
        stackPane.getChildren().add(vBox);

        return scene;
    }

    /**
     * Displays the given scene
     * @param scene the scene to be displayed
     */
    public void displayScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
