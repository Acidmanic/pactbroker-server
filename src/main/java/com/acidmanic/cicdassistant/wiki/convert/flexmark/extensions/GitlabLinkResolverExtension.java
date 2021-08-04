/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions;

import com.acidmanic.cicdassistant.utility.PathHelpers;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/**
 *
 * @author diego
 */
public class GitlabLinkResolverExtension implements HtmlRenderer.HtmlRendererExtension, Parser.ParserExtension {

    private final String currentUri;
    private final String prefixSegments;

    public GitlabLinkResolverExtension(String currentUri) {
        this(currentUri, "");
    }

    public GitlabLinkResolverExtension(String currentUri, String prefixSegments) {
        this.currentUri = currentUri;

        this.prefixSegments = "/" + StringUtils.stripSides(prefixSegments, "/");
    }

    @Override
    public void rendererOptions(MutableDataHolder mdh) {

    }

    @Override
    public void extend(HtmlRenderer.Builder builder, String string) {

        builder.linkResolverFactory(new GitlabLinkResolverFactory());
    }

    @Override
    public void parserOptions(MutableDataHolder mdh) {

    }

    @Override
    public void extend(Parser.Builder builder) {
    }

    private class GitlabLinkResolver implements LinkResolver {

        @Override
        public ResolvedLink resolveLink(Node node, LinkResolverBasicContext lrbc, ResolvedLink rl) {

            Path refferrer = Paths.get(currentUri);
            Path refferree = Paths.get(rl.getUrl());

            PathHelpers.PathRelation relation = new PathHelpers().relation(refferrer, refferree);

            String target = rl.getUrl();

            if (relation != PathHelpers.PathRelation.NotRelated) {

                System.out.println("GITLAB LINK: " + target);

                target =  prefixSegments + "/" + target;

            }

            rl = new ResolvedLink(rl.getLinkType(), target);

            return rl;
        }

    }

    private class GitlabLinkResolverFactory implements LinkResolverFactory {

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
            return new GitlabLinkResolver();
        }

    }
}
