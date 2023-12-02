# Практика по java
## Вариант - Показатели спортивных команд

*Задания моего варианты и ответы на него представлены ниже:*

![График](https://github.com/korm445/java-project/assets/152654984/3a3c08f4-512f-4cbe-a870-614c2fd43bff)
![Решения](https://github.com/korm445/java-project/assets/152654984/c2d0f583-0485-4daf-a9c7-c58f185f361f)

# Ход работы
## Немного о файле 
Мой csv-файл выглядит таким образом: ![image](https://github.com/korm445/java-project/assets/152654984/70135726-55ab-40e1-9912-eaba72d0f2e8)
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





