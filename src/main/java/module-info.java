module org.uem.dam.dam2chat_psp_frontend {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens org.uem.dam.dam2chat_psp_frontend to javafx.fxml;
    exports org.uem.dam.dam2chat_psp_frontend;
}