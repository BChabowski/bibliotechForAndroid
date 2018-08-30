package bibliotech4android.com.bibliotechforandroid;

import java.io.Serializable;

public class Author implements Serializable {
    private Integer id;
    private String name;
    private String lastName;
    private Integer yearOfBirth;
    private Integer yearOfDeath;

    public Author(){}

    public Author(String name, String lastName, Integer yearOfBirth, Integer yearOfDeath) {
        this.name = name;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
    }
    public Author(Integer id, String name, String lastName, Integer yearOfBirth, Integer yearOfDeath) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.yearOfDeath = yearOfDeath;
    }

    @Override
    public String toString(){
        return lastName+", "+name+" ("+yearOfBirth+" - "+yearOfDeath+")";
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public Integer getYearOfDeath() {
        return yearOfDeath;
    }
}
