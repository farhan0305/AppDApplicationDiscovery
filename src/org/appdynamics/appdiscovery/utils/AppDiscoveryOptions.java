package org.appdynamics.appdiscovery.utils;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

import java.util.logging.Logger;
import java.util.logging.Level;

public class AppDiscoveryOptions {
	
    public static final String USAGE="java -jar AppDApplicationDiscovery.jar";

    public static final String CONTROLLER_L="controller";
    public static final String CONTROLLER_S="c";
    public static final String CONTROLLER_D="This is going to be the FQDN of the controller, for example: appdyn.saas.appdynamics.com";
    public static final boolean CONTROLLER_R=true;
    public static final boolean CONTROLLER_A=true;
    
    public static final String PORT_L="port";
    public static final String PORT_S="P";
    public static final String PORT_D="The is going to be the port that is used by the controller.";
    public static final boolean PORT_R=true;
    public static final boolean PORT_A=true;
    
    public static final String ACCOUNT_L="account";
    public static final String ACCOUNT_S="a";
    public static final String ACCOUNT_D="If controller is multi-tenant add the account";
    public static final boolean ACCOUNT_R=false;
    public static final boolean ACCOUNT_A=false;
    
    public static final String USERNAME_L="username";
    public static final String USERNAME_S="u";
    public static final String USERNAME_D="The user name to use for the connection";
    public static final boolean USERNAME_R=true;
    public static final boolean USERNAME_A=true;
    
    public static final String PASSWD_L="passwd";
    public static final String PASSWD_S="p";
    public static final String PASSWD_D="The password to use for the connection";
    public static final boolean PASSWD_R=true;
    public static final boolean PASSWD_A=true;
    
    public static final String SSL_L="ssl";
    public static final String SSL_S="s";
    public static final String SSL_D="Use SSL with connection";
    public static final boolean SSL_R=false;
    public static final boolean SSL_A=false;
    
    public static final String HISTORYFILE_L="historyfile";
    public static final String HISTORYFILE_S="h";
    public static final String HISTORYFILE_D="The history file containing the ID of the most recently discovered application";
    public static final boolean HISTORYFILE_R=true;
    public static final boolean HISTORYFILE_A=true;
    
    public static final String APPLICATION_L="application";
    public static final String APPLICATION_S="A";
    public static final String APPLICATION_D="The name of the application to post the custom event to";
    public static final boolean APPLICATION_R=true;
    public static final boolean APPLICATION_A=true;
    
    public static final String OPTION_ERROR_1="A required parameter was not found. Please view the help menu for required parameters.";
    
    public static String USERNAME_V=null;
    public static String PASSWD_V=null;
    public static String HISTORYFILE_V=null;
    public static String APPLICATION_V=null;
    public static String CONTROLLER_V=null;
    public static String ACCOUNT_V="Customer1";
    public static String PORT_V=null;
    public static boolean SSL_V=false;
    
    private static Logger logger=Logger.getLogger(AppDiscoveryOptions.class.getName());
    private static Options options=new Options();
    private String[] arguments;
    private CommandLine cmdLine;
    
    public AppDiscoveryOptions(String[] args) throws ParseException {
        this.arguments=args;
        init();
    }
    
    public void init(){
        Option controller = OptionBuilder.withLongOpt(CONTROLLER_L)
                                .withArgName( CONTROLLER_S )
                                .hasArg()
                                .withDescription( CONTROLLER_D )
                                .create( CONTROLLER_S );
        
        options.addOption(controller);
        Option port = OptionBuilder.withLongOpt(PORT_L).withArgName( PORT_S )
                                .hasArg()
                                .withDescription(  PORT_D )
                                .create( PORT_S );
        options.addOption(port);
        Option account = OptionBuilder.withLongOpt(ACCOUNT_L).withArgName( ACCOUNT_S )
                                .hasArg()
                                .withDescription( ACCOUNT_D )
                                .create( ACCOUNT_S );
        options.addOption(account);
        Option username = OptionBuilder.withLongOpt(USERNAME_L).withArgName( USERNAME_S )
                                .hasArg()
                                .withDescription( USERNAME_D )
                                .create( USERNAME_S );
        options.addOption(username);
        Option passwd = OptionBuilder.withLongOpt(PASSWD_L).withArgName( PASSWD_S )
                                .hasArg()
                                .withDescription( PASSWD_D )
                                .create( PASSWD_S );
        options.addOption(passwd);
        
        Option historyfile = OptionBuilder.withLongOpt(HISTORYFILE_L).withArgName( HISTORYFILE_S )
                .hasArg()
                .withDescription( HISTORYFILE_D )
                .create( HISTORYFILE_S );
        options.addOption(historyfile);

        Option application = OptionBuilder.withLongOpt(APPLICATION_L).withArgName( APPLICATION_S )
                .hasArg()
                .withDescription( APPLICATION_D )
                .create( APPLICATION_S );
        options.addOption(application);
        
        options.addOption(SSL_S, SSL_L, SSL_A, SSL_D);
    }
    
