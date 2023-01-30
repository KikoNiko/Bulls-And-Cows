package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Game {

    static Scanner scanner = new Scanner(System.in);
    static int codeLength;
    static int symbolsRange;
    static StringBuilder secCode;

    public static void main(String[] args) {

        codeInfo();

        generateCode();

        userGuess(secCode);

    }

    private static void userGuess(StringBuilder secCode) {

        //Start guessing
        System.out.println("Okay, let's start a game!");
        Scanner scanner = new Scanner(System.in);

        boolean playing = true;
        int turn = 1;

        while (playing) {
            System.out.printf("Turn %d: ", turn);
            String userGuess = scanner.nextLine();

            int bullCount = 0;
            int cowCount = 0;

            for (int i = 0; i < userGuess.length(); i++) {
                if (secCode.charAt(i) == userGuess.charAt(i)) {
                    bullCount ++;
                } else if (secCode.indexOf(String.valueOf(userGuess.charAt(i))) != -1) {
                    cowCount ++;
                }
            }

            //Print results
            System.out.print("Grade: ");
            System.out.print(bullCount == 1 && cowCount == 1 ?
                    "1 bull and 1 cow" : bullCount > 1 && cowCount == 1 ?
                    bullCount + " bulls and 1 cow" : cowCount > 1 && bullCount == 1 ?
                    "1 bull and " + cowCount + " cows" : bullCount == 0 && cowCount == 1 ?
                    "1 cow" : bullCount == 1 && cowCount == 0 ? "1 bull" :
                    bullCount > 1 && cowCount == 0 ? bullCount + " bulls" :
                            cowCount > 1 && bullCount == 0 ? cowCount + " cows" :
                                    bullCount > 1 && cowCount > 1 ?
                                            bullCount + " bulls and " + cowCount + " cows" : "None");
            System.out.println();
            turn++;

            if (bullCount == codeLength) {
                System.out.println("Congratulations! You guessed the secret code!");
                playing = false;
            }
        }

    }

    private static void generateCode() {
        //Generate secret code
        Random random = new Random();
        secCode = new StringBuilder(codeLength);
        String[] allowedChars = "0123456789abcdefghijklmnopqrstuvwxyz".split("");

        while (secCode.length() < codeLength) {
            int randomIndex = random.nextInt(symbolsRange);
            String digit = allowedChars[randomIndex];
            if (secCode.indexOf(digit) == -1) {
                secCode.append(digit);
            }
        }

        String secretAsStars = "*".repeat(codeLength);
        String symbols;
        if (symbolsRange <= 10) {
            symbols = "0-" + allowedChars[symbolsRange - 1];
        } else {
            symbols = "0-9, a-" + allowedChars[symbolsRange - 1];
        }
        System.out.printf("The secret is prepared: %s (%s). \n", secretAsStars, symbols);
        System.out.println(secCode);
    }

    private static void codeInfo() {

        System.out.println("Enter the length of the secret code: ");
        if (scanner.hasNextInt()) {
            codeLength = scanner.nextInt();
        } else {
            String userInput = scanner.next();
            System.out.printf("Error: %s isn't a valid number.", userInput);
            System.exit(1);
        }

        if (codeLength > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d " +
                    "because there aren't enough unique digits. \n", codeLength);
            System.exit(1);
        } else if (codeLength < 1) {
            System.out.println("Error: secret code's length cannot be less than 1");
            System.exit(1);
        }

        System.out.println("Enter number of possible symbols in the code: ");
        symbolsRange = scanner.nextInt();

        if (symbolsRange > 36) {
            System.out.println("Error: maximum number of possible symbols is 36 (0-9, a-z)");
            System.exit(1);
        }
        if (symbolsRange < codeLength) {
            System.out.printf("Error: it's not possible to generate a code " +
                    "with length of %d with %d unique symbols.", codeLength, symbolsRange);
            System.exit(1);
        }

    }


}



