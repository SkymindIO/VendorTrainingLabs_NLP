package ai.skymind.solutions;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.learning.impl.elements.CBOW;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;

import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;

import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.io.ClassPathResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static ai.skymind.libs.iterators.IteratorOps.iteratorSentence;

public class Lab4Word2VecSol {
    private static Logger log       = LoggerFactory.getLogger(Lab4Word2VecSol.class);
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
        SentenceIterator iter = iteratorSentence(filePath);


        /** Q2: Add additional options to train Word2Vec model **/
        //Increase Layer Size if not all words are appearing in the corpus
        String unk   = Word2Vec.DEFAULT_UNK;
        Word2Vec vec = new Word2Vec.Builder()
                .seed(12345)            //for repeatability
                .vocabCache(cache)
                .lookupTable(table)
                .iterate(iter)
                .minWordFrequency(20)                                   //leave out sparse words
                .batchSize(256)
                .useAdaGrad(false)
                .epochs(5)                                              //decrease epochs for demo purposes
                .layerSize(300)                                         //size of word vectors
                .iterations(5)                                          //number of iterations during each mini-batch training
                .learningRate(1e-3)
                .minLearningRate(1e-4)
                //negative samples: each example only update a small portion of model weights
                .sampling(1e-3)                                         //randomly down-sampled
                .negativeSample(10)                                     //number of negative samples to affect only small portion of weights
                .minWordFrequency(25)                                   //discard words that appear less than threshold
                .windowSize(5)                                          //set skip length between words
                .elementsLearningAlgorithm(new CBOW<VocabWord>())       //Alt: GloVe, SkipGram
                .allowParallelTokenization(true)                        //enable parallel tokenizing
                .tokenizerFactory(t)
                .build();

        vec.fit();


        //Evaluate Word2Vec Model: on similar words
        Collection<String> similar = vec.wordsNearest("day", 10);
        log.info("10 Words closest to 'day': {}", similar);


        /** Q3: Get Word Vector Length utilizing .getWordVector
         *   a. based on a particular vocabulary word from the vocab cache
         */
        //Acquire a particular label
        VocabCache<VocabWord> vocabCache = vec.vocab();
        String label   = vocabCache.wordAtIndex(0);
        int vectorSize = vec.getWordVector(label).length;
        log.info("Word Vector Size: {}", vectorSize);


        /** Q4: Serialize and reload the model via WordVectorSerializer for verification **/
        //serialize model output
        if(! new File("exports").exists()) {
            File exportsDir = new File("exports");
            exportsDir.mkdir();
        }
        WordVectorSerializer.writeWord2VecModel(vec, modelPath);
        //reload model
        Word2Vec word2VecRd = WordVectorSerializer.readWord2VecModel(modelPath);
        //retrieve similar words
        Collection<String> similarVerify = word2VecRd.wordsNearest("day", 10);
        log.info("10 Words closest to 'day': {}", similarVerify);
        //lookup table
        WeightLookupTable weightLookupTable = vec.lookupTable();
        Iterator<INDArray> vectors = weightLookupTable.vectors();
        INDArray wordVectorMatrix  = vec.getWordVectorMatrix("day");
        double[] wordVector        = vec.getWordVector("day");

        /** Q5: Optional: Use T-SNE to Visualization - For Later Section **/


    }
}
