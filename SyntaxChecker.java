import java.util.Arrays;

public class SyntaxChecker {
    public static void main(String[] args){
        String sample = "for ( int i = 9 ; i > 0 ; i-- ) { System.out.println ( \"hello\" ) ; }";
        String[] sampleToken = tokenizer(sample);
        for(String i: sampleToken){
            System.out.println(i);
        }
    }

    public static String[] tokenizer(String input){
        String[] untokenizedStrings = input.trim().split("\\s+");
        String[] tokenizedStrings = new String[untokenizedStrings.length];
        for(int i = 0; i < tokenizedStrings.length; i++){
            switch(untokenizedStrings[i]){
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
                    String comparisonOptr[] = {"==", "!=", ">=", "<=", ">", "<"};
                    if (Arrays.asList(comparisonOptr).contains(untokenizedStrings[i])) {
                        tokenizedStrings[i] = "comparisonOptr";
                        break;
                    }else if (untokenizedStrings[i].matches("\"(\\w*\\s*\\'*\\,*)*\"")){
                        tokenizedStrings[i] = "string";
                        break;
                    }else if (untokenizedStrings[i].matches("\\d")){
                        tokenizedStrings[i] = "integer";
                        break;
                    }else if (untokenizedStrings[i].matches("(([a-zA-Z]+|_+)+(\\d*|[a-zA-Z]*|_*)*)+")){
                        tokenizedStrings[i] = "variable";
                        break;
                    }else if (untokenizedStrings[i].matches("(([a-zA-Z]+|_+)+(\\d*|[a-zA-Z]*|_*)*)+\\+\\+")) {
                        tokenizedStrings[i] = "incVar";
                        break;
                    }else if (untokenizedStrings[i].matches("(([a-zA-Z]+|_+)+(\\d*|[a-zA-Z]*|_*)*)+\\-\\-")) {
                        tokenizedStrings[i] = "decVar";
                        break;
                    }else{
                        tokenizedStrings[i] = "unknown";
                        break;
                    }
                
            }

        }
        return tokenizedStrings;
    }
}