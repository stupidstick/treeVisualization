module com.bebra.treevisualization {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bebra.treevisualization to javafx.fxml;
    exports com.bebra.treevisualization;
    exports com.bebra.treevisualization.visual;
    opens com.bebra.treevisualization.visual to javafx.fxml;
}