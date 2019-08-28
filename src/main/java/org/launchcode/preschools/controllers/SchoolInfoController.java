package org.launchcode.preschools.controllers;

import org.launchcode.preschools.models.data.SchoolInfoDao;
import org.launchcode.preschools.models.forms.SchoolInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("admin")
public class SchoolInfoController {

    @Autowired
    private SchoolInfoDao schoolInfoDao;

    static ArrayList<SchoolInfo> schoolInfos = new ArrayList<>(); //need this?

    @RequestMapping(value = "add-info", method = RequestMethod.POST)
    public String proccessSchoolInfoForm(@ModelAttribute @Valid SchoolInfo newSchoolInfo, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "");
            return "admin/schoolInfo";
        }

        schoolInfoDao.save(newSchoolInfo);
        return "/admin/index"; //display list of all schools...or just park entered...
    }
}