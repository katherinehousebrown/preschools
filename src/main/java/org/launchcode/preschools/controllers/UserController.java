package org.launchcode.preschools.controllers;

import org.launchcode.preschools.models.data.AddressDao;
import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.Admin.Address;
import org.launchcode.preschools.models.forms.Admin.SchoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.NumberFormat;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    @RequestMapping(value = "")
    public String index(Model model)
    {
        model.addAttribute("title", "Preschools");

        return "user/index";
    }

    @RequestMapping(value = "list")
    public String list(Model model)
    {
        model.addAttribute("title","All Preschools");
        model.addAttribute("addresses", addressDao.findAll());

        return "user/list";
    }

    @RequestMapping(value = "view/{addressId}", method = RequestMethod.GET)
    public String displaySchool(Model model, @PathVariable int addressId)
    {
        Address address = addressDao.findById(addressId).orElse(null);
        SchoolInfo schoolInfo = schoolInfoDao.findByAddressId(addressId);

        Double perHourNum = (schoolInfo.getTuition())/(schoolInfo.getHours()*4);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String perHour = formatter.format(perHourNum);
        Double tuition = schoolInfo.getTuition();
        String tuition_currency = formatter.format(tuition);

        model.addAttribute("tuition", tuition_currency);
        model.addAttribute("perHour", perHour);
        model.addAttribute("address", address);
        model.addAttribute("schoolInfo", schoolInfo);
        model.addAttribute("title", address.getName());

        return "/user/view";
    }

    @RequestMapping(value="/search/searchMethod", method = RequestMethod.GET)
    public String displaySearchMethod(Model model){
        model.addAttribute("title", "How would you like to search?");
        return "/user/search/searchMethod";
    }

    @RequestMapping(value = "search/searchMethod", method = RequestMethod.POST)
    public String processSearchMethod(Model model, @RequestParam String searchChoice){
        if(searchChoice.equals("name")){
            model.addAttribute("title", "Search by Name");
            return "redirect:/user/search/name";
        }
        if (searchChoice.equals("pottyTrained")) {
            model.addAttribute("title", "Search by Potty Trained");
            return "redirect:/user/search/pottyTrained";
        }
        if (searchChoice.equals("tuition")){
            model.addAttribute("title", "Search by Tuition");
            return "redirect:/user/search/tuition";
        }
        if (searchChoice.equals("location")) {
            model.addAttribute("title", "Search by Location");
            return "redirect:/user/search/location";
        }
        return "redirect:/user/search/searchMethod";
    }

    //search by name, location (google map API), if potty trained, tuition less than x amount
    @RequestMapping(value = "/search/name", method = RequestMethod.GET)
    public String displaySearch(Model model)
    {
        model.addAttribute("title","Search by Name");
        //model.addAttribute(new UserSearch());
        return "/user/search/name";
    }

    @RequestMapping(value = "/search/name", method = RequestMethod.POST)
    public String search(Model model, @RequestParam String searchName)
    {
        for(Address address : addressDao.findAll()) {
            if (address.getName().toLowerCase().equals(searchName.toLowerCase())) {
                Integer addressId = address.getId();
                SchoolInfo schoolInfo = schoolInfoDao.findByAddressId(addressId);

                Double perHourNum = (schoolInfo.getTuition()) / (schoolInfo.getHours() * 4);
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                String perHour = formatter.format(perHourNum);

                model.addAttribute("address", address);
                model.addAttribute("schoolInfo", schoolInfo);
                model.addAttribute("perHour", perHour);
                return "redirect:/user/view/" + addressId;
            }
        }
        model.addAttribute("error", "There are no schools by that name.");
        return "/user/search/name";
    }

    @RequestMapping(value = "/search/pottyTrained", method = RequestMethod.GET)
    public String displaySearchPottyTrained(Model model)
    {
        model.addAttribute("title", "Search by Potty Trained");
        return "/user/search/pottyTrained";
    }





}
