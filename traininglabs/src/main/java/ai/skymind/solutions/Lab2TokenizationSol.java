package ai.skymind.solutions;

import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Lab2TokenizationSol {
    private static Logger log = LoggerFactory.getLogger(Lab2TokenizationSol.class);

    public static void main(String[] args) {
        List<String> tokens = new ArrayList<>();
        String sentence = "This is some sample text, to be followed by more text.  Go Warriors! <POS>";

        /** Q1: Tokenize text sentence above with a selected PreProcessor **/
        //note this does some common pre-processing: remove punctuation, lower case, ...
        TokenizerFactory tokenizerFactory1 = new DefaultTokenizerFactory();
        tokenizerFactory1.setTokenPreProcessor(new CommonPreprocessor());
        Tokenizer t1 = tokenizerFactory1.create(sentence);

        TokenizerFactory tokenizerFactory2 = new DefaultTokenizerFactory();
        tokenizerFactory2.setTokenPreProcessor(new CommonPreprocessor());
        Tokenizer t2 = tokenizerFactory2.create(sentence);

        /** Q2: Get Provided Tokens from Tokenizer
         *   Note you may find it useful to create multiple tokenizers
         *   a. get full list of tokens
         *   b. iterating via tokens one by one
         *   c. what's the difference between the two methods above, and when should you use each one
         */
        log.info("Number Tokens: {}", t1.countTokens());
        //Extract Tokens: moving cursor to end
        log.info("Tokens: {}", t1.getTokens());
        log.info("==============================: Begin Iteration");
        //Extract Tokens: one by one, if cursor is at beginning (.getTokens has not been called)
        while(t2.hasMoreTokens()) {
            String token = t2.nextToken();
            log.info("Token: {}", token);
        }
    }

}
