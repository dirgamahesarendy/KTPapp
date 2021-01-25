/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Microsoft
 */
public class DatabaseHelper {

    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DB = "sistem_ktp";
    private static final String MYCONN = "jdbc:mysql://localhost/" + DB;
    private static final String SQCONN = "jdbc:sqlite:Sistem_Ktp.sqlite";

    public static Connection getConnection(String driver) throws SQLException {
        Connection conn = null;
        switch (driver) {
            case "SQLITE": {
                try {
                    Class.forName("org.sqlite.JDBC");
                    conn = DriverManager.getConnection(SQCONN);
                    createTable(conn, driver);
                } catch (ClassNotFoundException ex) {
                    System.out.println("Librari tidak ada");
                    Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "MYSQL": {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(MYCONN, USER, PASSWORD);
                    createTable(conn, driver);
                } catch (ClassNotFoundException ex) {
                    System.out.println("Librari tidak ada");
                    Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }

        return conn;
    }

    public static void createTable(Connection conn, String driver) throws SQLException {
        String sqlCreate = "";
        switch (driver) {
            case "MYSQL": {
                sqlCreate = "CREATE TABLE IF NOT EXISTS `sistem_ktp` ("
                        + "  `no_antrian` int(10) NOT NULL,"
                        + "  `nama` varchar(100) DEFAULT NULL,"
                        + "  `alamat` varchar(100) DEFAULT NULL,"
                        + "  PRIMARY KEY (`no_antrian`)"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                        + "CREATE TABLE IF NOT EXISTS `akun` ("
                        + "  `nomor_nik` int(10) NOT NULL,"
                        + "  `no_antrian` int(10) DEFAULT NULL,"
                        + "  PRIMARY KEY (`nomor_nik`),"
                        + "  KEY `no_antrian` (`no_antrian`),"
                        + "  FOREIGN KEY (`no_antrian`) REFERENCES `sistem_ktp` (`no_antrian`) ON UPDATE CASCADE"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                        + "CREATE TABLE IF NOT EXISTS `disdukcapil` ("
                        + "  `no_antrian` int(10) NOT NULL,"
                        + "  `kontak` varchar(100) DEFAULT NULL,"
                        + "  PRIMARY KEY (`no_antrian`),"
                        + "  FOREIGN KEY (`no_antrian`) REFERENCES `sistem_ktp` (`no_antrian`) ON UPDATE CASCADE"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                        + "CREATE TABLE IF NOT EXISTS `pendaftar` ("
                        + "  `no_antrian` int(10) NOT NULL,"
                        + "  `gender` varchar(10) DEFAULT NULL,"
                        + "  `birthdate` date DEFAULT NULL,"
                        + "  PRIMARY KEY (`no_antrian`),"
                        + "  FOREIGN KEY (`no_antrian`) REFERENCES `sistem_ktp` (`no_antrian`) ON UPDATE CASCADE"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
                break;
            }
            case "SQLITE": {
                sqlCreate = "CREATE TABLE IF NOT EXISTS  account_holder ("
                        + "    holder_id INT (10)      PRIMARY KEY,"
                        + "    name      VARCHAR (100),"
                        + "    address   VARCHAR (100) "
                        + ");"
                        + "CREATE TABLE IF NOT EXISTS account ("
                        + "    acc_number INT (10)       PRIMARY KEY,"
                        + "    balance    DOUBLE (16, 2),"
                        + "    holder_id  INT (10)       REFERENCES account_holder (holder_id) ON DELETE RESTRICT"
                        + "                                                                    ON UPDATE CASCADE"
                        + ");"
                        + "CREATE TABLE IF NOT EXISTS corporate_holder ("
                        + "    holder_id INT (10)      PRIMARY KEY"
                        + "                            REFERENCES account_holder (holder_id) ON DELETE RESTRICT"
                        + "                                                                  ON UPDATE CASCADE,"
                        + "    contact   VARCHAR (100) "
                        + ");"
                        + "CREATE TABLE IF NOT EXISTS  individual_holder ("
                        + "    holder_id INT (10)     PRIMARY KEY"
                        + "                           REFERENCES account_holder (holder_id) ON DELETE RESTRICT"
                        + "                                                                 ON UPDATE CASCADE,"
                        + "    gender    VARCHAR (10),"
                        + "    birthdate DATE"
                        + ");";
                break;
            }
        }
        String sqls[] = sqlCreate.split(";");
        for (String sql : sqls) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
        }
    }
}
