/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

import com.acidmanic.cicdassistant.utility.Final;
import com.acidmanic.delegates.arg1.Action;

/**
 *
 * @author diego
 */
public class WikiRepoStatus {

    private static class Status {

        private boolean ready;
        private boolean featchDemand;

        public Status() {
            this.ready = true;
            this.featchDemand = false;
        }

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }

        public boolean isFeatchDemand() {
            return featchDemand;
        }

        public void setFeatchDemand(boolean featchDemand) {
            this.featchDemand = featchDemand;
        }

    }

    private static final Status STATUS = new Status();

    private synchronized void accessStatus(Action<Status> accessCode) {

        accessCode.perform(WikiRepoStatus.STATUS);
    }

    public boolean isReady() {

        Final<Boolean> result = new Final<>();

        accessStatus(stt -> result.setValue(stt.isReady()));

        return result.getValue();
    }

    public void setReady() {

        accessStatus(stt -> stt.setReady(true));
    }

    public void setBusy() {

        accessStatus(stt -> stt.setReady(false));
    }

    public boolean anyFetchDemand() {

        Final<Boolean> result = new Final<>();

        accessStatus(stt -> result.setValue(stt.isFeatchDemand()));

        return result.getValue();
    }

    public void setFetchSatisfied() {

        accessStatus(stt -> stt.setFeatchDemand(false));
    }

    public void setFetchDemand() {

        accessStatus(stt -> stt.setFeatchDemand(true));
    }
}
