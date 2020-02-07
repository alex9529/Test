package com.Zotero.Zotero.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class LoginController {

    @Controller
    public class LibraryController {

        @GetMapping("/library")
        public String library(@RequestParam(name="id", required=true, defaultValue="0000000") String id, Model model) {
            model.addAttribute("id", id);

            return "library";
        }

    }

}
