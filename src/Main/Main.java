package Main;

import Manager.Manager;

public class Main {
    /* И снова здравствуйте, я всё сделал(опять) и проверил, всё работает. Может быть хоть в этот раз всё по тз :)
       1. Добавил пересчёт статуса эпика в метод AddNewSubTask.
       2. Добавил удаление всех subTask в метод deleteAllEpic.
       3. В методе deleteSubTaskById убрал цикл.
       4. В методе deleteEpicById добавил цикл удаления subTask по этому эпику.
       5. В методе updateSubTask заменил else на return в конце if.
       6. В методе findEpicStatus исправил итерацию на более короткую.
       Так же на предыдущей итерации я упростил метод deleteSubTaskFromList, но он перестал работать, поменял
       передаваемый параметр с примитива на объект, теперь всё ок.
       И по 6 пункту есть вопросы, в первой версии findEpicStatus работал правильно, но не по тз.
       Я пока его исправлял в соответствии с тз, он перестал работать.
       Комментарий из первой версии был такой: "Можно сразу присваивать статус эпику и не возвращать ничего.
       Параметр HashMap<Integer, SubTusk> subTusk - лишний, эпик ищем в имеющейся мапе."
       Из него я сделал вывод, что логика была реализована верно, но были ошибки в параметрах и том, что метод возвращал
       статус. Поэтому я вернул логику первой версии и метод работает корректно, но теперь он void и параметры
        передаются правильные в соответствии с тз.
      */

    public static void main(String[] args) {

        Manager manager = new Manager();
        manager.test();
    }

}
