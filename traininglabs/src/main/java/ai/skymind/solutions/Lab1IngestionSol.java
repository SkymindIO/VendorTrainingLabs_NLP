package ai.skymind.solutions;

import ai.skymind.libs.iterators.IteratorOps;
import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.iterator.LabeledSentenceProvider;
import org.deeplearning4j.iterator.provider.FileLabeledSentenceProvider;
import org.deeplearning4j.iterator.provider.LabelAwareConverter;
import org.deeplearning4j.text.documentiterator.*;

import org.nd4j.linalg.io.ClassPathResource;

import org.nd4j.linalg.primitives.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ai.skymind.libs.iterators.IteratorOps.iterator;
import static java.lang.Thread.sleep;

public class Lab1IngestionSol {
    private static Logger log = LoggerFactory.getLogger(Lab1IngestionSol.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        int bufferSize    = 64;
        int subStringSize = 20;

        ClassPathResource resourceLabeled    = new ClassPathResource("data/labeled");
        ClassPathResource resourceUnlabeled  = new ClassPathResource("data/unlabeled");
        ClassPathResource resource           = new ClassPathResource("datasets/imdb/aclimdbSubset/train");
        String trainingPath = resource.getFile().getAbsolutePath();


        /** Q1: Asynchronously acquire files via FileLabelAwareIterator
         *  a. build up an iterator over a given labeled directory with subdirectories
         *  b. log information about the resource using a LabelsSource on the iterator: number of labels, label names
         */
        //Label does not correspond to output: but a given directory
        LabelAwareIterator iterLabeled       = iterator(trainingPath, IteratorOps.IteratorType.IteraratorLabeled);
        AsyncLabelAwareIterator iterAsyncDir = new AsyncLabelAwareIterator(iterLabeled, bufferSize);
        LabelsSource srcDir    = iterAsyncDir.getLabelsSource();
        List<String> labelsDir = srcDir.getLabels();
        int numLabels          = srcDir.getNumberOfLabelsUsed();
        log.info("Annotated --- Number Labels: {}, Labels: {}", numLabels, labelsDir);


        //Iterate over the LabelAware Iterator to extract a labeled document and log partial content
        iterAsyncDir.reset();
        while(iterAsyncDir.hasNext()) {
            LabelledDocument doc = iterAsyncDir.next();
            log.info("Annotated --- label:{}, content: {}", doc.getLabels(), doc.getContent().substring(0,subStringSize));
        }


        /** Q2: Create a LabeledSentenceProvider from an existing LabelAwareIterator utilizing LabelAwareConverter
         *  a. Extract the Number of labels and the labels themselves
         *  b. As an iterator, iterate through the content to acquire the document Pairing (label, text)
         */
        //iterating through pairings
        iterAsyncDir.reset();
        LabeledSentenceProvider providerConv = new LabelAwareConverter(iterAsyncDir, labelsDir);
        numLabels = providerConv.numLabelClasses();
        labelsDir = providerConv.allLabels();
        log.info("Annotated --- Num Classes: {}, Labels: {}", numLabels, labelsDir);
        while (providerConv.hasNext()) {
            Pair<String, String> docPair = providerConv.nextSentence();
            String label = docPair.getValue();
            String key   = docPair.getKey();
            log.info("Annotated --- Label: {}, Text: {}", label, key.substring(0,subStringSize));
        }



        /** Q3 Asynchronously acquire files via FilenamesLabelAwareIterator
         *  a. build up an iterator over a particular labeled directory
         *  b. log information about the resource using a LabelsSource on the iterator: number of labels, label names
         */
        Map<String, List<File>> lookup = new HashMap<>();
        for (String label : labelsDir) {
            String path = FilenameUtils.concat(trainingPath, label);
            LabelAwareIterator iterNamed          = iterator(path, IteratorOps.IteratorType.IteratorNamed);
            AsyncLabelAwareIterator iterAsyncFile = new AsyncLabelAwareIterator(iterNamed, bufferSize);
            iterAsyncFile.reset();
            sleep(1000);                                  //only for lab exercises context

            LabelsSource srcFiles    = iterNamed.getLabelsSource();
            List<String> labelsFiles = srcFiles.getLabels();;
            numLabels                = srcFiles.getNumberOfLabelsUsed();
            log.info("Files --- Number Labels: {}, Labels: {}", numLabels, labelsFiles);

            if (numLabels > 0) {
                //collect data and map to list of Files
                List<File> fileStream = labelsFiles.stream().map(x -> FilenameUtils.concat(path, x))
                        .map(File::new)
                        .collect(Collectors.toList());
                lookup.put(label, fileStream);
            }
        }


        /** Q4: Create a FileLabeledSentenceProvider from an existing HashMap created in the prior step
         *  a. Extract the Number of labels and the labels themselves
         *  b. As an iterator, iterate through the content to acquire the document Pairing (label, text)
         */
        //Alternatively from a Given List of Files (.listFiles), create a FileLabeledSentenceProvider
        LabeledSentenceProvider provider = new FileLabeledSentenceProvider(lookup);
        List<String> labelsFiles = provider.allLabels();
        numLabels                = provider.numLabelClasses();
        log.info("Num Classes: {}, Labels: {}", numLabels, labelsFiles);
        while (provider.hasNext()) {
            Pair<String, String> docPair = provider.nextSentence();
            String text  = docPair.getFirst();
            String label = docPair.getSecond();
            log.info("Files --- Label: {}, Text: {}", label, text);
        }


        /** Q5: Read  entire stream of files (e.g. 10)
         *  a. Create a FileDocumentIterator
         *  b. Iterate through to acquire document context as a Java InputStream and log some content
         */
        int numDocs = 10;
        //read an entire stream
        FileDocumentIterator iteratorDoc    = new FileDocumentIterator(resource.getFile());
        while (iteratorDoc.hasNext() && numDocs > 0) {
            //Acquire the next document as an input stream
            InputStream inputStream = iteratorDoc.nextDocument();
            byte[] data             = new byte[1024];
            int    bytesRead        = inputStream.read(data);
            //action: do some work with the content
            log.info("Sub Document: {}, Bytes Read: {}",
                    new String(data, "UTF-8").substring(0, subStringSize), bytesRead);
            inputStream.close();
            numDocs--;
        }

    }
}
