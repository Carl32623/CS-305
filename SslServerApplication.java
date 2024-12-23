package com.snhu.sslserver;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
//FIXME: Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";

@RestController
class ServerController{    
    @RequestMapping("/hash")
    public String myHash(){
    	String data = "Carl LaLonde";
    	String checkSum;
    	
    	try {
    		//Creating the MessageDigest object.
    		MessageDigest md = MessageDigest.getInstance("SHA-256");
    		
    		//Passing data to the created MessageDigest object.
    		md.update(data.getBytes());
    		
    		//Compute the MessageDigest.
    		byte[] encodedHash = md.digest(data.getBytes(StandardCharsets.UTF_8));
    		
    		//Converts Hash bytes to Hex
    		checkSum = bytesToHex(encodedHash);
    			
    	} catch (NoSuchAlgorithmException e) {
    		return "Error: Unable to generate CheckSum.";
    	}
    	
    	
       
        return "<p>data:" + data + "<br>algorithm: SHA-256<br>checksum: " + checkSum + "</p>";
    }
    
    /**
     * This function converts bytes to hexadecimal.  It takes each byte, put into an
     * array, and processes it into a two character hexadecimal.  
     * 
     * Adapted from Baeldung's tutorial on SHA-256 hashing in java
     * Original Article: https://www.baeldung.com/sha-256-hashing-java
     * 
     * @param hash, the byte array to be converted to hexadecimal
     * @return String in hexadecimal format.
     */
      private static String bytesToHex(byte[] hash) {
      	StringBuilder hexString = new StringBuilder(2 * hash.length);
      	for (int i = 0; i < hash.length; i++) {
      		String hex = Integer.toHexString(0xff & hash[i]);
      		if (hex.length() == 1) {
      			hexString.append('0');
      		}
      		hexString.append(hex);
      	}
      	return hexString.toString();
      }

  }