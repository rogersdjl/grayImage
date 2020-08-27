package com.djl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public interface FileResolve {

    void resolve(FileInputStream file) throws IOException;

}
