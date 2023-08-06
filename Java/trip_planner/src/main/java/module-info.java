module project{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens gui to javafx.fxml;
    exports gui;
    exports logic;
    opens logic to javafx.fxml;
    opens entity to javafx.base;
}