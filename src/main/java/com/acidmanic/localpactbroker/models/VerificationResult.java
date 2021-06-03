/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localpactbroker.models;

import java.util.HashMap;

/**
 *
 * @author diego
 */
public class VerificationResult {

    private HashMap<String, Link> _links = new HashMap<>();
    private Link[] curies = {};

    public HashMap<String, Link> get_links() {
        return _links;
    }

    public void set_links(HashMap<String, Link> _links) {
        this._links = _links;
    }

    public Link[] getCuries() {
        return curies;
    }

    public void setCuries(Link[] curies) {
        this.curies = curies;
    }

}
