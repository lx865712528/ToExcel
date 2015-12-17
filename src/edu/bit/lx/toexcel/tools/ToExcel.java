package edu.bit.lx.toexcel.tools;

import edu.bit.lx.toexcel.utils.directoryVistor;

/**
 * Created by Xiao on 2015/12/16 0016.
 */
public class ToExcel {

    public static void main(String[] args) {
        directoryVistor dv = new directoryVistor("D:\\data\\history",
            "D:\\output");
        dv.travel();
    }
}
