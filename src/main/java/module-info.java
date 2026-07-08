module com.example.kidsdaycareactivitycenter {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.microsoft.sqlserver.jdbc;

    opens com.example.kidsdaycareactivitycenter to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter;

    opens com.example.kidsdaycareactivitycenter.Login to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.Login;

    opens com.example.kidsdaycareactivitycenter.DashboardAdmin to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.DashboardAdmin;

    opens com.example.kidsdaycareactivitycenter.DashboardCaregiver to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.DashboardCaregiver;

    opens com.example.kidsdaycareactivitycenter.MasterKaryawan to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.MasterKaryawan;

    opens com.example.kidsdaycareactivitycenter.MasterOrangTua to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.MasterOrangTua;

    opens com.example.kidsdaycareactivitycenter.Database to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.Database;

    opens com.example.kidsdaycareactivitycenter.ActivitySchedule to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.ActivitySchedule;

    opens com.example.kidsdaycareactivitycenter.TransaksiDaycare to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.TransaksiDaycare;

    opens com.example.kidsdaycareactivitycenter.MasterAnak to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.MasterAnak;

    opens com.example.kidsdaycareactivitycenter.Model to javafx.base;
    exports com.example.kidsdaycareactivitycenter.Model;

    opens com.example.kidsdaycareactivitycenter.PaketDaycare to javafx.fxml;
    exports com.example.kidsdaycareactivitycenter.PaketDaycare;

}