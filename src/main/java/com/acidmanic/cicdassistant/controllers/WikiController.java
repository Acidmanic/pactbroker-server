/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.controllers;

import com.acidmanic.cicdassistant.application.configurations.Configurations;
import com.acidmanic.cicdassistant.application.services.web.Controller;
import static com.acidmanic.cicdassistant.controllers.WikiController.WIKI_ROUTE;
import com.acidmanic.cicdassistant.html.interception.RebaseAnchorsHtmlInterceptor;
import com.acidmanic.cicdassistant.models.Dto;
import com.acidmanic.cicdassistant.services.EncyclopediaStore;
import com.acidmanic.cicdassistant.services.WikiRepoStatus;
import com.acidmanic.cicdassistant.services.routing.Router;
import com.acidmanic.cicdassistant.storage.TokenStorage;
import com.acidmanic.cicdassistant.utility.StringUtils;
import com.acidmanic.cicdassistant.utility.web.MimeTypeTable;
import com.acidmanic.cicdassistant.wiki.GitlabRelativeLinkManipulator;
import com.acidmanic.cicdassistant.wiki.convert.MarkdownToHtmlConvertor;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.GitLabCommitHashAnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.MailToAnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.SmartWebLinkAnchorSource;
import com.acidmanic.cicdassistant.wiki.convert.anchorsources.TerminologyAnchorSource;
import com.acidmanic.cicdassistant.wiki.authorization.GitlabAuthorizationProvider;
import com.acidmanic.cicdassistant.wiki.authorization.RequestDataProvider;
import com.acidmanic.cicdassistant.wiki.convert.style.HtmlStyleProvider;
import com.acidmanic.lightweight.logger.Logger;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author diego
 */
@Path(WIKI_ROUTE)
@Controller
public class WikiController extends ControllerBase {

    public static final String WIKI_ROUTE = "wiki";
    private static final java.nio.file.Path WIKI_ROUTE_PATH = Paths.get(WIKI_ROUTE);
    private final Router router;
    private final Configurations configurations;
    private final EncyclopediaStore encyclopediaStore;
    private final WikiRepoStatus wikiRepoStatus;
    private final HtmlStyleProvider htmlStyleProvider;
    private final RequestDataProvider requestDataProvider;

    public WikiController(Router router, Configurations configurations, EncyclopediaStore encyclopediaStore, WikiRepoStatus wikiRepoStatus, HtmlStyleProvider htmlStyleProvider, RequestDataProvider requestDataProvider, TokenStorage tokenStorage, Logger logger) {
        super(tokenStorage, logger);
        this.router = router;
        this.configurations = configurations;
        this.encyclopediaStore = encyclopediaStore;
        this.wikiRepoStatus = wikiRepoStatus;
        this.htmlStyleProvider = htmlStyleProvider;
        this.requestDataProvider = requestDataProvider;
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
    public Response get(
            @PathParam("rawuri") String rawUri) {

        return provideResourse(rawUri);
    }

    private String grantThemeName(String theme) {

        if (StringUtils.isNullOrEmpty(theme)) {

            theme = configurations.getWikiConfigurations().getThemeName();

        }
        return theme;
    }

    private Response provideResourse(String rawUri) {

        byte[] data;
        int status;

        String theme = this.requestDataProvider.readCookie("theme");

        String setTheme = this.requestDataProvider.readQuery("theme");

        String type = new MimeTypeTable().getMimeTypeForUri(rawUri);

        if (isAuthorizedUser()) {

            if (!StringUtils.isNullOrEmpty(setTheme)) {

                setTheme = grantThemeName(setTheme);

                theme = setTheme;

            } else {
                theme = grantThemeName(theme);
            }

            status = 200;

            data = readFile(rawUri, theme);
        } else {

            status = 403;

            String html = "<html><head><title>Oops!</title></head><body>"
                    + "<h1>You are not authorized.</h1> <br>"
                    + "<h3> please contact "
                    + "your system administrator to resolve this."
                    + "</h3></body></html>";

            data = html.getBytes();
        }
        return Response
                .status(status)
                .type(type)
                .entity(data)
                .cookie(new NewCookie("theme", theme, "/", "", "wiki theme", 10000000, false))
                .build();
    }

    private byte[] readFile(String uri, String theme) {

        File file = this.router.mapPath(uri);

        if (file.exists()) {

            if (file.getName().toLowerCase().endsWith(".md")) {

                return readMdAsHtml(file, theme);
            }
        }
        return tryReadAsBytes(file.toPath());
    }

    private byte[] readMdAsHtml(File file, String theme) {

        try {

            MarkdownToHtmlConvertor convertor = new MarkdownToHtmlConvertor()
                    .addAnchorSource(new SmartWebLinkAnchorSource())
                    .addAnchorSource(new MailToAnchorSource())
                    .setStyleProvider(this.htmlStyleProvider)
                    .setLinkManipulator(new GitlabRelativeLinkManipulator())
                    .useIndexTree(this.wikiRepoStatus::getIndexTree)
                    .useLinkTextProvider(this.wikiRepoStatus::getIndexTree);

            this.encyclopediaStore.getAvailables()
                    .forEach(en -> convertor.addAnchorSource(new TerminologyAnchorSource().addEncyclopedia(en.getEntries())));

            this.configurations.getGitlabConfigurations()
                    .forEach(con -> convertor.addAnchorSource(new GitLabCommitHashAnchorSource(con)));

            String markdown = new String(Files.readAllBytes(file.toPath()));

            String html = convertor.convert(markdown, theme);

            html = intercept(html);

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

    private String intercept(String html) {

        Document document = Jsoup.parse(html);

        new RebaseAnchorsHtmlInterceptor(WIKI_ROUTE_PATH).manipulate(document);

        return document.html();
    }

    private boolean isAuthorizedUser() {

        if (this.configurations.getAuthorizationConfigurations().isUseGitlab()) {

            return new GitlabAuthorizationProvider(
                    this.configurations.getAuthorizationConfigurations().getGitlabBaseUrl()
            ).isAuthorized(this.requestDataProvider);
        }

        // if there is no authorization method configured, so maybe
        // it doesnt need authorization
        return true;
    }
}