    public boolean parse() throws ParseException {
            CommandLineParser parser = new GnuParser();
        try {
            // parse the command line arguments
            cmdLine = parser.parse( options, arguments );
            
            /* Required */
            if(!cmdLine.hasOption(USERNAME_L) || !cmdLine.hasOption(USERNAME_S)){
                logger.log(Level.INFO, OPTION_ERROR_1 + " 1");
                return false;
            }else{ USERNAME_V=cmdLine.getOptionValue(USERNAME_S);}

            if(!cmdLine.hasOption(PASSWD_L) || !cmdLine.hasOption(PASSWD_S)){
                logger.log(Level.INFO, OPTION_ERROR_1+ " 1");
                return false;
            }else{ PASSWD_V=cmdLine.getOptionValue(PASSWD_S);}
            
            if(!cmdLine.hasOption(CONTROLLER_L) || !cmdLine.hasOption(CONTROLLER_S)){
                logger.log(Level.INFO, OPTION_ERROR_1+ " 1");
                return false;
            }else{ CONTROLLER_V=cmdLine.getOptionValue(CONTROLLER_S);}
            
            if(!cmdLine.hasOption(PORT_L) || !cmdLine.hasOption(PORT_S)){
                logger.log(Level.INFO, OPTION_ERROR_1+ " 1");
                return false;
            }else{ PORT_V=cmdLine.getOptionValue(PORT_S);}

            if(!cmdLine.hasOption(HISTORYFILE_L) || !cmdLine.hasOption(HISTORYFILE_S)){
                logger.log(Level.INFO, OPTION_ERROR_1+ " 1");
                return false;
            }else{ HISTORYFILE_V=cmdLine.getOptionValue(HISTORYFILE_S);}

            if(!cmdLine.hasOption(APPLICATION_L) || !cmdLine.hasOption(APPLICATION_S)){
                logger.log(Level.INFO, OPTION_ERROR_1+ " 1");
                return false;
            }else{ APPLICATION_V=cmdLine.getOptionValue(APPLICATION_S);}
            
            //Optional options
            if(cmdLine.hasOption(SSL_L) || cmdLine.hasOption(SSL_S)){
                SSL_V=true;
            }
            
            if(cmdLine.hasOption(ACCOUNT_L) || cmdLine.hasOption(ACCOUNT_S)){
                ACCOUNT_V=cmdLine.getOptionValue(ACCOUNT_S);    
            }
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            logger.log(Level.SEVERE, new StringBuilder().append("Parsing failed.  Reason: ").append(exp.getMessage()).append("\n\n").toString() );
            return false;
        }
        
        StringBuilder bud = new StringBuilder();
        bud.append("\nRunning application discovery with the following options:");
        bud.append("\n\tController: ").append(AppDiscoveryOptions.CONTROLLER_V);
        bud.append("\n\tController Port: ").append(AppDiscoveryOptions.PORT_V);
        bud.append("\n\tUser Name: ").append(AppDiscoveryOptions.USERNAME_V);
        bud.append("\n\tUse SSL: ").append(AppDiscoveryOptions.SSL_V);
        bud.append("\n\tAccount name: ").append(AppDiscoveryOptions.ACCOUNT_V);
        bud.append("\n\tApplication name: ").append(AppDiscoveryOptions.APPLICATION_V);
        bud.append("\n\tHistory file: ").append(AppDiscoveryOptions.HISTORYFILE_V);
        
        logger.log(Level.INFO, bud.toString());
       
        return true;
    }
    
    public void printHelp(){
        
        new HelpFormatter().printHelp(USAGE, options);
    }

    public static Options getOptions() {
        return options;
    }


    public String[] getArguments() {
        return arguments;
    }

    public int getValidNumber(String stringInt){
        try{
            Integer val = new Integer(stringInt);
            return val.intValue();
        }catch(Exception e){
            logger.log(Level.SEVERE, new StringBuilder().append("Exception occurred while parsing number from ")
                    .append(stringInt).append(" ").append(e.getMessage()).toString());
        }
        return -1;
    }
    
    public double getDoubleValidNumber(String stringDouble){
        try{
            Double val = new Double(stringDouble);
            if(val > 99) return -1.0;
            if(val >= 1) return val/100;
            return val;
        }catch(Exception e){
            logger.log(Level.SEVERE, new StringBuilder().append("Exception occurred while parsing number from ")
                    .append(stringDouble).append(" ").append(e.getMessage()).toString()); 
        }
        return -1.0;
    }
    
    public boolean validInterval(int interval){
        if(interval > 0 && interval < 36) return true;
        return false;
    }
    
    public boolean validDebug(int debugLevel){
        if(debugLevel >= 0 && debugLevel < 4) return true;
        return false;
    }
}
