/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.wiki.convert.autolink.HtmlTextMarker;
import com.acidmanic.cicdassistant.wiki.convert.autolink.Mark;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author diego
 */
public class HtmlMarkerTest {

    public static void main(String[] args) {

        String html = "<body><h1>Header</h1>This is some text<ul><li>An Li Text content</li></ul></body>";

        try {

            html = new String(Files.readAllBytes(Paths.get("debug/tasks.html")));
        } catch (Exception e) {
        }

        List<Mark> marks = new HtmlTextMarker().markTexts(html);

        for (Mark mark : marks) {

            System.out.println("----------------------------------");
            System.out.println(mark.getTag());
            System.out.println("-----------");
            System.out.println(mark.pickChunk(html));
            System.out.println("----------------------------------");
        }
    }
}
