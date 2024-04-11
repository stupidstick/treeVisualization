package com.bebra.treevisualization.visual;

import com.bebra.treevisualization.tree.BinaryTree;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


public class BstPane<K extends Comparable<K>, V> extends Pane {
    private BinaryTree<K, V> tree;
    private double radius = 15;
    private double vGap = 50;

    protected BstPane(){ }

    public BstPane(BinaryTree<K, V> tree){
        this.tree = tree;
        setBackground(new Background(new BackgroundFill(Color.web("#" + "40E0D0"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setStatus(String msg){
        getChildren().add(new Text(20, 20, msg));
    }

     public void displayTree(){
        this.getChildren().clear();
        if(tree.getHead() != null){
            displayTree(tree.getHead(), getPrefWidth() / 2, vGap, getPrefWidth() / 4, Color.MEDIUMPURPLE);
        }
    }

    protected void displayTree(BinaryTree.Node<K, V> head, double x, double y, double hGap, Color color){
        if(head.getLeft() != null){
            getChildren().add(new Line(x - hGap, y + vGap, x, y));
            displayTree(head.getLeft(), x - hGap, y + vGap, hGap / 2,color);
        }

        if (head.getRight() != null){
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
            displayTree(head.getRight(), x + hGap, y + vGap, hGap / 2, color);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(color);
        circle.setStroke(Color.BLACK);

        Label info = new Label(head.toString());

        info.setLayoutX(x);
        info.setLayoutY(y);
        getChildren().addAll(circle, info);
    }

}