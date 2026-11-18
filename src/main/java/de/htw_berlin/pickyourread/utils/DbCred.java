package de.htw_berlin.pickyourread.utils;

public interface DbCred {
    String driverClass = "oracle.jdbc.driver.OracleDriver";
    String url = System.getenv("db_url");
    String user = System.getenv("db_user");
    String password = System.getenv("db_password");
    String schema = System.getenv("db_schema");
}