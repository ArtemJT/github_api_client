## github_api_client

### Acceptance criteria:

An api consumer, given username and header “Accept: application/json”, will get a list of all his github repositories, which are not forks. Information, which required in the response, is:

> Repository Name

> Owner Login

For each branch it’s name and last commit sha. 

An api consumer, given not existing github user, will receive 404 response in such a format:

	{

    	“status”: ${responseCode}
    
    	“message”: ${whyHasItHappened}
    
	}

#### For connection to GitHub, used recommended library GitHub API for Java:

    <dependency>
        <groupId>org.kohsuke</groupId>
        <artifactId>github-api</artifactId>
        <version>1.318</version>
    </dependency>


#### Links:

https://github-api.kohsuke.org/

https://docs.github.com/en/rest/using-the-rest-api/libraries-for-the-rest-api?apiVersion=2022-11-28
