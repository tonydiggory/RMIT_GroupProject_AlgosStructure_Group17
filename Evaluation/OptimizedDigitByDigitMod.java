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

public class OptimizedDigitByDigitMod {
    private static final int SECRET_KEY_LENGTH = 12;
    private static final String POSSIBLE_CHARACTERS = "MOCHA";

    // Variable to track the number of guesses used.


    /**
     * The "start" function uses a Depth-first guessing method to refine each position of the guessKey from left to right until the secretKey is discovered or a limit is reached.
     * This method employs a strategy of modifying the guessKey based on character frequencies and their potential implact.
     * 
     * If the secretKey is found, the method returns the number of guesses used (-1 if secretKey is unfound)
     * 
     * @param: secretKey - the secretKey in the current test
     * @return: guessKey - the final guessKey
     * 
     * @see #generateCharStorage(SecretKey, String)
     * @see #generateGuessingSequence(char, FrequencyDictionary)
     * @see #modifyGuessKeyChar(String, int, char)
     */

    public String start (SecretKey secretKey) {
        // Generate and sort the character storage (charStorage)
        FrequencyDictionary charStorage = generateCharStorage(secretKey, POSSIBLE_CHARACTERS);
        charStorage.insertionSortByValue();

        // NOTE: checking a valid position consumes 1 guesses while correcting a position consumes up to 3 guesses
        // => Create an initial guessKey by repeating the most frequent character
        char baseCharacter = charStorage.getMostFrequentChar();
        String guessKey = "" + baseCharacter;
        guessKey = guessKey.repeat(SECRET_KEY_LENGTH);

        int newCorrectPositions, correctPositions = charStorage.getFreqByChar(baseCharacter);

        // Check if the secretKey consists of 12 identical characters
        if(correctPositions != SECRET_KEY_LENGTH){
            String guessingSequence, tmpGuessKey = guessKey;
            char charModifier, previousCharacter;

            // Using for-loop to traverse each position in the guessKey to refine them
            for(int i = 0; i < SECRET_KEY_LENGTH; i++){
                // Generate the guessing order for the current position
                guessingSequence = generateGuessingSequence(baseCharacter, charStorage);

                // Using for-loop to consequently modify the current position based on the guessingSequence until reaching the correct character.
                for(int j = 0; j < guessingSequence.length(); j++){
                    charModifier = guessingSequence.charAt(j);
                    previousCharacter = guessKey.charAt(i);
                    tmpGuessKey = modifyGuessKeyChar(guessKey, i, charModifier);

                    newCorrectPositions = secretKey.guess(tmpGuessKey);

                    // There are 3 cases while modifying a position:
                    // - newCorrectPositions < correctPositions: the previous character is a correct for the examining position.
                    // - newCorrectPositions = correctPositions: both previous and current characters are incorrect for the examining position.
                    //- newCorrectPositions > correctPositions: the current character is a correct for the examining position.
                    if(newCorrectPositions < correctPositions){
                        charStorage.adjustCharFrequency(previousCharacter, correctPositions - newCorrectPositions);
                        break;
                    }else if(newCorrectPositions > correctPositions){
                        guessKey = tmpGuessKey;
                        correctPositions = newCorrectPositions;
                        charStorage.adjustCharFrequency(charModifier, correctPositions - newCorrectPositions);
                        break;
                    }
                }

                // Early terminate the program if the secretKey is found!
                if(correctPositions == 12){
                    break;
                }
            }
        }

        return guessKey;
    }


    /**
     * The "generateCharStorage" function generates a Character Storage to store possible characters along with their remaining usages.
     * NOTE: The initial remaining usage of each character is equal to its frequency in the secretKey
     * 
     * @param secretKey - the hidden sequence that the program is trying to guess
     * @param possibleCharacters - all characters that can be in the secretKey ("MOCHA")
     * @return charStorage - the FrequencyDictionary storing characters and their remaining usages
     */

    private FrequencyDictionary generateCharStorage (SecretKey secretKey, String possibleCharacters) {
        int size = possibleCharacters.length();
        FrequencyDictionary charStorage = new FrequencyDictionary(size);

        String guessKey;
        char character;
        int frequency = 0, frequencyCounter = 0;

        // Using for-loop to consequently try guessKey that contains 12 identical characters - in order: 'M', 'O', 'C', 'H'
        for(int i = 0; i < size - 1; i++){
            
            // Terminate the function and return charStorage if all 12 elements in the secretKey are found
            if(frequencyCounter == SECRET_KEY_LENGTH){
                return charStorage;
            }

            // Generate a guessKey for each character
            character = possibleCharacters.charAt(i);
            guessKey = "" + character;
            guessKey = guessKey.repeat(SECRET_KEY_LENGTH);

            // Using provided guess function to calculate the frequency of the current letter
            frequency = secretKey.guess(guessKey);

            // Update the charStorage and frequencyCounter
            charStorage.put(character, frequency);
            frequencyCounter += frequency;
        }

        // Calculate the frequency of the letter 'A'
        // NOTE: frequency of the letter A = secretKey.length - sum of frequencies of other letters
        character = possibleCharacters.charAt(size - 1);
        frequency = SECRET_KEY_LENGTH - frequencyCounter;
        charStorage.put(character, frequency);

        return charStorage;
    }


    /**
     * The "generateGuessingSequence" function generates a string representing the modification order of the current position.
     * 
     * Condition for generating the guessingSequence: 
     * - Base characters does not need to be guessed again.
     * - Characters with higher remaining usages are more likely to be correct.
     * - Characters that run out of usages do not need to be guessed.
     * 
     * TODO:
     * - Exclude base characters and characters with no remaining usages.
     * - Sort the charStorage in descending order by value to rank the characters based on remaining occurances.
     * 
     * @param baseCharacter - The character used to generate the base guessKey.
     * @param charStorage - The up-to-date character storage.
     * @return guessingSequence - The string indicating the modification order of the current position.
     */

    private String generateGuessingSequence (char baseCharacter, FrequencyDictionary charStorage) {
        String guessingSequence = "";

        FrequencyDictionary sortedCharStorage = charStorage;
        sortedCharStorage.insertionSortByValue();

        char[] characters = sortedCharStorage.getCharacters();
        int frequency;

        // Using for-loop to iterate through remaining characters to identify the suitable ones for the guessKey's current position.
        for(int i = 0; i < characters.length; i++){
            frequency = sortedCharStorage.getFreqByChar(characters[i]);

            // Exclude base character and characters with no remaining usages.
            if(characters[i] != baseCharacter && frequency > 0){
                guessingSequence += characters[i];
            }
        }

        return guessingSequence;
    }

    /**
     * The "modifyGuessKeyChar" function replaces a character at a specified position in a guessKey with a new character.
     * 
     * @param guessKey - The current guessKey that needs to be modified.
     * @param index - the position in the current guessKey where the modification is required.
     * @param newChar - The new character to replace the one at the specified position.
     * @return newGuessKey - the updated guessKey after being modified.
     */
    private String modifyGuessKeyChar (String guessKey, int index, char newChar) {
        String newGuessKey = guessKey.substring(0, index) + newChar + guessKey.substring(index + 1);
        return newGuessKey;
    }
}
