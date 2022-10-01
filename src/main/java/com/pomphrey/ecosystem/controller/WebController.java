package com.pomphrey.ecosystem.controller;

import com.pomphrey.ecosystem.exception.EcosystemNotExistException;
import com.pomphrey.ecosystem.model.worldstate.Ecosystem;
import com.pomphrey.ecosystem.repository.InitialConditionRepository;
import com.pomphrey.ecosystem.repository.InteractionRepository;
import com.pomphrey.ecosystem.repository.SpeciesRepository;
import com.pomphrey.ecosystem.repository.SummaryRepository;
import com.pomphrey.ecosystem.service.EcosystemService;
import com.pomphrey.ecosystem.model.presentation.PopulationChart;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.servlet.ServletUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class WebController {

    @Autowired
    EcosystemService ecosystemService;

    @Autowired
    SummaryRepository summaryRepository;

    @Autowired
    InitialConditionRepository conditionRepository;

    @Autowired
    InteractionRepository interactionRepository;

    @Autowired
    SpeciesRepository speciesRepository;

    @RequestMapping("/home")
    public String homePage(HttpServletRequest request, Model model) throws EcosystemNotExistException {
        mapHomeScreenAttributes(request, model);
        return "home.html";
    }

    @RequestMapping(value = "/home", params = {"initialise"})
    public String initialiseEcosystem(HttpServletRequest request, Model model) throws EcosystemNotExistException {
        ecosystemService.initialiseEcosystem();
        mapHomeScreenAttributes(request, model);
        return "home.html";

    }

    @RequestMapping(value = "/home", params = {"processOneYear"})
    public String processOneYear(HttpServletRequest request, Model model) throws EcosystemNotExistException {
        ecosystemService.processOneYear();
        mapHomeScreenAttributes(request, model);
        return "home.html";

    }

    public void mapHomeScreenAttributes(HttpServletRequest request, Model model) throws EcosystemNotExistException {
        if(ecosystemService.isEcosystemExists()) {
            model.addAttribute("ecosystemExists", true);
            Ecosystem ecosystem = ecosystemService.getEcosystem();
            model.addAttribute("ecosystemDate", ecosystem.getDate());
            model.addAttribute("populations", ecosystem.getPopulations());
        } else {
            model.addAttribute("ecosystemExists", false);
        }
        model.addAttribute("conditions",conditionRepository.findAll());
        model.addAttribute("interactions",interactionRepository.findAll());
        PopulationChart chart = new PopulationChart();
        chart.addSummaryData(summaryRepository.findAll());
        try {
            String chartFileName = ServletUtilities.saveChartAsJPEG(chart.drawChart(), 500,500, null, request.getSession());
            String chartUrl = request.getContextPath() + "/chart?filename=" + chartFileName;
            model.addAttribute("chartUrl", chartUrl);
        } catch (IOException ex){
            System.out.println(ex.getStackTrace());
        }
    }

    @RequestMapping("/species")
    public String speciesPage(HttpServletRequest request, Model model) throws EcosystemNotExistException {
        mapSpeciesScreenAttributes(request, model);
        return "species.html";
    }

    private void mapSpeciesScreenAttributes(HttpServletRequest request, Model model){
        model.addAttribute("speciesList",speciesRepository.findAll());
    }

}
