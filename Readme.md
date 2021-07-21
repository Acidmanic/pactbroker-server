

Cicd Assistant
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
* Provides an artifact server
  * artifacts can be uploaded to artifacts server through multipart post call to artifacts/upload
  * uploaded artifacts can be downloaded by their names from artifacts/<artifact-file-name>


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
|endpoint path          |  &lt;base-url&gt;/mail/send   |
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


License
======

This software is published under MIT license, as described in LICENSE.txt file. 
But I think i have to mention the code used for serving artifacts as resumable 
multipart downloads, is the from 
[davinkevin](https://github.com/davinkevin/Podcast-Server/blob/d927d9b8cb9ea1268af74316cd20b7192ca92da7/src/main/java/lan/dk/podcastserver/utils/multipart/MultipartFileSender.java)
 which handles the job pretty well.
