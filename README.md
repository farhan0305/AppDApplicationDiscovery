AppDApplicationDiscovery
========================

The AppDApplicationDiscovery is a solution that can be used to periodically review
all applications in an AppDynamics controller, and generate a custom event when
it detects that a new application has been added.  It utilizes a simple text file
to keep track of the most recently discovered application ID.  When it discovers
an application with an ID greater than the value stored in the file, it generates
the custom event and increments the value of the application ID in the file.
The type of the custom event is "New Application Discovered".  This type can be
used during policy creation, to send an email whenever a new application is
discovered.  When first running this utility, create a text file containing the
application ID of the most recently created application. 

Requirements:
-------------------
The AppDApplicationDiscovery solution is dependent on the AppDRESTAPI-SDK
               (https://github.com/Appdynamics/AppDRESTAPI-SDK.git) this should be
cloned and built before cloning this package. It is recommended that both packages
share a base directory, this will make the dependency checking easier. Compile and
package the AppDRESTAPI-SDK to insure files that are needed are present. Once this
package has been cloned edit the file called ‘one_time_git.properties’, insure the
location of AppDRESTAPI-SDK and the version of the jar file are correct. The file
‘one_time_git.properties’ should not be synced with the git repository after it has
been edited because the settings only apply to your environment.

The file contains two variables please insure that they are correct (insure that no
extra spaces are present):

appd_rest_base=../AppDRESTAPI-SDK

appd_rest_jar=RESTAPI_1.0.16.jar

Building:
-----------
To build the package run the following command within the appdApplicationDiscovery directory
      ant -f AppD_build.xml

This will create a directory called execLib with all of the necessary libraries to run the tool.


Usage
--------
```
java -cp "execLib/*" org.appdynamics.appdiscovery.AppDiscovery -c <FQDN-For-Controller> -P <PORT> -u<USER-NAME> -p <PASSWORD> -a <ACCOUNT-NAME> -h <HISTORY_FILE_NAME> -A <APPLICATION_TO_POST_EVENT_TO> -x <HTTP_PROXY> [-s]


 -a,--account <a>    :   If controller is multi-tenant add the account

 -c,--controller <c> :   This is going to be the FQDN of the controller, for example: appdyn.saas.appdynamics.com

 -s,--ssl            :   Use SSL with connection

 -P,--port <P>       :   This is going to be the port that is used by the controller.

 -u,--username <u>   :   The user name to use for the connection

 -p,--passwd <p>     :   The password to use for the connection

 -h,--historyfile <h>:   The history file containing the ID of the most recently discovered application

 -A,--appname <A>    :   The name of the application to post the custom event to

 -X,--proxyurl <X>   :   Optional http proxy url used to connect to the controller 

 -x,--proxyport <x>  :   Optional http proxy port used to connect to the controller 

```

Example:

java -cp "execLib/*" org.appdynamics.appdiscovery.AppDiscovery -c mycontroller.mycompany.com -P 443 -s -u myUserName -p myPassword -a customer1 -h ./AppDiscoveryHistory.txt -A "My Application To Post Custom Event To"


Support:
--------
For support and feature requests please email derek.mitchell@appdynamics.com
