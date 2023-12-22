/*
 * RMIT University Vietnam
 * Course: COSC2658 - Data Structures and Algorithms
 * Semester: 2023C
 * Assessment: Group Project
 * Author - ID:  Nguyen Thien Co  -  s3938338
 *
 * NOTE:
 * - You are not allowed to use code copied from the Internet.
 * - Your program must try to make this counter as small as possible when it finds out the correct secret key
 */

package Group_17_Assessment3_GroupProject.SourceCode;

/*
 * The FrequencyDictionary is a data structure that uses char and int arrays to character-frequency pairs
 * 
 * The FrequencyDictionary provides methods to manipulate data, including:
 * - Adding entries, retrieving all characters, retrieving frequency by character, adjusting frequency
 * - Finding the most frequent character
 * - Generating a string representing all data within the FrequencyDictionary
 * - Sorting entries by frequency
 */
public class FrequencyDictionary {
    private static final int DEFAULT_CAPACITY = 5; // number of unique letters in "MOCHA"

    private char[] characters;
    private int[] frequencies;
    private int size;

    /**
     * This constructor creates a FrequencyDictionary with the default capacity.
     */
    public FrequencyDictionary () {
        this(DEFAULT_CAPACITY);
    }


    /**
     * This constructor creates a Frequency with the predefined capacity.
     * 
     * @param capacity - the initial capacity of the FrequencyDictionary.
     */
    public FrequencyDictionary (int capacity) {
        characters = new char[capacity];
        frequencies = new int[capacity];
        size = 0;
    }


    /**
     * The "put" method adds a character-frequency pair to the FrequencyDictionary.
     * 
     * @param character - The character to be added.
     * @param frequency - The frequency of the character.
     */
    public void put (char character, int frequency) {
        characters[size] = character;
        frequencies[size] = frequency;
        size++;
    }


    /**
     * The "getCharacters" method returns a char array containing all stored characters.
     * @return characters - an array of characters
     */
    public char[] getCharacters () {
        return characters;
    }


    /**
     * The "getFreqByChar" method returns the frequency of a specified character in the FrequencyDictionary.
     * @param character - The character to retrieve the frequency for.
     * @return The frequency of the specified character (-1 if the character is not found).
     */
    public int getFreqByChar (char character) {
        for(int i = 0; i < size; i++){
            if(characters[i] == character){
                return frequencies[i];
            }
        }
        return -1;
    }


    /**
     * The "adjustCharFrequency" method adjusts the frequency of a specified character by a given amount.
     * @param character - The character to adjust the frequency for.
     * @param changeAmount - The amount by which to adjust the character's frequency.
     */
    public void adjustCharFrequency (char character, int changeAmount) {
        for(int i = 0; i < size; i++){
            if(characters[i] == character){
                frequencies[i] += changeAmount;
            }
        }
    }


    /**
     * The "getMostFrequentChar" method returns the first character with highest frequencies in the FrequencyDictionary.
     * @return The first most frequent character
     */
    public char getMostFrequentChar () {
        int mostFrequentCharIdx = 0;

        for(int i = 1; i < size; i++){
            if(frequencies[mostFrequentCharIdx] < frequencies[i]){
                mostFrequentCharIdx = i;
            }
        }

        return characters[mostFrequentCharIdx];
    }


    /**
     * The "insertionSortByValue" method sorts the FrequencyDictionary in descending order based on frequency using Insertion Sort algorithm.
     * 
     * NOTE: FrequencyDictionary usually stores a small number of records.
     * => Insertion Sort is effective enough to complete the sort.
     */
    public void insertionSortByValue () {
        int freqKey;
        char charKey;
        
        for(int i = 0; i < size; i++){
            freqKey = frequencies[i];
            charKey = characters[i];
            int j = i - 1;

            while(j >= 0 && frequencies[j] < freqKey){
                frequencies[j + 1] = frequencies[j];
                characters[j + 1] = characters[j];
                j -= 1;
            }

            frequencies[j + 1] = freqKey;
            characters[j + 1] = charKey;
        }
    }

    /*
     * The "toString" function returns a String that represents the FrequencyDictionary
     * 
     * @return dict - a String that represents the FrequencyDictionary
     */
    public String toString () {
        String dict = "";
        if(size > 0){
            for(int i = 0; i < size; i++){
                dict += characters[i] + " : " + frequencies[i];
                if(i != size - 1){
                    dict += "\n";
                }
            }
        }else{
            dict = "Your dictionary has 0 element!";
        }
        return dict;
    }
}
