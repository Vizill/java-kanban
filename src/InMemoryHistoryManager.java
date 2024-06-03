import java.util.*;

class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> nodesMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        if (nodesMap.containsKey(task.getId())) {
            removeNode(nodesMap.get(task.getId()));
        }

        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (nodesMap.containsKey(id)) {
            removeNode(nodesMap.get(id));
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;

        while (current != null) {
            history.add(current.task);
            current = current.next;
        }

        return history;
    }

    private void linkLast(Task task) {
        Node newNode = new Node(task);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        nodesMap.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        if (node == null) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        nodesMap.remove(node.task.getId());
    }
}