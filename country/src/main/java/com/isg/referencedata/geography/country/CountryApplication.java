package com.isg.referencedata.geography.country;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition( info = @Info( title = "Country", 
description = "Country Entity Rest API", 
contact = @Contact( name = "", url = "", email = "" ),
license = @License( name = "ISG Licence", url = "")), 
servers = @Server(url = "http://localhost:9090") )

public class CountryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryApplication.class, args);
	}

}
