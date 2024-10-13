import java.util.Arrays;
import java.util.regex.*;

public class SyntaxChecker {
    public static void main(String[] args){
        String sample = "for ( int i = 2 ; i > 0 ; i-- )  { System.out.prinn ( \"hello\" ) ; System.out.print ( \"hi\" ) ; }";
        System.out.println("sample input: " + sample + "\n");
        System.out.println("running syntax checks...\n\n");
        String[] sampleToken = tokenizer(sample);
        // for(String i: sampleToken){
        //     System.out.println(i);
        // }
        boolean forBodyCheckStatus = forBodyCheck(sampleToken);
        boolean validDelimStatus = validDelimiterCheck(sampleToken);
        boolean printStatementSyntaxStatus = printStatementSyntaxCheck(sampleToken);
        boolean forLoopPartCheckStatus = forLoopPartCheck(sampleToken);
        boolean initAssignCheckStatus = initAssignCheck(sampleToken);


        // Mas maigi na separate checks sila HAHA tas magkasama nalang sa sample run since working sample (no errors) naman don
        if(forBodyCheckStatus){
            System.out.println("Correct for-loop body");
        }else{
            System.out.println("Incorrect for-loop body");
        }
        if(validDelimStatus){
            System.out.println("Valid open-closed delimiters");
        }else{
            System.out.println("Invalid open-closed delimiters");
        }
        if(printStatementSyntaxStatus){
            System.out.println("Correct syntax of print statement");
        }else{
            System.out.println("Incorrect syntax of print statement");
        }
        if(forLoopPartCheckStatus){
            System.out.println("Contains initialization, condition, and update block");
        }else{
            System.out.println("Missing/Incorrect initialization, condition, or update block");
        }
        if(initAssignCheckStatus){
            System.out.println("Valid initialization assignment");
        }else{
            System.out.println("Invalid initialization assignment");
        }
        
        // _________________________________________________________________________________
        // ewan kay kirk

        String varName = "([a-zA-Z_][a-zA-Z0-9_]*)?";
        String num = "(\\d+)?";
        String condition = "(<|>|==|!=|<=|>=|.{1,2})?";
        String incOrDec = "(\\+\\+|--|.{1,2})?";

        String initializationPattern = "\\s*((int)?\\s*" + varName + "\\s*(=|.)?\\s*" + num + ")?\\s*";
        String conditionPattern = "\\s*(" + varName + "\\s*" + condition + "\\s*" + num + ")?\\s*";
        String advancementPattern = "\\s*(" + varName + incOrDec + ")\\s*";

        String forBody = "([^}]*)?";
        
        Pattern forPattern = Pattern.compile(
            "(for|.{0,3})?\\s*(\\(|.)?"+ 
            initializationPattern + "(;|.)?" + 
            conditionPattern + "(;|.)?" + 
            advancementPattern + "(\\)|.)?" + "\\s*(\\{|.)?\\s*" +
            forBody + "\\s*(\\}|.)?"
            );
        
        /* INDEXES
        *  0 = *entire string*
        *  1 = for 
        *  2 = (
        *  3 = *initialization pattern*
        *      4 = int     , optional
        *      5 = *variable name*
        *      6 = =
        *      7 = *number*
        *  8 = ;
        *  9 = *condition pattern*
        *      10 = *variable name*
        *      11 = *conditional operator*
        *      12 = *number*
        *  13 = ;
        *  14 = *advancement pattern*
        *      15 = *variable name*
        *      16 = *increment or decrement*
        *  17 = )
        *  18 = {
        *  19 = *for loop body*
        *  20 = }
        */

        /*  
        *  pwede i-compare mga indices na 'to if tama ung symbols na gamit
        *  kaso up to kung ilang chanracters lng ung tamang value
        *  
        *  1, 2, 4, 6, 8, 11, 13, 16, 17, 18, 20
        * 
        *  ex. 1 = for
        *      pwede pang detect ng misspelling like far, fer, fir
        *      pero pag fors, mapupunta ung s sa next matching pattern.
        * 
        */

        String input = sample;

        System.out.println("______________\newan kay kirk");
        try{
            Matcher matcher = forPattern.matcher(input);
            //System.out.println("match?: " + matcher.matches());
            for(int x = 1; x <= matcher.groupCount(); x++){
                System.out.print("[" + x + "]");
                System.out.print(matcher.group(x) + "...should be ");
                switch (x){
                    case(1):
                        System.out.println("'for'");
                        break;
                    case(2):
                        System.out.println("'('");
                        break;
                    case(3):
                        System.out.println("*initialization*");
                        break;
                    case(4):
                        System.out.println("int (optional)");
                        break;
                    case(5):
                        System.out.println("var name");
                        break;
                    case(6):
                        System.out.println("'='");
                        break;
                    case(7):
                        System.out.println("number");
                        break;
                    case(8):
                        System.out.println("';'");
                        break;
                    case(9):
                        System.out.println("condition");
                        break;
                    case(10):
                        System.out.println("var name");
                        break;
                    case(11):
                        System.out.println(">|<|==|!=|<=|>=");
                        break;
                    case(12):
                        System.out.println("number");
                        break;
                    case(13):
                        System.out.println("';'");
                        break;
                    case(14):
                        System.out.println("advancement");
                        break;
                    case(15):
                        System.out.println("var name");
                        break;
                    case(16):
                        System.out.println("++|--");
                        break;
                    case(17):
                        System.out.println("')'");
                        break;
                    case(18):
                        System.out.println("'{'");
                        break;
                    case(19):
                        System.out.println("for loop body");
                        break;
                    case(20):
                        System.out.println("'}'");
                        break;
                }
            }
        }
        catch(IllegalStateException e){
            System.out.println("does not follow (kirk's) syntax");
            return;
        }

        System.out.println("ewan kay kirk\n___________________");
        // ewan kay kirk
        // _________________________________________________________________________________

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

    public static boolean initAssignCheck(String[] tokenizedStrings){
        String[] tokenCheck = {"intType", "variable", "assign", "integer"};
        for(int i = 2; i < 6; i++){
            if(!tokenizedStrings[i].equals(tokenCheck[i-2])){
                return false;
            }
        }
        return true;
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

    public static boolean printStatementSyntaxCheck(String[] tokenizedStrings){
        boolean printStatementCheck = true;
        for(int i = 1; i < tokenizedStrings.length-3;i++){
            if(tokenizedStrings[i].equals("openParen") && tokenizedStrings[i+1].equals("string") && tokenizedStrings[i+2].equals("closeParen")){
                if(tokenizedStrings[i-1].equals("unknown")){
                    printStatementCheck = false;
                    break;
                }
            }
        }

        return printStatementCheck;
    }

    public static boolean forLoopPartCheck(String[] tokenizedString){
        String[] tokenCheck = {"openParen", "intType", "variable", "assign", "integer", "semiCol",
    "variable", "comparisonOptr", "integer", "semiCol", "decVar", "closeParen"};
        for(int i = 1; i < 13; i++){
            if(!tokenizedString[i].equals(tokenCheck[i-1])){
                return false;
            }
        }
        return true;
    }

    public static boolean validDelimiterCheck(String[] tokenizedStrings){
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