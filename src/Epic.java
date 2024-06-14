public class Epic extends Task {

    Epic(String title, String description) {
        super(title, description);
        this.type = Type.EPIC;
    }
}

