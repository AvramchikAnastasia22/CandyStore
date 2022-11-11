package candystore.controller;

import candystore.model.User;
import candystore.repository.UserRepository;
import candystore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



@Controller
public class Autorization_controller {

    private String trigger="";
    @Autowired
    UserService userService;


    @GetMapping("/candystore")
    private String candystore(Model model){
       userService.first_registration();
        String tr=trigger;
        model.addAttribute("trigger",tr);
        trigger="";
        return "Autorization";
    }

    @PostMapping("/registration_user")
    private String registration_user(@RequestParam String name, @RequestParam String sur_name, @RequestParam String patronymic,
                                     @RequestParam String phone, @RequestParam String login, @RequestParam String password
    ){
        userService.registration(name, sur_name,patronymic,phone,login,password);
        trigger="registration";
        return "redirect:/candystore";
    }

    @PostMapping("/autorization_user")
    private String autorization_user(@RequestParam String login, @RequestParam String password){

        List<User> list_employee=userService.getAllUsers();
        Boolean check=false;
        for(int i=0;i<list_employee.size();i++){
            if(list_employee.get(i).getLogin().equals(login)&&list_employee.get(i).getPassword().equals(password)){
                check=true;
                if(list_employee.get(i).getStatus().equals("Заблокировано")){
                    trigger="ban";
                    return ("redirect:/candystore");
                }
                else {
                    if(list_employee.get(i).getType().equals("Admin")){
                        return "redirect:/admin_room:"+list_employee.get(i).getId();
                    }
                    else {
                        return "redirect:/user_room:"+list_employee.get(i).getId();
                    }
                }
            }
        }
        if(check!=true){
            trigger="no found";
        }
        return ("redirect:/candystore");
    }
}
