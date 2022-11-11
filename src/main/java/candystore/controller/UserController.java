package candystore.controller;


import candystore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;


@Controller
public class UserController {

    private String trigger = "";
    @Autowired
    RecordsService recordsService;
    @Autowired
    UserService userService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    HistoryService historyService;
    @Autowired
    OrderService orderService;
    private int pers_id;


    @PostMapping("/update_settings_user")
    private String update_settings(@RequestParam String name_set, @RequestParam String sur_set, @RequestParam String patr_set,
                                   @RequestParam String phone_set, @RequestParam String login_set, @RequestParam String password_set,
                                   @RequestParam(required = false) MultipartFile pers_photo
    ) throws IOException {
        userService.updateSettings(name_set, sur_set, patr_set, phone_set, login_set, password_set, pers_photo);
        trigger = "settins";
        return "redirect:/user_room:" + pers_id;
    }

    @GetMapping("/user_room:{id}")
    private String user_menu(Model model, @PathVariable(value = "id") Integer id_user) throws ParseException {
        recordsService.close_record();
        String tr = trigger;
        model.addAttribute("trigger", tr);
        model.addAttribute("pers_info", userService.getUserById(id_user));
        model.addAttribute("all_employee", employeeService.getEmployeeList());
        model.addAttribute("list_history", historyService.sortHistory(historyService.getHistoryList()));
        model.addAttribute("list_record", recordsService.sortRecord(recordsService.getRecordsList()));
        model.addAttribute("list_order", orderService.getOrderList());
        trigger = "";
        return "User_menu";
    }

    @PostMapping("/add_record")
    private String add_record(@RequestParam int id_order, @RequestParam int id_employee, @RequestParam String fio_employee,
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                              LocalDateTime date
    ) {
        recordsService.addRecords(id_order, id_employee, fio_employee, date);
        trigger = "order";
        return "redirect:/user_room:" + pers_id;
    }

    @GetMapping("/cancel_record:{id}")
    private String cancel_record(@PathVariable(value = "id") Integer id_order) {
        recordsService.deleteById(id_order);

        trigger = "history";
        return "redirect:/user_room:" + pers_id;
    }

}