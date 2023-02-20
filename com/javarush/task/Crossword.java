package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

/*
Кроссворд
*/

public class Crossword {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'n', 'j', 'j'}
        };
        detectAllWords(crossword, "home", "same");
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> result = new ArrayList<>();
        Word cache;

        for (int i = 0; i < crossword.length; i++) {
            for (int j = 0; j < crossword[0].length; j++) {
                if (checkChar((char) crossword[i][j], words))
                    if ((cache = check(i, j, crossword, words)) != null)
                        result.add(cache);
            }
        }
        for (Word word : result) {
            System.out.println(word);
        }

        return result;
    }

    private static boolean checkChar(char c, String... words){
        //just checks whether char in current tile is used in words we are looking for
        for (String word : words){
            if (word.startsWith(""+c))
                return true;
        }
        return false;
    }

    private static Word check(int iOrig, int jOrig, int[][] crossword, String... words){
        Word cache;

        for (String word : words){
            int c = word.getBytes()[1];
            int length = word.length() - 1;
            if (word.startsWith(""+(char) crossword[iOrig][jOrig])){
                // potryasayusche vsratiy algoritm proverki
                // checks direction, in which second char is placed, sending that direction to method checkWord
                // checking begins only if there are more tiles to search, than the word's length
                // horisontal
                if ( (jOrig + length) <= crossword[0].length && crossword[iOrig][jOrig+1] == c )
                    if ((cache = checkWord(1,0,iOrig,jOrig,crossword,word)) != null)
                        return cache;
                if ( (jOrig - length) >= 0 && crossword[iOrig][jOrig-1] == c )
                    if ((cache = checkWord(-1,0,iOrig,jOrig,crossword,word)) != null)
                        return cache;
                // vertical
                if ( (iOrig + length) < crossword.length && crossword[iOrig+1][jOrig] == c )
                    if ((cache = checkWord(0,1,iOrig,jOrig,crossword,word)) != null)
                        return cache;
                if ( (iOrig - length) >= 0 && crossword[iOrig-1][jOrig] == c )
                    if ((cache = checkWord(0,-1,iOrig,jOrig,crossword,word)) != null)
                        return cache;
                // diagonal
                if ( (jOrig + length) <= crossword[0].length && (iOrig + length) < crossword.length &&
                        crossword[iOrig+1][jOrig+1] == c )
                    if ((cache = checkWord(1,1,iOrig,jOrig,crossword,word)) != null)
                        return cache;
                if ( (jOrig - length) >= 0 && (iOrig - length) >= 0 &&
                        crossword[iOrig-1][jOrig-1] == c )
                    if ((cache = checkWord(-1,-1,iOrig,jOrig,crossword,word)) != null)
                        return cache;
                if ( (jOrig + length) <= crossword[0].length && (iOrig - length) >= 0 &&
                        crossword[iOrig-1][jOrig+1] == c )
                    if ((cache = checkWord(1,-1,iOrig,jOrig,crossword,word)) != null)
                        return cache;
                if ( (jOrig - length) >= 0 && (iOrig + length) <= crossword.length-1 &&
                        crossword[iOrig+1][jOrig-1] == c )
                    if ((cache = checkWord(-1,1,iOrig,jOrig,crossword,word)) != null)
                        return cache;
            }
        }
        return null;
    }

    private static Word checkWord(int dH, int dV, int iOrig, int jOrig, int[][] crossword, String word){
        // checks every word char-by-char in array, if every char is found = returns a Word with defined start and end points
        Word found;
        int i = iOrig - dV;
        int j = jOrig - dH;

        for (char c : word.toCharArray()){
            if (!(crossword[i + dV][j + dH] == c))
                return null;
            i += dV;
            j += dH;
        }
        found = new Word(word);
        found.setStartPoint(jOrig, iOrig);
        found.setEndPoint(j, i);

        return found;
    }


    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}
