package zipgo.domain;

import java.util.Objects;

public class Keyword {
    private String name;
    public Keyword(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(name, keyword.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
