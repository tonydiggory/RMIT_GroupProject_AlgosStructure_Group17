/*
 * RMIT University Vietnam
 * Course: COSC2658 - Data Structures and Algorithms
 * Semester: 2023C
 * Assessment: Group Project
 * Author - ID:  Nguyen Thien Co  -  s3938338
 *               Vo Minh Thien An -  s3916570
 *
 * NOTE: To enhance convenience during evaluation process such as retrieving guessCounter or generating random SecretKey, slight changes have been made in comparison with the corresponding file in "SourceCode" folder.
 * 
 * NOTE:
 * - You are not allowed to use code copied from the Internet.
 * - Your program must try to make this counter as small as possible when it finds out the correct secret key.
 */

package Group_17_Assessment3_GroupProject.Evaluation;

public class DigitByDigitMod {
    public static final int MAX_ITERATION = 100;        // To handle run time problem while using Tester
    
    
    /**
     * The "start" function uses a Depth-first guessing method to refine each position of the guessKey from left to right until the secretKey is discovered or a limit is reached. Then, it returns the number of guesses used.
     * 
     * NOTE: To enhance convenience while performing evaluation, several functions have been modified slightly.
     * 
     * @param testKey - the current secretKey in the test
     * @return guessCounter - number of guessed used to find the secretKey
     * 
     * @see #order(char c)
     * @see #charOf(int order)
     * @see #next(String current, int index)
     */

    public String start(SecretKey testKey) {
        // brute force key guessing
        SecretKey key = testKey;
        String str = "MMMMMMMMMMMM";
        String prevStr = str;

        int correctPositions = key.guess(str);
        int newCorrectPositions = correctPositions;
        int currIndex = 0;
        int iteration = 0;

        while (iteration < MAX_ITERATION) {
            if(correctPositions == 12){
                return str;
            }

            prevStr = str;
            str = next(str, currIndex);
            newCorrectPositions = key.guess(str);

            // There are 3 cases while modifying a position:
            // - newCorrectPositions < correctPositions: the previous character is a correct for the examining position.
            // - newCorrectPositions = correctPositions: both previous and current characters are incorrect for the examining position.
            //- newCorrectPositions > correctPositions: the current character is a correct for the examining position.
            if (newCorrectPositions < correctPositions) {
                currIndex++;
                newCorrectPositions = correctPositions;
                str = prevStr;
            } else if(newCorrectPositions > correctPositions){
                currIndex++;
                correctPositions = newCorrectPositions;
            }

            iteration++;
        }
        
        return null;
    }

    /**
     * The "order" function converts character to int based on the order of letters in "MOCHA"
     * @param c - The character that needs to be converted
     * @return an integer representing the order of the converted character
     */
    static int order(char c) {
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


    /**
     * The "charOf" function converts int to character based on the order of letters in "MOCHA"
     * @param order - the order of the letter in "MOCHA"
     * @return a character in order-th position
     */
    static char charOf(int order) {
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
    public String next(String current, int index) {
        char[] curr = current.toCharArray();
        for (int i = curr.length - index - 1; i >=0; i--) {
            if (order(curr[i]) < 4) {
                // increase this one and stop
                curr[i] = charOf(order(curr[i]) + 1);
                break;
            } else {
                curr[i] = 'M';
            }
        }
        return String.valueOf(curr);
    }
}
