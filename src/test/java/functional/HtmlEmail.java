/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.html.Body;
import com.acidmanic.cicdassistant.html.H1;
import com.acidmanic.cicdassistant.html.Hr;
import com.acidmanic.cicdassistant.html.Html;
import com.acidmanic.cicdassistant.html.Img;
import com.acidmanic.cicdassistant.html.Ol;
import com.acidmanic.cicdassistant.html.P;
import com.acidmanic.cicdassistant.html.RawString;
import com.acidmanic.cicdassistant.html.Span;
import com.acidmanic.cicdassistant.html.Table;
import com.acidmanic.cicdassistant.html.Tr;
import com.acidmanic.cicdassistant.html.Ul;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 * @author diego
 */
public class HtmlEmail {

    public static void main(String[] args) {

        Body body = new Body();

        body.addChild(new P());
        body
                .addChild(new H1().addChild(new RawString("This is a title")))
                .addChild(new Hr())
                .addChild(new Span()
                        .addChild(new RawString("Hellooo")))
                .addChild(new Ol()
                        .addChild(new RawString("First Item"))
                        .addChild(new RawString("SecondItem"))
                        .addChild(new Img("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png")))
                .addChild(new Span().addChild(new RawString("Another List")))
                .addChild(new Ul()
                        .addChild(new RawString("Instruction a"))
                        .addChild(new RawString("Instruction b")))
                .addChild(new Table()
                        .setStyle("border: 2px solid gray")
                        .addChild(new RawString(" 1 - 1 ")).addChild(new RawString(" 1 - 2 "))
                        .addChild(new Tr())
                        .addChild(new RawString(" 2 - 1 ")).addChild(new RawString(" 2 - 2 "))
                        .addChild(new Tr())
                        .addChild(new RawString(" 3 - 1 ")).addChild(new RawString(" 3 - 2 ")))
                ;

        Html html = new Html();

        html.setTitle("Test page");

        body.copyInto(html.getBody());

        String htmlBody = html.toString();

        System.out.println(htmlBody);
        
        try {
            Files.write(Paths.get("html.html"), htmlBody.getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
        }
    }
}
