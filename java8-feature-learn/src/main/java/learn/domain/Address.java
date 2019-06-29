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
public class Address {
    private Country country;

    public Optional<Country> getCountry2() {
        return Optional.ofNullable(country);
    }

}
