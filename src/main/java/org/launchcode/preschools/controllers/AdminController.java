package org.launchcode.preschools.controllers;

import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.Admin.Address;
import org.launchcode.preschools.models.data.AddressDao;
import org.launchcode.preschools.models.forms.Admin.SchoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    public Double pricePerHour;

    @RequestMapping(value = "")
    public String index(Model model)
    {
        model.addAttribute("title","Admin Page");
        model.addAttribute("addresses", addressDao.findAll());
        return "admin/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayNewAddressForm(Model model)
    {
        model.addAttribute("title", "Add New Preschool");
        model.addAttribute(new Address());
        return "admin/address";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddNewAddressForm(@ModelAttribute @Valid Address newAddress, Errors errors,
                                           Model model)
    {
        if(errors.hasErrors())
        {
            model.addAttribute("title", "Add New Preschool");
            return "admin/address";
        }
        addressDao.save(newAddress);
        return "redirect:/admin/schoolInfo"; //after address form is filled out, redirect to info form

    }

    @RequestMapping(value = "schoolInfo", method = RequestMethod.GET)
    public String displaySchoolInfoForm(Model model)
    {
        model.addAttribute("title", "Add School Information");
        /*TODO: pull the school name and add to title, possible error enter address
        without entering info, leaving incorrect mapping */
        model.addAttribute(new SchoolInfo());
        return "admin/schoolInfo";
    }

    @RequestMapping(value = "schoolInfo", method = RequestMethod.POST)
    public String processSchoolInfoForm(@ModelAttribute @Valid SchoolInfo newSchoolInfo, Errors errors,
                                        Model model)
    {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add School Information");
            return "admin/schoolInfo";
        }
        schoolInfoDao.save(newSchoolInfo);
        return "redirect:/admin"; //display list of all schools...or just school entered...?
    }

    @RequestMapping(value = "displaySchool/{addressId}", method = RequestMethod.GET)
    public String displaySchool(Model model, @PathVariable int addressId)
    {
        Address address = addressDao.findById(addressId).orElse(null);
        //get schoolInfo from FK,
        //SchoolInfo schoolInfo = schoolInfoDoa.findById
        //model.addAttribute("schoolInfo", schoolInfo)
        model.addAttribute("address", address);
        model.addAttribute("title", address.getName());
        return "admin/displaySchool";
    }

    @RequestMapping(value = "deleteSchool", method = RequestMethod.GET)
    public String displayDeleteSchool(Model model)
    {
        model.addAttribute("addresses", addressDao.findAll());
        model.addAttribute("title", "Delete Preschool");
        return "redirect:/";
    }

}
