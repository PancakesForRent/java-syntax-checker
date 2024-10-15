import java.util.Arrays;

public class SyntaxChecker {
    public static void main(String[] args) {
        String sample = "for( int j = 0 ; i > 10 ; i++) { System.out.print(i); variable = \"message\";}";
        System.out.println("sample input: " + sample + "\n");
        System.out.println("running syntax checks...\n\n");
        String[] sampleToken = tokenizer(sample);
        // for(String i: sampleToken){
        // System.out.println(i);
        // }
        boolean forBodyCheckStatus = forBodyCheck(sampleToken);
        boolean validDelimStatus = validDelimiterCheck(sampleToken);
        boolean printStatementSyntaxStatus = printStatementSyntaxCheck(sampleToken);
        boolean forLoopPartCheckStatus = forLoopPartCheck(sampleToken);
        boolean initAssignCheckStatus = initAssignCheck(sampleToken);
        boolean assignmenntStatementSyntaxStatus = assignmentStatementSyntaxCheck(sampleToken);

        // Mas maigi na separate checks sila HAHA tas magkasama nalang sa sample run
        // since working sample (no errors) naman don
        if (forBodyCheckStatus) {
            System.out.println("Correct for-loop body");
        } else {
            System.out.println("Incorrect for-loop body");
        }
        if (validDelimStatus) {
            System.out.println("Valid open-closed delimiters");
        } else {
            System.out.println("Invalid open-closed delimiters");
        }
        if (printStatementSyntaxStatus) {
            System.out.println("Correct syntax of print statement");
        } else {
            System.out.println("Invalid syntax of print statement or no print statement found");

        }
        if (assignmenntStatementSyntaxStatus) {
            System.out.println("Correct syntax of assignment statement");
        } else {
            System.out.println("Invalid syntax of assignment statement or no assignment statement found");
        }
        if (forLoopPartCheckStatus) {
            System.out.println("Contains initialization, condition, and update block");
        } else {
            System.out.println("Missing/Incorrect initialization, condition, or update block");
        }
        if (initAssignCheckStatus) {
            System.out.println("Valid initialization assignment");
        } else {
            System.out.println("Invalid initialization assignment");
        }
    }

    public static String[] tokenizer(String input) {
        input = input.replace("(", " ( ").replace(")", " ) ")
                .replace("{", " { ").replace("}", " } ")
                .replace(";", " ; ");
        String[] untokenizedStrings = input.trim().split("\\s+");
        String[] tokenizedStrings = new String[untokenizedStrings.length];
        for (int i = 0; i < tokenizedStrings.length; i++) {
            switch (untokenizedStrings[i]) {
                case "for":
                    tokenizedStrings[i] = "for";
                    break;
                case "{":
                    tokenizedStrings[i] = "openCurl";
                    break;
                case "}":
                    tokenizedStrings[i] = "closeCurl";
                    break;
                case "(":
                    tokenizedStrings[i] = "openParen";
                    break;
                case ")":
                    tokenizedStrings[i] = "closeParen";
                    break;
                case "int":
                    tokenizedStrings[i] = "intType";
                    break;
                case "=":
                    tokenizedStrings[i] = "assign";
                    break;
                case ";":
                    tokenizedStrings[i] = "semiCol";
                    break;
                case "System.out.print":
                    tokenizedStrings[i] = "printStmt";
                    break;
                case "System.out.println":
                    tokenizedStrings[i] = "printStmt";
                    break;
                default:
                    String comparisonOptr[] = { "==", "!=", ">=", "<=", ">", "<" };
                    if (Arrays.asList(comparisonOptr).contains(untokenizedStrings[i])) {
                        tokenizedStrings[i] = "comparisonOptr";
                        break;
                    } else if (untokenizedStrings[i].matches("\"(\\w*\\s*\\'*\\,*)*\"")) {
                        tokenizedStrings[i] = "string";
                        break;
                    } else if (untokenizedStrings[i].matches("\\d{1,3}")) {
                        tokenizedStrings[i] = "integer";
                        break;
                    } else if (untokenizedStrings[i].matches("(([a-zA-Z]+|_+)+(\\d*|[a-zA-Z]*|_*)*)+")) {
                        tokenizedStrings[i] = "variable";
                        break;
                    } else if (untokenizedStrings[i].matches("(([a-zA-Z]+|_+)+(\\d*|[a-zA-Z]*|_*)*)+\\+\\+")) {
                        tokenizedStrings[i] = "incVar";
                        break;
                    } else if (untokenizedStrings[i].matches("(([a-zA-Z]+|_+)+(\\d*|[a-zA-Z]*|_*)*)+\\-\\-")) {
                        tokenizedStrings[i] = "decVar";
                        break;
                    } else {
                        tokenizedStrings[i] = "unknown";
                        break;
                    }

            }

        }
        return tokenizedStrings;
    }

