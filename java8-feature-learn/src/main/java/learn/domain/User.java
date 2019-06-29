package learn.domain;

import lombok.Data;

import java.util.Optional;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-06-29
 * @since Jdk 1.8
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String email;
    private Address address;
    private String position;
    public User() {
    }

    public User(String email, String name ) {
        this.name = name;
        this.email = email;
    }

    public Optional<String> getPosition() {
        return Optional.ofNullable(position);
    }

    public Optional<Address> getAddress2() {
        return Optional.ofNullable(address);
    }
}
