import java.util.*;

public class Word {
    private String wordToGuess;
    private char[] guessedWord;
    private Set<Character> guessedLetters;

    public Word(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.guessedWord = new char[wordToGuess.length()];
        Arrays.fill(guessedWord, '_');
        this.guessedLetters = new HashSet<>();
    }

    // Reveal some random letters in the word
    public void revealLetters(int numberOfLetters) {
        Random random = new Random();
        for (int i = 0; i < numberOfLetters; i++) {
            int index;
            do {
                index = random.nextInt(wordToGuess.length());
            } while (guessedWord[index] != '_'); // Ensure a unique index
            char letterToReveal = wordToGuess.charAt(index);
            guessedWord[index] = letterToReveal;
            guessedLetters.add(letterToReveal); // Mark the letter as already guessed
        }
    }

    // Process the letter guess
    public boolean processLetterGuess(char guess) {
        if (guessedLetters.contains(guess)) {
            return false; // Letter already guessed
        }
        guessedLetters.add(guess);

        boolean correct = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess) {
                guessedWord[i] = guess;
                correct = true;
            }
        }
        return correct;
    }

    // Process the word guess
    public boolean processWordGuess(String guess) {
        if (guess.equals(wordToGuess)) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                guessedWord[i] = wordToGuess.charAt(i);
            }
            return true; // Correct word guessed
        }
        return false; // Incorrect word guessed
    }

    public boolean isGuessed() {
        return wordToGuess.equals(String.valueOf(guessedWord));
    }

    public String getGuessedWord() {
        return String.valueOf(guessedWord);
    }

    public Set<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}





