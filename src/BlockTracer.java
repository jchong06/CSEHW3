import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class BlockTracer {
    private Stack<Block> stack;

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
                if (line.length() > 1){
                    if (line.contains("int ")) {
                        addVariablesToBlock(line);
                    }
                    if (line.contains("/*$print")) {
                        print(line);
                    }
                    if (line.startsWith("}")) {
                        stack.pop();
                    }
                }
            } else if (line.startsWith("}")) {
                stack.pop();
            } else if (line.contains("/*$print")) {
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
            String variableName = line.substring(startIndex, endIndex).trim();
            printVariable(variableName);
        }
    }

    private void printVariable(String variableName) {
        variableName = variableName.replace(" ", "");
        boolean check = false;
        for (int i = stack.size() - 1; i >= 0; i--) {
            if (stack.get(i).hasVariable(variableName)) {
                System.out.println("Variable Name Initial Value");
                System.out.println(variableName + String.join("", Collections.nCopies((14 - variableName.length())," ")) + stack.get(i).getVariableInitialValue(variableName));
                check = true;
                break;
            }
        }

        if (!check) {
            System.out.println("Variable not found: " + variableName);
        }
    }
}
