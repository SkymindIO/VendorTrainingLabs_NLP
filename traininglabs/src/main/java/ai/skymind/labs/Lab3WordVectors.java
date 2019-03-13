package ai.skymind.labs;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.nd4j.linalg.api.ndarray.INDArray;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Lab3WordVectors {
    private static Logger log = LoggerFactory.getLogger(Lab3WordVectors.class);

    //Pre-Trained Word Vectors: https://code.google.com/archive/p/word2vec/
    private static final String WORD_VECTORS_PATH    = "data/vendors/skymind/wordvectors";
    private static final String WORD_VECTORS_ARCHIVE = "GoogleNews-vectors-negative300.bin";

    private WordVectors wordVectors;

    public static void main(String[] args) {
        //Load the WordVectors: change the path above if the the location is different
        String wordVectorsPath =
                FilenameUtils.concat(System.getProperty("user.home"),
                        FilenameUtils.concat(WORD_VECTORS_PATH, WORD_VECTORS_ARCHIVE) );
        log.info("Word Vectors Path: {}", wordVectorsPath);
        WordVectors wordVectors = WordVectorSerializer.loadStaticModel(new File(wordVectorsPath));
        VocabCache vocabCache   = wordVectors.vocab();


        /** Q1: Log some information about the pre-trained WordVectors that was loaded
         *   a. Unknown Vectors
         *   b. Number of Tokens from the vocabulary
         *   c. A particular sequence element
         **/


        /** Q2:  Retrieve the Vector Matrix for a given word (token)
         *   a.  Non-Normalized
         *   b.  Normalized
         */
        INDArray vector     = null;         //<FILL-IN>
        INDArray vectorNorm = null;         //<FILL-IN>
        String exampleWord  = "day";
        log.info("Shape: {},  Word: {}, Vector: {}", vector.shape(), exampleWord, vector);
        log.info("Shape: {},  Word: {}, Vector: {}", vectorNorm.shape(), exampleWord, vectorNorm);


        /** Q3: Get Word Vector Length utilizing .getWordVector
         *   a. based on a particular vocabulary word from the vocab cache
         */
        //Acquire a particular label
        String label  = null;               //<FILL-IN>
        int vectorSize = wordVectors.getWordVector(label).length;
        log.info("Word Vector Size: {}", vectorSize);


    }
}
