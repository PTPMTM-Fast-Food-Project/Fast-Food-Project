package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.City;
import com.ecommerce.library.model.Country;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ForgotPasswordToken;
import com.ecommerce.library.service.CityService;
import com.ecommerce.library.service.CountryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ForgotPasswordService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
public class CustomerController {
    private final String SHOP_URL = "http://localhost:9966/shop";

    private final CustomerService customerService;
    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder;
    private final CityService cityService;
    private final ForgotPasswordService forgotPasswordService;

    public CustomerController(CustomerService customerService, CountryService countryService, 
                                PasswordEncoder passwordEncoder, CityService cityService, 
                                ForgotPasswordService forgotPasswordService) {
        this.customerService = customerService;
        this.countryService = countryService;
        this.passwordEncoder = passwordEncoder;
        this.cityService = cityService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customer = customerService.getCustomer(username);
        List<Country> countryList = countryService.findAll();
        List<City> cities = cityService.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countryList);
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "Profile");
        return "customer-information";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                BindingResult result,
                                RedirectAttributes attributes,
                                Model model,
                                Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customer = customerService.getCustomer(username);
        System.out.println(customer.getCountry());
        List<Country> countryList = countryService.findAll();
        List<City> cities = cityService.findAll();
        model.addAttribute("countries", countryList);
        model.addAttribute("cities", cities);
        if (result.hasErrors()) {
            return "customer-information";
        }
        customerService.update(customerDto);
        CustomerDto customerUpdate = customerService.getCustomer(principal.getName());
        attributes.addFlashAttribute("success", "Update successfully!");
        model.addAttribute("customer", customerUpdate);
        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Change password");
        model.addAttribute("page", "Change password");
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            CustomerDto customer = customerService.getCustomer(principal.getName());
            if (passwordEncoder.matches(oldPassword, customer.getPassword())
                    && !passwordEncoder.matches(newPassword, oldPassword)
                    && !passwordEncoder.matches(newPassword, customer.getPassword())
                    && repeatPassword.equals(newPassword) && newPassword.length() >= 5) {
                customer.setPassword(passwordEncoder.encode(newPassword));
                customerService.changePass(customer);
                attributes.addFlashAttribute("success", "Your password has been changed successfully!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "Your password is wrong");
                return "change-password";
            }
        }
    }

    // Forgot Password with sending email
    @GetMapping("/forgot-password")
    public String getPasswordRequest(Model model) {
        model.addAttribute("title", "Forgot password");
        model.addAttribute("page", "Forgot password");
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String savePasswordRequest(@RequestParam String username, Model model) {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            model.addAttribute("error", "This email is not registered");
            return "forgot-password";
        }

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setExpireDateTime(forgotPasswordService.expireDateTimeRange());
        forgotPasswordToken.setToken(forgotPasswordService.generateToken());
        forgotPasswordToken.setCustomer(customer);
        forgotPasswordToken.setUsed(false);

        forgotPasswordService.createForgotPasswordToken(forgotPasswordToken);

        String emailLink = SHOP_URL + "/reset-password?token=" + forgotPasswordToken.getToken();

        try {
            forgotPasswordService.sendEmail(customer.getUsername(), "Password Reset Link", emailLink);
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email, please try again");
            return "forgot-password";
        }

        return "redirect:/forgot-password?success";
    }

    @GetMapping("/reset-password")
    public String getResetPassword(Model model, @RequestParam("token") String token, HttpSession session) {
        session.setAttribute("token", token);
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.getForgotPasswordTokenByToken(token);
        model.addAttribute("title", "Reset password");
        model.addAttribute("page", "Reset password");
        model.addAttribute("customerDto", new CustomerDto());
        return forgotPasswordService.checkValidity(forgotPasswordToken, model);
    }

    @PostMapping("/reset-password")
    public String saveResetPassword(@Valid @ModelAttribute("customerDto") CustomerDto customerDto, 
                                    BindingResult result,
                                    HttpSession session,
                                    Model model) {
        if (result.hasErrors()) {
            return "reset-password";
        }

        if (!customerDto.getPassword().equals(customerDto.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("title", "Reset password");
            model.addAttribute("page", "Reset password");
            return "reset-password";
        }

        String token = (String) session.getAttribute("token");
        String password = passwordEncoder.encode(customerDto.getPassword());

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.getForgotPasswordTokenByToken(token);
        Customer customer = forgotPasswordToken.getCustomer();
        customerService.resetPassword(customer, password);
        forgotPasswordToken.setUsed(true);
        forgotPasswordService.createForgotPasswordToken(forgotPasswordToken);

        model.addAttribute("title", "Reset password");
        model.addAttribute("page", "Reset password");
        model.addAttribute("message", "You have been successfully reset password");
        return "reset-password";
    }
}
