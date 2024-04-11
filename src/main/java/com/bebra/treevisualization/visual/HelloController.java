package com.bebra.treevisualization.visual;

import com.bebra.treevisualization.tree.BinaryTree;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private VBox vBox;
    @FXML
    private TextField keyField;
    @FXML
    private TextField valField;


    private BinaryTree<Integer, String> bst = new BinaryTree<>();

    private BstPane<Integer, String> bstPane = new BstPane<>(bst);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bstPane.setPrefWidth(1280);
        bstPane.setPrefHeight(1024);
        vBox.getChildren().add(0, bstPane);
        bstPane.displayTree();
    }

    @FXML
    private void put() {
        Integer key = parseInt(keyField.getText());
        if (key == null)
            return;
        bst.put(key, valField.getText());
        bstPane.displayTree();
        System.out.println(bst.keys());
    }

    @FXML
    private void delete() {
        Integer key = parseInt(keyField.getText());
        if (key == null)
            return;
        bst.delete(key);
        bstPane.displayTree();
    }

    @FXML
    private void change() {
        Integer key = parseInt(keyField.getText());
        if (key == null)
            return;
        bst.set(key, valField.getText());
        bstPane.displayTree();
    }

    @FXML
    private void clear() {
        bst.clear();
        bstPane.displayTree();
    }

    private static Integer parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception exception) {
            return null;
        }
    }
}