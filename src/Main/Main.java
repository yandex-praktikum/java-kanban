package Main;

import Manager.InMemoryTaskManager;

public class Main {
    /*И снова здравствуйте! :) Что я тут натворил:
    * 1. Сделал таск менеджер интерфейсом, вроде всё ок.
    * 2. Создал класс Managers - зачем он и что делает не понимаю пока что, надеюсь дальше узнаю.
    * 3. Сделал статусы enum.
    * 4. Создал интерфейс и класс для хранения истории.
    * По последнему пункту есть вопросы. Не понимаю как по тз правильно это всё реализовать.
    * Пока что получилось мплементировать HistoryManager в InMemoryTaskManager, потом для принта истории вызывать
    * getDefaultHistory. История записывается и выводится, но есть ощущение, что всё неправильно :)
    * С нетерпением жду коментариев.
    * */

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        manager.test();
    }

}
