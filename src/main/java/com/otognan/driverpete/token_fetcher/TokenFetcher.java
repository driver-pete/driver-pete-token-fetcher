package com.otognan.driverpete.token_fetcher;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.logging.Level;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class TokenFetcher {

    public static void main(String[] args) throws Exception {
        
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(Level.OFF);
        
        Options options = new Options();
        options.addOption("address", true, "Server address (e.g. https://localhost:8443)");
        options.addOption("email", true, "Facebook email (e.g. testuser@gmail.com)");
        options.addOption("pass", true, "Facebook password");
        
        CommandLineParser parser = new DefaultParser();
 
        try {
            // parse the command line arguments
            CommandLine cmd = parser.parse( options, args);
            
            String address = cmd.getOptionValue("address");
            String email = cmd.getOptionValue("email");
            String pass = cmd.getOptionValue("pass");
            
            if (address == null) {
                throw new ParseException("Server adrress can not be null");
            }
            
            if (email == null) {
                throw new ParseException("Email can not be null");
            }
            
            if (pass == null) {
                throw new ParseException("Password can not be null");
            }

            String token = TokenFetcher.fetch_token(address, email, pass);
            System.out.println(token);
        }
        catch( ParseException exp ) {
            System.out.println("Unexpected exception: " + exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("driver-pete-token-fetcher", options );
            System.exit(-1);
        }
    }
    
    public static String fetch_token(String serverAddress, String facebookUsername, String facebookPassword) throws Exception {
        // Perform facebook login automation with HTMLUnit
        WebClient webClient = new WebClient();
        // Disable SSL - otherwise redirect from facebook to our app will fail
        // because of testing certificates
        webClient.getOptions().setUseInsecureSSL(true);
        
        HtmlPage page1 = webClient.getPage(serverAddress + "/auth/facebook");
        HtmlForm form = (HtmlForm) page1.getElementById("login_form");
        if (form == null) {
            throw new Exception("Login form is not found. Possibly we are not on login page. " +
                    "Most of the time it happens when facebook rejects our app url");
        }
        HtmlSubmitInput button = (HtmlSubmitInput) form.getInputsByValue("Log In").get(0);
        HtmlTextInput textField = form.getInputByName("email");
        textField.setValueAttribute(facebookUsername);
        HtmlPasswordInput textField2 = form.getInputByName("pass");
        textField2.setValueAttribute(facebookPassword);

        HtmlPage homePage = button.click();

        // Check that we are redirected back to the application
        Cookie tokenCookie = webClient.getCookieManager().getCookie("AUTH-TOKEN");
        String token = tokenCookie.getValue();
        return token;
    }

}
