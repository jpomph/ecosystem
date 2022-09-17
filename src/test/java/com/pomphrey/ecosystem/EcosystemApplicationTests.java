package com.pomphrey.ecosystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;


@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(SpringRunner.class)
class EcosystemApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void findsSpecies() throws Exception{
		this.mockMvc.perform(get("/species/wolf")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("wolf")));

	}

	@Test
	void correctErrorWhenSpeciesNotFound() throws Exception{
		this.mockMvc.perform(get("/species/chicken")).andDo(print()).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Species not found")));

	}

}
