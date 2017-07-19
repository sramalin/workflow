package workflow.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sramalin on 31/05/17.
 */

/* Modified by talupu on 10/07/17 to add mapping for create new tickets/users */


@Controller
public class UIController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage() {

        return "index";

    }

    //TODO:REVIEW URL NAMING CONVENTION

    @RequestMapping(value = "/admintasks", method = RequestMethod.GET)
    public String getAdminTasksPage(Model model) {

        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username


        model.addAttribute("LoggedInUser", name);
        return "admin_tasks";

    }

    @RequestMapping(value = "/createtickets", method = RequestMethod.GET)
    public String getCreateTicketPage() {

        return "admin_create_new_ticket_upload_file";

    }

    @RequestMapping(value = "/createusers", method = RequestMethod.GET)
    public String getCreateUserPage() {

        return "admin_create_new_user";

    }

    @RequestMapping(value = "/viewallusers", method = RequestMethod.GET)
    public String getViewAllUsersPage() {

        return "admin_view_users";

    }

    @RequestMapping(value = "/viewalltickets", method = RequestMethod.GET)
    public String getViewAllTicketsPage() {

        return "admin_view_tickets";

    }

    @RequestMapping(value = "/web/user/userhomepage", method = RequestMethod.GET)
    public String getUserHomePage() {

        return "user_get_and_view_tickets";

    }

    @RequestMapping(value = "/web/user/userassignticket", method = RequestMethod.GET)
    public String getAssignTicketPage() {

        return "user_get_single_ticket";

    }

    @RequestMapping(value = "/web/user/userviewtickets", method = RequestMethod.GET)
    public String getUserViewTickets() {

        return "view_single_user";

    }
    @RequestMapping(value = "/mylogin", method = RequestMethod.GET)
    public String getmylogin() {

        return "index";

    }
}

