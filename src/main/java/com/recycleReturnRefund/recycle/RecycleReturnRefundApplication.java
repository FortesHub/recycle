package com.recycleReturnRefund.recycle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecycleReturnRefundApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecycleReturnRefundApplication.class, args);

		String minhaString = "Esta é a minha string que eu quero imprimir.";
		System.out.println(minhaString);
	}
}
