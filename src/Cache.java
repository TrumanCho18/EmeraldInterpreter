public class Cache {
    private String name;
    private String varType;
    private String value;

    public Cache(String n, String t, String v) {
        name = n;
        varType = t;
        value = v;
    }

    @Override
    public String toString() {
        return (name + " | " + varType + " | " + value);
    }

    public String getName() {
        return name;
    }

    public String getVarType() {
        return varType;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
