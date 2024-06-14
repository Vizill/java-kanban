public class Subtask extends Task {
    private int parentEpic;

    public Subtask(String title, String description, int parentEpic) {
        super(title, description);
        this.parentEpic = parentEpic;
        this.type = Type.SUBTASK;
    }

    public int getParentEpic() {
        return parentEpic;
    }
}
