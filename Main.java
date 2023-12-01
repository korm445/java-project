import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<SportComands> comands;
    //Метод который парсит файл .csv,
    public static void main(String[] args) {
        comands = new ArrayList<>();
        try (BufferedReader bufferedReaderr = new BufferedReader(new FileReader("Показатели спортивных команд.csv"))) {
            String str = bufferedReaderr.readLine();
            while ((str = bufferedReaderr.readLine()) != null) {
                String[] column = str.split(",");
                SportComands aa = new SportComands(column[0], column[1], column[2], Integer.parseInt(column[3]),
                        Integer.parseInt(column[4]), Double.parseDouble(column[5]));
                comands.add(aa);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:sport.db"); // Установка соедения с базой данных
            ResultSet avg;
            if (connection != null) {
                System.out.println("Соединение установленно.");
                SQL.connection(connection);
                SQL.CreateTable();
                //SQL.WriteTable(comands, connection);
                System.out.println();

                //1 задание
                System.out.println("Задание 1 - Постройте график по среднему возрасту во всех командах");
                System.out.println();
                List<Double> avgHeight = SQL.AvgHeight(connection); // Ось У
                List<String> comands = SQL.ComandAvgHeight(connection); // Ось Х
                Chart chart = new Chart("График",  comands, avgHeight); // Создание окна графика
                chart.pack( );
                chart.setSize(800,600);
                chart.setVisible( true );

                //2 задание
                System.out.println("Задание 2 - Найти команду с самым высоким средним ростом. Выведите в консоль 5 самых высоких игроков команды");
                ResultSet ss = SQL.NamesComandMostAVGHeight(connection);
                System.out.println("-Самый большой показатель среднего роста - " + ss.getDouble(2) + " inches, Имеет команда с названием - " + ss.getString(1));
                System.out.println("-Вот 5 самых высоких игроков этой команды, а также их рост в дюймах: ");
                System.out.println();
                SQL.mostHighsPlayers(connection, 5);
                System.out.println();

                //3 задание
                /*Проверка условий из задания проводится в мейне,
                а именно проверяется лежит ли рост в диапазоне от 74 до 78 и лежит ли вес в диапазоне от 190 до 210 */
                System.out.println("Задание 3 - Найдите команду, с средним ростом равным от 74 до 78 inches и средним весом от 190 до 210 lbs, с самым высоким средним возрастом.");
                avg = SQL.mostOlderPlayer(connection);
                if (avg.getDouble(2) >= 74 && avg.getDouble(2) <= 78
                    && avg.getDouble(3) >= 190 && avg.getDouble(3) <= 210);{
                    System.out.println("-Команда, которая имеет самый высокий средний возраст  - " + avg.getDouble(4) + " лет, имеет название - " + avg.getString(1));
                }
            }
            connection.close(); // Закрываем активное соединение с базой
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка");
        }
    }
}
