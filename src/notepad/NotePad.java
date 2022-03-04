/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package notepad;

import java.awt.Desktop;
import static java.awt.SystemColor.desktop;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 *
 * @author ahmed
 */
public class NotePad extends Application {

    MenuItem new_i;
    MenuItem open;
    MenuItem save;
    SeparatorMenuItem sep_f;
    MenuItem exit;
    Menu file;
////edit
    MenuItem undo;
    SeparatorMenuItem sep_e1;
    MenuItem cut;
    MenuItem copy;
    MenuItem paste;
    MenuItem delete;
    SeparatorMenuItem sep_e2;
    MenuItem select;
    Menu edit;
////help
    MenuItem about;
    Menu help;

    MenuBar bar;
    BorderPane border;

    TextArea text;

    Scene s;

    Alert a;

    String path;
    DataInputStream dataIn;
    DataOutputStream dataOut;
    @Override
    public void init() {
////file
        new_i = new MenuItem("New");
        new_i.setAccelerator(KeyCombination.keyCombination("Ctrl+n"));
        open = new MenuItem("Open");
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+o"));
        save = new MenuItem("Save");
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+s"));
        sep_f = new SeparatorMenuItem();
        exit = new MenuItem("Exit");
        file = new Menu("File");
        file.getItems().addAll(new_i, open, save, sep_f, exit);
////edit
        undo = new MenuItem("Undo");
        undo.setAccelerator(KeyCombination.keyCombination("Ctrl+u"));
        sep_e1 = new SeparatorMenuItem();
        cut = new MenuItem("Cut");
        cut.setAccelerator(KeyCombination.keyCombination("Ctrl+d"));
        copy = new MenuItem("Copy");
        copy.setAccelerator(KeyCombination.keyCombination("Ctrl+e"));
        paste = new MenuItem("Paste");
        paste.setAccelerator(KeyCombination.keyCombination("Ctrl+p"));
        delete = new MenuItem("Delete");
        sep_e2 = new SeparatorMenuItem();
        select = new MenuItem("Select All");
        edit = new Menu("Edit");
        edit.getItems().addAll(undo, sep_e1, cut, copy, paste, delete, sep_e2, select);
////help
        about = new MenuItem("About Notepad");
        help = new Menu("Help");
        help.getItems().addAll(about);

        bar = new MenuBar(file, edit, help);
        border = new BorderPane();
        border.setTop(bar);

        text = new TextArea();
        border.setCenter(text);

        s = new Scene(border, 500, 500);

        
    }

    @Override
    public void start(Stage primaryStage) {
        a =  new Alert(Alert.AlertType.NONE);
        FileChooser fileChooser = new FileChooser();
        

        new_i.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are you sure ?!");
                a.showAndWait().ifPresent(r -> {
                    if (r == ButtonType.OK) {
                        text.clear();
                    }
                });
            }
        });

        exit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are you sure ?!");
                a.showAndWait().ifPresent(r -> {
                    if (r == ButtonType.OK) {
                        Platform.exit();
                    }
                });
            }
        });

        undo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are you sure ?!");
                a.showAndWait().ifPresent(r -> {
                    if (r == ButtonType.OK) {
                        text.undo();
                    }
                });
            }
        });

        cut.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                text.cut();
            }
        });

        copy.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                text.copy();
            }
        });

        paste.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                text.paste();
            }
        });

        delete.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                text.deleteText(text.getSelection());
            }
        });

        select.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                text.selectAll();
            }
        });

        about.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Ahmed ElSayed ElKhouly\nEmail : ahmed.elsayed.elkhouly99@gmail.com");
                a.show();
            }
        });

        save.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"), new ExtensionFilter("Text", "*.txt*"));
                File file = fileChooser.showSaveDialog(primaryStage);
                
                    if (file != null) {
                    path = file.getPath();
                    try{
                    dataOut = new DataOutputStream(new FileOutputStream(path));
                    dataOut.writeUTF(text.getText());
//                    dataOut.writeUTF(text.getText());
                    dataOut.close();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });

        open.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void  handle(ActionEvent e){
                File file = fileChooser.showOpenDialog(primaryStage);
                    if (file != null) {
                    path = file.getPath();
                    try{
                    dataIn = new DataInputStream(new FileInputStream(path));
                    byte[] data = new byte[(int)file.length()];
                    dataIn.read(data);
                    text.setText(new String(data));
//                    text.setText(dataIn.readUTF());
                    dataIn.close();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });



        primaryStage.setTitle("NotePad");
        primaryStage.setScene(s);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
