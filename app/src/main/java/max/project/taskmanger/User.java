package max.project.taskmanger;

import java.util.Objects;

public class User {

    private String name;
    private String email;
    private String id;
    private int avatarMockUpResource;

    public User(){}

    public User(String name, String email, String id, int avatarMockUpResource, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.avatarMockUpResource = avatarMockUpResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvatarMockUpResource() {
        return avatarMockUpResource;
    }

    public void setAvatarMockUpResource(int avatarMockUpResource) {
        this.avatarMockUpResource = avatarMockUpResource;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return avatarMockUpResource == user.avatarMockUpResource &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, id, avatarMockUpResource);
    }
}

