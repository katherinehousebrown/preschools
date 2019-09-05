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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    public Double pricePerHour;
    public Integer newAddressFK;

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

        newAddressFK = newAddress.getId();
        model.addAttribute("newAddressFK", newAddressFK); //pass FK to view (hidden)

        return "redirect:/admin/schoolInfo";

    }

    @RequestMapping(value = "schoolInfo", method = RequestMethod.GET)
    public String displaySchoolInfoForm(Model model)
    {
        model.addAttribute("title", "Add School Information");
        /*TODO: pull the school name and add to title, possible error enter address
        without entering info, leaving incorrect mapping */
        model.addAttribute("newAddressFK", newAddressFK);
        model.addAttribute(new SchoolInfo());
        return "admin/schoolInfo";
    }

    @RequestMapping(value = "schoolInfo", method = RequestMethod.POST)
    public String processSchoolInfoForm(@ModelAttribute @Valid SchoolInfo newSchoolInfo, @RequestParam("newAddressFK") Integer newAddressFK,
                                        Errors errors, Model model)
    {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add School Information");
            return "admin/schoolInfo";
        }
        int newAddressFKInt = Integer.valueOf(newAddressFK);

        Optional<Address>newAddress = addressDao.findById(newAddressFK);

        Address presentAddress;
        if (newAddress.isPresent()) {
            presentAddress = newAddress.get();
        } else {
            presentAddress = new Address();
        }
        newSchoolInfo.setAddress(presentAddress);
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

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String displayDeleteSchool(Model model)
    {
        model.addAttribute("addresses", addressDao.findAll());
        model.addAttribute("title", "Delete Preschool");
        return "redirect";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String processDeleteSchool(@RequestParam int[] addressIds)
    {
        for (int addressId : addressIds) {
            Address address = addressDao.findById(addressId).orElse(null);

            addressDao.delete(address); //check to make sure deletes corresponding row in School Info
        }
        return "redirect:/admin/index";
    }

}
