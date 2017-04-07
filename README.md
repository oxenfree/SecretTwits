
# SecretTwits
Twitter Integration of OpenSecrets API 

## The Grand Plan
To get the top contributions to a political candidate, just tweet me with the candidates name in this style: Lastname, Firstname. For example: Warren, Elizabeth or Booker, Cory or Collins, Susan. SecretTwits then replies to your tweet with an image, uploaded to Twitter, with a list of top contributions for that candidate or representative. (Has to be an image because the list of contributors and their total contributions is over 140 characters long.) 

It works currently, I've tested it a few times live with the help of my friend. What I need to do now is set up automation, and considering Twitter's rate limit, that probably means a crontab to run every 15 minutes or so. If you tweet me right now, you probably won't get a SecretTwits reply until I do that.

## More information
My OpenSecrets API returns JSONObject(s) by calling methods and supplying specific parameters. (You can find that repository under my profile here on Github.)

SecretTwits is attempting (work in progress) to get Twitter mentions that include the name of a candidate or representative such as "Pelosi, Nancy" or "Toomey, Pat" and return an image file of that candidate's top contributors. That involves parsing the JSON from my OpenSecrets API, formatting it into a more readable form, creating an image with that text, and interacting with the Twitter4J library to post a reply back to Twitter. There's a lot of file writing and image creation magic going on. It has to be an image because the list of contributors and their contributions is too long for a normal Tweet.

Stay tuned. I'm still working on it. 

## License and Invitation to Edit
Please see the license information for this project. This is open-source software and anyone is welcomed to suggest changes or new features. 