    public static boolean initAssignCheck(String[] tokenizedStrings) {
        String[] tokenCheck = { "intType", "variable", "assign", "integer" };
        for (int i = 2; i < 6; i++) {
            if (!tokenizedStrings[i].equals(tokenCheck[i - 2])) {
                return false;
            }
        }
        return true;
    }

    public static boolean forBodyCheck(String[] tokenizedStrings) {
        boolean forCheck = (tokenizedStrings[0].equals("for"));
        boolean forParenCheck = (tokenizedStrings[1].equals("openParen") && tokenizedStrings[12].equals("closeParen"));
        boolean forCurlCheck = (tokenizedStrings[13].equals("openCurl")
                && tokenizedStrings[tokenizedStrings.length - 1].equals("closeCurl"));
        int semiColonCount = 0;
        for (int i = 0; i < Arrays.asList(tokenizedStrings).indexOf("closeParen"); i++) {
            if (tokenizedStrings[i].equals("semiCol")) {
                semiColonCount++;
            }
        }

        return forCheck && forParenCheck && forCurlCheck && (semiColonCount == 2);
    }

    public static boolean printStatementSyntaxCheck(String[] tokenizedStrings) {
        for (int i = 0; i < tokenizedStrings.length - 1; i++) {
            // Check if this token is a print statement
            if (tokenizedStrings[i].equals("printStmt")) {
                if (i + 4 < tokenizedStrings.length
                        && tokenizedStrings[i + 1].equals("openParen")
                        && (tokenizedStrings[i + 2].equals("string"))
                        && tokenizedStrings[i + 3].equals("closeParen")
                        && tokenizedStrings[i + 4].equals("semiCol")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean assignmentStatementSyntaxCheck(String[] tokenizedStrings) {
        int variableCount = 0;
        for (int i = 0; i < tokenizedStrings.length - 1; i++) {
            if (tokenizedStrings[i].equals("variable")) {
                variableCount++;
                if (variableCount >= 3) {
                    if (i + 3 < tokenizedStrings.length
                            && tokenizedStrings[i + 1].equals("assign")
                            && (tokenizedStrings[i + 2].equals("integer") || tokenizedStrings[i + 2].equals("string")
                                    || tokenizedStrings[i + 2].equals("variable"))
                            && tokenizedStrings[i + 3].equals("semiCol")) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public static boolean forLoopPartCheck(String[] tokenizedString) {
        String[] tokenCheck = { "openParen", "intType", "variable", "assign", "integer", "semiCol",
                "variable", "comparisonOptr", "integer", "semiCol", "decVar", "closeParen" };
        String[] tokenCheckv2 = { "openParen", "intType", "variable", "assign", "integer", "semiCol",
                "variable", "comparisonOptr", "integer", "semiCol", "incVar", "closeParen" };
        for (int i = 1; i < 13; i++) {
            if (!tokenizedString[i].equals(tokenCheck[i - 1])) {
                if (!tokenizedString[i].equals(tokenCheckv2[i - 1])) {
                    return false;
                }

            }
        }
        return true;
    }

    public static boolean validDelimiterCheck(String[] tokenizedStrings) {
        int openCurlCount = 0;
        int closeCurlCount = 0;
        int openParenCount = 0;
        int closeParenCount = 0;
        for (int i = 0; i < tokenizedStrings.length; i++) {
            if (tokenizedStrings[i].equals("openParen")) {
                openParenCount++;
            } else if (tokenizedStrings[i].equals("openCurl")) {
                openCurlCount++;
                ;
            } else if (tokenizedStrings[i].equals("closeParen")) {
                closeParenCount++;
            } else if (tokenizedStrings[i].equals("closeCurl")) {
                closeCurlCount++;
            }
        }

        return (openParenCount == closeParenCount) &&
                ((openCurlCount + closeCurlCount) >= 2) &&
                (openCurlCount == closeCurlCount);
    }
}
