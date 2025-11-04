package edu.ntu.omelianov.LW_3.model;

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
}
