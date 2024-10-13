import java.util.Arrays;

public class SyntaxChecker {
    public static void main(String[] args){
        String sample = "for ( int i = 9 ; i > 0 ; i-- )  { System.out.println ( \"hello\" ) ; System.out.print ( \"hi\" ) ; }";
        String[] sampleToken = tokenizer(sample);
        // for(String i: sampleToken){
        //     System.out.println(i);
        // }
        boolean forBodyCheckStatus = forBodyCheck(sampleToken);
        boolean validDelimCountStatus = validDelimiterCountCheck(sampleToken);
        if(forBodyCheckStatus){
            System.out.println("Correct for-loop body");
        }else{
            System.out.println("Incorrect for-loop body");
        }
        if(validDelimCountStatus){
            System.out.println("Correct number of open-closed delimiters");
        }else{
            System.out.println("Incorrect number of open-closed delimiters");
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

    public static boolean forBodyCheck(String[] tokenizedStrings){
        boolean forCheck = (tokenizedStrings[0].equals("for"));
        boolean forParenCheck = (tokenizedStrings[1].equals("openParen") && tokenizedStrings[12].equals("closeParen"));
        boolean forCurlCheck = (tokenizedStrings[13].equals("openCurl") && tokenizedStrings[tokenizedStrings.length-1].equals("closeCurl"));
        int semiColonCount = 0;
        for(int i = 0; i < Arrays.asList(tokenizedStrings).indexOf("closeParen"); i++){
            if(tokenizedStrings[i].equals("semiCol")){
                semiColonCount++;
            }
        }
        
        return forCheck && forParenCheck && forCurlCheck && (semiColonCount == 2);
    }

    public static boolean validDelimiterCountCheck(String[] tokenizedStrings){
        int openDelimCount = 0;
        int closeDelimCount = 0;
        for(int i = 0; i < tokenizedStrings.length; i++){
            if(tokenizedStrings[i].equals("openParen") || tokenizedStrings[i].equals("openCurl")){
                openDelimCount++;
            }else if(tokenizedStrings[i].equals("closeParen") || tokenizedStrings[i].equals("closeCurl")){
                closeDelimCount++;
            }
        }

        return (openDelimCount == closeDelimCount);
    }
}