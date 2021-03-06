package agh.cs.lab;

import org.junit.jupiter.api.Test;

import javax.xml.soap.Text;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextParserTests {

    @Test
    public void NumberOfTextPartUokikTest() throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("uokik.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        }

        TextPart textPart = new TextPart(TextPartType.Root, text);
        LinkedHashMap<String, TextPart> children = textPart.getAllChildren();

        int a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Section, textPart);
        assertEquals(10, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Chapter, textPart);
        assertEquals(19, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Title, textPart);
        assertEquals(0, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Article, textPart);
        assertEquals(175, a);
    }

    @Test
    public void NumberOfTextPartKonstytucjaTest() throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("konstytucja.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        }

        TextPart textPart = new TextPart(TextPartType.Root, text);
        LinkedHashMap<String, TextPart> children = textPart.getAllChildren();

        int a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Section, textPart);
        assertEquals(0, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Chapter, textPart);
        assertEquals(13, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Title, textPart);
        assertEquals(16, a);

        a = countAllTextPartsWithSpecifiedTextPartType(TextPartType.Article, textPart);
        assertEquals(243, a);
    }


    private int countAllTextPartsWithSpecifiedTextPartType(TextPartType textPartType, TextPart root) {
        if (textPartType == TextPartType.END) return 0;

        int a = 0;
        for (TextPart textPart : root.getAllChildren().values()) {
            a += countAllTextPartsWithSpecifiedTextPartType(textPartType, textPart);
        }
        if (root.getTextPartType() == textPartType) a += 1;

        return a;
    }

}
