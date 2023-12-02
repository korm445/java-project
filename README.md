# Практика по java
## Вариант - Показатели спортивных команд

*Задания моего варианты и ответы на него представлены ниже:*

![График](https://github.com/korm445/java-project/assets/152654984/3a3c08f4-512f-4cbe-a870-614c2fd43bff)
![Решения](https://github.com/korm445/java-project/assets/152654984/c2d0f583-0485-4daf-a9c7-c58f185f361f)

# Ход работы
## Немного о файле 
Мой csv-файл выглядит таким образом:
![image](https://github.com/korm445/java-project/assets/152654984/70135726-55ab-40e1-9912-eaba72d0f2e8)
Полный файл можно посмотреть в файлах проекта
На основе этого файла был создан класс SportComands, этот класс будет представлять каждую запись нашей будущей таблицы
*Полный код можно посмотреть в файле SportComands.java*

## Создание парсера 
```
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
```
Все записи таблицы (обьекты SportComands) находятся в массиве comands

## Создание таблицы и базы данных 
Для работы с базой данных я создал соотвествующий класс SQL.
*Полный код можно посмотреть в файле SQL.java*
В этом классе сздается два метода 
Первый  метод создаёт структуры таблицы

```
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
```
Второй метод заполянет ее данными (объектами класса SportComands)
```
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
```
В классе Main, я вызываю эти два методы чтобы создать базу данных и таблицу
```
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:sport.db"); // Установка соедения с базой данных
            ResultSet avg;
            if (connection != null) {
                System.out.println("Соединение установленно.");
                SQL.connection(connection);
                SQL.CreateTable();
                SQL.WriteTable(comands, connection);
                System.out.println();
```
После запуска Main, в корневом каталоге проекта появится файл с .bd, в котором хранится наша база данных и таблица.
Этот файл можно открыть в любом удобном веб-обозревателе

## Выполнение заданий
### *Задание 1*
#### Постройте график по среднему возрасту во всех командах
Для начала выполним запрос к базе данных
![Задание 1](https://github.com/korm445/java-project/assets/152654984/dd84aba1-690b-410d-b2cf-defef57269ff)

После напишем в классе SQL пишем два похожих метода, которые обрабатывают этот запрос.

Первый метод возвращает массив названий команд 
```
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
```
Второй метод выполянет тот же запрос но возвращает из этого запроса 2 столбец с средним возрастом команд и записывает его в массив
```
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
```

Для построения графика я создал отдельный класс Chart
*Код этого класса можно посмотреть в файле Chart.java*.
Перед началом построения графика подключим две библиотеки для работы с графиками - Jfreechart, jcommon.
Далее пишем в классе пишем структуру графика
```
public Chart(String WindowTitle , List<String> comand1, List<Double> avgHeight) {
        super(WindowTitle);
        JFreeChart Chart = ChartFactory.createBarChart(
                "Средний возраст команд", // Название графика
                "Команда", // Название Оси Х
                "Средний возраст, лет", // Название Оси У
                createDataset(comand1, avgHeight), // Создание набора данных
                PlotOrientation.VERTICAL, // Отоброжение графика по вертикали
                false, true, false);
        CategoryPlot plot = (CategoryPlot) Chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE); // Изменил цвет столбцов


        ChartPanel chartPanel = new ChartPanel(Chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.black); // Изменил фон
        //chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
        setContentPane(chartPanel);
    }
```
Также создаем метод который передает данные для графика ( те самые методы с названием команд и средним возрастом )
```
private CategoryDataset createDataset(List<String> comand, List<Double> avg) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(avg.get(0),"средний возраст", comand.get(0));
        dataset.addValue(avg.get(1),"средний возраст", comand.get(1));
        dataset.addValue(avg.get(2),"средний возраст", comand.get(2));
        dataset.addValue(avg.get(3),"средний возраст", comand.get(3));
        dataset.addValue(avg.get(4),"средний возраст", comand.get(4));
        dataset.addValue(avg.get(5),"средний возраст", comand.get(5));
        dataset.addValue(avg.get(6),"средний возраст", comand.get(6));
        dataset.addValue(avg.get(7),"средний возраст", comand.get(7));
        dataset.addValue(avg.get(8),"средний возраст", comand.get(8));
        dataset.addValue(avg.get(9),"средний возраст", comand.get(9));
        return dataset;
    }
```
После создания структуры, в классе Main вызываем приложения для графика и его пармаетры
```
                List<Double> avgHeight = SQL.AvgHeight(connection); // Ось У
                List<String> comands = SQL.ComandAvgHeight(connection); // Ось Х
                Chart chart = new Chart("График",  comands, avgHeight); // Создание окна графика
                chart.pack( );
                chart.setSize(800,600);
                chart.setVisible( true );
```
### *Задание 2*
#### Найдите команду с самым высоким средним ростом. Выведите в консоль 5 самых высоких игроков команды.
Для выполнения этого задания выполним два запроса к базе данных.
Первый запрос выводит команду с самым высоким средним ростом
![Задание 2 1](https://github.com/korm445/java-project/assets/152654984/af9512fd-0fb3-48c6-a4cb-b18accab9629)
Из первого запроса видно, что на первом месте команда - "CWS".
Второй запрос выводит 5 самых высоких игроков этой команды
![Задание 2 2](https://github.com/korm445/java-project/assets/152654984/a7ad0fe7-82e6-4ddc-a54c-06944357f754)

В классе SQl пишем два метода которые обрабатывают эти запросы, в классе Main, пишем красивый вывод этих методов в консоль
![image](https://github.com/korm445/java-project/assets/152654984/1de923bf-7410-4478-95d8-8c0770949d46)


### *Задание 3*
#### Найдите команду, с средним ростом равным от 74 до 78 inches и средним весом от 190 до 210 lbs, с самым высоким средним возрастом.
Выполняем запрос к таблице исходя из условий:
![Задание 3](https://github.com/korm445/java-project/assets/152654984/57be10f9-816f-49fb-9cf0-196b50f7c04b)

Пишем метод в классе SQL, который обрабатывает этот запрос
```
public static ResultSet mostOlderPlayer(Connection conn) throws java.sql.SQLException {
        List<ResultSet> arr = new ArrayList<>();
        String query = "SELECT team, AVG(Height) AS Средний_рост, AVG(Weight) AS Средний_вес, AVG(Age) AS Средний_возраст FROM 'Indicators'   GROUP BY team  ORDER BY Средний_возраст DESC LIMIT 1";
        PreparedStatement pr = conn.prepareStatement(query);
        ResultSet rs  = pr.executeQuery();
        return  rs;
    }
```
В классе Main пишем красивый вывод этого метода
![image](https://github.com/korm445/java-project/assets/152654984/8e94646d-6283-4d6a-a683-6737728c8630)



































