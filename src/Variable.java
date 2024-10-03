/**
 *  * @author Justin Chong
 *  * Email: justin.chong@stonybrook.edu
 *  * Student ID: 116143020
 *  * Recitation Number: CSE 214 R03
 *  * TA: Kevin Zheng
 *
 * The Variable class represents a variable within a block of code.
 * Each variable has a name and an initial value, which are set when the
 * variable is created. This class provides access to the variable's name
 * and initial value.
 */
public class Variable {

    // The name of the variable
    private String name;

    // The initial value of the variable
    private int initialValue;

    /**
     * Constructs a Variable object with the given name and initial value.
     *
     * @param name The name of the variable.
     * @param initialValue The initial value assigned to the variable.
     */
    public Variable(String name, int initialValue) {
        this.name = name;
        this.initialValue = initialValue;
    }

    /**
     * Returns the name of the variable.
     *
     * @return The variable's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the initial value of the variable.
     *
     * @return The variable's initial value.
     */
    public int getInitialValue() {
        return initialValue;
    }
}
