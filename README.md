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
Для работы с базой данных я создал соотвествующий класс SQL 
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
После запуска Main, в корневом каталоге проекта появится файл с .bd, в котором хранится наша база данных и таблица
Этот файл можно открыть в любом удобном веб-обозревателе

## Выполнение заданий
### *Задание 1*
#### Постройте график по среднему возрасту во всех командах
Для начала выполним запрос к базе данны

















