package edu.bit.lx.toexcel.utils;

import java.io.File;

/**
 * Created by Xiao on 2015/12/16 0016.
 */
public class directoryVistor {

    private String inDir = null;

    private String outDir = null;

    private directoryVistor() {
        this.inDir = "D:\\";
        this.outDir = ".";
    }

    public directoryVistor(String inputDir, String outputDir) {
        this.inDir = inputDir;
        this.outDir = outputDir;
    }

    public void travel() {
        File inputRoot = new File(inDir), outputRoot = new File(outDir);
        File[] files = inputRoot.listFiles();
        for (File topicRoot : files) {
            String dirName = topicRoot.getName();
            String topic = dirName.replace("topics", "");
            System.out.println(topic);
            fileVistor fv = new fileVistor(topicRoot.getAbsolutePath(),
                outputRoot.getAbsolutePath() + File.separator + topic);
            fv.transfer();
        }
    }
}
