import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SQL {
    public static Statement statement;
    //public static String[] nameComands = new String[] {"BAL", "CWS", "ANA", "BOS", "CLE", "OAK", "NYY", "DET", "SEA", "TB"};
    public static List<Integer> height;
    public static void connection(Connection connection)
            throws ClassNotFoundException, SQLException{
        statement = connection.createStatement();
    }

    //Метод который создает таблицу или ее изменяет, а именно ее структуру, название столбцов, тип переменных

    public static void CreateTable()
            throws ClassNotFoundException, SQLException {
        String table = "CREATE TABLE if not exists Indicators (" +
                "'Name' TEXT," +
                "'Team' TEXT," +
                "'Position' TEXT, " +
                "'Height' INT, " +
                "'Weight' INT, " +
                "'Age' REAL) ";
        statement.execute(table);
        System.out.println("Таблица создана или уже существует.");
    }

    //Метод, который заполняет таблицу Indicators
    public static void WriteTable(ArrayList<SportComands> arr, Connection connection) throws SQLException {
        for (SportComands sc : arr){
            String insert = "INSERT INTO Indicators VALUES (?,?,?,?,?,?)";
            PreparedStatement pr = connection.prepareStatement(insert);
            pr.setObject(1, sc.getName());
            pr.setObject(2, sc.getTeam());
            pr.setObject(3, sc.getPosition());
            pr.setObject(4, sc.getHeight());
            pr.setObject(5, sc.getWeight());
            pr.setObject(6, sc.getAge());
            pr.execute();
        }
        System.out.println("Таблица заполнена.");
    }

    //Задание 3
    //Метод, выполянет запрос который выводит одну строчку с командой у которой самая высокий средний возраст
    public static ResultSet mostOlderPlayer(Connection conn) throws java.sql.SQLException {
        List<ResultSet> arr = new ArrayList<>();
        String query = "SELECT team, AVG(Height) AS Средний_рост, AVG(Weight) AS Средний_вес, AVG(Age) AS Средний_возраст FROM 'Indicators'   GROUP BY team  ORDER BY Средний_возраст DESC LIMIT 1";
        PreparedStatement pr = conn.prepareStatement(query);
        ResultSet rs  = pr.executeQuery();
        return  rs;
    }
    //Задание 2
    //Метод выполняет запрос, который выводит название команды с самым высоким средним ростом. Нужен для задания 2
    public static ResultSet NamesComandMostAVGHeight(Connection connection) throws java.sql.SQLException{
        String query = "SELECT team, AVG(Height) AS Средний_рост FROM 'Indicators'   GROUP BY team  ORDER BY Средний_рост DESC LIMIT 1";
        PreparedStatement pr = connection.prepareStatement(query);
        ResultSet rs = pr.executeQuery();
        return rs;
    }
    //Задание 2
    /*Метод выполянет запрос, который выводит определенное число самых высоких игроков и их рост,
    команды, которая была найдена в предыдущем методе */
    public static void mostHighsPlayers(Connection connection, int countPlayer) throws java.sql.SQLException{
        List<String> names = new ArrayList<>();
        String query = "SELECT * FROM 'Indicators' WHERE team = ( SELECT team FROM 'Indicators' GROUP BY team ORDER BY AVG(Height) DESC LIMIT 1 ) ORDER BY Height DESC LIMIT ?";
        PreparedStatement pr = connection.prepareStatement(query);
        pr.setObject(1, countPlayer);
        ResultSet rs = pr.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString(1) + " - " + rs.getInt(4));
        }
    }
    //Задание 1
    /*Метод выполянет запрос, который выводит имена всех команд и их посчитанный возраст
    Из этого метода извлекается только столбец со средним возрастом, чтобы потом поместить его в Ось Y */
    public static List<Double> AvgHeight(Connection conn) throws java.sql.SQLException {
        List<Double> arr = new ArrayList<>();
        String query = "SELECT team, AVG(Age) AS Средний_возраст FROM 'Indicators'   GROUP BY team  ORDER BY Средний_возраст DESC LIMIT 10";
        PreparedStatement pr = conn.prepareStatement(query);
        ResultSet rs  = pr.executeQuery();
        while(rs.next()){
            arr.add(rs.getDouble(2));
        }
        return arr;
    }
    //Задание 1
    /*Метод точно такой же как и AvgHeight, только из этого метода извлекается столбец с
    именами команд, для того чтобы в будущем разместить их по Оси Х */
    public static List<String> ComandAvgHeight(Connection conn) throws java.sql.SQLException {
        List<String> arr = new ArrayList<>();
        String query = "SELECT team, AVG(Age) AS Средний_возраст FROM 'Indicators'   GROUP BY team  ORDER BY Средний_возраст DESC LIMIT 10";
        PreparedStatement pr = conn.prepareStatement(query);
        ResultSet rs = pr.executeQuery();
        while (rs.next()) {
            arr.add(rs.getString(1));
        }
        return arr;
    }
}
