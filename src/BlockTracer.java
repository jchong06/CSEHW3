import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class BlockTracer {
    private static Stack<Block> stack;

    public BlockTracer() {
        stack = new Stack<>();
    }

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

    public void processFile(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.startsWith("{")) {
                stack.push(new Block());
            } else if (line.startsWith("}")) {
                stack.pop();
            } else if (line.startsWith("/*$print")) {
                print(line);
            } else if (line.contains("int ")) {
                addVariablesToBlock(line);
            }
        }

        scanner.close();
    }

    private void addVariablesToBlock(String line) {
        String variablesLine = line.substring(line.indexOf("int ") + 4, line.indexOf(";")).trim();
        String[] variables = variablesLine.split(",");
        for (String variable : variables) {
            String[] parts = variable.split("=");
            String varName = parts[0].replace(" ",  "");
            int initialValue = 0;
            if (parts.length > 1){
                initialValue = Integer.parseInt(parts[1].trim());
            }
            stack.peek().addVariable(new Variable(varName, initialValue));
        }
    }

    private void print(String line) {
        if (line.contains("LOCAL")) {
            stack.peek().printLocal();
        }
        else {
            int startIndex = line.indexOf(" ");
            int endIndex = line.lastIndexOf("*");
            String variableName = line.substring(startIndex, endIndex);
            printVariable(variableName);
        }
    }

    private void printVariable(String variableName) {
        variableName = variableName.replace(" ", "");
        boolean found = false;
        for (int i = stack.size() - 1; i >= 0; i--) {
            Block currentBlock = stack.get(i);
            if (currentBlock.hasVariable(variableName)) {
                System.out.println("Variable Name Initial Value");
                System.out.println(variableName + "             " + currentBlock.getVariableInitialValue(variableName));
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Variable not found: " + variableName);
        }
    }
}
