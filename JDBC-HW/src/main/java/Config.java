public class Config {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_demo"; // URL of database to be connected
    private static final String USER = "JessieShao"; // default: "root"
    private static final String PASSWORD = "shaojiarui";

    public Config() {
    }

    public static String getUrl() {
        return URL;
    }

    public static String getUser() {
        return USER;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
