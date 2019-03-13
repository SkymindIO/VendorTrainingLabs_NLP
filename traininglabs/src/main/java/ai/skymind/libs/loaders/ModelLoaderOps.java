package ai.skymind.libs.loaders;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ModelLoaderOps {
    private static final Logger log = LoggerFactory.getLogger(ModelLoaderOps.class);

    /**
     * Serializes Model  go given path
     * @param net network to save
     * @param modelPath path to serialize (e.g. "NetworkExample.zip")
     * @throws IOException
     */
    public static void serializeModel(MultiLayerNetwork net,
                                      String modelPath) throws IOException {
        File modelFile = new File(modelPath);
        if (!modelFile.exists()) {
            modelFile.createNewFile();
        }
        log.info( modelFile.getPath() );
        ModelSerializer.writeModel(net, modelFile,true);

        //Restore Network
        //ComputationGraph  restored = ModelSerializer.restoreComputationGraph(modelFile);
        //MultiLayerNetwork restored = ModelSerializer.restoreMultiLayerNetwork(modelFile);
    }

}
