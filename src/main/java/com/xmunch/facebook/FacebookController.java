package com.xmunch.facebook;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xmunch.facebook.model.FacebookAgent;

@Controller
@RequestMapping("/")
public class FacebookController {

    private Facebook facebook;
    private FacebookAgent fa;
    
    @Inject
    public FacebookController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping(method=RequestMethod.GET)
    public String helloFacebook(Model model) {
        if (!facebook.isAuthorized()) {
            return "redirect:/connect/facebook";
        } else {
        	 this.fa = new FacebookAgent(facebook);	
             return "done";
        }
    }
}
