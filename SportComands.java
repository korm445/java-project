//Класс который создает структуру объекта SportComands
public class SportComands {
    String name;
    String team;
    String position;
    int height;
    int weight;
    Double age;

    //Конструктор
    public SportComands(String name, String team, String position, int height, int weight, Double age) {

        this.name = name;
        team = team.replace(" ", "").replace("\"\"\"","\"" );
        this.team = team;
        position = position.replace(" ", "").replace("\"\"\"","\"" );
        this.position = position;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public String toString() {
        return "(" + name + "," + team + "," + position + "," + height + "," + weight + "," +  age + ");";
    }
    //Геттеры
    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getPosition() {
        return position;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public Double getAge() {
        return age;
    }


}
