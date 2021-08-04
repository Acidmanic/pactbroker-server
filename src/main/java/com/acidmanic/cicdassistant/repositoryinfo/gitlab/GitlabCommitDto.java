/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.repositoryinfo.gitlab;

import java.util.Date;

/**
 *
 * @author diego
 */
public class GitlabCommitDto {

    private String id;
    private String short_id;
    private String title;
    private String message;
    private String author_name;
    private String author_email;
    private Date authored_date;
    private String committer_name;
    private String committer_email;
    private Date committed_date;
    private String web_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShort_id() {
        return short_id;
    }

    public void setShort_id(String short_id) {
        this.short_id = short_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public Date getAuthored_date() {
        return authored_date;
    }

    public void setAuthored_date(Date authored_date) {
        this.authored_date = authored_date;
    }

    public String getCommitter_name() {
        return committer_name;
    }

    public void setCommitter_name(String committer_name) {
        this.committer_name = committer_name;
    }

    public String getCommitter_email() {
        return committer_email;
    }

    public void setCommitter_email(String committer_email) {
        this.committer_email = committer_email;
    }

    public Date getCommitted_date() {
        return committed_date;
    }

    public void setCommitted_date(Date committed_date) {
        this.committed_date = committed_date;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

}
