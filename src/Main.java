import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> lines = getFileData("src/Shell");
        ArrayList<Cache> Memory = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            while (true) {
                if (line.startsWith("    ")) {
                    line = line.substring(4);
                } else {
                    break;
                }
            }

            if (line.length() > 1 && line.substring(0, 2).equals("--")) { //COMMENT

            } else if (line.split(" ")[0].equals("print")) { //PRINT

                String Query;
                if (line.contains("'")) {
                    Query = line.split("'")[1];
                } else {
                    Query = line.split(" ")[1];
                    Query = Query.substring(1, Query.length() - 1);
                }

                if (line.contains("'")) {
                    System.out.println(Query);
                }
                else {
                    System.out.println(SearchMemVal(Memory, Query));
                }
            } else if (line.split(" ")[0].equals("if")) { //IF STATEMENTS
                int iPlus = 1;

                while (true) {
                    if ((lines.get(i + iPlus)).equals("}")) {
                        break;
                    } else {
                        iPlus++;
                    }
                }

                String Query = inParenthesis(line);

                if (Query.contains("==")) {
                    String val1 = Query.split(" ")[0];
                    String val2 = Query.split(" ")[2];
                    double v1;
                    double v2;

                    if (isDouble(val1)) {
                        v1 = Double.parseDouble(val1);
                    } else {
                        v1 = Double.parseDouble(SearchMemVal(Memory, val1));
                    }

                    if (isDouble(val2)) {
                        v2 = Double.parseDouble(val2);
                    } else {
                        v2 = Double.parseDouble(SearchMemVal(Memory, val2));
                    }

                    if (v1 != v2) {
                        i += iPlus;
                    }
                }
                else {
                    i += iPlus;
                }

            } else if (line.equals("}")) { //check for close

            }
            else { //VARIABLE DECLARATION

                String varName = line.split(" ")[0];

                String varType = "null";
                String val;
                if (line.contains("'")) {
                    varType = "String";
                    val = line.split("'")[1];
                } else {
                    val = line.split(" ")[2];
                }


                if (isInt(val)) {
                    varType = "Integer";
                    val = line.split(" ")[2];
                } else if (isDouble(val)) {
                    varType = "Double";
                    val = line.split(" ")[2];
                } else {

                }

                if (SearchMemVal(Memory, varName).equals("null")) {
                    Cache c = new Cache(varName, varType, val);
                    Memory.add(c);
                } else {
                    SearchMem(Memory, varName).setVarType(varType);
                    SearchMem(Memory, varName).setValue(val);
                }
            }
        }
        SpitMemory(Memory);
    }

    public static String SearchMemVal(ArrayList<Cache> Memory, String target) {
        for (int j = 0; j < Memory.size(); j++) {
            if (Memory.get(j).getName().equals(target)) {
                return Memory.get(j).getValue();
            }
        }
        return "null";
    }

    public static Cache SearchMem(ArrayList<Cache> Memory, String target) {
        for (int j = 0; j < Memory.size(); j++) {
            if (Memory.get(j).getName().equals(target)) {
                return Memory.get(j);
            }
        }
        return (null);
    }

    public static void SpitMemory(ArrayList<Cache> Memory) {
        System.out.println();
        System.out.println("--MEMORY CACHES--");
        for (int i = 0; i < Memory.size(); i++) {
            System.out.println(Memory.get(i));
        }
    }

    public static boolean isDouble(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInt(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isStr(String str) {
        if (str == null) {
            return false;
        }
        return str.charAt(0) == '\'' && str.charAt(str.length() - 1) == '\'';
    }

    public static String inParenthesis(String str) {
        boolean include = false;
        String ans = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).equals("(")) {
                include = true;
            } else if (str.substring(i, i + 1).equals(")")) {
                include = false;
            } else {
                if (include) {
                    ans += str.substring(i, i + 1);
                }
            }
        }
        return ans;
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}