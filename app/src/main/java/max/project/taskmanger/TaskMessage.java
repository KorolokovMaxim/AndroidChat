package max.project.taskmanger;

public class TaskMessage {

    private String text;
    private String name;
    private String sender;
    private String recipient;
    private String imageUrl;
    private String date;
    private boolean isMine;

    public TaskMessage() {

    }

    public TaskMessage(String text,
                       String name,
                       String sender,
                       String recipient,
                       String imageUrl,
                       String date,
                       boolean isMine) {
        this.text = text;
        this.name = name;
        this.sender = sender;
        this.recipient = recipient;
        this.imageUrl = imageUrl;
        this.date = date;
        this.isMine = isMine;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getDate() {
       return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
