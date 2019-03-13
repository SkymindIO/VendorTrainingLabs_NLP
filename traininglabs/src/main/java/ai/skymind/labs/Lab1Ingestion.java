package ai.skymind.labs;

import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.iterator.LabeledSentenceProvider;
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

import static java.lang.Thread.sleep;

public class Lab1Ingestion {
    private static Logger log = LoggerFactory.getLogger(Lab1Ingestion.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        int bufferSize    = 64;
        int subStringSize = 20;

        ClassPathResource resourceLabeled    = new ClassPathResource("data/labeled");
        ClassPathResource resourceUnlabeled  = new ClassPathResource("data/unlabeled");
        ClassPathResource resource           = new ClassPathResource("datasets/imdb/aclimdb/train");
        String trainingPath = resource.getFile().getAbsolutePath();


        /** Q1: Asynchronously acquire files via FileLabelAwareIterator
         *  a. build up an iterator over a given labeled directory with subdirectories
         *  b. log information about the resource using a LabelsSource on the iterator: number of labels, label names
         */
        //Label does not correspond to output: but a given directory
        LabelAwareIterator iterLabeled = null;          //<FILL-IN>
        AsyncLabelAwareIterator iterAsyncDir = new AsyncLabelAwareIterator(iterLabeled, bufferSize);
        LabelsSource srcDir    = iterAsyncDir.getLabelsSource();
        List<String> labelsDir = null;                  //<FILL-IN>
        int numLabels          = 0;                     //<FILL-IN>
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
        LabeledSentenceProvider providerConv = null;    //<FILL-IN>
        numLabels = 0;                                  //<FILL-IN>
        labelsDir = null;                               //<FILL-IN>
        log.info("Annotated --- Num Classes: {}, Labels: {}", numLabels, labelsDir);
        while (providerConv.hasNext()) {
            Pair<String, String> docPair = null;        //<FILL-IN>
            String label = null;                        //<FILL-IN>
            String key   = null;                        //<FILL-IN>
            log.info("Annotated --- Label: {}, Text: {}", label, key.substring(0,subStringSize));
        }



        /** Q3 Asynchronously acquire files via FilenamesLabelAwareIterator
         *  a. build up an iterator over a particular labeled directory
         *  b. log information about the resource using a LabelsSource on the iterator: number of labels, label names
         */
        Map<String, List<File>> lookup = new HashMap<>();
        for (String label : labelsDir) {
            String path = FilenameUtils.concat(trainingPath, label);
            LabelAwareIterator iterNamed          = null;       //<FILL-IN>
            AsyncLabelAwareIterator iterAsyncFile = new AsyncLabelAwareIterator(iterNamed, bufferSize);
            iterAsyncFile.reset();
            sleep(1000);                                  //only for lab exercises context

            LabelsSource srcFiles    = null;                    //<FILL-IN>
            List<String> labelsFiles = null;                    //<FILL-IN>
            numLabels = 0;                                      //<FILL-IN>
            log.info("Files --- Number Labels: {}, Labels: {}", numLabels, labelsFiles);

            //collect data and map to list of Files
            List<File> fileStream = labelsFiles.stream().map(x -> FilenameUtils.concat(path, x))
                    .map(File::new)
                    .collect(Collectors.toList());
            lookup.put(label, fileStream);
        }


        /** Q4: Create a FileLabeledSentenceProvider from an existing HashMap created in the prior step
         *  a. Extract the Number of labels and the labels themselves
         *  b. As an iterator, iterate through the content to acquire the document Pairing (label, text)
         */
        //Alternatively from a Given List of Files (.listFiles), create a FileLabeledSentenceProvider
        LabeledSentenceProvider provider = null;                //<FILL-IN>
        List<String> labelsFiles = null;                        //<FILL-IN>
        numLabels = 0;                                          //<FILL-IN>
        log.info("Num Classes: {}, Labels: {}", numLabels, labelsFiles);
        while (provider.hasNext()) {
            Pair<String, String> docPair = null;                //<FILL-IN>
            String text  = null;                                //<FILL-IN>
            String label = null;                                //<FILL-IN>
            log.info("Files --- Label: {}, Text: {}", label, text);
        }


        /** Q5: Read  entire stream of files (e.g. 10)
         *  a. Create a FileDocumentIterator
         *  b. Iterate through to acquire document context as a Java InputStream and log some content
         */
        int numDocs = 10;
        //read an entire stream
        FileDocumentIterator iteratorDoc    = null;             //<FILL-IN>
        while (iteratorDoc.hasNext() && numDocs > 0) {
            //Acquire the next document as an input stream
            InputStream inputStream = null;                     //<FILL-IN>
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
