package com.pomphrey.ecosystem.dao;

import com.pomphrey.ecosystem.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpeciesDao {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public Species querySingleSpecies(String name){
        String query = "SELECT * FROM species WHERE NAME = :name;";
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        List<Species> speciesList;
        speciesList = template.query(query, params, new BeanPropertyRowMapper<>(Species.class));
        return speciesList.get(0);
    }

    public void insertSpecies(Species species){
        Map<String, String> params = new HashMap<>();
        params.put("name", species.getName());
        params.put("type", species.getType());
        params.put("food", species.getFood());
        params.put("requirement", Integer.toString(species.getRequirement()));
        params.put("mature_age", Integer.toString(species.getMaturity_age()));
        params.put("senile_age", Integer.toString(species.getSenility_age()));
        params.put("infant_survival", Double.toString(species.getInfant_survival()));
        params.put("senile_survival", Double.toString(species.getSenile_survival()));
        params.put("offspring", Integer.toString(species.getOffspring()));
        params.put("gestation", Integer.toString(species.getGestation()));
        params.put("reproduction_period", Integer.toString(species.getReproduction_period()));

        String query = "insert into species values (:name, :type, :food, :requirement, :mature_age, :senile_age, " +
                ":infant_survival, :senile_survival, :offspring, :gestation, :reproduction_period)";

        template.update(query, params);
    }

}
