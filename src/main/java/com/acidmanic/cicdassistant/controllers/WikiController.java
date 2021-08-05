/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.configurations.Configurations;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import com.acidmanic.cicdassistant.models.Dto;
import com.acidmanic.cicdassistant.services.EncyclopediaStore;
import com.acidmanic.cicdassistant.services.WikiRepoStatus;
import com.acidmanic.cicdassistant.services.routing.Router;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.cicdassistant.utility.web.MimeTypeTable;
import com.acidmanic.cicdassistant.wiki.convert.MarkdownToHtmlConvertor;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.GitLabCommitHashAnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.MailToAnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.SmartWebLinkAnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.TerminologyAnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.flexmark.extensions.GitlabLinkResolverExtension;
import com.acidmanic.lightweight.logger.Logger;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author diego
 */
@Path("wiki")
@Controller
public class WikiController extends ControllerBase {

    private final Router router;
    private final Configurations configurations;
    private final EncyclopediaStore encyclopediaStore;
    private final WikiRepoStatus wikiRepoStatus;

    public WikiController(Router router, Configurations configurations, EncyclopediaStore encyclopediaStore, WikiRepoStatus wikiRepoStatus, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.router = router;
        this.configurations = configurations;
        this.encyclopediaStore = encyclopediaStore;
        this.wikiRepoStatus = wikiRepoStatus;
    }

    @GET
    @Produces(value = MediaType.TEXT_HTML)
    public Response index() {
        return provideResourse("");
    }

    @POST
    @Path("fetch")
    public void requestWikiUpdate(@HeaderParam("token") String token) {

        authorize(token, () -> {
            wikiRepoStatus.setFetchDemand();
            return new Dto<Object>();
        });

    }

    @GET
    @Path("{rawuri:.*}")
    public Response get(@PathParam("rawuri") String rawUri) {
        return provideResourse(rawUri);
    }

    private Response provideResourse(String rawUri) {

        int status = 200;

        String type = new MimeTypeTable().getMimeTypeForUri(rawUri);

        byte[] data = readFile(rawUri);

        return Response
                .status(status)
                .type(type)
                .entity(data)
                .build();
    }

    private byte[] readFile(String uri) {

        File file = this.router.mapPath(uri);

        if (file.exists()) {

            if (file.getName().toLowerCase().endsWith(".md")) {

                return readMdAsHtml(file, uri);
            }
        }
        return tryReadAsBytes(file.toPath());
    }

    private byte[] readMdAsHtml(File file, String requestUri) {

        try {

            MarkdownToHtmlConvertor convertor = new MarkdownToHtmlConvertor()
                    .addAnchorSource(new SmartWebLinkAnchorSource())
                    .addAnchorSource(new MailToAnchorSource())
                    .addExtension(new GitlabLinkResolverExtension(requestUri, "wiki"));

            this.encyclopediaStore.getAvailables()
                    .forEach(en -> convertor.addAnchorSource(new TerminologyAnchorSource().addEncyclopedia(en.getEntries())));

            this.configurations.getGitlabConfigurations()
                    .forEach(con -> convertor.addAnchorSource(new GitLabCommitHashAnchorSource(con)));

            convertor.setThemeName(configurations.getWikiConfigurations().getThemeName());

            String markdown = new String(Files.readAllBytes(file.toPath()));

            String html = convertor.convert(markdown);

            return html.getBytes(Charset.forName("utf-8"));

        } catch (Exception e) {

            System.out.println(e);
        }
        return notFound();
    }

    private byte[] tryReadAsBytes(java.nio.file.Path file) {
        try {

            return Files.readAllBytes(file);

        } catch (Exception e) {
        }
        return notFound();
    }

    private byte[] notFound() {

        String notFoundString = "<html><head><title>"
                + "Not Found</title></head><body>"
                + "<h1>The page you are looking for, does not exists</h1>"
                + "</body></html>";
        return notFoundString.getBytes(Charset.forName("utf-8"));
    }
}
