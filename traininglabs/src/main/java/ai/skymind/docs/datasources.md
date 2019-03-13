# Data Sources 

## SandBox  
- A few simple files and folder structure within /resources/data to get started on small applications

## IMDB DataSet
- Execute **DownloaderApp** from within the IDE to download files
- Move them from the target/classes/ to /resources/datasets/imdb
- These can be placed anywhere, but you may find it handy to keep it here or create a softlink, 
but causes it to be scanned and indexed and packaged into the target deliverable

```bash
> cd resources/datasets/imdb
> ln -s ~/data/vendors/skymind/imdb/aclImdb aclImdb
```

### Sampling the Dataset  
During the exercises you will find it useful to downsample the dataset before 
working with the entire dataset.  This is test the functionality, not necessarily 
the performance.

In /scripts/subset_dat.py there is python script that will subset the data for you,
from a given untarred directory.  The paths assume a relative rather than an 
abolute path directory.

## Word2Vec Google News Word Vectors
- Word Vectors will be used from [Google Word2Vec](https://code.google.com/archive/p/word2vec/) 
    - Download from: [GoogleNews-vectors-negative300](https://skyminddata.blob.core.windows.net/baml/datasets/GoogleNews-vectors-negative300.bin.gz)