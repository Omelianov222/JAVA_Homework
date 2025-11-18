package edu.ntu.omelianov.LW_4.model;
import java.util.Objects;

public abstract class StructuralUnit {
    private final String name;
    private final Human head;

    protected StructuralUnit(String name, Human head) {
        this.name = name;
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public Human getHead() {
        return head;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructuralUnit that = (StructuralUnit) o;
        return name.equals(that.name) &&
                head.equals(that.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, head);
    }

}
