package com.test.tracker;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileSystem {

    void saveFile(String fileName, File fileToSave);

    void readFile(String fileName, File pathWhereToSave) throws FileNotFoundException;

}
