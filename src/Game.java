import java.util.*;

public class Game {
    // Expanded list of possible words to guess
    private String[] words = {
        "elephant", "laptop", "mountain", "giraffe", "sunset", "notebook",
        "apple", "banana", "cherry", "strawberry", "watermelon", "pineapple",
        "kiwi", "orange", "grape", "mango", "peach", "plum", "blueberry",
        "avocado", "pear", "melon", "cantaloupe", "grapefruit", "blackberry",
        "lemon", "raspberry", "apricot", "papaya", "pomegranate", "fig",
        "coconut", "tomato", "carrot", "potato", "spinach", "lettuce", "broccoli",
        "cucumber", "onion", "garlic", "asparagus", "chocolate", "candy", "icecream",
        "cookie", "cupcake", "donut", "cake", "brownie", "pie", "pasta", "pizza",
        "burger", "sushi", "noodles", "taco", "hotdog", "sandwich", "fries", "soup",
        "steak", "fish", "shrimp", "chicken"
    };
    
    private Word word;
    private int attemptsLeft;
    private Scanner scanner = new Scanner(System.in); // Use a single Scanner instance for the entire class

    // Hanged man parts to display progressively
    private final String[] hangmanStages = {
        " _______ \n |     |\n |\n |\n |\n |\n |",  // Stage 0: No parts
        " _______ \n |     |\n |     O\n |\n |\n |\n |",  // Stage 1: Head
        " _______ \n |     |\n |     O\n |     |\n |\n |\n |",  // Stage 2: Body
        " _______ \n |     |\n |     O\n |    /|\n |\n |\n |",  // Stage 3: Left arm
        " _______ \n |     |\n |     O\n |    /|\\\n |\n |\n |",  // Stage 4: Right arm
        " _______ \n |     |\n |     O\n |    /|\\\n |    /\n |\n |",  // Stage 5: Left leg
        " _______ \n |     |\n |     O\n |    /|\\\n |    / \\ \n |\n |"  // Stage 6: Right leg (final stage)
    };

    public void start() {
        do {
            // Ask the player if they want to play with random words or enter their own
            String gameMode = getGameMode();
            setupGame(gameMode);
            
            while (attemptsLeft > 0 && !word.isGuessed()) {
                displayState();
                String guess = getPlayerInput();
                processGuess(guess);
            }
            endGame();
        } while (askToPlayAgain()); // Ask if the player wants to play again
    }

    private String getGameMode() {
        System.out.println("Do you want to play with a random word or your own word?");
        System.out.println("Type 'random' for a random word or 'custom' to enter your own word.");
        
        // Check if input is available
        while (!scanner.hasNextLine()) {
            System.out.println("No input available. Please enter 'random' or 'custom'.");
            scanner.nextLine(); // Discard any leftover input
        }

        String mode = scanner.nextLine().toLowerCase();
        
        while (!mode.equals("random") && !mode.equals("custom")) {
            System.out.println("Invalid choice. Please type 'random' or 'custom'.");
            mode = scanner.nextLine().toLowerCase();
        }
        
        return mode;
    }

    private void setupGame(String gameMode) {
        Random random = new Random();
        
        String wordToGuess;
        
        if (gameMode.equals("random")) {
            // Randomly select the correct word (no longer display the options)
            wordToGuess = words[random.nextInt(words.length)];
        } else {
            // If the player chose to enter their own word
            System.out.println("Please enter your word (letters only): ");
            
            // Check for input availability
            while (!scanner.hasNextLine()) {
                System.out.println("No input available. Please enter your word (letters only): ");
                scanner.nextLine(); // Discard any leftover input
            }

            wordToGuess = scanner.nextLine().toLowerCase();

            // Ensure the entered word only contains letters and is not empty
            while (!wordToGuess.matches("[a-zA-Z]+") || wordToGuess.isEmpty()) {
                System.out.println("Invalid word. Please enter a word with letters only: ");
                wordToGuess = scanner.nextLine().toLowerCase();
            }
        }

        // Initialize the game with the selected word
        word = new Word(wordToGuess);
        attemptsLeft = 6; // Number of incorrect guesses allowed

        // Reveal some random letters (e.g., 25% of the word length)
        int lettersToReveal = Math.max(1, wordToGuess.length() / 4); 
        word.revealLetters(lettersToReveal);
    }

    private void displayState() {
        // Display the hangman drawing corresponding to the number of incorrect attempts
        System.out.println(hangmanStages[6 - attemptsLeft]);
        
        // Display the word with guessed letters and blanks
        System.out.println("Word: " + word.getGuessedWord());
        System.out.println("Attempts left: " + attemptsLeft);
        System.out.println("Guessed letters: " + word.getGuessedLetters());
    }

    private String getPlayerInput() {
        System.out.print("Enter a letter or a word: ");
        
        // Check for input availability
        while (!scanner.hasNextLine()) {
            System.out.println("No input available. Please enter a letter or a word: ");
            scanner.nextLine(); // Discard any leftover input
        }

        return scanner.nextLine().toLowerCase(); // Ensure case-insensitive input
    }

    private void processGuess(String guess) {
        if (guess.length() == 1) {
            // Guess is a letter
            char letterGuess = guess.charAt(0);
            if (!word.processLetterGuess(letterGuess)) {
                System.out.println("Incorrect letter guess!");
                attemptsLeft--;
            }
        } else {
            // Guess is a full word
            if (!word.processWordGuess(guess)) {
                System.out.println("Incorrect word guess!");
                attemptsLeft--;
            }
        }
    }

    private void endGame() {
        if (word.isGuessed()) {
            System.out.println("Congratulations! You guessed the word: " + word.getWordToGuess());
        } else {
            System.out.println("Game Over! The word was: " + word.getWordToGuess());
        }
    }

    private boolean askToPlayAgain() {
        System.out.println("Do you want to play again? (yes/no)");
        while (!scanner.hasNextLine()) {
            System.out.println("No input available. Please enter 'yes' or 'no'.");
            scanner.nextLine(); // Discard any leftover input
        }

        String response = scanner.nextLine().toLowerCase();
        while (!response.equals("yes") && !response.equals("no")) {
            System.out.println("Invalid input. Please type 'yes' or 'no'.");
            response = scanner.nextLine().toLowerCase();
        }

        return response.equals("yes");
    }
}











