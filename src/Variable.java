public class Variable {
    private String name;
    private int initialValue;

    public Variable(String name, int initialValue) {
        this.name = name;
        this.initialValue = initialValue;
    }

    public String getName() {
        return name;
    }

    public int getInitialValue() {
        return initialValue;
    }
}

