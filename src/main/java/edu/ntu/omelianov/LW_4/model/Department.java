package edu.ntu.omelianov.LW_4.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Department extends StructuralUnit {
    private final List<Group> groups;

    public Department(String name, Human head) {
        super(name, head);
        this.groups = new ArrayList<>();
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public List<Group> getGroups() {
        return Collections.unmodifiableList(groups);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        if (!super.equals(o)) return false;
        Department that = (Department) o;
        return groups.equals(that.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), groups);
    }

}
