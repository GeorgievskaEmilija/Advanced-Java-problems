package av6.benford;

import java.io.OutputStream;
import java.io.PrintWriter;

public class CountVisualizer {
    private final int n;
    public CountVisualizer(int n){
        this.n=n;
    }
    public void visualize(OutputStream outputStream, int [] counts){
        PrintWriter printWriter=new PrintWriter(outputStream);
        for(Integer count:counts){
            while(count>0){
                printWriter.write("*");
                count-=n;
            }
            printWriter.println();
        }
        printWriter.flush();
    }
}
