package ai.skymind.solutions;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.sequencevectors.sequence.SequenceElement;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;


import org.apache.commons.io.FilenameUtils;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;

public class Lab3WordVectorsSol {
    private static Logger log = LoggerFactory.getLogger(Lab3WordVectorsSol.class);

    //Pre-Trained Word Vectors: https://code.google.com/archive/p/word2vec/
    private static final String WORD_VECTORS_PATH    = "data/vendors/skymind/wordvectors";
    private static final String WORD_VECTORS_ARCHIVE = "GoogleNews-vectors-negative300.bin";
    private WordVectors wordVectors;

    public static void main(String[] args) {

        String wordVectorsPath =
                FilenameUtils.concat(System.getProperty("user.home"),
                FilenameUtils.concat(WORD_VECTORS_PATH, WORD_VECTORS_ARCHIVE) );
        log.info("Word Vectors Path: {}", wordVectorsPath);
        WordVectors wordVectors = WordVectorSerializer.loadStaticModel(new File(wordVectorsPath));
        VocabCache vocabCache   = wordVectors.vocab();


        /** Q1: Log some information about the pre-trained WordVectors that was loaded
         *   a. Unknown Vectors
         *   b. Number of Tokens from the vocabulary
         *   c. A particular sequence element (e.g. VocabWord)
         **/
        SequenceElement sequenceElement = vocabCache.elementAtIndex(5);
        //Alternatively UNK during training becomes average of all unknown words, not the same as setting as UNK
        //wordVectors.setUNK("UNK");
        log.info("Unknown: {}", wordVectors.getUNK());
        log.info("Number Tokens: {}, Size: {}", vocabCache.numWords(), vocabCache.tokens().size());
        log.info("Sequence Element Vocab: {}",  sequenceElement);
        //Collection<VocabWord> items = vocabCache.tokens();


        /** Q2:  Retrieve the Vector Matrix for a given word (token)
         *   a.  Non-Normalized
         *   b.  Normalized
         */
        String exampleWord  = "day";
        INDArray vector     = wordVectors.getWordVectorMatrix(exampleWord);
        INDArray vectorNorm = wordVectors.getWordVectorMatrixNormalized(exampleWord);
        log.info("Shape: {},  Word: {}, Vector: {}", vector.shape(), exampleWord, vector);
        log.info("Shape: {},  Word: {}, Vector: {}", vectorNorm.shape(), exampleWord, vectorNorm);


        /** Q3: Get Word Vector Length utilizing .getWordVector
         *   a. based on a particular vocabulary word from the vocab cache
         */
        //Acquire a particular label
        String label   = vocabCache.wordAtIndex(0);
        int vectorSize = wordVectors.getWordVector(label).length;
        log.info("Word Vector Size: {}", vectorSize);
    }

}
