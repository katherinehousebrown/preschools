package org.launchcode.preschools.controllers;

import org.launchcode.preschools.models.data.AddressDao;
import org.launchcode.preschools.models.data.AdminDao;
import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.Admin.Address;
import org.launchcode.preschools.models.forms.Admin.Admin;
import org.launchcode.preschools.models.forms.Admin.SchoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.NumberFormat;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    @Autowired
    private AdminDao adminDao;

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
        Address address = addressDao.findById(newAddressFK).orElse(null);

        model.addAttribute("newAddressFK", newAddressFK);
        model.addAttribute("title", "Enter " + address.getName() + "'s Information" );
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

        Optional<Address> newAddress = addressDao.findById(newAddressFK);

        Address presentAddress;
        if (newAddress.isPresent()) {
            presentAddress = newAddress.get();
        } else {
            presentAddress = new Address();
        }

        newSchoolInfo.setAddress(presentAddress);

        //calculate and save cost per hour
        Double perHourNum = (newSchoolInfo.getTuition())*4/(newSchoolInfo.getHours());
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String perHour = formatter.format(perHourNum);
        newSchoolInfo.setPerHour(perHour);

        schoolInfoDao.save(newSchoolInfo);

        //TODO: add perHour to the SchoolInfo database, tried delete and save again
      //  schoolInfoDao.save(newSchoolInfo);

        return "redirect:/admin"; //display list of all schools...or just school entered...?
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

        return "admin/view";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String displayDeleteSchool(Model model)
    {
        model.addAttribute("addresses", addressDao.findAll());
        model.addAttribute("title", "Delete Preschool");
        return "/admin/delete";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String processDeleteSchool(@RequestParam int[] addressIds)
    {
        for (int addressId : addressIds)
        {
            schoolInfoDao.delete(schoolInfoDao.findByAddressId(addressId));
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayAdminLogin(Model model)
    {
        model.addAttribute("title", "Admin Login");
        model.addAttribute(new Admin());
        return "/admin/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processAdminLogin(Model model, @ModelAttribute @Valid Admin admin)
    {
        String username = admin.getUserName();
        String password = admin.getPassword();

        model.addAttribute("title", "Admin Login");

        for (Admin admins : adminDao.findAll()) {
            String oneUsername = admins.getUserName();
            String onePassword = admins.getPassword();
            if ((username.toLowerCase().equals(oneUsername.toLowerCase())) & (password.toLowerCase().equals(onePassword.toLowerCase()))){
                return "redirect:/admin";
            }else{
                model.addAttribute("error", "INCORRECT USERNAME and/or PASSWORD, please try again");
                model.addAttribute("title", "Admin Login");
                return "/admin/login";
                }
            }
        return "redirect:/";
    }

}
