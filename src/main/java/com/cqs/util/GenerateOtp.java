package com.cqs.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class GenerateOtp implements Serializable {
	
	
	private static final long serialVersionUID = -4795029445307728434L;
	
	private final static Integer LENGTH = 4;
    private final static int n= 12;
	public String generateCode() {
		String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
	    String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
	    String numbers = RandomStringUtils.randomNumeric(2);
	    String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
	    String totalChars = RandomStringUtils.randomAlphanumeric(2);
	    String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
	      .concat(numbers)
	      .concat(specialChar)
	      .concat(totalChars);
	    List<Character> pwdChars = combinedChars.chars()
	      .mapToObj(c -> (char) c)
	      .collect(Collectors.toList());
	    Collections.shuffle(pwdChars);
	    String password = pwdChars.stream()
	      .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
	      .toString();
	    return password;
	}
	

    public Supplier<String> generateOtp() {
        return () -> {
            Random random = new Random();
            StringBuilder oneTimePassword = new StringBuilder();
            for (int i = 0; i < LENGTH; i++) {
                int randomNumber = random.nextInt(10);
                oneTimePassword.append(randomNumber);
            }
            return oneTimePassword.toString().trim();
        };
    }

}
