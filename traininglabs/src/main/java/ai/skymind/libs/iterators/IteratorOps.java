package ai.skymind.libs.iterators;

import org.deeplearning4j.text.documentiterator.*;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IteratorOps {
    private static Logger log = LoggerFactory.getLogger(IteratorOps.class);

    public enum IteratorType {
        IteraratorLabeled,
        IteratorNamed
    }

    /**
     * Read Stream from a given Document Iterator
     *
     * @param iteratorIn DocumentIterator
     * @throws IOException
     */
    public static void readStream(FileDocumentIterator iteratorIn, int numDocs)
            throws IOException {

        while (iteratorIn.hasNext() && numDocs > 0) {
            InputStream inputStream = iteratorIn.nextDocument();
            byte[] data      = new byte[1024];
            int    bytesRead = inputStream.read(data);
            //do some work with the content
            log.info("Sub Document: {}, Bytes Read: {}",
                     new String(data, "UTF-8").substring(0, bytesRead), bytesRead);
            inputStream.close();
            numDocs--;
        }
    }

    /**
     * Iterate over Files/Documents
     * Document Iterators: https://deeplearning4j.org/api/latest/org/deeplearning4j/text/documentiterator/FileDocumentIterator.html
     * Sentence Iterators: https://deeplearning4j.org/api/latest/org/deeplearning4j/text/sentenceiterator/SentenceIterator.html
     *
     * @param path Path to associate with
     * @param type type of iterator to associate with
     * @return LabelAwareIterator
     * @throws FileNotFoundException
     */
    public static LabelAwareIterator iterator(String path, IteratorType type)
            throws FileNotFoundException {

        LabelAwareIterator iter = null;

        //java.util.Iterator<LabelledDocument>
        //LabelAwareIterator -> FileLabelAwareIterator
        //FileLabelAwareIterator: (Level subfolder1, Level subfolder 2): (Sub)Directory Aware
        if (type == IteratorType.IteraratorLabeled) {
            iter = new FileLabelAwareIterator.Builder()
                    .addSourceFolder(new File(path))
                    .build();
        }

        //FilenamesLabelAwareIterator
        else if (type == IteratorType.IteratorNamed) {
            iter = new FilenamesLabelAwareIterator.Builder()
                    .addSourceFolder(new File(path))
                    .useAbsolutePathAsLabel(false)
                    .build();
        }
        return iter;
    }


    /**
     * Iterate over sentences and pre-process them
     * There are many types of Iterators, e.g. BasicLineIterator, LineSentenceIterator
     *
     * Sentence Iterators: https://deeplearning4j.org/api/latest/org/deeplearning4j/text/sentenceiterator/SentenceIterator.html
     *
     * @param path path to Associate
     * @return SentenceIterator
     * @throws FileNotFoundException
     */
    public static SentenceIterator iteratorSentence(String path)
            throws FileNotFoundException {
        //Strip white space before and after for each line
        SentenceIterator iter  = new BasicLineIterator(path);
        //Alternatively CommonPreProcessor instead of SentencePreProcessor
        /**
         * CommonPreProcessor:
         * following regex to each token: [\d\.:,"'\(\)\[\]|/?!;]+
         * lower cases tokens
         * all numbers, punctuation symbols and some special symbols are stripped off.
         */
        iter.setPreProcessor(new SentencePreProcessor() {
            @Override
            public String preProcess(String sentence) {
                return sentence.toLowerCase();
            }
        });
        return iter;
    }


    /**
     * Iterate through documents and Extract Information from them
     * @param iteratorIn LabelAwareIterator
     */
    public static void logLabelIterator(LabelAwareIterator iteratorIn) {
        while (iteratorIn.hasNext()) {
            LabelledDocument document = iteratorIn.nextDocument();
            log.info("Document: {}, Category: {}", document.getContent().substring(0,10),  document.getLabels());
        }
    }

}
