package Main;

import Manager.Manager;

public class Main {
    /* Сбили с толку статусы NEW IN_PROGRESS DONE, подумал что это константы и их нельзя менять, поэтому пришлось
    * прописывать методы setStatus для замены статуса и findEpicStatus возвращал final String :D
    * Всё оказалось проще, исправил. Так же переделал все ошибки исходя из замечаний, надеюсь всё теперь
    * реализовано правильно. */

    public static void main(String[] args) {
        Manager manager = new Manager();

    }
}
