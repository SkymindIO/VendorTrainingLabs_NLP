import os
import shutil
import glob 

if __name__ == "__main__":
    # relative path directory
    num_articles = 50
    src_path, dest_path = "imdb/aclImdb", "imdb/aclImdbSubset"
    partitions, labels  = ["train", "test"], ["pos", "neg", "unsup"]

    # copy files 
    for partition in partitions:
        for label in labels:
            path  = os.path.join(partition, label)
            spath = os.path.join(src_path,  path)
            dpath = os.path.join(dest_path, path)
            sfiles = glob.glob(spath + '/*')[:num_articles]
            if not os.path.exists(dpath): os.makedirs(dpath)
            # copy subset of files 
            for fname in sfiles: shutil.copy(fname, dpath)
            # check number of files in path 
            dfiles = glob.glob(dpath + '/*')[:num_articles]
            print("Path: {}, Num Files: {}".format(dpath, len(dfiles)))