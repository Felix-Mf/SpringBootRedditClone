package com.demo.redditclone.exceptions;

public class SpringBootRedditException extends RuntimeException {
	
    public SpringBootRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringBootRedditException(String exMessage) {
        super(exMessage);
    }
    
}
