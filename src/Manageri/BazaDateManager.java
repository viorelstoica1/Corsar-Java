package Manageri;

import Backbone.Application;
import Backbone.Scoruri;

import java.sql.*;

public class BazaDateManager {
    private static final String DB_URL = "jdbc:sqlite:src/resources/BazaDate.db";
    private static final String USER = "guest";
    private static final String PASS = "guest123";

    public static void CitireScoruri() {
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Nivel1, Nivel2, Nivel3, Nume1, Nume2, Nume3 FROM Scoruri");
            for(int i=1; i<= 3;i++){
                rs.next();
                //System.out.println(rs.getInt(1) + " " +rs.getInt(2) + " "  + rs.getString(3));
                Scoruri.get().CitireScorBazaDate(0,i-1,rs.getInt(1),rs.getString(4));
                Scoruri.get().CitireScorBazaDate(1,i-1,rs.getInt(2),rs.getString(5));
                Scoruri.get().CitireScorBazaDate(2,i-1,rs.getInt(3),rs.getString(6));
            }
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Application.CloseGame();
        }
    }
    public static void CitireSetariNivele(float[][] setariNivele){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SetariNivele");
            for(int i=1; i<= 3;i++){
                rs.next();
                for(int j=1; j<=10; j++){
                    //System.out.print(rs.getFloat(j) + " ");
                    setariNivele[i-1][j-1] = rs.getFloat(j);
                }
                //System.out.println(" ");
            }
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Application.CloseGame();
        }
    }
    public static void CitireSetariGenerale(float[] setariGenerale){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SetariGenerale");
            for(int i=1; i<= 8;i++){
                //System.out.print(rs.getFloat(i) + " ");
                setariGenerale[i-1] = rs.getFloat(i);
            }
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Application.CloseGame();
        }
    }
    public static void ScriereScor(int nivel, int scor, String nume, int pozitie){
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            nivel++;
            switch(nivel){
                case 1 ->{
                    String sql = "UPDATE Scoruri set Nivel1 = "+scor+" where Pozitie = "+pozitie;
                    stmt.executeUpdate(sql);
                    sql = "UPDATE Scoruri set Nume1 = '"+nume+"' where Pozitie = "+pozitie;
                    stmt.executeUpdate(sql);
                }
                case 2 ->{
                    String sql = "UPDATE Scoruri set Nivel2 = "+scor+" where Pozitie = "+pozitie;
                    stmt.executeUpdate(sql);
                    sql = "UPDATE Scoruri set Nume2 = '"+nume+"' where Pozitie = "+pozitie;
                    stmt.executeUpdate(sql);
                }
                case 3 ->{
                    String sql = "UPDATE Scoruri set Nivel3 = "+scor+" where Pozitie = "+pozitie;
                    stmt.executeUpdate(sql);
                    sql = "UPDATE Scoruri set Nume3 = '"+nume+"' where Pozitie = "+pozitie;
                    stmt.executeUpdate(sql);
                }
            }
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Application.CloseGame();
        }
    }
}
