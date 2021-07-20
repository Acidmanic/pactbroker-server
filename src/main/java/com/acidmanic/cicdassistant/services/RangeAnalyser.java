/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.cicdassistant.services;

/**
 *
 * @author diego
 */
public class RangeAnalyser {

    public class Range {

        public long start;
        public long end;
    }

    public Range getRange(String rangeHeader) {
        
        if (rangeHeader != null && rangeHeader.length() > 0) {
            
            if (rangeHeader.startsWith("bytes=")) {

                String rangeString = rangeHeader.substring(6, rangeHeader.length());

                int dashIndex = rangeString.indexOf("-");

                if (dashIndex > -1 && dashIndex<rangeString.length()) {

                    String leftString = rangeString.substring(0, dashIndex);
                    
                    String rightString = rangeString.substring(dashIndex + 1, rangeString.length());
                    
                    try {
                        
                        long left = Long.parseLong(leftString);
                        
                        long right = Long.parseLong(rightString);
                        
                        Range range = new Range();
                        
                        range.start = left;
                        
                        range.end = right;
                        
                        return range;
                        
                    } catch (Exception e) {
                    }

                }
            }
        }
        return new Range();
    }
}
