package com.pruebas.mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebas.mockito.controller.CtrlCuenta;
import com.pruebas.mockito.entity.Cuenta;
import com.pruebas.mockito.entity.TransaccionDTO;
import com.pruebas.mockito.service.SvcCuenta;

@WebMvcTest(CtrlCuenta.class)
public class CtrlCuentaTest
{
	@Autowired
	private MockMvc mockMVC;
	
	@MockBean
	private SvcCuenta svcCuenta;

	ObjectMapper objMapper;

	@BeforeEach
	void configurar()
	{
		objMapper = new ObjectMapper();
	}
	
	@Test
	void testVerDetalles() throws Exception
	{
		when(svcCuenta.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow());
		
		mockMVC.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.persona").value("ADLG"))
			.andExpect(jsonPath("$.saldo").value("33000"));
		
		verify(svcCuenta).findById(1L);
	}
	
	@Test
	void testTransferirDinero() throws Exception
	{
		TransaccionDTO transaccionDTO = new TransaccionDTO();
		transaccionDTO.setCuentaOrigenId(1L);
		transaccionDTO.setCuentaDestinoId(2L);
		transaccionDTO.setMonto(new BigDecimal("100"));
		transaccionDTO.setBancoId(1L);

		System.out.println(objMapper.writeValueAsString(transaccionDTO));
		
		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put("date", LocalDate.now().toString());
		respuesta.put("status", "OK");
		respuesta.put("mensaje", "Tranferencia realizada con exito");
		respuesta.put("transaccionDTO", transaccionDTO);
		
		System.out.println(objMapper.writeValueAsString(respuesta));

		mockMVC.perform(post("/api/cuentas/transferir").contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(transaccionDTO)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.mensaje").value("Tranferencia realizada con exito"))
				.andExpect(jsonPath("$.transaccionDTO.cuentaOrigenId").value(transaccionDTO.getCuentaOrigenId()))
				.andExpect(content().json(objMapper.writeValueAsString(respuesta)));
	}

	@Test
	void testListasCuentas() throws Exception
	{
		List<Cuenta> cuentas = Arrays.asList(Datos.crearCuenta001().orElseThrow()
											,Datos.crearCuenta002().orElseThrow());
		when(svcCuenta.listAll()).thenReturn(cuentas);
		
		mockMVC.perform(get("/api/cuentas").contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].persona").value("ADLG"))
		.andExpect(jsonPath("$[1].persona").value("CJ"))
		.andExpect(jsonPath("$[0].saldo").value("33000"))
		.andExpect(jsonPath("$[1].saldo").value("15000"))
		.andExpect(jsonPath("$",hasSize(2)))
		.andExpect(content().json(objMapper.writeValueAsString(cuentas)));
		
		verify(svcCuenta).listAll();
	}

	@Test
	void guardarCuenta() throws Exception
	{
		Cuenta cuenta = new Cuenta(3 ,"Sweet", new BigDecimal(9700));
		when(svcCuenta.save(any())).then(invocation ->
		{
			Cuenta c = invocation.getArgument(0);
			c.setId(3L);
			return c;
		});
		
		mockMVC.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(objMapper.writeValueAsString(cuenta)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id",is(3)))
				.andExpect(jsonPath("$.persona",is("Sweet")))
				.andExpect(jsonPath("$.saldo",is(9700)));
		
		verify(svcCuenta).save(any());
	}
}
