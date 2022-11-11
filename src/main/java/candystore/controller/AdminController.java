package candystore.controller;


import candystore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.ParseException;


@Controller
public class AdminController {

    private String trigger="";
    private int personal_id;
    @Autowired
    RecordsService recordsService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    BanListService banListService;
    @Value("${upload.path_user}")
    private String load_user;
    @Value("${upload.path_order}")
    private String load_order;
    @Value("${upload.path_employee}")
    private  String load_employee;


    @GetMapping("/admin_room:{id}")
    private String admin_menu(Model model, @PathVariable(value = "id")Integer id_admin) throws ParseException {
        recordsService.close_record();
        String tr=trigger;
        model.addAttribute("trigger",tr);
        model.addAttribute("pers_info",userService.getUserById(id_admin));
        model.addAttribute("all_user",userService.getAllUsers());
        model.addAttribute("service", orderService.getOrderList());
        model.addAttribute("ban",banListService.getBanListAll());
        model.addAttribute("employee",employeeService.getEmployeeList());
        trigger="";
        return "Admin_menu";
    }

    @PostMapping("/update_settings")
    private String update_settings(@RequestParam String name_set, @RequestParam String sur_set, @RequestParam String patr_set,
                                  @RequestParam String phone_set, @RequestParam String login_set, @RequestParam String password_set,
                                  @RequestParam(required = false) MultipartFile pers_photo
    ) throws IOException {
        userService.updateSettings(name_set,sur_set,patr_set,phone_set,login_set,password_set,pers_photo);
        trigger="settings";
        return "redirect:/admin_room:"+personal_id;
    }

    @PostMapping("/add_order")
    private String add_order(@RequestParam String type,@RequestParam String name_order,@RequestParam double price,@RequestParam MultipartFile photo_order) throws IOException {
        orderService.addOrder(type, name_order, price,photo_order);
        trigger="order";
        return "redirect:/admin_room:"+personal_id;
    }

    @GetMapping("/deleted_user:{id}")
    private String deleted_user(@PathVariable(value = "id")Integer user_id){
        userService.deletedUser(user_id);
        trigger="users";
        return "redirect:/admin_room:"+personal_id;
    }

    @GetMapping("/do_administration:{id}")
    private String do_administration(@PathVariable(value = "id")Integer user_id){
        userService.doAdmin(user_id);
        trigger="users";
        return "redirect:/admin_room:"+personal_id;
    }
    @GetMapping("/unblock_user:{id}")
    private String unblock_user(@PathVariable(value = "id")Integer user_id){
        userService.unblockUser(user_id);
        trigger="ban_list";
        return "redirect:/admin_room:"+personal_id;
    }

    @PostMapping("/block_user")
    private String block_user(@RequestParam String comment,@RequestParam int id_user){
        userService.blockUser(comment, id_user);
        trigger="users";
        return "redirect:/admin_room:"+personal_id;
    }

    @GetMapping("/deleted_employee:{id}")
    private String deleted_employee(@PathVariable(value = "id")Integer employee_id){
        employeeService.deleteEmployee(employee_id);
        trigger="employee";
        return "redirect:/admin_room:"+personal_id;
    }


    @PostMapping("/add_employee")
    private String add_employee(@RequestParam String name,@RequestParam String sur_name,@RequestParam String patronymic
            ,@RequestParam String phone,@RequestParam String position,@RequestParam int id_order,
                                @RequestParam MultipartFile photo_employee
    ) throws IOException {
        employeeService.addEmployee(name,sur_name,patronymic,phone,position,id_order,photo_employee);
        trigger="employee";
        return "redirect:/admin_room:"+personal_id;
    }
    @GetMapping("/deleted_order:{id}")
    private String deleted_order(@PathVariable(value = "id")Integer id_order){
        orderService.deleteOrder(id_order);
        trigger="order";
        return "redirect:/admin_room:"+personal_id;
    }
}
