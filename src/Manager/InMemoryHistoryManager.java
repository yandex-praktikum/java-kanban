package Manager;

import Task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;
    public class Node {
        public Task data;
        public Node next;
        public Node prev;

        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

        public void linkLast(Task element) {
            final Node oldTail = tail; // сохранили текущий хвост в старый
            final Node newNode = new Node(null, element, oldTail); // создали новый узел
            tail = newNode; // присвоили текущему хвосту новый узел
            if (oldTail == null) { // если старый хвост пустой
                head = newNode; // голова-хвост
            } else { // если старый хвост был
                tail.prev = oldTail; // дали новому хвосту ссылку на предыдущий элемент
                oldTail.next = newNode; //дали старому хвосту ссылку на следущий
            }
        }

        public List<Task> getTasks() {
            List tasks = new ArrayList<>(); // создал лист истории
            Node curTail = tail; // присвоил значение хвоста текущему хвосту
            while (curTail != null) { // пока текущий хвост не нулл
                if (nodeData.containsValue(curTail)) { // проверил наличие узла в мапе
                    tasks.add(curTail.data); // добавил значения в список
                }
                curTail = curTail.prev; // присвоили текущему хвосту значение предыдущего
            }
            return tasks;
        }

    Map<Integer, Node> nodeData = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task){
        if (task == null)
            return;
        int id = task.getId();
        removeNode(id);
        linkLast(task);
        nodeData.put(id, tail);
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }
    public void removeNode(int id) {
        nodeData.remove(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + tail;
    }
}
