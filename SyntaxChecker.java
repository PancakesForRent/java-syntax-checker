public class SyntaxChecker {
    public static void main(String[] args){
        String sample = "for ( int i = 9 ; i < 0 ; i++ ) { System.out.println ( \"hello\" ) ; }";
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
                
            }

        }
        return untokenizedStrings;
    }
}