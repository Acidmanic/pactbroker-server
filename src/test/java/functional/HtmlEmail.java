/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functional;

import com.acidmanic.cicdassistant.html.Body;
import com.acidmanic.cicdassistant.html.H1;
import com.acidmanic.cicdassistant.html.Hr;
import com.acidmanic.cicdassistant.html.P;
import com.acidmanic.cicdassistant.html.RawString;
import com.acidmanic.cicdassistant.html.Span;

/**
 *
 * @author diego
 */
public class HtmlEmail {
    
    
    
    
    public static void main(String[] args){
        
        Body body = new Body();
        
        body.addChild(new P());
        body
                .addChild(new H1().addChild(new RawString("This is a title")))
                .addChild(new Hr())
                .addChild(new Span()
                        .addChild(new RawString("Hellooo"))
                );
        
        String htmlBody = body.toString();
        
        System.out.println(htmlBody);
    }
}
