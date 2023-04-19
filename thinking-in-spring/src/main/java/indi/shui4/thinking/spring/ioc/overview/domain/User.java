package indi.shui4.thinking.spring.ioc.overview.domain;

/**
 * 用户
 *
 * @author shui4
 */
public class User {
    private String id;
    private String name;

    public static User createUser() {
        User user = new User();
        user.setId("1");
        user.setName("shui4");
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}