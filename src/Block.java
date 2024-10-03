import java.util.ArrayList;
import java.util.Collections;

/**
 *  * @author Justin Chong
 *  * Email: justin.chong@stonybrook.edu
 *  * Student ID: 116143020
 *  * Recitation Number: CSE 214 R03
 *  * TA: Kevin Zheng
 *
 * The Block class represents a block of code that can contain multiple variables.
 * Each block maintains a list of variables declared within it. This class provides
 * methods to add variables, check for the existence of a variable, retrieve the
 * initial value of a variable, and print all local variables.
 */
public class Block {

    // List of variables declared within this block
    public ArrayList<Variable> variables;

    /**
     * Constructs a new Block object, initializing an empty list of variables.
     */
    public Block() {
        variables = new ArrayList<>();
    }

    /**
     * Adds a variable to the current block.
     *
     * @param variable The Variable object to be added.
     */
    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    /**
     * Checks if a variable with the specified name exists in the current block.
     *
     * @param name The name of the variable to search for.
     * @return true if the variable is found, false otherwise.
     */
    public boolean hasVariable(String name) {
        for (Variable variable : variables) {
            if (variable.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the initial value of a variable with the given name.
     *
     * @param name The name of the variable whose initial value is to be retrieved.
     * @return The initial value of the variable, or -1 if the variable is not found.
     */
    public int getVariableInitialValue(String name) {
        for (Variable variable : variables) {
            if (variable.getName().equals(name)) {
                return variable.getInitialValue();
            }
        }
        return -1;  // Return -1 if the variable is not found
    }

    /**
     * Prints the names and initial values of all local variables in the current block.
     * If no variables are present, it prints a message indicating that there are
     * no local variables to print.
     */
    public void printLocal() {
        if (variables.isEmpty()) {
            System.out.println("No local variables to print.");
        } else {
            System.out.println("Variable Name Initial Value");
            for (Variable variable : variables) {
                System.out.println(variable.getName() + String.join("", Collections.nCopies((14 - variable.getName().length()), "")) + variable.getInitialValue());
            }
        }
    }
}
