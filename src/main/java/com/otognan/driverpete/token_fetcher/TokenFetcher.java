package com.otognan.driverpete.token_fetcher;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class TokenFetcher {

    public static void main(String[] args) throws ParseException {
        
        Options options = new Options();
        options.addOption("t", true, "display current time");
        
        CommandLineParser parser = new DefaultParser();
        
        CommandLine cmd = parser.parse( options, args);
        
        String countryCode = cmd.getOptionValue("t");
        System.out.println(countryCode);
    }

}
