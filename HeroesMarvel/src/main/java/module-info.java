module org.om2324.heroesmarvel {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens org.om2324.heroesmarvel to javafx.fxml;
    exports org.om2324.heroesmarvel;
}