package ai.skymind.labs;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;

import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import org.nd4j.linalg.io.ClassPathResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class Lab4Word2Vec {
    private static Logger log       = LoggerFactory.getLogger(Lab4Word2Vec.class);
    private static String modelPath = "exports/word2vec.bin";

    public static void main(String[] args) throws IOException {
        String filePath = new ClassPathResource("datasets/norvig_big.txt").getFile().getAbsolutePath();
        //Common PreProcessor to apply regex pattern for basic processing (punctuation, digits, symbols, lowercase)
        TokenizerFactory t    = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());
        //Define vocabulary cache and weight lookup
        AbstractCache<VocabWord> cache     = new AbstractCache<>();
        WeightLookupTable<VocabWord> table = new InMemoryLookupTable.Builder<VocabWord>()
                                                                    .vectorLength(100)
                                                                    .useAdaGrad(false)
                                                                    .cache(cache)
                                                                    .build();

        /** Q1: Define a Sentence Iterator to be used with Word2Vec **/
        SentenceIterator iter = null;       //<FILL-IN>


        /** Q2: Add additional options to train Word2Vec model **/
        //Increase Layer Size if not all words are appearing in the corpus
        String unk   = Word2Vec.DEFAULT_UNK;
        Word2Vec vec = new Word2Vec.Builder()
                                   .seed(12345)            //for repeatability
                                                           //add additional options here <FILL-IN>
                                   .build();
        vec.fit();


        //Evaluate Word2Vec Model: on similar words
        Collection<String> similar = vec.wordsNearest("day", 10);
        log.info("10 Words closest to 'day': {}", similar);

        /** Q3: Get Word Vector Length utilizing .getWordVector
         *   a. based on a particular vocabulary word from the vocab cache
         */
        //Acquire a particular label
        String label  = null;                               //<FILL-IN>
        int vectorSize = vec.getWordVector(label).length;
        log.info("Word Vector Size: {}", vectorSize);



        /** Q4: Serialize and reload the model via WordVectorSerializer for verification **/
        //serialize model output
        if(! new File("exports").exists()) {
            File exportsDir = new File("exports");
            exportsDir.mkdir();
        }
        //<FILL-IN>

        /** Q5: Optional: Use T-SNE to Visualization - For Later Section **/


    }
}
