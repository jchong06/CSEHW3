import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 *  * @author Justin Chong
 *  * Email: justin.chong@stonybrook.edu
 *  * Student ID: 116143020
 *  * Recitation Number: CSE 214 R03
 *  * TA: Kevin Zheng
 *
 * The BlockTracer class processes files containing code blocks,
 * identifies variable declarations, and manages print commands
 * within those blocks. It uses a stack to handle nested blocks
 * and tracks the variables within each block.
 */
public class BlockTracer {
    // Stack to manage the blocks of code.
    private Stack<Block> stack;

    /**
     * Constructor for BlockTracer.
     * Initializes an empty stack for managing code blocks.
     */
    public BlockTracer() {
        stack = new Stack<>();
    }

    /**
     * The main method drives the program. It prompts the user
     * to enter the name of a file, and then processes that file
     * to trace code blocks, variables, and print commands.
     *
     * @param args Command-line arguments (not used here).
     * @throws FileNotFoundException If the file specified by the user cannot be found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        BlockTracer blockTracer = new BlockTracer();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the filename: ");
        String filename = scanner.nextLine();

        try {
            blockTracer.processFile(filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Processes the specified file line by line. This method identifies
     * blocks of code, adds variables to the current block, and handles
     * print commands. It uses a stack to maintain the hierarchy of code blocks.
     *
     * @param filename The name of the file to be processed.
     * @throws IOException If there is an error reading the file.
     */
    public void processFile(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        // Read the file line by line
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // Start of a new block
            if (line.startsWith("{")) {
                stack.push(new Block());

                // Handle cases where additional code follows the opening brace
                if (line.length() > 1) {
                    if (line.contains("int ")) {
                        addVariablesToBlock(line);
                    }
                    if (line.contains("/*$print")) {
                        print(line);
                    }
                    if (line.startsWith("}")) {
                        stack.pop(); // Close the block
                    }
                }

                // End of a block
            } else if (line.startsWith("}")) {
                stack.pop();

                // Handle print commands
            } else if (line.contains("/*$print")) {
                print(line);

                // Handle variable declarations
            } else if (line.contains("int ")) {
                addVariablesToBlock(line);
            }
        }

        scanner.close();
    }

    /**
     * Parses a line of code that declares variables and adds those variables
     * to the current block. Each variable can have an optional initial value.
     *
     * @param line The line containing the variable declaration(s).
     */
    private void addVariablesToBlock(String line) {
        String variablesLine = line.substring(line.indexOf("int ") + 4, line.indexOf(";")).trim();
        String[] variables = variablesLine.split(",");

        // Parse each variable declaration
        for (String variable : variables) {
            String[] parts = variable.split("=");
            String varName = parts[0].replace(" ", "");
            int initialValue = 0;

            // If the variable has an initial value, parse it
            if (parts.length > 1) {
                initialValue = Integer.parseInt(parts[1].trim());
            }
            stack.peek().addVariable(new Variable(varName, initialValue));
        }
    }

    /**
     * Handles the print commands in the code. It can either print all
     * variables local to the current block or print the value of a
     * specific variable from any block in the stack.
     *
     * @param line The line containing the print command.
     */
    private void print(String line) {
        // Print local variables if specified
        if (line.contains("LOCAL")) {
            stack.peek().printLocal();
        }
        // Print a specific variable's value
        else {
            int startIndex = line.indexOf(" ");
            int endIndex = line.lastIndexOf("*");
            String variableName = line.substring(startIndex, endIndex).trim();
            printVariable(variableName);
        }
    }

    /**
     * Searches for the specified variable in the stack, starting from
     * the topmost block, and prints its initial value. If the variable
     * is not found, it outputs an error message.
     *
     * @param variableName The name of the variable to print.
     */
    private void printVariable(String variableName) {
        variableName = variableName.replace(" ", "");
        boolean check = false;

        // Search for the variable from the top of the stack
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).hasVariable(variableName)) {
                System.out.println("Variable Name Initial Value");
                System.out.println(variableName + String.join("", Collections.nCopies((14 - variableName.length()), " ")) + stack.get(i).getVariableInitialValue(variableName));
                check = true;
                break;
            }
        }

        // Print an error if the variable was not found
        if (!check) {
            System.out.println("Variable not found: " + variableName);
        }
    }
}
