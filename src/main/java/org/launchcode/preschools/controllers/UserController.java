package org.launchcode.preschools.controllers;

import org.launchcode.preschools.models.data.AddressDao;
import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.Admin.Address;
import org.launchcode.preschools.models.forms.Admin.SchoolInfo;
import org.launchcode.preschools.models.forms.Admin.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        model.addAttribute("title","Admin Page");
        model.addAttribute("addresses", addressDao.findAll());

        return "user/list";
    }

    @RequestMapping(value = "view/{addressId}", method = RequestMethod.GET)
    public String displaySchool(Model model, @PathVariable int addressId)
    {
        Address address = addressDao.findById(addressId).orElse(null);
        SchoolInfo schoolInfo = schoolInfoDao.findByAddressId(addressId);

        Double perHourNum = (schoolInfo.getTuition())*4/(schoolInfo.getHours());

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
        //WHy won't it accept an empty new object? new UserSearch()
        model.addAttribute(new UserSearch("searchResults"));

        return "/user/search";
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute @Valid UserSearch userSearch)
    {


        model.addAttribute("userSearch",userSearch);
        return "redirect:/user/display";
    }

}
