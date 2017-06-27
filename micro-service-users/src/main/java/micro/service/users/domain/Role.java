package micro.service.users.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9.
 */
public class Role implements Serializable{
    private Long id ;
    private String name;
    private List<Power> powers ;

    public Role(String name) {
        this.name = name;
        this.powers =  new ArrayList<Power>();
    }

    public List<Power> getPowers() {
        return powers;
    }

    public void setPowers(List<Power> powers) {
        this.powers = powers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", powers=" + powers +
                '}';
    }
}
