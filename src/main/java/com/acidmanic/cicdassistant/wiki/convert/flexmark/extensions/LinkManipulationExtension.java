/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions;

import com.acidmanic.cicdassistant.wiki.linkprocessing.LinkManipulator;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import java.util.Set;

/**
 *
 * @author diego
 */
public class LinkManipulationExtension implements HtmlRenderer.HtmlRendererExtension, Parser.ParserExtension {

    private final LinkManipulator linkManipulator;

    public LinkManipulationExtension(LinkManipulator linkManipulator) {
        this.linkManipulator = linkManipulator;
    }

    @Override
    public void rendererOptions(MutableDataHolder mdh) {

    }

    @Override
    public void extend(HtmlRenderer.Builder builder, String string) {
        builder.linkResolverFactory(new InterceptorLinkResolverFactory());
    }

    @Override
    public void parserOptions(MutableDataHolder mdh) {
    }

    @Override
    public void extend(Parser.Builder builder) {
    }

    private class InterceptorLinkResolver implements LinkResolver {

        @Override
        public ResolvedLink resolveLink(Node node, LinkResolverBasicContext lrbc, ResolvedLink rl) {

            String href = rl.getUrl();

            rl = new ResolvedLink(rl.getLinkType(), linkManipulator.manipulate(href));

            return rl;
        }

    }

    private class InterceptorLinkResolverFactory implements LinkResolverFactory {

        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public LinkResolver apply(LinkResolverBasicContext lrbc) {
            return new InterceptorLinkResolver();
        }

    }
}
