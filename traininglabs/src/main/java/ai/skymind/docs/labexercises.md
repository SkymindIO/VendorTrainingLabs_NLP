# Exercises 
Each lab exercise will primarily be dependent on a prior lab exercise, such that 
we are continually progressing towards building more advanced pipelines and model artifacts.

## Data Sources 
Reference the **DataSources.md** file to download the dependencies for a particular exercise.


### Lab 1: Unstructured Data Data Ingestion
#### Objective 
Understand how to load a set of unstructured documents that may be categorized or 
just a set of files that have not been annotated, and working with **data hiding** latency
loading.  This will form the basis of how documents and their content will be retrieved.

The following will be discussed:
- Iterating through a set of batched documents 
- LabeledDocument: as a container for text 
- Categorized Labeled Documents
- Un-Categorized Documents
- Reading text from an InputStream


### Lab 2: Tokenization
#### Objective 
Tokenization is a preprocessing step to be performed as part of vectorization text content 
from a document. Raw Unstructured content should be seperated with any form of 
additional pre-processing required.  

There are times when we just want to extract the full list of tokens from some text 
and others when we need to perform some additional processing on each particular 
token, such as checking if a token is not in an existing vocabulary, and mapping 
those to a common unknown (UNK) vector.


### Lab 3: Handling Word Vectors
#### Objective 
Word Embeddings are at the core artifact Unsupervised Shallow Networks, with models such as 
Google's Word2Vec.  In this lab, first we will get familiar with an existing
pre-trained model, with aspects such as:

- Loading a Pre-trained Model
- Vocabulary, Vocabulary Cache
- VocabWord vs SequenceElement
- Embedding Dimensions
- Embedding Matrix

Word Embeddings are not just limited to being utilized from a shallow network, but 
rather as way to initialize deeper networks from more general context or domain specific.


### Lab 4: Shallow Models (Word2Vec)
#### Objective 
In this exercise, rather than using pre-trained word vectors, we will train an 
Unsupervised Shallow Network to produce our own word embeddings.  Creating our own 
wording embeddings, especially accounting for domain specific vocabulary can be 
very useful.