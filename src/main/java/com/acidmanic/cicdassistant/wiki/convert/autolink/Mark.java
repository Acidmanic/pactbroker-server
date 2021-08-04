/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.wiki.convert.autolink;

/**
 *
 * @author diego
 */
public class Mark {

    private int start;
    private int end;
    private String tag;

    public Mark(int start, int end) {
        this.start = start;
        this.end = end;
        this.tag = "";
    }

    public Mark(int start, int end, String tag) {
        this.start = start;
        this.end = end;
        this.tag = tag;
    }

    public Mark() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isEmpty() {

        return this.start >= this.end;
    }

    @Override
    public Mark clone() {

        return new Mark(start, end, tag);
    }

    public String pickChunk(String base) {

        return base.substring(start, end);
    }

    public boolean isTagged() {

        return this.tag != null && this.tag.length() > 0;
    }

    @Override
    public String toString() {

        return Integer.toString(start)
                + " - "
                + Integer.toString(end)
                + (this.isTagged() ? (" [" + this.tag + "]") : "");
    }

    public Mark offset(int offset) {

        this.start += offset;
        this.end += offset;

        return this;
    }

}
