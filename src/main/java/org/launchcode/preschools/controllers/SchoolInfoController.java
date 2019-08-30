package org.launchcode.preschools.controllers;

import org.launchcode.preschools.models.data.AddressDao;
import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.SchoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class SchoolInfoController {

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    @Autowired
    private AddressDao addressDao;

    @RequestMapping(value = "schoolInfo", method = RequestMethod.GET)
    public String displaySchoolInfoForm(Model model)
    {
        model.addAttribute("title", "Add School Information");
        model.addAttribute(new SchoolInfo());
        return "admin/schoolInfo";
    }

    @RequestMapping(value = "schoolInfo", method = RequestMethod.POST)
    public String processSchoolInfoForm(@ModelAttribute @Valid SchoolInfo newSchoolInfo, Errors errors,
                                        Model model)
    {
        if (errors.hasErrors())
        {
            model.addAttribute("title", "Add School Information");
            return "admin/schoolInfo";
        }
        schoolInfoDao.save(newSchoolInfo);
        return "/admin/index"; //display list of all schools...or just school entered...?
    }
}