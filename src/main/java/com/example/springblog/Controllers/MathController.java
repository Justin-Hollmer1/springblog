package com.example.springblog.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MathController {

    @GetMapping("/add/{number1}/and/{number2}")
    @ResponseBody
    public int addTwoNumbers(@PathVariable int number1, @PathVariable int number2) {
        return number1 + number2;
    }

    @GetMapping("/subtract/{number1}/from/{number2}")
    @ResponseBody
    public int subtractTwoNumbers(@PathVariable int number1, @PathVariable int number2) {
        return number1 - number2;
    }

    @GetMapping("/multiply/{number1}/and/{number2}")
    @ResponseBody
    public int multiplyTwoNumbers(@PathVariable int number1, @PathVariable int number2) {
        return number1 * number2;
    }

    @GetMapping("/divide/{number1}/by/{number2}")
    @ResponseBody
    public int DivideTwoNumbers(@PathVariable int number1, @PathVariable int number2) {
        return number1 / number2;
    }

}
