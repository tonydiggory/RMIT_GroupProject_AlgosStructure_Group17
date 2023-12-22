/*
 * RMIT University Vietnam
 * Course: COSC2658 - Data Structures and Algorithms
 * Semester: 2023C
 * Assessment: Group Project
 * Author - ID:  Nguyen Thien Co  -  s3938338
 *               Vo Minh Thien An -  s3916570
 * Acknowledgement: examples on the course's Microsoft Team page (Lecture_sessions/Assessment/Group Project/SecretKey.java)
 *
 * NOTE: To enhance convenience during evaluation process such as retrieving guessCounter or generating random SecretKey, slight changes have been made in comparison with the corresponding file in "SourceCode" folder.
 * 
 * NOTE:
 * - You are not allowed to use code copied from the Internet.
 * - a sample SecretKey implementation - don't need to change anything
 * - Your program must try to make this counter as small as possible when it finds out the correct secret key
 */

package Group_17_Assessment3_GroupProject.Evaluation;

public class SecretKey {
    private String correctKey;
    private int counter;

    public SecretKey(String key) {
        // for the real test, your program will not know this
        correctKey = key;
        counter = 0;
    }

    public String getCorrectKey() {
        return correctKey;
    }


    public int getCounter() {
        return counter;
    }

    public int guess(String guessedKey) {
        counter++;
        // validation
        if (guessedKey.length() != correctKey.length()) {
            return -1;
        }
        int matched = 0;
        for (int i = 0; i < guessedKey.length(); i++) {
            char c = guessedKey.charAt(i);
            if (c != 'M' && c != 'O' && c != 'C' && c != 'H' && c != 'A') {
                return -1;
            }
            if (c == correctKey.charAt(i)) {
                matched++;
            }
        }
        
        return matched;
    }
}

