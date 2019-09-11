package org.launchcode.preschools.controllers;

import org.launchcode.preschools.models.data.AddressDao;
import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.Admin.Address;
import org.launchcode.preschools.models.forms.Admin.SchoolInfo;
import org.launchcode.preschools.models.forms.User.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    @RequestMapping(value = "user")
    public String index(Model model)
    {
        model.addAttribute("title", "Preschools");

        return "index";
    }

    @RequestMapping(value = "list")
    public String list(Model model)
    {
        model.addAttribute("title","Admin Page");
        model.addAttribute("addresses", addressDao.findAll());

        return "list";
    }

    @RequestMapping(value = "view/{addressId}", method = RequestMethod.GET)
    public String displaySchool(Model model, @PathVariable int addressId)
    {
        Address address = addressDao.findById(addressId).orElse(null);
        //calculate tuition / hours * 4

        SchoolInfo schoolInfo = schoolInfoDao.findByAddressId(addressId);
        model.addAttribute("address", address);
        model.addAttribute("schoolInfo", schoolInfo);
        model.addAttribute("title", address.getName());

        return "view";
    }

    //search by name, location (google map API), if potty trained, tuition less than x amount
    @RequestMapping(value = "/user/search", method = RequestMethod.GET)
    public String displaySearch(Model model)
    {
        model.addAttribute("title","Search");
       // model.addAttribute(new UserSearch());
        return "search";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute @Valid UserSearch userSearch)
    {
        model.addAttribute("userSearch",userSearch);
        return "redirect:display";
    }

}
