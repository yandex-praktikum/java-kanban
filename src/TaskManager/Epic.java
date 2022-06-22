package TaskManager;

public class Epic extends Task { //тут связь с сабтасками идёт по айди эпика

   public Epic(String name, Integer id, String description, String status) {

       super(name, id, description, status);
    }
    /* Не понял комментарий: getter'ы/setter'ы и переопределенные методы Object надо добавить.
       Если всё переопределил в родительском классе и новых полей тут тоже нет, всё нужно переопределить или
       ничего не изменится и можно только toString? */
    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
