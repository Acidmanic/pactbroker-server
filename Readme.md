

![image](Graphics/logo.png) __Cicd Assistant__
==============

About
-----

This application is a standalone server providing some features helpful for 
setting up and performing cicd tasks.


Features:
---------

* [It serves as a PACT broker](#Pact-Broker-Api)
  * provides push operation: contract collection (pact) will be ready for download by clients
  * provides pull operation: clients will download the contract collection (pact)
  * provides store operation: stores a contract collection (pact) corresponding with a tag, but still its not selected for being downloaded
  * provides elect operation: sets a contract collection (pact), the one that will be downloaded by clients
* [It serves as a badge broker based on tags](#Badge-Broker-Api)
  * Success badge
  * Failure badge
  * Pending badge (Otherwise)
  * Can serve badges with svg or png formats
* [Provides Api calls for sending emails](#Email-Notification-Api)
  * Uses smtp server address and credentials from configurations
  * Api for self-contained email body 
  * Api for template based email body
  * Api for registering templates
* [Provides an artifact server](#Artifact-Server-Apis)
  * artifacts can be uploaded to artifacts server through multipart post call to artifacts/upload
  * uploaded artifacts can be downloaded by their names from artifacts/<artifact-file-name>
* [Provides a Simple proxy](#Proxy)
  * A small proxy endpoint suitable for text files and small artifacts.
* [Serves as a Wiki Server](#Wiki-Server)
  * Serves Markdown wikis 
  * Currently supports (only) gitlab link
  * User can select themes.
  * Wiki themes are extendible
  * Auto-link feature:
    * Replaces raw-text urls, with links having title and descriptive captions (if available)
    * Replaces email addresses with __mailto__ links
    * Replaces known keywords with links to external web pages (using json files stored at knowledge-base directory)
    * Replaces git commit hashes with link to repository server if available
      * Currently supports (only) gitlab
      * uses configuration to communicate with git repository server
* [Proxies Your ssh commands](#Ssh-Proxy)
    * WwwForm parameter api endpoint receives your host and shell command
    * Credentials should be saved in configurations
    * There is no need for installation of open-ssh for this to work.
* [Fav Icon](#Fav-Icon)

Pact Broker Api
====================

You can use pact broker api directly by making calls to these endpoints:

Pull
----

This endpoint will return main contract collection (Pact) if any is delivered 
at the moment.

|                       |                           |
|:---------------------:|:-------------------------:|
|endpoint path          |  &lt;base-url&gt;/pull          |
|Http Method            |  GET                      |
|Headers                |  token: &lt;token&gt;           |


__Response__ when a pact is present:


```json
{
    "model": {
        "contracts": [
            {
                "consumer": {
                    "name": "authentication"
                },
                "provider": {
                    "name": "v1"
                },
                "interactions": [...],
                "metadata": {
                    "pactSpecification": {
                        "version": "3.0.0"
                    }
                }
        ]
    },
    "failure": false,
    "error": ""
}

```

__Response__ when __No__ pacts are present yet:

```json
{
    "model": null,
    "failure": true,
    "error": {
        "message": "Not found",
        "code": 404
    }
}

```

Push
-------

Push endpoint sets a contract collection (Pact) as the main delivered pact on 
the broker.

|                       |                           |
|:---------------------:|:-------------------------:|
|endpoint path          |  &lt;base-url&gt;/push          |
|Http Method            |  POST                     |
|Headers                |  token: &lt;token&gt;           |

__Request Body__:

```json
{
        "contracts": [
            {
                "consumer": {
                    "name": "authentication"
                },
                "provider": {
                    "name": "v1"
                },
                "interactions": [...],
                "metadata": {
                    "pactSpecification": {
                        "version": "3.0.0"
                    }
                }
        ]
    }
```

__Response__:

```json
{
    "model": {
        "contracts": [
            {
                "consumer": {
                    "name": "authentication"
                },
                "provider": {
                    "name": "v1"
                },
                "interactions": [...],
                "metadata": {
                    "pactSpecification": {
                        "version": "3.0.0"
                    }
                }
        ]
    },
    "failure": false,
    "error": ""
}

```

Store
------


In many cases, its more desired to produce pact files at one point of the ci 
pipeline, and send them to the broker in another point. For example you might 
want to review the merge request after it's tests are passed (so pact files are 
already generated, and set these files to broker when the merge request is 
accepted and merged into the main branch (usually develop) so the pacts in broker 
would be the ones actually generated by last merged code. In such cases you can 
use store and elect apis. store api will deliver pact files to broker alongside a 
tag. (I use branch head as a tag) and when the branch is merged, you would 
call elect with that exact tag to tell the broker this is the pact data that 
should be served through pull api.

|                       |                           |
|:---------------------:|:-------------------------:|
|endpoint path          |  &lt;base-url&gt;/store/<tag>   |
|Http Method            |  POST                     |
|Headers                |  token: &lt;token&gt;           |

__Request Body__:

```json
{
        "contracts": [
            {
                "consumer": {
                    "name": "authentication"
                },
                "provider": {
                    "name": "v1"
                },
                "interactions": [...],
                "metadata": {
                    "pactSpecification": {
                        "version": "3.0.0"
                    }
                }
        ]
    }
```

__Response__:


```json
{
    "model": null,
    "failure": false,
    "error": {
        "message": "Ok",
        "code": 200
    }
}
```

Elect
------

This api will elect a stored contract collection (Pact) by it's tag as the main 
pact to be delivered via pull api.

|                       |                           |
|:---------------------:|:-------------------------:|
|endpoint path          |  &lt;base-url&gt;/elect/<tag>   |
|Http Method            |  POST                     |
|Headers                |  token: &lt;token&gt;           |

__Response__:

The elect api will respond with elected pact.

```json
{
    "model": {
        "contracts": [
            {
                "consumer": {
                    "name": "authentication"
                },
                "provider": {
                    "name": "v1"
                },
                "interactions": [...],
                "metadata": {
                    "pactSpecification": {
                        "version": "3.0.0"
                    }
                }
        ]
    },
    "failure": false,
    "error": ""
}

```

Badge Broker Api
=============

The badge broker service, currently provides badges for successful,failed and 
pending (or unknown) statuses. 

It can register/update one or more tags with a success or failure state, via 
badges api (POST), and in result, a (GET) call to badges endpoint will provide 
suitable image with previously registered status. 

Badges (POST)
-----------

|                       |                           |
|:---------------------:|:-------------------------:|
|endpoint path          |  &lt;base-url&gt;/badges  |
|Http Method            |  POST                     |
|Headers                |  token: &lt;token&gt;           |


__Request Body__:

```json
{
    "tag1":"Success",
    "tag2":"Failure",
    ...
}
```
__Response__:

```json
{
    "model": {
        "tag1": "Success"
        "tag2": "Failure",
        ...
    },
    "failure": false,
    "error": ""
}

```

Badges (GET) image url:
--------------------

To get badges, you just add the tag at eht end of the url 
```<base-url>/badges/svg``` to get a svg format badge, or use 
```<base-url>/badges/png``` for png format.

|                                        |                                     |
|:--------------------------------------:|:-----------------------------------:|
|&lt;base-url&gt;/badges/png/tag-successful    |![Badge](Graphics/Badges/success.png)|
|&lt;base-url&gt;/badges/png/tag-failed        |![Badge](Graphics/Badges/failure.png)|
|&lt;base-url&gt;/badges/png/tag-not-registered|![Badge](Graphics/Badges/unknown.png)|



Email Notification Api
======================

By providing your smpt server address and username and password in 
application's configuration file (the file _Configuration.json_ at the root directory
of the application), you can use Email apis to send notification emails.


Send Email Api (Self contained)
--------------

This api receives a json containing contacts and email body, and sends the email 
to receiver(s).


|                       |                               |
|:---------------------:|:-----------------------------:|
|endpoint path          |  &lt;base-url&gt;/mail/send-selfcontained   |
|Http Method            |  POST                         |
|Headers                |  token: &lt;token&gt;         |


__Request Body__:


```json
{
    "from":"info@acidmanic.com",
    "recipients":["acidmanic.moayedi@gmail.com"],
    "subject":"Cicd Assistant Notification",
    "base64Content":"PGJvZHk+PHNwYW4+SGVsbG8gVGhpcyBtYWlsIGlzIHNlbmQgZnJvbSBDaWNkIEFzc2lzdGFudDwvc3Bhbj48L2JvZHk+",
    "html":true
}
```

This example is sending following html as email body.

```html
<body><span>Hello This mail is send from Cicd Assistant</span></body>
```

 * You need to encode your body content into base64 string to prevent parsing 
issues.
 * You can send Plain text emails, the same way. You would encode your string to 
 a base64 string and set html field to false.


Registering Email Template 
---------------

In practical usages, the html code of the notification email body, can be very 
large and its not desired to send it in every json payload for each api call.
To solve that, you can use html templates and substitutions. First you will register 
your html template using this api. Then you can send an email 
by providing the substitutions and template name to _send-bytemplate_ api.


|                       |                               |
|:---------------------:|:-----------------------------:|
|endpoint path          |  &lt;base-url&gt;/mail/register-template   |
|Http Method            |  POST                         |
|Headers                |  token: &lt;token&gt;         |



__Request Body__:


```json
{
    "templateName":"product-support-notification",
    "base64Content":"PGJvZHk+PHNwYW4+CkRlYXIgJFVTRVJfTkFNRSwgICAgWW91IGNhbiByZWdpc3RlciB5b3VyIHB1cmNoYXNlZCBwcm9kdWN0IHZpYSB0aGlzIDxhIGhyZWYgPSAiaHR0cDovL3NvbWV3aGVyZS9yZWdpc3Rlci9QUk9EVUNUX1NFUklBTF9OVU1CRVJfMjMiPiBsaW5rLiA8L2E+CgpSZWdhcmRzLgo8L3NwYW4+PC9ib2R5Pg=="
}
```

This request contains base64 encoded string for following html:

```html
<body><span>
Dear $USER_NAME,    You can register your purchased product via this <a href = "http://somewhere/register/PRODUCT_SERIAL_NUMBER_23"> link. </a>

Regards.
</span></body>
```

calling the api with this request will register this html template associated 
with the name "product-support-notification".



Send Email Api (Using Templates)
--------------

Using this api, you can send emails usint a template name and a substitution 
list within the json payload.


|                       |                               |
|:---------------------:|:-----------------------------:|
|endpoint path          |  &lt;base-url&gt;/mail/send-bytemplate   |
|Http Method            |  POST                         |
|Headers                |  token: &lt;token&gt;         |



__Request Body__:


```json
{
    "from":"info@acidmanic.com",
    "recipients":["acidmanic.moayedi@gmail.com"],
    "templateName":"product-support-notification",
    "substitutions":{
        "$USER_NAME":"Morty Smith",
        "PRODUCT_SERIAL_NUMBER_(\\d+)":"T728-11-AG67",
        "\\s+":" "
        },
    "subject":"Template based Email"
}
```

 * Fields _from_ and _recipients_ are the same as send api. template name is a 
string you used to register your template within the Cicd-Assistant instance. 
and substitutions are a table of key values where keys are regular expressions. 
any match for these regular expressions, will be replace by corresponding value 
for that key.

 * the example above, will read the template, and will replace any occurrence of the 
term $USER_NAME, with the string "Morty Smith". In a same way it will replace 
any term that starts with PRODUCT_SERIAL_NUMBER_ followed by some digits, by the 
string "T728-11-AG67". And finally any combination of white spaces, will be 
replaced, with one single horizontal space. Then the result will be used as html 
content body for sending email.

For example, this substitution can change the example html template from 
 [register template](#Registering-Email-Template) to following html just before
 sending it to recipient(s).

```html
<body><span> Dear Morty Smith, You can register your purchased product via this <a href = "http://somewhere/register/T728-11-AG67"> link. </a> Regards. </span></body>
```


Artifact Server Apis
====================

With this feature, you can upload and store your artifacts to cicd-assistant and 
download them in other machines from it's own url. 


Upload Artifacts
----------------

a multi-part form post request towards the url: &lt;base-url&gt;/artifacts/upload 
will upload your file to cicd-assistant. you should provide the file name in a form 
input-part ```name=<your-file-name>``` and the file data itself with form input-part 
```file=<file-content>```.

To use this api, you need to be authorized, so you will have to provide the 
token header (token : &lt;token&gt;) within the request. Following script shows 
how to use [curl](https://curl.se/) to do so:

```bash

    curl -v -F file=@<path-to-file> <base-url>/artifacts/upload -F name=<file-name> -H "token: <token>"
    
    #Example:
    # curl -v -F file=@data/reports.zip http://some.where:8585/artifacts/upload -F name=daily-reports.zip -H "token: e3e45...ab513f"

``` 

Download Artifacts
----------------

Downloading the previously uploaded artifacts, does not required authorization, 
you can use wget or any download manager to download the artifact.

Any uploaded artifact, can be downloaded from &gt;base-url&lt;/artifacts/&lt;file-name&gt;

where __file-name__ is the name of the file you provided when using the upload api.


Delete Artifact
---------------

This api deletes a specific artifact.

|                       |                               |
|:---------------------:|:-----------------------------:|
|endpoint path          |  &lt;base-url&gt;/artifacts   |
|Http Method            |  DELETE                       |
|Headers                |  token: &lt;token&gt;         |
|Query Params:          |  filename=&lt;file-name&gt;   |

Where &lt;file-name&gt; is the name you provided while uploading the artifact. To prevent 
growing space usage on the server you run cicd-assistant, it can be a good practice to delete
 artifacts when you don't need them anymore.


Clear Artifacts
---------------

This api deletes all existing artifacts

|                       |                               |
|:---------------------:|:-----------------------------:|
|endpoint path          |  &lt;base-url&gt;/artifacts/clear   |
|Http Method            |  DELETE                       |
|Headers                |  token: &lt;token&gt;         |

Proxy
=======


In some cases when deployment target machine, for some reasons (security, etc...) 
 does not have access to a specific file over the internet, you can use cicd-asistant's 
proxy. This work better for text files and small binary files. To use this proxy,
 you you pass your destination url as a query argument ```url``` to &lt;base-url&gt;/proxy?url=

For example, &lt;base-url&gt;/proxy?url=http://www.example.com/data/list.asc will fetch and serve 
the file list.asc from http://www.example.com/data/list.asc. 

Authorization
============

Most of the Apis, need the consumer to authorize their access using a token in the header. When you install (unzip the binary package),
cicd-assistant on your server, you can use start.sh script to start the server. you can also use token.sh script to run the application 
just to generate a token. The generate token will printed out on the terminal like this:

```
Console service's state: Created.
BrokerWeb service's state: Created.
KillFile service's state: Created.
Checking for pre start commands.
=============================================
use following token for authentication over network:
---------------------------------------------
23a910bc-3661-45ae-8d1e-8fcf75cf2304.a603a884-c211-4292-a656-06674476d3f8
=============================================
BrokerWeb service's state: Stopping....
KillFile service's state: Stopping....
Console service's state: Stopping....
```

you can copy this and use it on your requests. All application data would be stored in json files. including this token. So if you ever lost it, and did not want to re generate it, you can just open the file Token.json alongside the application binaries and copy the value 
from it.

Wiki Server
===========

To use Wiki server features, you need to add wiki configurations in your configuration json file:

```json
{
    ...,
"wikiConfigurations": {
        "remote": "git@gitlab.example.com:awesome-project.git",
        "username": "<username>",
        "password": "<password>",
        "autoRefetchMinutes": 10,
        "wikiBranch":"master",
        "themeName":"Dark Blue"
    }
}

```

The _autoRefetchMinutes_ field, determines the interval for service to fetch wiki content from remote repository. but also you can 
trigger cicd assistant to re-fetch wiki content at any time by calling the fetch api:

|                       |                               |
|:---------------------:|:-----------------------------:|
|endpoint path          |  &lt;base-url&gt;/wiki/fetch  |
|Http Method            |  POST                         |
|Headers                |  token: &lt;token&gt;         |

With that, you can make a call from wiki repository to cicd-assistant whenever new content is pushed.

Themes
=======

Wiki can be rendered with different themes. Each theme is a css file inside the _wiki-styles_ directory at the 
application workspace. Cicd Assistant comes with three simple styles. You can add or edit these files to match your 
taste and preferences. To Add a theme to cicd-assistant please:

* Name the file &lt;theme-name&gt;-&lt;color-code&gt;.css
  * theme name will be used to recognize your theme.
  * color code, defines a color which describes the theme palette.
  * both these values are being used to render small theme selector icons on the top-left corner of each page
* Put your file under the directory _wiki-styles_
* Restart the service and navigate to __&lt;base-url&gt;/wiki__ to use the wiki with your theme.

Ssh Proxy
=========

In some cases you cant execute ssh commands from cicd pipelines directly. For example if your pipeline is being 
executed on a docker, for ssh to work, you need to install ssh on the docker image and also do some work-arouds 
for authentication over ssh. In such cases, you can put any target machin's credentials inside the cicd-assistant's 
configurations and just call ssh api with host name and command. The api finds correct credentials for given host,
creates an ssh session and executes your commands on the target machine.

|                       |                               |
|:---------------------:|:-----------------------------:|
|endpoint path          |  &lt;base-url&gt;/ssh         |
|Http Method            |  POST                         |
|Headers                |  token: &lt;token&gt;         |
|wwwform-url-encoded    |  host: &lt;example.host:22    |
|wwwform-url-encoded    |  command: wget http://source.com/application.zip && unzip  application.zip -d application |

* Host parameter can be followed with a port number. If not, the default would be 22.
* You can execute any commands that are available on the target machine.

__Response Body__:


```json
{
    "model": [
        "Welcome to Ubuntu 20.04.2 LTS (GNU/Linux 5.4.0-80-generic x86_64)",
        "",
        " * Documentation:  https://help.ubuntu.com",
        " * Management:     https://landscape.canonical.com",
        " * Support:        https://ubuntu.com/advantage",
        "",
        "0 updates can be applied immediately.",
        "",
        "Last login: Mon Aug  9 12:37:47 2021 from 127.0.0.1",
        "",
        "wget http://source.com/application.zip",
        ...
        "application.jar      13%[=>                  ]   1.98M   418KB/s    eta 31s    ",
        ...
    ],
    "failure": false,
    "error": ""
}
```
Credentials - Configurations
--------------

in a cicd design, you might target several machines with default or alternative ssh ports. You can add all targeted 
machines (usually deploy machines) with proper credentials in configurations like this:

```json
{
    ...,
    "sshSessions":[
        {
            "host": "product.example.com",
            "username": "root",
            "password": "4_v3ry-57r0n9-r007_P4$$w0rd",
            "port": 22424
        },...
    ] 
}

```

Usage Example
--------------


```bash
    curl -X POST http://cicd.source.server:8585/ssh -H "Content-Type: application/x-www-form-urlencoded" -d "command=ls -a&host=product.example.com:22424" -H "token: 23a910bc-...-06674476d3f8"
```

Fav Icon
========

You can provide an image file named favicon (ex: favicon.png, Favicon.bmp and etc.) beside the Configuration file inside the application directory. This image would be presented as Wiki's FavIcon. If the file is not present or in case of any issues loading the image file, the cicd-assistant logo will be used as default fav-icon.

Configurations
==============

Cicd-Assistant, stores all its configurations inside a json file, named Configurations.json. This file will be created first time you run the application and looks like this:

```json
{
    "mailSmtpServer": "mail.example.com",
    "credentials": {},
    "servicePort": 8585
}

```

* servicePort: This field determines what port the server will be started on. by default it's 8585. For example if you start 
your cicd-assistant instance on a server with the ip 23.128.23.4 with the port 8585, then your &lt;base-url&gt; would be 
http(s)://23.128.23.4:8585.

* mailSmtpServer: This field must be set to the address of your smtp mail server. It's usually like mail.example.com or smtp.example.com.

* credentials: This is where you put your smtp server accounts credential. Its a dictionary of username and passwords. When you send an email, using the email apis, based on the _from_ field, cicd-assistant finds the account and it's password from this configurations and uses
it to communicate with the smtp server. For example you might update your configurations as flowing to use Google smtp servers:

```json
{
    "mailSmtpServer": "smtp.gmail.com",
    "credentials": {
        "info.awsomecompany@gmail.com":"50m353(Ur3P4$$w0rd",
        "support.awsomecompany@gmail.com":"S3(0nD53(Ur3P4$$w0rd",
        "admin.awsomecompany@gmail.com":"07#3r53(Ur3P4$$w0rd"
    },
    "servicePort": 8585
}

```

Then you can close and re-start the application for configurations to take effect. After that, a call to email apis having the _from_ field 
equal to "info.awsomecompany@gmail.com", will cause cicd-assistant to pick the password associated with this account ("50m353(Ur3P4$$w0rd") 
and use it to send email(s).


License
======

This software is published under MIT license, as described in LICENSE.txt file. 
But I think i have to mention the code used for serving artifacts as resumable 
multipart downloads, is the from 
[davinkevin](https://github.com/davinkevin/Podcast-Server/blob/d927d9b8cb9ea1268af74316cd20b7192ca92da7/src/main/java/lan/dk/podcastserver/utils/multipart/MultipartFileSender.java)
 which handles the job pretty well.

