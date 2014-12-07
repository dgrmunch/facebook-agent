package com.xmunch.facebook;

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.xmunch.facebook.aux.GlobalValues;
import com.xmunch.facebook.model.FacebookAgent;

@Controller
@RequestMapping("/")
public class FacebookController {

	Gson gson = new Gson();
    private Facebook facebook;
    private FacebookAgent fa;
    
    @Inject
    public FacebookController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (!facebook.isAuthorized()) {
            return GlobalValues.FACEBOOK_REDIRECT.get();
        } else {
        	 this.fa = new FacebookAgent(facebook);	
        	 model.addAttribute(GlobalValues.JSON_ATTRIBUTE.get(),gson.toJson(fa.getFriends()));
             return GlobalValues.DONE_TEMPLATE.get();
        }
    }
}
