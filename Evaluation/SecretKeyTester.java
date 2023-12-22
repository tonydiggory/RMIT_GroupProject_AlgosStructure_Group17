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

public class SecretKeyTester {
    /**
     * The "generateRandomSecretKey" function creates a String representing a SecretKey.
     * @return randomSecretKey - the secretKey that is randomly generated.
     */
    public static String generateRandomSecretKey () {
        char[] characters = {'M', 'O', 'C', 'H', 'A'};
        String randomSecretKey = "";

        // Using for-loop to generate a random character for each position
        for(int i = 0; i < 12; i++){
            int randomIndex = (int) (Math.random() * 5);
            char randomChar = characters[randomIndex];
            randomSecretKey += randomChar;
        }

        return randomSecretKey;
    }


    /**
     * The "generateTestData" function creates a dataset with a predifined size
     * @param size - number of tests
     * @return testData - a String[] array to store all tests
     */
    public static String[] generateTestData (int size) {
        String[] testData = new String[size];

        for(int i = 0; i < size; i++){
            testData[i] = generateRandomSecretKey();
        }

        return testData;
    }
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("=".repeat(25) + "EVALUATION" + "=".repeat(25));
        System.out.println();
        
        int testSize = 2007;
        String[] testData = new String[testSize];
        testData = generateTestData(testSize);

        // Initialize variables to store the value of performance metrics
        int ODBDMAccuracy = 0;
        int ODBDMGuess = 0, ODBDMGuessTotal = 0, ODBDMGuessMin = 48, ODBDMGuessMax = -1;
        long ODBDMTime = 0, ODBDMTimeTotal = 0, ODBDMTimeMin = System.nanoTime() + 1000000000, ODBDMTimeMax = -1;

        int DBDMAccuracy = 0;
        int DBDMGuess = 0, DBDMGuessTotal = 0, DBDMGuessMin = 48, DBDMGuessMax = -1;
        long DBDMTime = 0, DBDMTimeTotal = 0, DBDMTimeMin = System.nanoTime() + 1000000000, DBDMTimeMax = -1;

        double guessAverage = 0, timeAverage = 0;
        String finalGuessKey;

        // Using for-loop to apply different methods on all tests
        for(int i = 0; i < testSize; i++){      
            SecretKey testKey = new SecretKey(testData[i]);
            
            // Optimized Digit By DIgit Modification (ODBDM)
            ODBDMTime = System.nanoTime();
            finalGuessKey = new OptimizedDigitByDigitMod().start(testKey);
            ODBDMGuess = testKey.getCounter();
            ODBDMTime = System.nanoTime() - ODBDMTime;

            if(finalGuessKey.equals(testKey.getCorrectKey())){
                ODBDMAccuracy++;

                ODBDMGuessTotal += ODBDMGuess;
                ODBDMTimeTotal += ODBDMTime;

                if(ODBDMGuess <= ODBDMGuessMin){
                    ODBDMGuessMin = ODBDMGuess;
                }
                if(ODBDMGuess >= ODBDMGuessMax){
                    ODBDMGuessMax = ODBDMGuess;
                }

                if(ODBDMTime <= ODBDMTimeMin){
                    ODBDMTimeMin = ODBDMTime;
                }
                if(ODBDMTime >= ODBDMTimeMax){
                    ODBDMTimeMax = ODBDMTime;
                
                }
            }

            // Digit By DIgit Modification (DBDM)
            testKey = new SecretKey(testData[i]);

            DBDMTime = System.nanoTime();
            finalGuessKey = new DigitByDigitMod().start(testKey);
            DBDMGuess = testKey.getCounter();
            DBDMTime = System.nanoTime() - DBDMTime;

            if(finalGuessKey.equals(testKey.getCorrectKey())){
                DBDMAccuracy++;

                DBDMGuessTotal += DBDMGuess;
                DBDMTimeTotal += DBDMTime;

                if(DBDMGuess <= DBDMGuessMin){
                    DBDMGuessMin = DBDMGuess;
                }
                if(DBDMGuess >= DBDMGuessMax){
                    DBDMGuessMax = DBDMGuess;
                }

                if(DBDMTime <= DBDMTimeMin){
                    DBDMTimeMin = DBDMTime;
                }
                if(DBDMTime >= DBDMTimeMax){
                    DBDMTimeMax = DBDMTime;
                
                }
            }
        }

        // Optimized Digit By DIgit Modification (ODBDM)
        // Accuracy Test
        System.out.println("_______ Optimized Digit By Digit Modification _______");
        System.out.println("Accuracy (2007 testKeys):");
        System.out.println("Success: " + ODBDMAccuracy);
        System.out.println("Not Pass: " + (testSize - ODBDMAccuracy));
        System.out.println();

        // Performance Test
        System.out.println("Performance (" + ODBDMAccuracy + " testKeys):");
        System.out.println("- Guess Usage Complexity:");
        guessAverage = (ODBDMGuessTotal / ODBDMAccuracy);
        System.out.println("   + Average Guesses: " + guessAverage);
        System.out.println("   + Lowest Guesses: " + ODBDMGuessMin);
        System.out.println("   + Highest Guesses: " + ODBDMGuessMax);

        System.out.println();

        System.out.println("- Time Complexity:");
        timeAverage = (ODBDMTimeTotal / ODBDMAccuracy);
        System.out.println("- Average Case: " + timeAverage + " nanoseconds");
        System.out.println("- Best Case: " + ODBDMTimeMin + " nanoseconds");
        System.out.println("- Worst Case: " + ODBDMTimeMax + " nanoseconds");

        System.out.println();
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println();

        // Digit By DIgit Modification (DBDM)
        // Accuracy Test
        System.out.println("_______ Digit By Digit Modification _______");
        System.out.println("Accuracy (2007 testKeys):");
        System.out.println("Success: " + DBDMAccuracy);
        System.out.println("Not Pass: " + (testSize - DBDMAccuracy));
        System.out.println();

        // Performance Test
        System.out.println("Performance (" + DBDMAccuracy + " testKeys):");
        System.out.println("- Guess Usage Complexity:");
        guessAverage = (DBDMGuessTotal / DBDMAccuracy);
        System.out.println("   + Average Guesses: " + guessAverage);
        System.out.println("   + Lowest Guesses: " + DBDMGuessMin);
        System.out.println("   + Highest Guesses: " + DBDMGuessMax);

        System.out.println();

        System.out.println("- Time Complexity:");
        timeAverage = (DBDMTimeTotal / DBDMAccuracy);
        System.out.println("- Average Case: " + timeAverage + " nanoseconds");
        System.out.println("- Best Case: " + DBDMTimeMin + " nanoseconds");
        System.out.println("- Worst Case: " + DBDMTimeMax + " nanoseconds");

        System.out.println();
    }
}

