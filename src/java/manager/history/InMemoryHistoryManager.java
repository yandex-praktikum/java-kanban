package manager.history;

import task.Task;

import java.util.*;
/** Менеджер истории, реализованный в виде двусвязного списка. **/
public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;
    private final Map<Integer, Node> nodeData = new HashMap<>();
    public class Node {
        public Task task;
        public Node next;
        public Node prev;

        public Node(Node prev, Task task, Node next) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }

    }

        public void linkLast(Task element) {
            final Node oldTail = tail; // сохранили текущий хвост в старый
            final Node newNode = new Node(null, element, oldTail); // создали новый узел
            tail = newNode; // присвоили текущему хвосту новый узел
            if (oldTail != null) { // если старый хвост был
                tail.prev = oldTail; // дали новому хвосту ссылку на предыдущий элемент
                oldTail.next = newNode; //дали старому хвосту ссылку на следущий
            } else { // если старого хвоста не было
                head = newNode; // голова-хвост
            }
        }

        public List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>(); // создали лист истории
            Node curTail = tail; // присвоили значение хвоста текущему хвосту
            while (curTail != null) { // пока текущий хвост не нулл
                    tasks.add(curTail.task);// добавили значения в список
                curTail = curTail.prev; // присвоили текущему хвосту значение предыдущего
            }
            return tasks;
        }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task){
        int id = task.getId();
        if (nodeData.containsKey(id)) { // если повторный просмотр удалили старый
            remove(id);
            nodeData.remove(id);
        }
        linkLast(task);
        nodeData.put(id, tail);
    }

    @Override
    public void remove(int id) {
        if (nodeData.containsKey(id)) {
            removeNode(nodeData.get(id));
        }
        nodeData.remove(id);
    }

    public void removeNode(Node node) {
        if (node == tail && node.prev == null) { // если узел единственный
            tail = null;
        } else if (node == tail) { // если хвост
            node.prev.next = null;
            tail = node.prev;
        } else if (node == head) { // если голова
            node.next.prev = null;
            head = node.next;
        } else { // если в середине
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.task = null;
        }
    }
    @Override
    public String toString() {
        return "Task{" +
                "name='" + tail;
    }
}