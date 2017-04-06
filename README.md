
# SecretTwits
Twitter Integration of OpenSecrets API 

## Let's get started
*To interact with the API through you'll have to setup your application.properties file*. 

I have a template in the "src/main/resources" folder called "application.properties.dist". The information in that file is almost complete as of this writing (March 30th, 2017), all you need to do is add your APIKey you get from here:

[OpenSecrets.org API](https://www.opensecrets.org/resources/create/apis.php)

You'll also need Twitter Apps keys which you can retrieve from [Twitter Apps](https://apps.twitter.com/)

You'll place those in the same directory as a "twitter4j.properties". I've provided a twitter4j.properties.dist file for reference or you can check the [Twitter4J configuration page](http://twitter4j.org/en/configuration.html)

## Examples
For examples of how to interact with the library, see the examples files under "src/main/java/com/anthonyolivieri/secrettwits". 

## More information
My OpenSecrets API returns JSONObject(s) by calling methods and supplying specific parameters. 
This implementation is attempting (work in progress) to get Twitter mentions (so when someone mentions my @TwitterHandle) that include the name of a candidate or representative such as "Pelosi, Nancy" or "Toomey, Pat" and return an image file of that candidate's top contributors by parsing the json returned. There's a lot of file writing and image creation magic going on. It has to be an image because the list of contributors and their contributions is too long for a normal Tweet.

Stay tuned. I'm still working on it. 

## License and Invitation to Edit
Please see the license information for this project. This is open-source software and anyone is welcomed to suggest changes or new features. 

