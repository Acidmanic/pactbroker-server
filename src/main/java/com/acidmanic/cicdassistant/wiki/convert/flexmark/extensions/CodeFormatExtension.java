/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions;

import com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.codeparsing.CodeToHtmlFactory;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author diego
 */
public class CodeFormatExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    @Override
    public void parserOptions(MutableDataHolder mdh) {

    }

    @Override
    public void extend(Parser.Builder bldr) {

    }

    @Override
    public void rendererOptions(MutableDataHolder mdh) {

    }

    @Override
    public void extend(HtmlRenderer.Builder builder, String string) {

        builder.nodeRendererFactory(new JsonNodeRendererFactory());

    }

    private class JsonNodeRendererFactory implements NodeRendererFactory {

        @Override
        public NodeRenderer apply(DataHolder dh) {

            return new JsonNodeRenderer();
        }

    }

    private class JsonNodeRenderer implements NodeRenderer {

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
            set.add(new NodeRenderingHandler<>(FencedCodeBlock.class, this::render));
            return set;
        }

        private void render(FencedCodeBlock node, NodeRendererContext context, HtmlWriter html) {

            String codeText = node.getChars().toString();

            String codeHtml = new CodeToHtmlFactory().make(codeText).getHtmlFor(codeText);

            html.withAttr(new AttributablePart("class")).attr("class", "json-object");

            html.tag("code");

            html.append(codeHtml);

            html.tag("/code");
        }

    }
}
