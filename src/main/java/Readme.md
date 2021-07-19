

Cicd Assistant
==============



About
-----

This application is a standalone server providing some features helpful for 
setting up and performing cicd tasks.



Features:
---------

* It serves as a PACT broker
  * provides push operation: contract collection (pact) will be ready for download by clients
  * provides pull operation: clients will download the contract collection (pact)
  * provides store operation: stores a contract collection (pact) corresponding with a tag, but still its not selected for being downloaded
  * provides elect operation: sets a contract collection (pact), the one that will be downloaded by clients
* It serves as a badge broker based on tags
  * Success badge
  * Failure badge
  * Pending badge (Otherwise)
* Provides Api calls for sending emails
  * Uses smtp server address and credentials from configurations
  * Api for self-contained email body 
  * Api for template based email body
  * Api for registering templates
  

Features to be implemented in future
------------------------------------


* Serving as a download proxy
* Serving as an artifact server

