/*
 * RMIT University Vietnam
 * Course: COSC2658 - Data Structures and Algorithms
 * Semester: 2023C
 * Assessment: Group Project
 * Author - ID:  Nguyen Thien Co  -  s3938338
 *
 * NOTE: To enhance convenience during evaluation process such as retrieving guessCounter or generating random SecretKey, slight changes have been made in comparison with the corresponding file in "SourceCode" folder.
 * 
 * NOTE:
 * - You are not allowed to use code copied from the Internet.
 * - Your program must try to make this counter as small as possible when it finds out the correct secret key.
 */

package Group_17_Assessment3_GroupProject.Evaluation;

public class EnhancedBruteForce {
    private static final long MAX_ITERATION = 300000000;        // To handle run time problem while using Tester
    private static int guessCounter = 0;

    public int start(SecretKey testKey) {
        SecretKey secretKey = testKey;
        String guessKey = "MMMMMMMMMMMM";
        int correctPositions;

        // Check if the secretKey is "MMMMMMMMMMMM"
        correctPositions = secretKey.guess(guessKey);
        guessCounter++;
        if(correctPositions == 12){
            return guessCounter;
        }

        // Sequently store the frequency of each character {M, O, C, H, A}
        int[] secretKeyCharacters = new int[5];
        secretKeyCharacters = calculateCharFrequency(secretKey);
        for(int i = 0; i < secretKeyCharacters.length; i++){
            if(secretKeyCharacters[i] == 12){
                return guessCounter;
            }
        }

        long iteration = 0;
        // brute force key guessing
        while (iteration < MAX_ITERATION) {
            guessKey = next(guessKey);

            // Skip to the next guessKey by verifying character frequency
            if(verifySuitableGuessKey(secretKeyCharacters, guessKey)){
                correctPositions = secretKey.guess(guessKey);
                guessCounter++;

                if(correctPositions == 12){
                    return guessCounter;
                }
            }
            iteration++;
        }

        return -1;
    }

    private int[] calculateCharFrequency (SecretKey secretKey) {
        int[] frequencies = new int[5];
        for(int i = 0; i < 5; i++){
            frequencies[i] = 0;
        }

        for(int i = 0; i < 5; i++){
            String guessKey = "" + charOf(i);
            guessKey = guessKey.repeat(12);

            int correctPositions = secretKey.guess(guessKey);
            guessCounter++;
            frequencies[i] = correctPositions;

            if(correctPositions == 12){
                return frequencies;
            }
        }

        return frequencies;
    }

    private int[] calculateCharFrequency (String guessKey) {
        int[] frequencies = new int[5];
        for(int i = 0; i < 5; i++){
            frequencies[i] = 0;
        }

        for(int i = 0; i < 12; i++){
            frequencies[order(guessKey.charAt(i))]++;
        }

        return frequencies;
    }

    private Boolean verifySuitableGuessKey (int[] secretKeyCharacters, String guessKey) {
        int[] guessKeyCharacters = new int[5];
        guessKeyCharacters = calculateCharFrequency(guessKey);

        for(int i = 0; i < 5; i++){
            if(guessKeyCharacters[i] != secretKeyCharacters[i]){
                return false;
            }
        }

        return true;
    }

    private static int order(char c) {
        if (c == 'M') {
            return 0;
        } else if (c == 'O') {
            return 1;
        } else if (c == 'C') {
            return 2;
        } else if (c == 'H') {
            return 3;
        } 
        return 4;
    }

    private static char charOf(int order) {
        if (order == 0) {
            return 'M';
        } else if (order == 1) {
            return 'O';
        } else if (order == 2) {
            return 'C';
        } else if (order == 3) {
            return 'H';
        } 
        return 'A';
    }

    // return the next value in 'MOCHA' order, that is
    // M < O < C < H < A
    private static String next(String current) {
        char[] curr = current.toCharArray();
        for (int i = curr.length - 1; i >=0; i--) {
            if (order(curr[i]) < 4) {
                // increase this one and stop
                curr[i] = charOf(order(curr[i]) + 1);
                break;
            }
            curr[i] = 'M';
        }
        return String.valueOf(curr);
    }
}
