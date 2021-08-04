/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.wiki.convert.flexmark.extensions;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author diego
 */
public abstract class NodeRendererExtension<T extends Node> implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

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

        builder.nodeRendererFactory(dl -> new CustomeRenderer(this));

    }

    protected abstract void render(T node, NodeRendererContext context, HtmlWriter html);

    protected abstract Class<? extends Node> getTargetType();

    private class CustomeRenderer implements NodeRenderer {

        private final NodeRendererExtension parent;

        public CustomeRenderer(NodeRendererExtension parent) {
            this.parent = parent;
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
            set.add(new NodeRenderingHandler<>(parent.getTargetType(), this::render));
            return set;
        }

        private void render(T node, NodeRendererContext context, HtmlWriter html) {

            this.parent.render(node, context, html);
        }

    }
}
