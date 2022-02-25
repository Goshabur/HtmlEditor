package editor;



import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {

    private HTMLDocument document;
    private View view;
    private File currentFile;

    public static void main(String[] args) {
        View view1 = new View();
        Controller controller = new Controller(view1);
        view1.setController(controller);
        view1.init();
        controller.init();
    }

    public Controller(View view) {
        this.view = view;
    }

    public void init(){
        createNewDocument();
    }

    public void exit(){
        System.exit(0);
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public void  resetDocument(){

        if(document != null){
            document.removeUndoableEditListener(view.getUndoListener());

        }
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();

    }

    public void setPlainText(String text){
        try {
            resetDocument();
            StringReader stringReader = new StringReader(text);
            HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
            htmlEditorKit.read(stringReader, document, 0);
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }
    public String getPlainText(){
        StringWriter writer = new StringWriter();
        try {
            HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
            htmlEditorKit.write(writer, document, 0,document.getLength());
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
        return writer.toString();

    }

    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }

    public void saveDocument() {
        if(currentFile == null){
            saveDocumentAs();
        }else {
            try {
                view.selectHtmlTab();
                FileWriter writer = new FileWriter(currentFile);
                new HTMLEditorKit().write(writer, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

    public void openDocument() {
        try {
            view.selectHtmlTab();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new HTMLFileFilter());
            fileChooser.setDialogTitle("Open File");
            int result = fileChooser.showOpenDialog(view);

            if (result == 0) {

                currentFile = fileChooser.getSelectedFile();
                resetDocument();
                view.setTitle(currentFile.getName());

                FileReader reader = new FileReader(currentFile);
                new HTMLEditorKit().read(reader, document, 0);
                view.resetUndo();
            }
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public void saveDocumentAs() {
        try {
            view.selectHtmlTab();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new HTMLFileFilter());
            fileChooser.setDialogTitle("Save File");
            int result = fileChooser.showSaveDialog(view);

            if (result == 0) {

                currentFile = fileChooser.getSelectedFile();
                view.setTitle(currentFile.getName());

                FileWriter writer = new FileWriter(currentFile);
                new HTMLEditorKit().write(writer, document, 0, document.getLength());
            }
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

    }
}
