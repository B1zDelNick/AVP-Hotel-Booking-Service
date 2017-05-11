package com.avp.booking;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class HotelsBookingServiceApplication
{
	public static void main(String[] args)
	{
		new SpringApplicationBuilder()
				.profiles("dev")//.profiles("default")
				.sources(HotelsBookingServiceApplication.class)
				.run(args);
	}
}
