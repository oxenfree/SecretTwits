# OpensecretsAPI
A basic Java library for interacting with the Center for Responsive Politics API at https://www.opensecrets.org/resources/create/api_doc.php

## Let's get started
*To interact with the API through you'll have to setup your application.properties file*. 

I have a template in the "src/main/resources" folder called "application.properties.dist". The information in that file is almost complete as of this writing (March 30th, 2017), all you need to do is add your APIKey you get from here:

[OpenSecrets.org API](https://www.opensecrets.org/resources/create/apis.php)

## Examples
For examples of how to interact with the library, see the Examples.java file under "src/main/resources". 

## More information
The API returns JSONObject(s) by calling methods and supplying specific parameters. 

The parameters are typically identification strings which have been set by Open Secrets. For example, to find a specific representative or candidate you'll need to know that person's "CID" or "Candidate ID" which you'll need to get from [their API documentation](https://www.opensecrets.org/resources/create/api_doc.php). 

There are other ID's available including Committee ID's, Industry ID's, Sector ID's, and specific Organization ID's. With these in hand you can find very specific stuff, including the top contributors to a candidate, the top contributions by industry or sector, and even a candidate's Twitter handle and fax line. 

## License and Invitation to Edit
Please see the license information for this project. This is open-source software and anyone is welcomed to suggest changes or new features. 
