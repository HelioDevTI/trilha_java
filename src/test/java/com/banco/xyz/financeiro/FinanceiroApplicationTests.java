package com.banco.xyz.financeiro;

import com.banco.xyz.financeiro.api.cambio.ApiExternaCambio;
import com.banco.xyz.financeiro.api.cambio.ApiMockCambio;
import com.banco.xyz.financeiro.business.CalculoTransacoes;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class FinanceiroApplicationTests {


	@Mock
	private ApiExternaCambio apiExternaCambio;

	@Mock
	private ApiMockCambio apiMockCambio;


	@InjectMocks
	private CalculoTransacoes calculoTransacoes;

	@Test
	void contextLoads() {
	}

}
