package ai.skymind.apps;

import org.nd4j.linalg.io.ClassPathResource;
import org.apache.commons.io.FilenameUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ai.skymind.libs.loaders.DataLoaderOps.downloadFile;
import static ai.skymind.libs.loaders.DataLoaderOps.extractTarGz;

public class DownloaderApp {
    private static final Logger log = LoggerFactory.getLogger(DownloaderApp.class);

    public static void main(String[] args) throws IOException {
        log.info("Downloading Data...");
        //System.getProperty("java.io.tmpdir");
        //String scratchDir = new ClassPathResource("datasets").getFile().getAbsolutePath();
        String scratchDir   = FilenameUtils.concat(System.getProperty("user.home"),
                                   "data/vendors/skymind/");
        String basePath     = FilenameUtils.concat(scratchDir, "imdb");
        String dataUrl      = "http://ai.stanford.edu/~amaas/data/sentiment/aclImdb_v1.tar.gz";

        String localFilePath = basePath + "/aclImdb_v1.tar.gz";
        if (downloadFile(dataUrl, localFilePath)) {
            log.info("Data downloaded from {}", dataUrl);
        }

        extractTarGz(localFilePath, basePath);
        log.info("Base Path: {} Archive Path:{}", basePath, localFilePath);
    }
}
