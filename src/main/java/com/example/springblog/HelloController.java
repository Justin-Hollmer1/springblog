package com.example.springblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {
    @GetMapping("/join")
    public String showJoinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model) {
        model.addAttribute("cohort", "Welcome to " + cohort + "!");
        return "join";
    }
    @GetMapping("/roll-dice")
    public String showRollDice() {
        return "roll_dice";
    }

    @GetMapping("/roll-dice/1")
    @ResponseBody
    public String rollDice1Mapping() {
        if (getRandomNumber(1)) {
            return "You got the dice roll correct!";
        }
        return "Sorry, try again.";
    }

    @GetMapping("/roll-dice/2")
    @ResponseBody
    public String rollDice2Mapping() {
        if (getRandomNumber(1)) {
            return "You got the dice roll correct!";
        }
        return "Sorry, try again.";
    }

    @GetMapping("/roll-dice/3")
    @ResponseBody
    public String rollDice3Mapping() {
        if (getRandomNumber(1)) {
            return "You got the dice roll correct!";
        }
        return "Sorry, try again.";
    }

    @GetMapping("/roll-dice/4")
    @ResponseBody
    public String rollDice4Mapping() {
        if (getRandomNumber(1)) {
            return "You got the dice roll correct!";
        }
        return "Sorry, try again.";
    }

    @GetMapping("/roll-dice/5")
    @ResponseBody
    public String rollDice5Mapping() {
        if (getRandomNumber(1)) {
            return "You got the dice roll correct!";
        }
        return "Sorry, try again.";
    }

    @GetMapping("/roll-dice/6")
    @ResponseBody
    public String rollDice6Mapping() {
        if (getRandomNumber(1)) {
            return "You got the dice roll correct!";
        }
        return "Sorry, try again.";
    }

    public static boolean getRandomNumber(int input) {
        int correctNumber = (int) (Math.random()*(6) + 1);
        System.out.println(correctNumber);
        if (input == correctNumber) {
            return true;
        }
        else {
            return false;
        }
    }
}
