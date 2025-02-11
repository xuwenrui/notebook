itext文档：https://kb.itextpdf.com/it5kb/creating-table-of-contents-using-events
```
package com.frank.itext7demo.itext7;  
  
import java.io.FileOutputStream;  
import java.util.HashMap;  
import java.util.Map;  
  
import com.itextpdf.text.Chapter;  
import com.itextpdf.text.Chunk;  
import com.itextpdf.text.Document;  
import com.itextpdf.text.DocumentException;  
import com.itextpdf.text.Font;  
import com.itextpdf.text.FontFactory;  
import com.itextpdf.text.PageSize;  
import com.itextpdf.text.Paragraph;  
import com.itextpdf.text.pdf.BaseFont;  
import com.itextpdf.text.pdf.PdfContentByte;  
import com.itextpdf.text.pdf.PdfPageEventHelper;  
import com.itextpdf.text.pdf.PdfTemplate;  
import com.itextpdf.text.pdf.PdfWriter;  
import com.itextpdf.text.pdf.draw.VerticalPositionMark;  

// https://stackoverflow.com/questions/22108090/how-to-generate-a-table-of-contents-toc-with-itext

public class GenTocDemo extends PdfPageEventHelper {  
  
  
    private  Document document;  
    private  PdfWriter writer;  
    private  BaseFont baseFont = BaseFont.createFont();  
    private  Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.NORMAL);  
  
    // table to store placeholder for all chapters and sections  
    private final Map<String, PdfTemplate> tocPlaceholder = new HashMap<String, PdfTemplate>();  
  
    // store the chapters and sections with their title here.  
    private final Map<String, Integer> pageByTitle = new HashMap<>();  
  
    public static void main(final String[] args) throws Exception {  
        final GenTocDemo main = new GenTocDemo();  
  
        main.document.add(new Paragraph("This is an example to generate a TOC."));  
        main.createTOC(10);  
        main.createChapters(10);  
        main.document.close();  
    }  
  
    public GenTocDemo() throws Exception {  
        this.document = new Document(PageSize.A6);  
        this.writer = PdfWriter.getInstance(this.document, new FileOutputStream("test.pdf"));  
        this.writer.setPageEvent(this);  
        this.document.open();  
    }  
  
    @Override  
    public void onChapter(final PdfWriter writer, final Document document, final float paragraphPosition, final Paragraph title) {  
        this.pageByTitle.put(title.getContent(), writer.getPageNumber());  
    }  
  
    @Override  
    public void onSection(final PdfWriter writer, final Document document, final float paragraphPosition, final int depth, final Paragraph title) {  
        this.pageByTitle.put(title.getContent(), writer.getPageNumber());  
    }  
  
    private void createTOC(final int count) throws DocumentException {  
        // add a small introduction chapter the shouldn't be counted.  
        final Chapter intro = new Chapter(new Paragraph("This is TOC ", this.chapterFont), 0);  
        intro.setNumberDepth(0);  
        this.document.add(intro);  
  
        for (int i = 1; i < count + 1; i++) {  
            // Write "Chapter i"  
            final String title = "Chapter " + i;  
            final Chunk chunk = new Chunk(title).setLocalGoto(title);  
            this.document.add(new Paragraph(chunk));  
  
            // Add a placeholder for the page reference  
            this.document.add(new VerticalPositionMark() {  
                @Override  
                public void draw(final PdfContentByte canvas, final float llx, final float lly, final float urx, final float ury, final float y) {  
                    final PdfTemplate createTemplate = canvas.createTemplate(50, 50);  
                    GenTocDemo.this.tocPlaceholder.put(title, createTemplate);  
  
                    canvas.addTemplate(createTemplate, urx - 50, y);  
                }  
            });  
        }  
    }  
  
    private void createChapters(final int count) throws DocumentException {  
        for (int i = 1; i < count + 1; i++) {  
            // append the chapter  
            final String title = "Chapter " + i;  
            final Chunk chunk = new Chunk(title, this.chapterFont).setLocalDestination(title);  
            final Chapter chapter = new Chapter(new Paragraph(chunk), i);  
            chapter.setNumberDepth(0);  
  
            chapter.addSection("Foobar1");  
            chapter.addSection("Foobar2");  
            this.document.add(chapter);  
  
            // When we wrote the chapter, we now the pagenumber  
            final PdfTemplate template = this.tocPlaceholder.get(title);  
            template.beginText();  
            template.setFontAndSize(this.baseFont, 12);  
            template.setTextMatrix(50 - this.baseFont.getWidthPoint(String.valueOf(this.writer.getPageNumber()), 12), 0);  
            template.showText(String.valueOf(this.writer.getPageNumber()));  
            template.endText();  
  
        }  
    }  
}
```


官方参考：
[Creating Table of Contents using events](https://kb.itextpdf.com/itext/creating-table-of-contents-using-events)
https://kb.itextpdf.com/search.html?l=en&max=10&ol=&q=TOC&s=it5kb&start=0

