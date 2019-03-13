package ai.skymind.labs;

import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Lab2Tokenization {
    private static Logger log = LoggerFactory.getLogger(Lab2Tokenization.class);

    public static void main(String[] args) {
        List<String> tokens = new ArrayList<>();
        String sentence = "This is some sample text, to be followed by more text.  Go Warriors! <POS>";

        /** Q1: Tokenize text sentence above with a selected PreProcessor **/
        TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
        Tokenizer t = null;                 //<FILL-IN>
        log.info("Number Tokens: {}", t.countTokens());


        /** Q2: Get Provided Tokens from Tokenizer
         *   Note you may find it useful to create multiple tokenizers
         *   a. get full list of tokens
         *   b. iterating via tokens one by one
         *   c. what's the difference between the two methods above, and when should you use each one
         */

    }
}
