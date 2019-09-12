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

        model.addAttribute("perHour", perHour);
        model.addAttribute("address", address);
        model.addAttribute("schoolInfo", schoolInfo);
        model.addAttribute("title", address.getName());

        return "/user/view";
    }

    //search by name, location (google map API), if potty trained, tuition less than x amount
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String displaySearch(Model model)
    {
        model.addAttribute("title","Search");
        //model.addAttribute(new UserSearch());
        return "/user/search";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam String searchName)
    {
        int i = 1;
        for(Address address : addressDao.findAll()){
            if(address.getName().equals(searchName)){
                Integer addressId = address.getId();
                SchoolInfo schoolInfo = schoolInfoDao.findByAddressId(addressId);

                Double perHourNum = (schoolInfo.getTuition())/(schoolInfo.getHours()*4);
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                String perHour = formatter.format(perHourNum);

                model.addAttribute("address", address);
                model.addAttribute("schoolInfo", schoolInfo);
                model.addAttribute("perHour", perHour);
                return "redirect:/user/view/" + addressId;
            }else{
                model.addAttribute("error", "There are no schools by that name.");
                return "/user/search";
            }

        }

        return "redirect:/user";
    }

}
