package com.pomphrey.ecosystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pomphrey.ecosystem.config.Constants;
import com.pomphrey.ecosystem.model.InitialCondition;
import com.pomphrey.ecosystem.model.Interaction;
import com.pomphrey.ecosystem.model.InteractionKey;
import com.pomphrey.ecosystem.model.Species;
import com.pomphrey.ecosystem.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional(Transactional.TxType.REQUIRES_NEW)
@Rollback(false)//@ExtendWith(SpringRunner.class)
class EcosystemApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	Constants constants;

	@Test
	void contextLoads() {
	}

	@Test
	void findSpeciesReturnsoCorrectResponse() throws Exception{
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

	@Test
	void addNewSpeciesWorksCorrectly() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/species/add").content(asJsonString(Utils.createChickeen())).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(get("/species/chicken")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("chicken")));
	}

	@Test
	void addDuplicateSpeciesReturnsError() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.post("/species/add").content(asJsonString(Utils.createWolf())).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(constants.SPECIES_ALREADY_EXISTS)));
	}

	@Test
	void updateSpeciesWorksCorrectly() throws Exception{
		Species updatedWolf = Utils.createWolf();
		updatedWolf.setGestation(99);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/species/update").content(asJsonString(updatedWolf)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(get("/species/wolf")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("\"gestation\":99")));
	}

	@Test
	void updateInexistentSpeciesReturnsCorrectError() throws Exception{
		Species updatedChicken = Utils.createChickeen();
		updatedChicken.setGestation(99);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/species/update").content(asJsonString(updatedChicken)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(constants.SPECIES_DOESNT_EXIST)))
		;
	}

	@Test
	void updateInvalidSpeciesReturnsCorrectError() throws Exception{
		assertThrows(IllegalArgumentException.class, ()->
				{
					Species updatedChicken = Utils.createChickeen();
					updatedChicken.setType("X");
					this.mockMvc.perform(MockMvcRequestBuilders.post("/species/update").content(asJsonString(updatedChicken)).contentType(MediaType.APPLICATION_JSON))
							.andExpect(status().isBadRequest())
							.andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException));
				}
		);
	}

	@Test
	void findInteractionReturnsCorrectResponse() throws Exception{
		this.mockMvc.perform(get("/interaction/query").param("species1","wolf").
						param("species2","rabbit")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("wolf")));

	}

	@Test
	void notFoundInteractionReturnsCorrectResponse() throws Exception{
		this.mockMvc.perform(get("/interaction/query").param("species1","wolf").
						param("species2","chicken")).andDo(print()).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(constants.INTERACTION_DOESNT_EXIST)));

	}

	@Test
	void addInteractionWorks() throws Exception{
		Interaction newInteraction = Utils.createChickenGrainInteraction();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/interaction/add")
				.content(asJsonString(newInteraction)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(get("/interaction/query").param("species1",newInteraction.getInteractionKey().getSpecies1()).
						param(newInteraction.getInteractionKey().getSpecies2())).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("chicken")));
	}

	@Test
	void addExistingInteractionReturnsCorrectError() throws Exception{
		Interaction newInteraction = new Interaction(new InteractionKey("wolf","rabbit"),"p",356);
		this.mockMvc.perform(MockMvcRequestBuilders.post("/interaction/add")
				.content(asJsonString(newInteraction)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(constants.INTERACTION_ALREADY_EXISTS)));
		;

	}

	@Test
	void deleteInteractionWorks() throws Exception{
		Interaction newInteraction = Utils.createChickenGrainInteraction();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/interaction/add")
				.content(asJsonString(newInteraction)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(get("/interaction/delete").param("species1",newInteraction.getInteractionKey().getSpecies1()).
						param("species2",newInteraction.getInteractionKey().getSpecies2())).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("deleted")));
	}

	@Test
	void listInteractionWorks() throws Exception{
		Interaction newInteraction1 = Utils.createChickenGrainInteraction();
		Interaction newInteraction2 = Utils.createChickenGrassInteraction();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/interaction/add")
				.content(asJsonString(newInteraction1)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(MockMvcRequestBuilders.post("/interaction/add")
				.content(asJsonString(newInteraction2)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		this.mockMvc.perform(get("/interaction/list").param("species1",newInteraction1.getInteractionKey().getSpecies1()))
				.andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("grain")))
				.andExpect(content().string(containsString("grass")));
	}

	@Test
	void testQueryConditionWorks() throws Exception {
		this.mockMvc.perform(get("/condition/query/wolf")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("wolf")));
	}

	@Test
	void addConditionWorks() throws Exception{
		InitialCondition chickenCondition = Utils.createChickenCondition();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/condition/add")
						.content(asJsonString(chickenCondition)).contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isOk());
		this.mockMvc.perform(get("/condition/query/chicken")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("chicken")));
	}
	@Test
	void deleteConditionWorks() throws Exception{
		InitialCondition chickenCondition = Utils.createChickenCondition();
		this.mockMvc.perform(MockMvcRequestBuilders.post("/condition/add")
						.content(asJsonString(chickenCondition)).contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isOk());
		this.mockMvc.perform(get("/condition/delete/chicken")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(constants.CONDITION_DELETED)));
		this.mockMvc.perform(get("/condition/query/chicken")).andDo(print()).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(constants.NO_CONDITION_FOUND)));
	}

	@Test
	void listConditionWorks() throws Exception{
		this.mockMvc.perform(get("/condition/list")).andDo(print()).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("wolf")))
				.andExpect(content().string(containsString("rabbit")));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
