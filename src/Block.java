import java.util.ArrayList;

public class Block {
        public ArrayList<Variable> variables;

        public Block() {
            variables = new ArrayList<>();
        }

        public void addVariable(Variable variable) {
            variables.add(variable);
        }

        public boolean hasVariable(String name) {
            for (Variable variable : variables) {
                if (variable.getName().equals(name)) {
                    return true;
                }
            }
            return false;
        }

        public int getVariableInitialValue(String name) {
            for (Variable variable : variables) {
                if (variable.getName().equals(name)) {
                    return variable.getInitialValue();
                }
            }
            return -1;
        }

        public void printLocal() {
            if (variables.isEmpty()) {
                System.out.println("No local variables to print.");
            } else {
                System.out.println("Variable Name Initial Value");
                for (Variable variable : variables) {
                    System.out.println(variable.getName() + "             " + variable.getInitialValue());
                }
            }
        }


}
