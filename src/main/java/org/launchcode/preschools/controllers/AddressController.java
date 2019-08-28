package org.launchcode.preschools.controllers;


import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.Address;
import org.launchcode.preschools.models.data.AddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AddressController {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("title","Admin Page");
        model.addAttribute("addresses", addressDao.findAll());
        return "admin/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayNewAddressForm(Model model){
        model.addAttribute("title", "Add New Preschool");
        model.addAttribute(new Address());
        return "admin/address";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddNewAddressForm(@ModelAttribute @Valid Address newAddress, Errors errors,
                                           Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add New Preschool");
            return "admin/address";
        }

        addressDao.save(newAddress);
        return "/admin/schoolInfo"; //after address form is filled out, direct to info form

    }

}
