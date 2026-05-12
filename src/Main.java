import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> lines = getFileData("src/Shell");
        ArrayList<ArrayList<String>> Memory = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            while (true) {
                if (line.startsWith("    ")) {
                    line = line.substring(4);
                } else {
                    break;
                }
            }

            if (line.split(" ")[0].equals("print")) { //PRINT

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
                    System.out.println(SearchMem(Memory, Query));
                }
            } else if (line.split(" ")[0].equals("if")) {
                int iPlus = 1;

                while (true) {
                    if ((lines.get(i + iPlus)).equals("}")) {
                        break;
                    } else {
                        iPlus++;
                    }
                }

                String Query = line.split(" ")[1];
                Query = Query.substring(1, Query.length() - 1);



                if (Query.contains("==")) {
                    String val1 = Query.split(" ")[0];
                    String val2 = Query.split(" ")[1];
                    double v1;
                    double v2;

                    if (isDouble(val1)) {
                        v1 = Double.parseDouble(val1);
                    } else {
                        v1 = Double.parseDouble(SearchMem(Memory, val1));
                    }

                    if (isDouble(val2)) {
                        v2 = Double.parseDouble(val2);
                    } else {
                        v2 = Double.parseDouble(SearchMem(Memory, val2));
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

                ArrayList<String> Cache = new ArrayList<>();
                Cache.add(varName);
                Cache.add(varType);
                Cache.add(val);

                Memory.add(Cache);

            }
        }
        SpitMemory(Memory);
    }

    public static String SearchMem(ArrayList<ArrayList<String>> Memory, String target) {
        for (int j = 0; j < Memory.size(); j++) {
            if (Memory.get(j).get(0).equals(target)) {
                return Memory.get(j).get(2);
            }
        }
        return "null";
    }

    public static void SpitMemory(ArrayList<ArrayList<String>> Memory) {
        System.out.println();
        System.out.println("--MEMORY CACHES--");
        for (int i = 0; i < Memory.size(); i++) {
            for (int j = 0; j < Memory.get(i).size(); j++) {
                System.out.print(Memory.get(i).get(j) + " | ");
            }
            System.out.println();
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