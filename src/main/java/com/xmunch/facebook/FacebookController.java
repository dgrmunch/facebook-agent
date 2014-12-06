package com.xmunch.facebook;

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xmunch.facebook.model.FacebookAgent;

@Controller
@RequestMapping("/")
public class FacebookController {

    private Facebook facebook;

    @Inject
    public FacebookController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (!facebook.isAuthorized()) {
            return "redirect:/connect/facebook";
        } else {
        	
        	 // Create autonomous agent to browse Facebook
        	 FacebookAgent fa = new FacebookAgent(facebook);
        	 fa.start();

        	 // Basic information to show in the browser
        	 model.addAttribute(facebook.userOperations().getUserProfile());
             return "done";
        }
    }
}
