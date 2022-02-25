package editor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String str = ".html";
        if(f.isDirectory()){
            return true;
        }else if(f.getName().toLowerCase().endsWith(str) | f.getName().toLowerCase().endsWith(".htm")){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
