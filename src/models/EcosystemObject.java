package models;

public abstract class EcosystemObject {
    protected String name;

    public EcosystemObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String toString();
}